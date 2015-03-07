import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConnectFourView extends JFrame {

    private JPanel mainMenu; // this is the panel that will be displayed while the game is in the main menu
    private JPanel game; // this is the panel that will be displayed while the game is in either the actual game or the custom game

    //all the main menu buttons
    private JButton mainMenuPlay; // this button is located in the main menu and takes you to the connect four
    private JButton mainMenuCustom;// this button is located in the main button and takes you to the custom game

    //all buttons in the custom game and the game
    private JButton gameMainMenu;   // this button is located in both the game and the custom game and will take you back to the main menu
    private JButton gameRedButton;  // this button is located in both the game and the custom game and will make the next players turn red
    private JButton gameBlueButton; // this button is located in both the game and the custom game and will make the next players turn blue

    //the buttons just in the custom game
    private JButton customGameReset; // this button resets the board and makes it blank
    private JButton customGameCheckState; // this button checks if the current state of the game is valid and then displays the error messages

    //buttons just in the game

    //this is the board that is displayed on the screen
    private Board board;

    //the names of all the button images
    public static final String mainCustomButtonImageName = "./src/images/customGameButton1.png";
    public static final String mainPlayButtonImageName = "./src/images/playButton1.png";
    public static final String gameMainMenuImageName = "./src/images/mainMenuButton1.png";
    public static final String gameRedButtonImageName = "";
    public static final String gameBlueButtonImageName = "";
    public static final String customGameResetImageName = "";
    public static final String customGameCheckStateImageName = "";

    //this is the size of the buttons on the screen
    private static final int buttonWidth = 289;
    private static final int buttonHeight = 100;

    private static final int initialScreenHeight = 400;
    private static final int initialScreenWidth = 400;
    //constructor
    public ConnectFourView(){ this(400,400); }
    public ConnectFourView(int width, int height){
        setupMainMenu(width, height);
        setupGame(width, height);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//sets the default close operation
        this.setSize(width, height);//sets the size of the screen
    }//end constructor

    //sets up the main menu screen. It adds the corresponding components to the main menu panel
    private void setupMainMenu(int width, int height){
        //create the main menu panel
        mainMenu = new JPanel();
        mainMenu.setSize(width, height);
        mainMenu.setLocation(0,0);
        mainMenu.setLayout(null);
        //make the background

        //create all the things that are going to be on the main menu

        this.setLayout(null);
        //create the two buttons

        mainMenuCustom = createButton(mainCustomButtonImageName, width/2-buttonWidth/2, height/3*2-buttonHeight/2, buttonWidth, buttonHeight, mainMenu);
        mainMenuPlay = createButton(mainPlayButtonImageName, width/2-buttonWidth/2, height/3-buttonHeight/2, buttonWidth, buttonHeight, mainMenu);

        //add the main menu to the screen
        this.add(mainMenu);
    }//end setup board
    private void setupGame(int width, int height) {
        game = new JPanel();
        game.setSize(width, height);
        game.setLocation(0,0);
        game.setLayout(null);

        //adding all the buttons that are in both the game and the game screen
        gameMainMenu         = createButton(gameMainMenuImageName, 0,0, buttonWidth, buttonHeight, game);
        gameRedButton        = createButton(gameRedButtonImageName, 0,0, buttonWidth, buttonHeight, game);
        gameBlueButton       = createButton(gameBlueButtonImageName, 0,0, buttonWidth, buttonHeight, game);

        //buttons for the custom game
        customGameReset      = createButton(customGameResetImageName, 0,0, buttonWidth, buttonHeight, game);
        customGameCheckState = createButton(customGameCheckStateImageName, 0,0, buttonWidth, buttonHeight, game);

        //buttons for the game


        //create the board
        board = new Board(6,7);
        board.setLocation(this.getWidth()/2,this.getY()/2);
        game.add(board);

        //adds the game to the screen
        this.add(game);
        game.setVisible(false);
    }

    public void switchScreen(ConnectFourModel.GameState gameState){
        System.out.println("gameState: " + gameState);
        if(gameState == ConnectFourModel.GameState.MainMenu){
            game.setVisible(false);
            mainMenu.setVisible(true);
            return;
        }

        mainMenu.setVisible(false);
        game.setVisible(true);
    }//end function of the switch screen

    @Override
    public void paint(Graphics g){
        super.paint(g);
        //the board might have been moved or resized so we need to resize and reposition all the buttons
        mainMenu.setSize(this.getSize());
        game.setSize(this.getSize());
        //THIS WONT WORK BECAUSE THE SIZE OF THE PICTURE DOESNT CHANGE
        float scale = 1.0f; //Math.min(this.getWidth()/initialScreenWidth, this.getHeight()/initialScreenHeight);

        mainMenuPlay.setSize((int)(buttonWidth*scale), (int)(buttonHeight*scale));
        mainMenuCustom.setSize((int)(buttonWidth*scale), (int)(buttonHeight*scale));
        gameMainMenu.setSize((int)(buttonWidth * scale), (int) (buttonHeight * scale));
        customGameCheckState.setSize((int)(buttonWidth * scale), (int) (buttonHeight * scale));
        scale = 0.7f;
        gameRedButton.setSize((int) (buttonWidth * scale), (int) (buttonHeight * scale));
        gameBlueButton.setSize((int)(buttonWidth * scale), (int) (buttonHeight * scale));
        customGameReset.setSize((int) (buttonWidth * scale), (int) (buttonHeight * scale));
        //done adjusting the buttons size

        //adjust their position
        mainMenuPlay.setLocation(this.getWidth() / 2 - mainMenuPlay.getWidth() / 2, this.getHeight() / 3 - mainMenuPlay.getHeight() / 2);//middle top
        mainMenuCustom.setLocation(this.getWidth()/2 - mainMenuCustom.getWidth()/2, this.getHeight()/3*2 -mainMenuCustom.getHeight()/2);//middle bottom

        gameMainMenu.setLocation(0, this.getHeight()-gameMainMenu.getHeight());//bottom left
        gameRedButton.setLocation(0, 0);
        gameBlueButton.setLocation(0, 0);
        customGameReset.setLocation(this.getWidth()/2-customGameReset.getWidth()/2, this.getHeight()-customGameReset.getHeight());
        customGameCheckState.setLocation(this.getWidth()-customGameCheckState.getWidth(), this.getHeight()-gameMainMenu.getHeight());
        //done adjusting the buttons position

    }



    // this class creates a button and positions it at the location (x,y) with the width and height of the input. adds the button to the parent and sets its image to the filename equal to name in the images folder
    public JButton createButton(String name, int x, int y, int width, int height, JPanel parent) {
        //ImageIcon img = new ImageIcon("./src/images/" + name+"1.png");//gets the image of the button
        ImageIcon img = new ImageIcon(name);
        JButton newButton = new JButton(img); // creates the button

        if (!name.equals("")){//if the name is blank just returns that the name is blank
            //removes the default button graphics
            newButton.setOpaque(false);
            newButton.setContentAreaFilled(false);
            newButton.setBorderPainted(false);
            newButton.setFocusPainted(false);
        }else {
            System.out.println("go an empty location for image picture, using the default button");
            newButton.setText("place button label here");
        }

        // this sets the buttons size and location on the screen
        newButton.setSize(width,height);
        newButton.setLocation(x, y);

        parent.add(newButton);//this adds the button to the parent
        return newButton;//returns the newly created button
    }

    //this function assigns the listeners for the button and the screen.
    public void addCalculateListener(ActionListener listenForButton, MouseInputListener mouseListener){

        //make the listener for the main menu buttons
        mainMenuCustom.addActionListener(listenForButton);
        mainMenuPlay.addActionListener(listenForButton);
        gameMainMenu.addActionListener(listenForButton);

        //make the listener for the game buttons
        game.addMouseListener(mouseListener);

    }//end calculate listener

    public void setBoard(ConnectFourModel.Slot[][] boardConfig){
        board.setBoard(boardConfig);
    }


}//end class

/*
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

public class ConnectFourView extends JFrame {

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
}//end class
*/