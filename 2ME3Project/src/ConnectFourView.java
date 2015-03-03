

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;

import javax.swing.event.MouseInputListener;

import java.awt.Point;
/**
 * Created by Ahmed Khan, Saim Malik, Zayan Imtiaz, Aleem Ul Haq, Sergio Agraz.
 */
public class ConnectFourView extends JFrame {
    
    //this JPanel holds all the data
    private int diameterOfDisk = 30;			//represents the diameter of one disk
    private int spaceBetweenSlots = 7;		    //represents the space between the disk

    //holds all the buttons
    private Button customGameButton;
    private Button newGameButton;
    private Button mainMenuButton;
    private Button redTurnButton;
    private Button blueTurnButton;
    private Button CheckStateButton;
    private Button resetBoardButton;            //added Feb 26, 2015 sergio cosman
    private int numberOfRows;                   //holds the number of rows


    private JTextArea errorMessage;             //is the textarea that displays the error
    private JLabel turnLabel;                   //displays the turn
    
    private ConnectFourModel.GameState state;   //holds a reference to th game state
    private ConnectFourModel.Slot[][] config;   //holds the configuration of the game
    
    //constructor code: just initializes everything
    public ConnectFourView(ConnectFourModel.Slot[][] b, ConnectFourModel.GameState gs){
        setupBoard(b, gs, 400,400);//sets up the board
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//makes the window closable
        this.setSize(400, 400);//sets the size of the screen
    }//end constructor
    public ConnectFourView(int width, int height, ConnectFourModel.Slot[][] b, ConnectFourModel.GameState gs){
        setupBoard(b, gs, width, height);//sets the size of the screen
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//sets the default close operation
        this.setSize(width, height);//sets the size of the screen
    }//end constructor

    //PURPOSE: sets up the game screen
    private void setupBoard(ConnectFourModel.Slot[][] b, ConnectFourModel.GameState gs, int width, int height){
        //add everything in the panel for example all the buttons and images
    	state = gs;//holds the game state
    	
    	//initializes all the buttons
        newGameButton = new Button("New Game");
        customGameButton = new Button("Custom Game");
        mainMenuButton = new Button("Main Menu");
        redTurnButton = new Button("RED");
        blueTurnButton = new Button("BLUE");
        CheckStateButton = new Button("Check State");
        resetBoardButton = new Button ("Reset");            //added Feb 26, 2015 sergio cosman        
        turnLabel = new JLabel("not yet set");
        turnLabel.setHorizontalAlignment(SwingConstants.CENTER);
        
        //makes labels (indicates if blue or red piece is selected)
        errorMessage = new JTextArea("");
    	errorMessage.setEditable(false);
    	errorMessage.setBackground(new Color(238,238,238));

    	this.setLayout(null); // removes the default label
    	
        //adds buttons to the screen
        this.add(turnLabel);
        this.add(customGameButton);
        this.add(newGameButton);
        this.add(mainMenuButton);        
        this.add(redTurnButton);
        this.add(blueTurnButton);
        this.add(CheckStateButton);
        this.add(resetBoardButton);                         //added Feb 26, 2015 sergio cosman
        this.add(turnLabel);
        
        //adds labels to the screen
        this.add(errorMessage);
        
        //set the size of all the button
        int widthOfButtons = 150;
        int heightOfButtons = 50;
        newGameButton.setSize(widthOfButtons, heightOfButtons);
        customGameButton.setSize(widthOfButtons, heightOfButtons);
        mainMenuButton.setSize(widthOfButtons, heightOfButtons);
        redTurnButton.setSize(widthOfButtons/2,heightOfButtons);
        blueTurnButton.setSize(widthOfButtons/2,heightOfButtons);
        CheckStateButton.setSize(widthOfButtons, heightOfButtons);
        resetBoardButton.setSize(widthOfButtons, heightOfButtons);              //added Feb 26, 2015 sergio cosman
        turnLabel.setSize(widthOfButtons, heightOfButtons);        

        config = b;//set the configuration of the board
    }
    
    //sets label depending on which color's turn it is
    public void setTurn(ConnectFourModel.Slot turn){
    	turnLabel.setText("Current Turn: " + turn.toString());
    }

    //
    public void setError(String error) { 
    	errorMessage.setText(error);
    }
    
    //PURPOSE: this function makes it the listener for the buttons the controller object
    public void addCalculateListener(ActionListener listenForButton, MouseInputListener listenForMouseEvents){
        //calculateButton.addActionListener(listenForButton);
        this.addMouseListener(listenForMouseEvents);
    	
        //add all the listeners for the buttons
        customGameButton.addActionListener(listenForButton);
        mainMenuButton.addActionListener(listenForButton);
        newGameButton.addActionListener(listenForButton);
        blueTurnButton.addActionListener(listenForButton);
        redTurnButton.addActionListener(listenForButton);
        CheckStateButton.addActionListener(listenForButton);
        resetBoardButton.addActionListener(listenForButton);                        //added Feb 26, 2015 sergio cosman
    }
    
    //PURPOSE: displays and message on the screen of the text errorMessage that will be passed in as a parameter
    public void displayErrorMessage(String errorMessage){ JOptionPane.showMessageDialog(this, errorMessage); }

    //Purpose: if it is in the GameState then it will draw the entire screen
    @Override
    public void paint(Graphics g){
        super.paint(g);
        
        //check what scene you are in. If it is in the main menu then do nothing
        ConnectFourModel.GameState currentGameState = state;
        
        if(currentGameState == ConnectFourModel.GameState.MainMenu){
        	drawMainScreen(g);
        }else if(currentGameState == ConnectFourModel.GameState.CustomGame){
        	drawCustomGameScreen(g);
        }else if(currentGameState == ConnectFourModel.GameState.Game){
        	drawGame(g);
        }
    }
    
    public void adjustBoard(ConnectFourModel.Slot[][] c){ config = c; }
    
    private void drawGame(Graphics g){
    	
    	//hide the main screen
        hideTheMainScreen();
        mainMenuButton.setVisible(true);//add the main menu button
        
        //position the main menu button
        Dimension screenSize = this.getRootPane().getSize();
        mainMenuButton.setLocation(screenSize.width/6 - mainMenuButton.getWidth()/2, screenSize.height/8 * 7);
    }
    
    //PURPOSE: it draws the game screen
    private void drawCustomGameScreen(Graphics g){
        hideTheMainScreen();        

        //get the screen size
        Dimension screenSize = this.getRootPane().getSize();             
        
        //adjust the board size
        //this if so we can resize the board when the screen size is increased or decreased
        //it will be used in 
        //int boardWidth = (int)(screenSize.width/1.8);
        //diameterOfDisk = (int)((boardWidth/config.length)/1.3);
        //spaceBetweenSlots = (int)(boardWidth/config.length/5);
        
        //adjust the buttons
        mainMenuButton.setVisible(true);
        redTurnButton.setVisible(true);
        blueTurnButton.setVisible(true);
        CheckStateButton.setVisible(true);
        resetBoardButton.setVisible(true);                  //added Feb 26, 2015 sergio cosmans
        errorMessage.setVisible(true);        
        turnLabel.setVisible(true);       
        
        //position the buttons
        mainMenuButton.setLocation(screenSize.width/6 - mainMenuButton.getWidth()/2, screenSize.height/8 * 7);              
        redTurnButton.setLocation(getOriginOfBoard().x/2 - redTurnButton.getWidth()/2, screenSize.height/2);
        int offSet = (screenSize.width - getOriginOfBoard().x - getWidthOfBoard())/2;

        blueTurnButton.setLocation(screenSize.width - offSet - blueTurnButton.getWidth()/2, screenSize.height/2);
        CheckStateButton.setLocation(screenSize.width/6 * 5 - CheckStateButton.getWidth()/2, screenSize.height/8 * 7);
        resetBoardButton.setLocation(screenSize.width/6 * 3 - resetBoardButton.getWidth()/2, screenSize.height/8 * 7);      //added Feb 26, 2015 sergio cosman
        int width = 150;
        int height = 50;
        turnLabel.setBounds(screenSize.width/2-width/2, screenSize.height/7*6-height/2, width, height);
        
        int textLength = errorMessage.getText().length();
        width = (int)((textLength > 53)? 340:textLength*6.5);
        height = 90;        
        errorMessage.setBounds(screenSize.width/2-width/2, 0, width, height);
        
        //get the board configuration from the model
        if(config != null){
            numberOfRows = config.length;
            //then draw the model.
            drawTilesFromBoardConfiguration(g, config);
        }
    }
    //PURPOSE: it draws the main menu
    private void drawMainScreen(Graphics g){
        //makes all the buttons invisible
        mainMenuButton.setVisible(false);
        redTurnButton.setVisible(false);
        blueTurnButton.setVisible(false);
        CheckStateButton.setVisible(false);
        resetBoardButton.setVisible(false);                 //added Feb 26, 2015 sergio cosman

        //gets the size
        Dimension screenSize = this.getRootPane().getSize();//boardPanel.getRootPane().getSize();
        
        errorMessage.setVisible(false);
        
        turnLabel.setVisible(false);
        
        //make all the buttons visible
        newGameButton.setVisible(true);
        customGameButton.setVisible(true);
        
        //put them in the correct position
        int yPosition = screenSize.height/3;
        newGameButton.setLocation(screenSize.width/2 -newGameButton.getWidth()/2 , yPosition);
        customGameButton.setLocation(screenSize.width/2 - customGameButton.getWidth()/2, yPosition*2);
    }
        
    //Purpose: this function will be called when the screen changed
    public void switchScreen(ConnectFourModel.GameState currentGameState){
        //the if statement is here so that when we leave the custom game or the normal game screen it will remove any
        //if statement
    	if(currentGameState == ConnectFourModel.GameState.MainMenu) this.errorMessage.setText("");

        //updates the state of the game
        state = currentGameState;
        
    }
    //PURPOSE: hides the menu from he screen
    private void hideTheMainScreen(){
        //hides the main screen
        newGameButton.setVisible(false);
        customGameButton.setVisible(false);
    }

    //Purpose: calls the drawTileAtPosition in a nested loop to tell the drawTileAtPosition(); so all the tiles can be drawn if there is something to draw
    private void drawTilesFromBoardConfiguration(Graphics g, ConnectFourModel.Slot[][] slotConfiguration){      
        //when we draw stuff we put the bottom right position in the function
        
        int k = diameterOfDisk +spaceBetweenSlots;//k represents the distance between two slots
        int numberOfRows = slotConfiguration[0].length;//this is the number of slots the board has  

        //gets the width and height of the board
        int widthOfBoard = getWidthOfBoard();
        int heightOfBoard =  getHeightOfBoard();
        g.setColor(Color.BLACK);//makes the color black
        //draws the board itself, which is just a black rectangle and then we will place the blank disks on top of it
        g.fillRect(getOriginOfBoard().x, getOriginOfBoard().y - (numberOfRows+1)*k, widthOfBoard , heightOfBoard);
        
        //call the drawTileAtPosition an n number of time if it is not empty
        //draws all the disks on the board
        for(int rowCounter = 0; rowCounter < slotConfiguration.length; rowCounter++){
            for(int colCounter = 0; colCounter < slotConfiguration[0].length; colCounter++){
                drawTileAtPosition(g, new Point(colCounter, rowCounter), slotConfiguration[rowCounter][colCounter], slotConfiguration.length);
            }//end row counter loop
        }//end col counter loop
    }//end function
    //PURPOSE: draws one tile at the specified location
    private void drawTileAtPosition(Graphics g, Point pos, ConnectFourModel.Slot type, int numberOfRows){
        //need to convert the old position to new position      
        pos = new Point(pos.x, numberOfRows-1-pos.y);
        pos = getGameCoordinate(pos, pos.y);
        
        //draw the tile depending on the type and position
        Color c = new Color((type==ConnectFourModel.Slot.Blue)? 0:255,(type==ConnectFourModel.Slot.Empty)? 255:0,(type==ConnectFourModel.Slot.Red)? 0:255);
        g.setColor(c);//sets the color of the slot
        g.fillOval(pos.x, pos.y, diameterOfDisk, diameterOfDisk);       
    }//end draw tile at position function
    
    //PURPOSE: gets the origin of the board meaning the bottom left coordinate of the board 
    private Point getOriginOfBoard(){
    	Dimension screenSize = this.getRootPane().getSize();//gets the screen size
        //calculate the bottom left coordinate of the board and return that point
        return new Point(screenSize.width/2 - getWidthOfBoard()/2 ,screenSize.height/2 + getHeightOfBoard()/2);
    }
    
    //PURPOSE: converts the position with respect to the array to the position with respect to the game screen
    public Point getGameCoordinate(Point slotPosition, int numberOfRows){
        //this function will take in the position of the board and return the actual position on the screen
        int sizeOfSlotPlusExtraSpace = diameterOfDisk + spaceBetweenSlots;
        return new Point(slotPosition.x *sizeOfSlotPlusExtraSpace + getOriginOfBoard().x + spaceBetweenSlots, 
                        getOriginOfBoard().y - (slotPosition.y+1)*sizeOfSlotPlusExtraSpace + spaceBetweenSlots);
    }
    //PURPOSE: converts the position to becoming with respect to the array instead of the game screen  
    public Point getBoardCoordinateOfPoint(Point mousePosition){
        //convert the point mousePosition into a tile position where the x represents the column and y represents the row
        Point tilePosition = new Point((mousePosition.x- getOriginOfBoard().x)/(diameterOfDisk + spaceBetweenSlots),
                (getOriginOfBoard().y - mousePosition.y)/(diameterOfDisk + spaceBetweenSlots));
        //this is just making the tilePosition transition from different grids
        return new Point(tilePosition.x, numberOfRows - 1 - tilePosition.y);
    }

    //these two private functions are to get the width and height of the board, they are used to draw the board
    private int getWidthOfBoard(){  return (config[0].length)*(diameterOfDisk +spaceBetweenSlots) + spaceBetweenSlots;}
    private int getHeightOfBoard(){return (numberOfRows)*(diameterOfDisk + spaceBetweenSlots) + spaceBetweenSlots; }
}//end class
