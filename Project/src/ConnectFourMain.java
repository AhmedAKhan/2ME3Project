import javax.swing.*;

/**
 * Created by Created by Ahmed Khan, Saim Malik, Zayan Imtiaz, Aleem Ul Haq, Sergio Agraz.
 */
public class ConnectFourMain {

    public static void main(String[] args){

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ConnectFourModel theModel = new ConnectFourModel(7,6);
                ConnectFourView theView = new ConnectFourView(500,600);//, theModel.getBoardConfiguration(), theModel.getGameState());
                ConnectFourController theController = new ConnectFourController(theView,theModel);
                theView.setController(theController);
                theView.setVisible(true);
                theView.repaint();
            }
        });
    }//end main function

}//end class
