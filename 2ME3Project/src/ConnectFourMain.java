

/**
 * Created by Created by Ahmed Khan, Saim Malik, Zayan Imtiaz, Aleem Ul Haq, Sergio Agraz.
 */
public class ConnectFourMain {

    public static void main(String[] args){
        System.out.println("1");
        ConnectFourModel theModel = new ConnectFourModel(7,6);
        System.out.println("2");
        NewViewClass theView = new NewViewClass();//ConnectFourView(500,600, theModel.getBoardConfiguration(), theModel.getGameState());
        System.out.println("3");
        ConnectFourController theController = new ConnectFourController(theView,theModel);
        System.out.println("4");
        theView.setVisible(true);
    }//end main function

}//end class
