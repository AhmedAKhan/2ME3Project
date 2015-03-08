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
    private JTextArea errorMessage;

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

    //sets up the main menu screen. It adds the corresponding components to the main menu panel
    private void setupMainMenu(int width, int height){
        //create the main menu panel
        mainMenu = new JPanel();
        mainMenu.setSize(width, height);
        mainMenu.setLocation(0,0);
        mainMenu.setLayout(null);
        //make the background

        //create the two buttons

        mainMenuCustom = createButton(mainCustomButtonImageName, width/2-buttonWidth/2, height/3*2-buttonHeight/2, buttonWidth, buttonHeight, mainMenu);
        mainMenuPlay = createButton(mainPlayButtonImageName, width/2-buttonWidth/2, height/3-buttonHeight/2, buttonWidth, buttonHeight, mainMenu);

        //add the main menu to the screen
        this.add(mainMenu);
    }//end setup board
    private void setupGame(int width, int height, int boardRows, int boardCols) {
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
        board = new Board(boardCols, boardRows);
        board.setBounds(
                this.getWidth() / 2 - board.getWidthOfBoard() / 2,
                this.getHeight() / 2 - board.getHeightOfBoard()/2,
                board.getWidthOfBoard(),
                board.getHeightOfBoard());
        board.validate();
        board.repaint();
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
        int freeSpaceSides  = this.getWidth()/2 - board.getWidth();//this variable represents the amount of free space between the board and the end of the screen
        gameRedButton.setLocation(freeSpaceSides/2 - gameRedButton.getWidth()/2, getHeight()/2-gameBlueButton.getHeight()/2);
        gameBlueButton.setLocation(this.getWidth()-freeSpaceSides/2-gameBlueButton.getWidth()/2, getHeight()/2-gameBlueButton.getHeight()/2);
        customGameReset.setLocation(this.getWidth() / 2 - customGameReset.getWidth() / 2, this.getHeight() - customGameReset.getHeight());
        customGameCheckState.setLocation(this.getWidth()-customGameCheckState.getWidth(), this.getHeight()-customGameCheckState.getHeight());
        //done adjusting the buttons position

        board.setVisible(true);
        board.setBounds(
                this.getWidth() / 2 - board.getWidthOfBoard() / 2,
                this.getHeight() / 2 - board.getHeightOfBoard()/2,
                board.getWidthOfBoard(),
                board.getHeightOfBoard());
        board.repaint();


        System.out.println("board: " + board);


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
        board.addMouseListener(mouseListener);

    }//end calculate listener

    public void setBoard(ConnectFourModel.Slot[][] boardConfig){ board.setBoard(boardConfig); }

    public void setTurn(ConnectFourModel.Slot currentTurn){
        if(currentTurn == ConnectFourModel.Slot.Blue){
            //gameBlueButton.setIcon();
            //gameRedButton.setIcon();
            return;
        }else if(currentTurn == ConnectFourModel.Slot.Red){
            //gameBlueButton.setIcon();
            //gameRedButton.setIcon();
            return;
        }
        //in case if we are using this function wrong, inform the client
        System.out.println("You are trying make the current turn become EMPTY's, next time set it to blue or red");
        return;
    }

    //PURPOSE: displays and message on the screen of the text errorMessage that will be passed in as a parameter
    public void displayErrorMessage(String errorMessage){ JOptionPane.showMessageDialog(this, errorMessage); }

    public void setError(String error) { errorMessage.setText(error); }
}//end class