
import javax.swing.*;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;

import java.awt.Button;
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

            }else if(buttonPressedIconString.equals(ConnectFourView.gameMainMenuImageName)){
                model.setGameState(ConnectFourModel.GameState.MainMenu);
                view.switchScreen(ConnectFourModel.GameState.MainMenu);

            }else if(buttonPressedIconString.equals(ConnectFourView.customGameResetImageName)){                             //added Feb 26, 2015
                model.setGameState(ConnectFourModel.GameState.CustomGame);                  //added Feb 26, 2015
                model.resetConfiguration();                                                 //added Feb 26, 2015
                view.setBoard(model.getBoardConfiguration());
                view.setError("");

            }else if(buttonPressedIconString.equals(ConnectFourView.gameRedButtonImageName)){
                model.setTurn(ConnectFourModel.Slot.Red);
                view.setTurn(model.getTurn());

            }else if(buttonPressedIconString.equals(ConnectFourView.gameBlueButtonImageName)){
                model.setTurn(ConnectFourModel.Slot.Blue);
                view.setTurn(model.getTurn());

            }else if(buttonPressedIconString.equals(ConnectFourView.customGameCheckStateImageName)){
                if(!model.checkBoardConfiguration()){
                    view.setError(model.getErrorMessage());
                }else{
                	view.setError("Yay! No errors :D");
                }
            	
            }//end of the if else block
        }//end function

        //Purpose: this function will be called when the user presses a button, it will be responsible for handling the outcome of the button press.
        @Override
        public void mouseClicked(MouseEvent e) {

        	//if its in the main menu stop running the rest of the code
            if(model.getGameState() == ConnectFourModel.GameState.MainMenu) return;

            //get the mouse position of the click and then convert that to the board position where it will be in a different coordinate system
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
            //view.repaint();
        }
        @Override
        public void mousePressed(MouseEvent e) {} //unused
        @Override
        public void mouseReleased(MouseEvent e) {}//unused
        @Override
        public void mouseEntered(MouseEvent e) {}//unused
        @Override
        public void mouseExited(MouseEvent e) {}//unused
        @Override
        public void mouseDragged(MouseEvent e) {}//unused
        @Override
        public void mouseMoved(MouseEvent e) {}//unused
    }//end calculate listener class


}//end controller