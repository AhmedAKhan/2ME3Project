import javax.swing.*;
import javax.swing.event.MouseInputListener;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

/**
 * Created by Created by Ahmed Khan, Saim Malik, Zayan Imtiaz, Aleem Ul Haq, Sergio Agraz.
 */
public class ConnectFourController {
    private ConnectFourView view;
    private ConnectFourModel model;

    public ConnectFourController(ConnectFourView v, ConnectFourModel m){
        view = v;
        model = m;

        Listener listener = new Listener(); // create a listener
        view.addCalculateListener(listener, listener); // add the listener to the view
    }//end constructor

    public void showWinner(){
        if (model.getGameProgess() == (ConnectFourModel.GameProgress.blueWon))      view.displayMessage("Blue Won");
        if (model.getGameProgess() == (ConnectFourModel.GameProgress.redWon))       view.displayMessage("Red Won");
        else if (model.getGameProgess() == (ConnectFourModel.GameProgress.tieGame)) view.displayMessage("You both lose");
    }
    public void switchTurn(){
        if (model.getTurn() == ConnectFourModel.Slot.Blue) {
            model.setTurn(ConnectFourModel.Slot.Red);
            view.setTurn(ConnectFourModel.Slot.Red);
        }
        else if (model.getTurn() == ConnectFourModel.Slot.Red) {
            model.setTurn(ConnectFourModel.Slot.Blue);
            view.setTurn(ConnectFourModel.Slot.Blue);
        }
    }
    public ConnectFourModel.Slot[][] getConfiguration(){ return model.getBoardConfiguration(); }
    class Listener implements ActionListener, MouseInputListener {
        public Listener(){}//empty constructor

        //Purpose: when the user presses the main menu button it will call this function, and it will either go to the game stage or the custom game depending on what is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            //will get called for the menu buttons stuff
            //check what button is pressed
            String buttonPressedIconString = ((JButton)e.getSource()).getIcon().toString();
            if(buttonPressedIconString.equals(ConnectFourView.mainPlayButtonImageName)){
                //the play button in the main menu
                model.setGameState(ConnectFourModel.GameState.Game);
                model.resetConfiguration();
                view.switchScreen(ConnectFourModel.GameState.Game);
            }else if(buttonPressedIconString.equals(ConnectFourView.mainCustomButtonImageName)){
                // the custom game button in the main menu
                model.setGameState(ConnectFourModel.GameState.CustomGame);
                model.resetConfiguration();
                view.switchScreen(ConnectFourModel.GameState.CustomGame);
            }else if (buttonPressedIconString.equals(ConnectFourView.saveStateImage)) {
                //Save progress.  If a previous data save exists, ask to overwrite... or don't; it doesn't matter.
                if(model.checkBoardConfiguration()) { model.saveState(); view.displayMessage("The game has been saved");}
                else view.displayMessage("Can not save the current state, the current state is not valid");
            }else if(buttonPressedIconString.equals(ConnectFourView.mainLoadSavedStateImageName)){
                model.loadState();
                model.setGameState(ConnectFourModel.GameState.Game);
                view.switchScreen(ConnectFourModel.GameState.Game);
            }else if(buttonPressedIconString.equals(ConnectFourView.gameMainMenuImageName)){
                model.setGameState(ConnectFourModel.GameState.MainMenu);
                view.switchScreen(ConnectFourModel.GameState.MainMenu);

            }else if(buttonPressedIconString.equals(ConnectFourView.customGameResetImageName)){                             //added Feb 26, 2015
                //if(model.getGameState()== ConnectFourModel.GameState.Game) { model.resetConfiguration(); return; }
                //model.setGameState(ConnectFourModel.GameState.CustomGame);                  //added Feb 26, 2015
                model.resetConfiguration();                                                 //added Feb 26, 2015
                view.setBoard(model.getBoardConfiguration());
                view.setError("");
            }else if(model.getGameState() == ConnectFourModel.GameState.CustomGame){
                if(buttonPressedIconString.equals(ConnectFourView.gameRedButtonImageName)){
                    model.setTurn(ConnectFourModel.Slot.Red);
                    view.setTurn(model.getTurn());
                }else if(buttonPressedIconString.equals(ConnectFourView.gameBlueButtonImageName)) {
                    model.setTurn(ConnectFourModel.Slot.Blue);
                    view.setTurn(model.getTurn());
                }else if(buttonPressedIconString.equals(ConnectFourView.customGameCheckStateImageName)){
                    if(!model.checkBoardConfiguration()){
                        view.setError(model.getErrorMessage());
                    }else{
                        view.setError("Yay! No errors :D");
                    }
                }//end of the if else block
            }

//        	 if(buttonPressedIconString.equals(ConnectFourView.gameSaveButtonImageName)) model.saveState();
//        	 if(buttonPressedIconString.equals(ConnectFourView.gameLoadButtonImageName)) model.loadState();
        }//end function
        
        public void handleCustomGameState(MouseEvent e){
//get the mouse position of the click and then convert that to the board position where it will be in a different coordinate system
            
//            if (model.getWinState()==true)
            
            Point mousePosition = new Point(e.getX(), e.getY());
            Point tilePosition = view.getBoardCoordinateOfPoint(mousePosition);

            //if the click is outside the board just end the function
            if(tilePosition.x >= model.getBoardConfiguration()[0].length || tilePosition.x < 0) return;
            if(tilePosition.y >= model.getBoardConfiguration().length || tilePosition.y < 0) return;    
            //x corresponds to the rows in the array and y corresponds to the columns

            //check if this configuration is possible by calling the model
            ConnectFourModel.Slot[][] newBoardConfiguration = model.getBoardConfiguration();
       
            //make that tile of type the players turn, but if that tile is already there then remove it
            if(newBoardConfiguration[tilePosition.y][tilePosition.x] == model.getTurn())newBoardConfiguration[tilePosition.y][tilePosition.x] = ConnectFourModel.Slot.Empty;
            else newBoardConfiguration[tilePosition.y][tilePosition.x] = model.getTurn();

            //adjust the game and update the switchScreen
            view.adjustBoard(newBoardConfiguration);
            view.switchScreen(model.getGameState());
            //update the view if it is possible,
            //if it is not possible then call configurationNotPossible on the view class
        }

        //Purpose: this function will be called when the user presses a button, it will be responsible for handling the outcome of the button press.
        @Override
        public void mouseClicked(MouseEvent e) {
        	//if its in the main menu stop running the rest of the code
            if(model.getGameState() == ConnectFourModel.GameState.MainMenu) return;
            if (model.getGameState() == ConnectFourModel.GameState.CustomGame) { handleCustomGameState (e); return;}
            if(view.isAnimating()) return;

            Point mousePosition = new Point(e.getX(), e.getY());
            Point tilePosition = view.getBoardCoordinateOfPoint(mousePosition);
            //if the click is outside the board just end the function
            if(tilePosition.x >= model.getBoardConfiguration()[0].length || tilePosition.x < 0) return;
            if(tilePosition.y >= model.getBoardConfiguration().length || tilePosition.y < 0) return;
            //x corresponds to the rows in the array and y corresponds to the columns

            //get the mouse position of the click and t
           //Then convert that to the board position where it will be in a different coordinate system

            //make that tile of type the players turn, but if that tile is already there then remove it
            if (model.getGameProgess() != (ConnectFourModel.GameProgress.inProgress)) return;

            //adjust the game and update the switchScreen
            view.insertDisc(model.insertDisk(tilePosition), model.getTurn());
            //update the view if it is possible,
            //if it is not possible then call configurationNotPossible on the view class
        }

        @Override public void mouseReleased(MouseEvent e) {}//unused
        @Override public void mousePressed(MouseEvent e) {} //unused
        @Override public void mouseEntered(MouseEvent e) {}//unused
        @Override public void mouseExited(MouseEvent e) {}//unused
        @Override public void mouseDragged(MouseEvent e) {}//unused
        @Override public void mouseMoved(MouseEvent e) {}//unused
    }//end calculate listener class


}//end controller