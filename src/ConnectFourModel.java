
import java.util.Random;

/**
 * Created by ahmed on 2/19/15.
 */
public class ConnectFourModel {

    enum Slot {
        Empty,
        Red,
        Blue
    }
    enum GameState {
        MainMenu, CustomGame, Game
    }

    private GameState gameState;
    private Slot[][] boardConfiguration;
    private int turnCount = 1;
    private Slot currentTurn;
    private String errorMessage = "";
    
    public ConnectFourModel(int rows, int columns){
    	if (rows < 4 || columns < 4) {
    		System.out.println("Game board must be at least be of size 4x4. Board "
    				+ " created with default values.");
    		rows = 4;
    		columns = 4;
    	}
    	gameState = GameState.MainMenu;
    	Random rand = new Random();
    	currentTurn = (rand.nextFloat() < 0.5)? Slot.Red:Slot.Blue;
    	this.boardConfiguration = new Slot[rows][columns];
    	this.resetConfiguration();
    }//end constructor

    
    public boolean checkBoardConfiguration(){ return checkBoardConfiguration(boardConfiguration); }
    public boolean checkBoardConfiguration(Slot[][] newBoardConfiguration){    	
    	this.errorMessage = "";
    	/* Checking the parameter for errors */
    	if (newBoardConfiguration.length != boardConfiguration.length) {
    		System.out.println("Invalid number of rows!");
    	}
    	if (newBoardConfiguration[0].length != boardConfiguration[0].length) {    	
    		System.out.println("Invalid number of columns!");
    	}
    	
    	/* Check if there's too many of either discs */
    	if (getBlueDiscsCount(newBoardConfiguration) > 
    			getRedDiscsCount(newBoardConfiguration) + 1) {
    		this.errorMessage += "ERROR: Too many blue dics on the board\n";
    		System.out.println("Too many blue");
    	} else if (getBlueDiscsCount(newBoardConfiguration) + 1 < 
    					getRedDiscsCount(newBoardConfiguration)) {
    		this.errorMessage += "ERROR: Too many red dics on the board\n";
    		System.out.println("Too many red");
    		//return false;
    	}
    	
    	/* Check for floating discs */
    	for (int i = 0; i < newBoardConfiguration.length; i++) {
			for (int j = 0; j < newBoardConfiguration[0].length; j++) {
				if (i+1 < newBoardConfiguration.length) {
					if (!newBoardConfiguration[i][j].equals(Slot.Empty) && 
							newBoardConfiguration[i+1][j].equals(Slot.Empty)) {
						
						if (newBoardConfiguration[i][j].equals(Slot.Red)) {
							this.errorMessage += "ERROR: Red floating disc detected "
									+ "at row: " + i + " column: " + j + "\n";
						} else if (newBoardConfiguration[i][j].equals(Slot.Blue)) {
							this.errorMessage += "ERROR: Blue floating disc detected "
									+ "at row: " + i + " column: " + j + "\n";
						}
						
						System.out.println("Floating");
					}
				}
				
			}
		}
    	
    	/* Check if there are four discs in a row of the same color */
    	if (getWinState(newBoardConfiguration)) {
    		this.errorMessage += "ERROR: Four " + currentTurn.toString() + " discs "
    				+ "are together\n";
    		System.out.println("Four discs");
    	}
    
    	if(!this.errorMessage.equals(""))
    		return false;
    	
		/* Switch turns after a valid move is made */
		switchTurns();
		
        return true;
    }
    
    /** PRIVATE: Switch turns **/
    private void switchTurns() { this.turnCount = 3 - this.turnCount; }
    
    /** PRIVATE: Count the number of blue discs inside the configuration **/
    private int getBlueDiscsCount(Slot[][] boardConfiguration) {
    	int blue = 0;
    	for (int i = 0; i < boardConfiguration.length; i++) {
			for (int j = 0; j < boardConfiguration[0].length; j++) {
				if (boardConfiguration[i][j].equals(Slot.Blue)) blue++;
			}
		}
    	return blue;
    }
    
    /** PRIVATE: Count the number of red discs inside the configuration **/
    private int getRedDiscsCount(Slot[][] boardConfiguration) {
    	int red = 0;
    	for (int i = 0; i < boardConfiguration.length; i++) {
			for (int j = 0; j < boardConfiguration[0].length; j++) {
				if (boardConfiguration[i][j].equals(Slot.Red)) red++;
			}
		}
    	return red;
    }
    
    /** PRIVATE: Check if the game is in a winning state **/
    private boolean getWinState(Slot[][] boardConfiguration) {
    	boolean horizontal=false, vertical=false, diagLRD=false, diagRLD=false;
    	for (int i = 0; i < boardConfiguration.length; i++) {
			for (int j = 0; j < boardConfiguration[0].length; j++) {
				if (boardConfiguration[i][j].equals(Slot.Red)) {
					vertical = checkRed(boardConfiguration,i+1,j) 
							&& checkRed(boardConfiguration,i+2,j)
							&& checkRed(boardConfiguration,i+3,j);
					horizontal = checkRed(boardConfiguration,i,j+1) 
							&& checkRed(boardConfiguration,i,j+2)
							&& checkRed(boardConfiguration,i,j+3);
					diagLRD = checkRed(boardConfiguration,i+1,j+1) 
							&& checkRed(boardConfiguration,i+2,j+2)
							&& checkRed(boardConfiguration,i+3,j+3);
					diagRLD = checkRed(boardConfiguration,i+1,j-1) 
							&& checkRed(boardConfiguration,i+2,j-2)
							&& checkRed(boardConfiguration,i+3,j-3);
					
				} else if (boardConfiguration[i][j].equals(Slot.Blue)) {
					vertical = checkBlue(boardConfiguration,i+1,j) 
							&& checkBlue(boardConfiguration,i+2,j)
							&& checkBlue(boardConfiguration,i+3,j);
					horizontal = checkBlue(boardConfiguration,i,j+1) 
							&& checkBlue(boardConfiguration,i,j+2)
							&& checkBlue(boardConfiguration,i,j+3);
					diagLRD = checkBlue(boardConfiguration,i+1,j+1) 
							&& checkBlue(boardConfiguration,i+2,j+2)
							&& checkBlue(boardConfiguration,i+3,j+3);
					diagRLD = checkBlue(boardConfiguration,i+1,j-1) 
							&& checkBlue(boardConfiguration,i+2,j-2)
							&& checkBlue(boardConfiguration,i+3,j-3);
				}
				
				if (horizontal || vertical || diagLRD || diagRLD) return true;
			}
		}
    	return false;
    }
    
    /** PRIVATE: Check if certain slot has red disc **/
    private boolean checkRed(Slot[][] boardConfiguration, int i, int j) {
    	if (i < 0 || j < 0 || i > 
    		getRows(boardConfiguration)-1 || j > getColumns(boardConfiguration)-1) 
    			return false;
    	if (boardConfiguration[i][j].equals(Slot.Red)) return true;
    	return false;
    }
    
    /** PRIVATE: Check if certain slot has red disc **/
    private boolean checkBlue(Slot[][] boardConfiguration, int i, int j) {
    	if (i < 0 || j < 0 || i > 
    		getRows(boardConfiguration)-1 || j > getColumns(boardConfiguration)-1) 
    			return false;
    	if (boardConfiguration[i][j].equals(Slot.Blue)) return true;
    	return false;
    }
    
    /** PRIVATE: Return number of rows **/
    private int getRows(Slot[][] boardConfiguration) { return boardConfiguration.length; }
    
    /** PRIVATE: Return number of columns **/
    private int getColumns(Slot[][] boardConfiguration) { return boardConfiguration[0].length; }
    
    /** Check if the game is in a winning state **/
    public boolean getWinState() {
    	return getWinState(this.boardConfiguration);
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
    private static void show(Slot[][] boardConfiguration) {
    	for (int i = 0; i < boardConfiguration.length; i++) {
			for (int j = 0; j < boardConfiguration[0].length; j++) {
				System.out.printf(boardConfiguration[i][j] + "\t");
			}
			System.out.printf("\n");
		}
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

/*
public class ConnectFourModel {

    enum Slot {
        Empty,
        Red,
        Blue
    }
    enum GameState {
        MainMenu, Game
    }

    private GameState gameState;
    private Slot[][] gameConfiguration;

    public ConnectFourModel(){ }//end constructor

    public boolean setBoardConfiguration(GameState[] boardConfiguration){
        //check if the configuration is possible

        //if it is then return true and make game configuration = boardConfiguration
        //if it is not then return false
        return false;
    }
    public Slot[][] getBoardConfiguration(){ return gameConfiguration; }
    public void setBoardConfiguration(Slot[][] newConfiguration){ gameConfiguration = newConfiguration; }

    public void setGameState(GameState g){ gameState = g; }
    public GameState getGameState(){return gameState; }


}//end class
*/