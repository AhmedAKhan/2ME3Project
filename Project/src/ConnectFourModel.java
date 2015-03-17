
import java.util.Arrays;
import java.util.Random;
import java.awt.Point;
/**
 * Created by Created by Ahmed Khan, Saim Malik, Zayan Imtiaz, Aleem Ul Haq, Sergio Agraz.
 */
public class ConnectFourModel {

    /**
     * this is an enumerated type called slot.
     * We hold the positions of the tiles in an array and instead of having them
     * be 0, 1 ,2 we made an enumerated type because now the code is easier to read
     * it can have values of empty, red, or blue
     */
    enum Slot {
        Empty,
        Red,
        Blue
    }

    /**
     * The game state will represent all the possible stages the game can possible be in
     */
    enum GameState {
        MainMenu, CustomGame, Game
    }
    
    enum GameProgress {
    	tieGame, blueWon, redWon, inProgess;
    }

    private GameState gameState;
    private Slot[][] boardConfiguration;
    private int turnCount = 1;
    private Slot currentTurn;
    private String errorMessage = "";
    private boolean blueWins = false, redWins = false;
    
    private int rowSize;

    public ConnectFourModel(int rows, int columns) {
        
    	if (rows < 4 || columns < 4) {
            System.out.println("Game board must be at least be of size 4x4. Board "
                    + " created with default values.");
            rows = 4;
            columns = 4;
        }
    	rowSize = columns;
        gameState = GameState.MainMenu;
        Random rand = new Random();
        currentTurn = (rand.nextFloat() < 0.5) ? Slot.Red : Slot.Blue;
        this.boardConfiguration = new Slot[rows][columns];
        this.resetConfiguration();
    }//end constructor
    
    //Determines how many columns there are (the length of a row = # of columns)
    public int getRowSize() {
    	return rowSize;
    }
    
//    public boolean checkBoardConfiguration(){ return checkBoardConfiguration(boardConfiguration); }
    public boolean checkBoardConfiguration(){    	
    	this.errorMessage = "<html>";
    	/* Checking the parameter for errors */
    	if (boardConfiguration.length != boardConfiguration.length) {
    		System.out.println("Invalid number of rows!");
    	}
    	if (boardConfiguration[0].length != boardConfiguration[0].length) {    	
    		System.out.println("Invalid number of columns!");
    	}
    	
    	/* Check if there's too many of either discs */
    	if (this.getBlueDiscsCount() > this.getRedDiscsCount() + 1) {
    		this.errorMessage += "ERROR: Too many blue dics on the board<br>";//"ERROR: Too many blue dics on the board\n";
    		System.out.println("Too many blue");
    	} else if (this.getBlueDiscsCount() + 1 < this.getRedDiscsCount()) {
    		this.errorMessage += "ERROR: Too many red dics on the board<br>";//"ERROR: Too many red dics on the board\n";
    		System.out.println("Too many red");
    		//return false;
    	}
    	
    	/* Check for floating discs */
    	for (int i = 0; i < boardConfiguration.length; i++) {
			for (int j = 0; j < boardConfiguration[0].length; j++) {
				if (i+1 < boardConfiguration.length) {
					if (!boardConfiguration[i][j].equals(Slot.Empty) && 
							boardConfiguration[i+1][j].equals(Slot.Empty)) {
						
						if (boardConfiguration[i][j].equals(Slot.Red)) {
							this.errorMessage += "ERROR: Red floating disc detected "
									+ "at row: " + i + " column: " + j + "<br>";
						} else if (boardConfiguration[i][j].equals(Slot.Blue)) {
							this.errorMessage += "ERROR: Blue floating disc detected "
									+ "at row: " + i + " column: " + j + "<br>";
						}
						
						System.out.println("Floating");
					}
				}
				
			}
		}
    	
    	/* Check if there are four discs in a row of the same color */
    	if (this.getWinState()) {
    		this.errorMessage += "ERROR: Four " + currentTurn.toString() + " discs "
    				+ "are together<br>";
    		System.out.println("Four discs");
    	}
        errorMessage += "</html>";
    	if(!this.errorMessage.equals("<html></html>"))
    		return false;
    	
		/* Switch turns after a valid move is made */
        switchTurns();

        return true;
    }
    
    /** PRIVATE: Switch turns **/
    private void switchTurns() { this.turnCount = 3 - this.turnCount; }
    
    /** PRIVATE: Count the number of blue discs inside the configuration **/
    private int getBlueDiscsCount() {
    	int blue = 0;
    	for (int i = 0; i < boardConfiguration.length; i++) {
			for (int j = 0; j < boardConfiguration[0].length; j++) {
				if (boardConfiguration[i][j].equals(Slot.Blue)) blue++;
			}
		}
    	return blue;
    }
    
    /** PRIVATE: Count the number of red discs inside the configuration **/
    private int getRedDiscsCount() {
    	int red = 0;
    	for (int i = 0; i < boardConfiguration.length; i++) {
			for (int j = 0; j < boardConfiguration[0].length; j++) {
				if (boardConfiguration[i][j].equals(Slot.Red)) red++;
			}
		}
    	return red;
    }
    
    /** PRIVATE: Check if the game is in a winning state **/
    public boolean getWinState() {
    	boolean horizontal=false, vertical=false, diagLRD=false, diagRLD=false;
    	for (int i = 0; i < boardConfiguration.length; i++) {
			for (int j = 0; j < boardConfiguration[0].length; j++) {
				if (boardConfiguration[i][j].equals(Slot.Red)) {
					vertical = checkRed(i+1,j) 
							&& checkRed(i+2,j)
							&& checkRed(i+3,j);
					horizontal = checkRed(i,j+1) 
							&& checkRed(i,j+2)
							&& checkRed(i,j+3);
					diagLRD = checkRed(i+1,j+1) 
							&& checkRed(i+2,j+2)
							&& checkRed(i+3,j+3);
					diagRLD = checkRed(i+1,j-1) 
							&& checkRed(i+2,j-2)
							&& checkRed(i+3,j-3);
					if (horizontal || vertical || diagLRD || diagRLD) {
						redWins = true;
						return true;//"Blue wins";
					}
				} else if (boardConfiguration[i][j].equals(Slot.Blue)) {
					vertical = checkBlue(i+1,j) 
							&& checkBlue(i+2,j)
							&& checkBlue(i+3,j);
					horizontal = checkBlue(i,j+1) 
							&& checkBlue(i,j+2)
							&& checkBlue(i,j+3);
					diagLRD = checkBlue(i+1,j+1) 
							&& checkBlue(i+2,j+2)
							&& checkBlue(i+3,j+3);
					diagRLD = checkBlue(i+1,j-1) 
							&& checkBlue(i+2,j-2)
							&& checkBlue(i+3,j-3);
					if (horizontal || vertical || diagLRD || diagRLD) {
						blueWins = true;
						return true;// "Red wins";
					}
				}
			}
		}
    	return false;//"";
    }
    
    /** PRIVATE: Check if certain slot has red disc **/
    private boolean checkRed(int i, int j) {
    	if (i < 0 || j < 0 || i > 
    		this.getRows()-1 || j > this.getColumns()-1) 
    			return false;
    	if (boardConfiguration[i][j].equals(Slot.Red)) return true;
    	return false;
    }
    
    /** PRIVATE: Check if certain slot has red disc **/
    private boolean checkBlue(int i, int j) {
    	if (i < 0 || j < 0 || i > 
    		this.getRows()-1 || j > this.getColumns()-1) 
    			return false;
    	if (boardConfiguration[i][j].equals(Slot.Blue)) return true;
    	return false;
    }
    
    /** PRIVATE: Return number of rows **/
    private int getRows() { return boardConfiguration.length; }
    
    /** PRIVATE: Return number of columns **/
    private int getColumns() { return boardConfiguration[0].length; }
    
    /** Check if the game is in a winning state **/
//    public boolean getWinState() {
//    	return getWinState(this.boardConfiguration);
//    }
    
    /** Returns current state of the Game **/
    public GameProgress getGameProgess() {
//    	System.out.println("Current turn: " + this.getTurn().toString());
    	if (this.getWinState()) {
    		if (this.getTurn() == Slot.Blue) return GameProgress.blueWon;
    		else return GameProgress.redWon;
    	}
    	else if ((this.getBlueDiscsCount() + this.getRedDiscsCount()) == (this.getRows() * this.getColumns())) {
//    		System.out.println("Tie Game");
    		return GameProgress.tieGame;
    	}
    	else {
//    		System.out.println("In progress");
    		return GameProgress.inProgess;
    	}
    }
    
    public ConnectFourModel.Slot switchTurn(){
    	return ConnectFourModel.Slot.Blue;
    }
    
    /** Returns whose turn it is as a String (i.e. "red" or "blue") **/
    public Slot getTurn(){ return currentTurn; }
    public void setTurn(Slot newTurn){ if(currentTurn!=Slot.Empty) currentTurn = newTurn;}
    
    /** Returns the String with the error message **/
    public String getErrorMessage() { return this.errorMessage; }
    
    public Slot[][] getBoardConfiguration(){ return boardConfiguration; }

    public void setGameState(GameState g){ gameState = g; }
    public GameState getGameState(){return gameState; }
    
    /** Makes the entire board configuration empty slots **/
    public void resetConfiguration() {
    	for (int i = 0; i < boardConfiguration.length; i++) {
			for (int j = 0; j < boardConfiguration[0].length; j++) {
				boardConfiguration[i][j] = Slot.Empty;
			}
		}
    }
    
    /** PRIVATE: Visually see the board's configuration **/
    /** -- Used for Debugging -- **/
    private void show() {
    	for (int i = 0; i < boardConfiguration.length; i++) {
			for (int j = 0; j < boardConfiguration[0].length; j++) {
				System.out.printf(boardConfiguration[i][j] + "\t");
			}
			System.out.printf("\n");
		}
    }
    
    //the user clicks somewhere.  That somewhere will give us a point.  The point has (x,y) coordinates.
    public Point insertDisk(Point p) {
    	int counter = boardConfiguration.length-1;
    	Point insertHere = new Point(p.x, counter);
    	while (counter >= 0) {
    		Slot currentSlot = boardConfiguration[counter][p.x];//boardConfiguration[p.x][counter];//Point(x,counter);
            if ( currentSlot != Slot.Empty){//Slot.Red || currentSlot == Slot.Blue) { //if the slot is full;
    			counter--;
    		}else{
    			return new Point(p.x, counter);//insertHere; //from bottom-to-top, it will return the first EMPTY slot.
    		}
    	}
	    return null; //This part is only reachable if all slots in this column are full.
    }
    
//    public static void main(String[] args) {
//    	ConnectFourModel model = new ConnectFourModel(5,5);
//    	
//    	Slot[][] slot = {{Slot.Empty, Slot.Empty, Slot.Empty, Slot.Empty, Slot.Empty},
//    					 {Slot.Empty, Slot.Blue, Slot.Blue, Slot.Empty, Slot.Empty},
//    					 {Slot.Empty, Slot.Red, Slot.Red, Slot.Blue, Slot.Empty},
//    					 {Slot.Empty, Slot.Red, Slot.Blue, Slot.Blue, Slot.Empty},
//    					 {Slot.Blue, Slot.Red, Slot.Red, Slot.Red, Slot.Blue}};
//    	
//    	show(slot);
//    	model.setBoardConfiguration(slot);
//    }


}//end class
