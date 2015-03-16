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
    private JButton gameMainMenu;   //  this button is located in both the game and the custom game and will take you back to the main menu
    private JButton gameRedButton;  //  this button is located in both the game and the custom game and will make the next players turn red
    private JButton gameBlueButton; //  this button is located in both the game and the custom game and will make the next players turn blue
    private JButton gameSaveStateButton; //  this button saves the state of the game
    private JButton customGameReset; // this button resets the board and makes it blank

    //the buttons just in the custom game
    
    private JButton customGameCheckState; // this button checks if the current state of the game is valid and then displays the error messages
    //private JTextArea errorMessage;
    private JLabel errorMessage;

    //buttons just in the game

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

    //Game buttons - includes Save State button
    //public static final String redColumnSelect = "./src/images/redColumnSelect.png";
    //public static final String blueColumnSelect = "./src/images/blueColumnSelect.png";
    public static final String saveStateImage = "./src/images/savestate1.png";
    public static final String saveStateImagePressed = "./src/images/savestate2.png";

    //all the buttons that exist in both the games
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
    private static final int buttonWidth = 289;
    private static final int buttonHeight = 118;//74*2;
    //Size for blue, red and reset buttons
    private static final int smallButton = 100;	
    //Size for arrows
    //private static final int arrowButton = 30;

    private static final int initialScreenHeight = 400;
    private static final int initialScreenWidth = 400;
    //constructor
    public ConnectFourView(){ this(initialScreenWidth, initialScreenHeight); }
    public ConnectFourView(int width, int height){ this(width, height, 6,7); }//end constructor
    public ConnectFourView(int width, int height, int boardRows, int boardCols){
        this.getContentPane().setLayout(null);
        this.setLayout(null);

        setupMainMenu(width, height);
        setupGame(width, height, boardRows, boardCols);
        game.setVisible(false);
        mainMenu.setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//sets the default close operation
        this.setSize(width, height);//sets the size of the screen
    }

    public void setController(ConnectFourController c){ board.setController(c); }
    //sets up the main menu screen. It adds the corresponding components to the main menu panel
    private void setupMainMenu(int width, int height){
        //create the main menu panel
        mainMenu = new JPanel();
        mainMenu.setSize(width, height);
        mainMenu.setLocation(0,0);
        mainMenu.setLayout(null);
        //make the background

        //create the two buttons
        mainMenuCustom = createButton(mainCustomButtonImageName, width/2-buttonWidth/2, height/3*2-buttonHeight/2, buttonWidth, buttonHeight, mainMenu, mainCustomButtonimageNamePressed);
        mainMenuPlay = createButton(mainPlayButtonImageName, width/2-buttonWidth/2, height/3-buttonHeight/2, buttonWidth, buttonHeight, mainMenu, mainPlayButtonImageNamePressed);

        //add the main menu to the screen
        this.add(mainMenu);
    }//end setup board
    private void setupGame(int width, int height, int boardRows, int boardCols) {
        game = new JPanel();
        game.setSize(width, height);
        game.setLocation(0,0);
        game.setLayout(null);

        //adding all the buttons that are in both the game and the game screen
        gameMainMenu         = createButton(gameMainMenuImageName, 0,0, buttonWidth, buttonHeight, game, gameMainMenuImageNamePressed);
        gameRedButton        = createButton(gameRedButtonImageName, 0,0, smallButton, smallButton, game, gameRedButtonImageNamePressed);
        gameBlueButton       = createButton(gameBlueButtonImageName, 0,0, smallButton, smallButton, game, gameBlueButtonImageNamePressed);
        gameSaveStateButton  = createButton(gameSaveButtonImageName, 0,0, buttonWidth, smallButton, game, gameSaveButtonImageNamePressed);

        //buttons for the custom game
        customGameReset      = createButton(customGameResetImageName, 0,0, smallButton, smallButton, game, customGameResetImageNamePressed);
        customGameCheckState = createButton(customGameCheckStateImageName, 0,0, buttonWidth, buttonHeight, game, customGameCheckStateImageNamePressed);
        //errorMessage         = new JTextArea("this is where the error message goes");
        errorMessage = new JLabel("");
        errorMessage.setOpaque(false);
        errorMessage.setSize(width- gameSaveStateButton.getWidth(),100);
        errorMessage.setLocation((width-gameSaveStateButton.getWidth())/2,0);
        errorMessage.setHorizontalAlignment(JTextField.CENTER);
        //buttons for the game

        //create the board
        board = new Board(boardCols, boardRows);
        board.setBounds(
                this.getWidth() / 2 - board.getWidthOfBoard() / 2,
                this.getHeight() / 2 - board.getHeightOfBoard()/2,
                board.getWidthOfBoard(),
                board.getHeightOfBoard());
        board.validate();
        board.repaint();
        game.add(errorMessage);
        game.add(board);

        //adds the game to the screen
        this.add(game);
        game.setVisible(false);
    }

    public void switchScreen(ConnectFourModel.GameState gameState){
        if(gameState == ConnectFourModel.GameState.MainMenu){
            game.setVisible(false);
            mainMenu.setVisible(true);
            return;
        }
        System.out.println("gameState: " + gameState);
        mainMenu.setVisible(false);
        game.setVisible(true);


        customGameCheckState.setVisible(gameState.equals(ConnectFourModel.GameState.Game)? false:true);

    }//end function of the switch screen

    @Override
    public void paint(Graphics g){
        super.paint(g);

        //the board might have been moved or resized so we need to resize and reposition all the buttons
        mainMenu.setSize(this.getSize());
        game.setSize(this.getSize());
        float scale = 1.0f;//Math.min(((float)this.getWidth())/initialScreenWidth, ((float)this.getHeight())/initialScreenHeight);
        mainMenuPlay.setSize((int) (buttonWidth * scale), (int) (buttonHeight * scale));
        mainMenuCustom.setSize((int)(buttonWidth*scale), (int)(buttonHeight*scale));
        gameMainMenu.setSize((int)(buttonWidth * scale), (int) (buttonHeight * scale));
        customGameCheckState.setSize((int)(buttonWidth * scale), (int)(74*scale));
        gameSaveStateButton.setSize((int)((buttonWidth)* scale), (int)(buttonHeight*scale));
        scale = 0.7f;
        gameRedButton.setSize((int) (smallButton * scale), (int) (smallButton * scale));
        gameBlueButton.setSize((int)(smallButton * scale), (int) (smallButton * scale));
        customGameReset.setSize((int) (smallButton * scale), (int) (smallButton * scale));
        //done adjusting the buttons size

        //adjust their position
        int boundary = 10;
        mainMenuPlay.setLocation(this.getWidth() / 2 - mainMenuPlay.getWidth() / 2, this.getHeight() / 3 - mainMenuPlay.getHeight() / 2);//middle top
        mainMenuCustom.setLocation(this.getWidth()/2 - mainMenuCustom.getWidth()/2, this.getHeight()/3*2 -mainMenuCustom.getHeight()/2);//middle bottom

        gameMainMenu.setLocation(0+boundary, this.getHeight()-gameMainMenu.getHeight()-boundary);//bottom left
        gameSaveStateButton.setLocation(this.getWidth() - gameSaveStateButton.getWidth() - boundary, this.getHeight() - gameSaveStateButton.getHeight() - boundary);
        int freeSpaceSides  = this.getWidth()/2 - board.getWidth();//this variable represents the amount of free space between the board and the end of the screen
        gameRedButton.setLocation(freeSpaceSides/2 - gameRedButton.getWidth()/2, getHeight()/2-gameBlueButton.getHeight()/2);
        gameBlueButton.setLocation(this.getWidth()-freeSpaceSides/2-gameBlueButton.getWidth()/2, getHeight()/2-gameBlueButton.getHeight()/2);
        customGameReset.setLocation(this.getWidth() / 2 - customGameReset.getWidth() / 2, this.getHeight() - customGameReset.getHeight()-45);
        customGameCheckState.setLocation(boundary,boundary);
        //done adjusting the buttons position

        board.setVisible(true);
        board.setBounds(
                this.getWidth() / 2 - board.getWidthOfBoard() / 2,
                this.getHeight() / 2 - board.getHeightOfBoard()/2,
                board.getWidthOfBoard(),
                board.getHeightOfBoard());

        board.repaint();
    }

    // this class creates a button and positions it at the location (x,y) with the width and height of the input. adds the button to the parent and sets its image to the filename equal to name in the images folder
    public JButton createButton(String name, int x, int y, JPanel parent, String pressedName){ return createButton(name, x,y, buttonWidth, buttonHeight, parent, pressedName); }
    public JButton createButton(String name, int x, int y, int width, int height, JPanel parent, String pressedName) {
        //ImageIcon img = new ImageIcon("./src/images/" + name+"1.png");//gets the image of the button
        JButton newButton = new JButton(new ImageIcon(name)); // creates the button
        newButton.setPressedIcon(new ImageIcon(pressedName));

        if(newButton.getIcon().toString().length() > 2){
            //removes the default button graphics
            newButton.setOpaque(false);
            newButton.setContentAreaFilled(false);
            newButton.setBorderPainted(false);
            newButton.setFocusPainted(false);
        }else{
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

        //make the listener for the game buttons
        board.addMouseListener(mouseListener);

    }//end calculate listener

    public void setTurn(ConnectFourModel.Slot currentTurn){
        if(currentTurn == ConnectFourModel.Slot.Blue){
            gameBlueButton.setIcon(new ImageIcon(gameBlueButtonImageNameSelected));
            gameRedButton.setIcon(new ImageIcon(gameRedButtonImageName));
            return;
        }else if(currentTurn == ConnectFourModel.Slot.Red){
            gameBlueButton.setIcon(new ImageIcon(gameBlueButtonImageName));
            gameRedButton.setIcon(new ImageIcon(gameRedButtonImageNameSelected));
            return;
        }
        //in case if we are using this function wrong, inform the client
        System.out.println("You are trying make the current turn become EMPTY's, next time set it to blue or red");
        return;
    }
    public void setBoard(ConnectFourModel.Slot[][] boardConfig){ board.setBoard(boardConfig); }
    public void adjustBoard(ConnectFourModel.Slot[][] newBoardConfiguration){ board.setBoard(newBoardConfiguration); }

    //PURPOSE: displays and message on the screen of the text errorMessage that will be passed in as a parameter
    public void displayMessage(String errorMessage){ JOptionPane.showMessageDialog(this, errorMessage); }

    public Point getBoardCoordinateOfPoint(Point point){ return board.getBoardCoordinateOfPoint(point); }

    public void setError(String error) { errorMessage.setText(error); }
}//end class