import javax.swing.*;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class ConnectFourView extends JFrame {

    private JPanel mainMenu;                // this is the panel that will be displayed while the game is in the main menu
    private JPanel game;                    // this is the panel that will be displayed while the game is in either the actual game or the custom game

    //all the main menu buttons
    private JButton mainMenuPlay;           // this button is located in the main menu and takes you to the connect four
    private JButton mainMenuCustom;         // this button is located in the main button and takes you to the custom game
    private JButton mainMenuLoad;
    //all buttons in the custom game and the game
    private JButton gameMainMenu;           //  this button is located in both the game and the custom game and will take you back to the main menu
    private JButton gameRedButton;          //  this button is located in both the game and the custom game and will make the next players turn red
    private JButton gameBlueButton;         //  this button is located in both the game and the custom game and will make the next players turn blue
    private JButton gameSaveStateButton;    //  this button saves the state of the game
    private JButton customGameReset;        // this button resets the board and makes it blank
    //the buttons just in the custom game
    private JButton customGameCheckState;   // this button checks if the current state of the game is valid and then displays the error messages
    private JLabel textField;            // this is the label that will display the error message in the custom game

    //this is the board that is displayed on the screen
    private Board board;

    //the names of all the button images
    //main menu buttons
    public static final String mainCustomButtonImageName = "./src/images/customGameButton1.png";
    public static final String mainCustomButtonimageNamePressed = "./src/images/customGameButton3.png";
    public static final String mainPlayButtonImageName = "./src/images/playButton1.png";
    public static final String mainPlayButtonImageNamePressed = "./src/images/playButton3.png";
    public static final String gameMainMenuImageName = "./src/images/mainMenuButton1.png";
    public static final String gameMainMenuImageNamePressed ="./src/images/mainMenuButton3.png";
    public static final String mainLoadSavedStateImageName = "./src/images/loadstate1.png";
    public static final String mainLoadSavedStateImageNamePressed = "./src/images/loadstate2.png";
    //Game buttons
    public static final String saveStateImage = "./src/images/savestate1.png";
    public static final String saveStateImagePressed = "./src/images/savestate2.png";
    //all the names of buttons that exist in both the games
    public static final String gameRedButtonImageName           = "./src/images/red3.png";
    public static final String gameRedButtonImageNamePressed    = "./src/images/red2.png";
    public static final String gameRedButtonImageNameSelected   = "./src/images/red1.png";
    public static final String gameBlueButtonImageName          = "./src/images/blue3.png";
    public static final String gameBlueButtonImageNamePressed   = "./src/images/blue2.png";
    public static final String gameBlueButtonImageNameSelected  = "./src/images/blue1.png";
    public static final String gameSaveButtonImageName          = "./src/images/savestate1.png";
    public static final String gameSaveButtonImageNamePressed   = "./src/images/savestate2.png";
    public static final String customGameResetImageName = "./src/images/reset1.png";
    public static final String customGameResetImageNamePressed = "./src/images/reset2.png";
    //Custom game only
    public static final String customGameCheckStateImageName = "./src/images/checkstate1.png";
    public static final String customGameCheckStateImageNamePressed = "./src/images/checkstate2.png";

    //this is the size of the buttons on the screen
    private static final int buttonWidth = 289;     //stores the width of all the buttons
    private static final int buttonHeight = 74;     //stores the height of all the buttons
    //Size for blue, red and reset buttons
    private static final int smallButton = 100;     //stores the size of the small buttons
    private static final int initialScreenHeight = 400; // these two constants store the dimensions of the screen
    private static final int initialScreenWidth  = 400; // if the client does not specify a value for the screen

    //constructors
    public ConnectFourView(){ this(initialScreenWidth, initialScreenHeight); }
    public ConnectFourView(int width, int height){ this(width, height, 6,7); }//end constructor
    public ConnectFourView(int width, int height, int boardRows, int boardCols){
        //gets rid of java's default positioning system so we have more control and can manually position everything using absolute positioning
        this.getContentPane().setLayout(null);
        this.setLayout(null);

        setupMainMenu(width, height);                   //sets up the main menu panel
        setupGame(width, height, boardRows, boardCols); //sets up the game panel

        game.setVisible(false);     // makes the game invisible
        mainMenu.setVisible(true);  // shows the main menu

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//sets the default close operation
        this.setSize(width, height);//sets the size of the screen
    }

    public void setController(ConnectFourController c){ board.setController(c); }
    //sets up the main menu screen. It adds the corresponding components to the main menu panel
    private void setupMainMenu(int width, int height){
        //create the main menu panel
        mainMenu = new JPanel();            // creates an empty panel
        mainMenu.setSize(width, height);    // sets its size equal to the size and height of the screen
        mainMenu.setLocation(0,0);          // sets the location of the panel
        mainMenu.setLayout(null);           // removes the default layout manager, allowing us to position everything manually and giving us more control


        //create the two buttons
        mainMenuCustom = createButton(mainCustomButtonImageName, width/2-buttonWidth/2, height/4*2-buttonHeight/2, buttonWidth, buttonHeight, mainMenu, mainCustomButtonimageNamePressed);
        mainMenuPlay = createButton(mainPlayButtonImageName, width/2-buttonWidth/2, height/4-buttonHeight/2, buttonWidth, buttonHeight, mainMenu, mainPlayButtonImageNamePressed);
        mainMenuLoad = createButton(mainLoadSavedStateImageName, width/2-buttonWidth/2, height/4*3-buttonHeight/2, buttonWidth, buttonHeight, mainMenu, mainLoadSavedStateImageNamePressed);

        //add the main menu to the screen
        this.add(mainMenu);
    }//end setup board
    private void setupGame(int width, int height, int boardRows, int boardCols) {
        game = new JPanel();            // makes an empty panel
        game.setSize(width, height);    // sets the size of the game
        game.setLocation(0,0);          // sets the location of the panel
        game.setLayout(null);           // removes the default layout

        //adding all the buttons that are in both the game and the game screen
        gameMainMenu         = createButton(gameMainMenuImageName, 0,0, buttonWidth, buttonHeight, game, gameMainMenuImageNamePressed);
        gameRedButton        = createButton(gameRedButtonImageName, 0,0, smallButton, smallButton, game, gameRedButtonImageNamePressed);
        gameBlueButton       = createButton(gameBlueButtonImageName, 0,0, smallButton, smallButton, game, gameBlueButtonImageNamePressed);
        gameSaveStateButton  = createButton(gameSaveButtonImageName, 0,0, buttonWidth, smallButton, game, gameSaveButtonImageNamePressed);

        //buttons for the custom game
        customGameReset      = createButton(customGameResetImageName, 0,0, smallButton, smallButton, game, customGameResetImageNamePressed);
        customGameCheckState = createButton(customGameCheckStateImageName, 0,0, buttonWidth, buttonHeight, game, customGameCheckStateImageNamePressed);
        textField = new JLabel("");
        textField.setOpaque(false);
        textField.setSize(width - gameSaveStateButton.getWidth(), 110);
        textField.setLocation((width - gameSaveStateButton.getWidth()) / 2, 0);
        textField.setHorizontalAlignment(JTextField.CENTER);
        //buttons for the game

        board = new Board(boardCols, boardRows); //create the board

        board.setBounds( //sets the bounds of the board, meaning the area it can be located
                this.getWidth() / 2 - board.getWidthOfBoard() / 2,  //x value
                this.getHeight() / 2 - board.getHeightOfBoard()/2,  // y value
                board.getWidthOfBoard(),                            // width
                board.getHeightOfBoard());                          // height
        board.validate();       // make sure the board's state is valid
        board.repaint();        // draw the board on the screen
        game.add(textField); // add the error message textfield
        game.add(board);        // add the board to the game

        this.add(game);         //adds the game to the screen
        game.setVisible(false); // makes the game panel invisible so that we can not see it when the game starts up
    }

    public void switchScreen(ConnectFourModel.GameState gameState){
        if(gameState == ConnectFourModel.GameState.MainMenu){
            game.setVisible(false);     // make the game panel invisible
            mainMenu.setVisible(true);  // make the main menu invisible
            return;                     // end the function
        }

        mainMenu.setVisible(false); // make the main menu invisible
        game.setVisible(true);      // show the game screen

        //show this button if the current state is custom game
        //and reposition the text field depending on its position
        if(gameState == ConnectFourModel.GameState.Game){
            textField.setSize(getWidth(), 110);
            textField.setLocation(0, 0);


            Font labelFont = textField.getFont();
            String labelText = textField.getText();
            int stringWidth = textField.getFontMetrics(labelFont).stringWidth(labelText);
            int componentWidth = textField.getWidth();
            // Find out how much the font can grow in width.
            double widthRatio = (double)componentWidth / (double)stringWidth;
            int newFontSize = (int)(labelFont.getSize() * widthRatio);
            // Pick a new font size so it will not be larger than the height of label.
            int fontSizeToUse = Math.min(newFontSize, (int)(textField.getHeight()*0.85));
            // Set the label's font size to the newly determined size.
            textField.setFont(new Font(labelFont.getName(), Font.PLAIN, fontSizeToUse));
            customGameCheckState.setVisible(false);
        }else{
            textField.setFont(new Font(textField.getFont().getName(), Font.PLAIN, 15));
            textField.setSize(getWidth() - gameSaveStateButton.getWidth(), 110);
            textField.setLocation((getWidth() - gameSaveStateButton.getWidth()) / 2, 0);
            customGameCheckState.setVisible(true);
        }

    }//end function of the switch screen

    @Override
    public void paint(Graphics g){
        //this method will be called when the board is resized and when the screen loads
        super.paint(g); // repaint the screen

        //since this function gets called when the screen is resized we need to adjust the position and size of all the panels or else resizing would not work
        //the board might have been moved or resized so we need to resize and reposition all the buttons
        mainMenu.setSize(this.getSize());   //updates thes size of the game
        game.setSize(this.getSize());       //updates the size of the game
        //all the buttons are with respect to the scale variable. what this does is that it allows
        // each component to resize depending on this size variable, And if we make this variable adjust
        // its size based on the screen size the entire screen should scale accordingly, But currently the image on the buttons
        // does not scale so it does not look visually so we kept it 1.0
        float scale = 1.0f;
        //position all the values
        mainMenuPlay.setSize((int) (buttonWidth * scale), (int) (buttonHeight * scale));
        mainMenuCustom.setSize((int)(buttonWidth*scale), (int)(buttonHeight*scale));
        mainMenuLoad.setSize((int)(buttonWidth * scale), (int) (buttonHeight * scale));
        gameMainMenu.setSize((int)(buttonWidth * scale), (int) (buttonHeight * scale));
        customGameCheckState.setSize((int)(buttonWidth * scale), (int) (buttonHeight * scale));
        gameSaveStateButton.setSize((int)((buttonWidth)* scale), (int)(buttonHeight*scale));
        customGameCheckState.setSize((int)(buttonWidth * scale), (int) (buttonHeight * scale));
        scale = 0.7f;
        gameRedButton.setSize((int) (smallButton * scale), (int) (smallButton * scale));
        gameBlueButton.setSize((int)(smallButton * scale), (int) (smallButton * scale));
        customGameReset.setSize((int) (smallButton * scale), (int) (smallButton * scale));

        //the boundary represents the distance from the edge of the screen to the components on the screen,
        //by changing this variable we can increase or decrease the distance for all the buttons from the edge
        int boundary = 10;
        //adjust all the button's position
        mainMenuPlay.setLocation(this.getWidth() / 2 - mainMenuPlay.getWidth() / 2, this.getHeight() / 4 - mainMenuPlay.getHeight() / 2);//middle top
        mainMenuCustom.setLocation(this.getWidth()/2 - mainMenuCustom.getWidth()/2, this.getHeight()/4*2 -mainMenuCustom.getHeight()/2);//middle bottom
        mainMenuLoad.setLocation(this.getWidth()/2 - mainMenuCustom.getWidth()/2, this.getHeight()/4*3 -mainMenuCustom.getHeight()/2);
        gameMainMenu.setLocation(boundary, this.getHeight()-gameMainMenu.getHeight()-20 - boundary);//bottom left
        gameSaveStateButton.setLocation(this.getWidth() - gameSaveStateButton.getWidth() - boundary, this.getHeight() - gameSaveStateButton.getHeight() - 20 - boundary);
        int freeSpaceSides  = this.getWidth()/2 - board.getWidth();//this variable represents the amount of free space between the board and the end of the screen
        gameRedButton.setLocation(freeSpaceSides/2 - gameRedButton.getWidth()/2, getHeight()/2-gameBlueButton.getHeight()/2);
        gameBlueButton.setLocation(this.getWidth()-freeSpaceSides/2-gameBlueButton.getWidth()/2, getHeight()/2-gameBlueButton.getHeight()/2);
        customGameReset.setLocation(this.getWidth() / 2 - customGameReset.getWidth() / 2, this.getHeight() - customGameReset.getHeight()-40);
        customGameCheckState.setLocation(boundary,boundary);

        board.setVisible(true);//make the board visible
        board.setBounds( // set the bounds of the screen
                this.getWidth() / 2 - board.getWidthOfBoard() / 2,
                this.getHeight() / 2 - board.getHeightOfBoard()/2,
                board.getWidthOfBoard(),
                board.getHeightOfBoard());

        board.repaint();// repaints the board
    }

    // this class creates a button and positions it at the location (x,y) with the width and height of the input. adds the button to the parent and sets its image to the filename equal to name in the images folder
    public JButton createButton(String name, int x, int y, JPanel parent, String pressedName){ return createButton(name, x,y, buttonWidth, buttonHeight, parent, pressedName); }
    public JButton createButton(String name, int x, int y, int width, int height, JPanel parent, String pressedName) {
        JButton newButton = new JButton(new ImageIcon(name)); // creates the button, with the image name that is given
        newButton.setPressedIcon(new ImageIcon(pressedName)); // makes a new button

        //if the image name that you have given does not exist then use the default image,
        // if the image given exists then remove the default button graphics
        if(newButton.getIcon().toString().length() > 2){
            //removes the default button graphics
            newButton.setOpaque(false);
            newButton.setContentAreaFilled(false);
            newButton.setBorderPainted(false);
            newButton.setFocusPainted(false);
        }else{
            //inform the user that the image can not be found
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
        customGameReset.addActionListener(listenForButton);
        gameBlueButton.addActionListener(listenForButton);
        gameRedButton.addActionListener(listenForButton);

        customGameCheckState.addActionListener(listenForButton);
        mainMenuLoad.addActionListener(listenForButton);
        gameSaveStateButton.addActionListener(listenForButton);

        //make the listener for the game buttons
        board.addMouseListener(mouseListener);

    }//end calculate listener

    // this function adjusts the images of the buttons so they change to their correct version to show the current button highlighted
    public void setTurn(ConnectFourModel.Slot currentTurn){
        //this function gets called to set the turn
        if(currentTurn == ConnectFourModel.Slot.Blue){
            //update button images
            gameBlueButton.setIcon(new ImageIcon(gameBlueButtonImageNameSelected));
            gameRedButton.setIcon(new ImageIcon(gameRedButtonImageName));
        }else if(currentTurn == ConnectFourModel.Slot.Red){
            //update button images
            gameBlueButton.setIcon(new ImageIcon(gameBlueButtonImageName));
            gameRedButton.setIcon(new ImageIcon(gameRedButtonImageNameSelected));
        }
        else {
            //in case if we are using this function wrong, inform the client
            System.out.println("You are trying make the current turn become EMPTY's, next time set it to blue or red");
        }//end else-if block
    }//end the function

    //PURPOSE: displays and message on the screen of the text messageToDisplay that will be passed in as a parameter
    public void displayMessageAsPopup(String messageToDisplay){ JOptionPane.showMessageDialog(this, messageToDisplay); }
    //this function displays the following error in the error message text field
    public void setMessageText(String error) { textField.setText(error); }

    //all these functions get passed to the board
    //the reason the controller did not directly call the method from the board is that by doing so would remove the abstraction of the view class
    //the controller should not need to know how the view class works to work with it
    public Point getBoardCoordinateOfPoint(Point point){ return board.getBoardCoordinateOfPoint(point); }
    public void insertDisc(Point point, ConnectFourModel.Slot type){ board.insertDisc(point, type); }
    public boolean isAnimating(){ return board.isAnimating(); }
    public void setBoard(ConnectFourModel.Slot[][] boardConfig){ board.setBoard(boardConfig); } //sets the board configuration
    public void adjustBoard(ConnectFourModel.Slot[][] newBoardConfiguration){ board.setBoard(newBoardConfiguration); } // se
}//end class