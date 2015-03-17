
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

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

    private GameState gameState;
    private Slot[][] boardConfiguration;
    private int turnCount = 1;
    private Slot currentTurn;
    private String errorMessage = "";
    
    
    public ConnectFourModel(int rows, int columns) {
        if (rows < 4 || columns < 4) {
            System.out.println("Game board must be at least be of size 4x4. Board "
                    + " created with default values.");
            rows = 4;
            columns = 4;
        }
        gameState = GameState.MainMenu;
        Random rand = new Random();
        currentTurn = (rand.nextFloat() < 0.5) ? Slot.Red : Slot.Blue;
        this.boardConfiguration = new Slot[rows][columns];
        this.resetConfiguration();
    }//end constructor

    
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
					if (horizontal || vertical || diagLRD || diagRLD) return "Blue wins";
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
					if (horizontal || vertical || diagLRD || diagRLD) return "Red wins";
					
					
				}
				
				
			}
		}
    	return "";
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
    public void saveState () {
        PrintStream out;  //this will make all console output be placed in output.txt instead 
    	
		try {				//required exception in case the file is not found (will make it) 
			out = new PrintStream(new FileOutputStream("data/saveStateData.txt"));
			System.setOut(out);
		} catch (FileNotFoundException e1) {
			
			e1.printStackTrace();
		}
		
		
	    System.out.println(Arrays.deepToString(boardConfiguration)); //Prints out the 2D array in the given output file
    }
    
    //this method loads the state of the boardConfiguration from the save state file 
	public  void loadState () {
		try {
		
		
		 Scanner input	=	new Scanner(new File("data/saveStateData.txt")); //initialize scanner
		 String line = ""; 	// initialize empty string 
		 
		 
				 int x = 0;  //counters used to help parse the string 
				 int y = 0;
				 int z = 0;
				 int i = 0;  //counters used to loop through all indexes from 0-41, i = rows, j = columns 
				 int j = 0;
				 	for (int k = 0; k<42; k++){
				 		z++;		//increment counters every time the loop is run
				 		y++;
				 		x++;
			 
			 
				 		line = input.next();
				 		if ((z==1)) {
				 			line =line.substring(1, line.length()); //used to parse first character in the 2D array string (since we have [[ brackets)
				 
				 			}
			 
			 
				 		if ((y-1)%6==0){		//used to parse starter brackets before every group of 6 elements  
				 			line =line.substring(1, line.length());
				 		}
				 		if (x%6==0){		//used to parse end brackets after every group of 6 elements
				 			line =line.substring(0, line.length()-1);}
				 		line =line.substring(0, line.length()-1);
			 
			 
				 		boardConfiguration [i][j] = Slot.valueOf(line); //update board configuration 
				 		j++;
				 		if (j==6) {  //increment row counter to next value if column has reached 6 and reset column
				 			i++;
				 			j=0; }
				 		
				 		if (i==7)  {  //rest row counter to 0 if row has reached 7
				 			i=0; }
				 		
				 		
				 		}
		 
				  
				  
		      
		
	}catch(Exception e){}
	


	
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
