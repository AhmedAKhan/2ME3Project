
import javax.swing.event.MouseInputListener;

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

    class Listener implements ActionListener, MouseInputListener {
        public Listener(){}//empty constructor

        //Purpose: when the user presses the main menu button it will call this function, and it will either go to the game stage or the custom game depending on what is clicked
        @Override
        public void actionPerformed(ActionEvent e) {
            //will get called for the menu buttons stuff
            
            //check what button is pressed
            Button buttonPressed = (Button)e.getSource();
            
            if(buttonPressed.getLabel().equals("New Game")){
                model.setGameState(ConnectFourModel.GameState.Game);
                model.resetConfiguration();
            }else if(buttonPressed.getLabel().equals("Custom Game")){
                model.setGameState(ConnectFourModel.GameState.CustomGame);
                model.resetConfiguration();
                view.setTurn(model.getTurn());
            }else if(buttonPressed.getLabel().equals("Main Menu")){
                model.setGameState(ConnectFourModel.GameState.MainMenu);
            }else if(buttonPressed.getLabel().equals("Reset")){                             //added Feb 26, 2015
                model.setGameState(ConnectFourModel.GameState.CustomGame);                  //added Feb 26, 2015
                model.resetConfiguration();                                                 //added Feb 26, 2015
                view.setError("");
            }else if(buttonPressed.getLabel().equals("RED")){
                model.setTurn(ConnectFourModel.Slot.Red);
                view.setTurn(model.getTurn());
            }else if(buttonPressed.getLabel().equals("BLUE")){
                model.setTurn(ConnectFourModel.Slot.Blue);
                view.setTurn(model.getTurn());
            }else if(buttonPressed.getLabel().equals("Check State")){
                if(!model.checkBoardConfiguration()){
                	view.setError(model.getErrorMessage());
                }else{
                	view.setError("Yay! No errors :D");
                }
            	
            }
            view.switchScreen(model.getGameState());
            //update the view by called switchScreen
            view.repaint();
        }

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
            if(newBoardConfiguration[tilePosition.y][tilePosition.x] == model.getTurn()) newBoardConfiguration[tilePosition.y][tilePosition.x] = ConnectFourModel.Slot.Empty;
            else newBoardConfiguration[tilePosition.y][tilePosition.x] = model.getTurn();
            
            //adjust the game and update the switchScren
            view.adjustBoard(newBoardConfiguration);
            view.switchScreen(model.getGameState());
            //update the view if it is possible,
            //if it is not possible then call configurationNotPossible on the view class
            view.repaint();
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