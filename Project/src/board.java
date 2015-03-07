import javax.swing.*;
import java.awt.*;

/**
 * Created by ahmed on 3/6/15.
 */
public class Board extends JPanel {

    //this JPanel holds all the data
    private int diameterOfDisk = 30;			//represents the diameter of one disk
    private int spaceBetweenDisks = 7;		    //represents the space between the disk
    private int rows;
    private int columns;

    public Board(int rows, int columns){
        this.rows = rows;
        this.columns = columns;
    }//end function

    public void setBoard(ConnectFourModel.Slot[][] slots){

    }//end function

    @Override
    public void paintComponent(Graphics g){
        System.out.println("painting the board");
    }//end function

    //Purpose: calls the drawTileAtPosition in a nested loop to tell the drawTileAtPosition(); so all the tiles can be drawn if there is something to draw
    private void drawTilesFromBoardConfiguration(Graphics g, ConnectFourModel.Slot[][] slotConfiguration){
        //when we draw stuff we put the bottom right position in the function

        int spaceBetweenSlots = diameterOfDisk + spaceBetweenDisks;//k represents the distance between two slots
        int numberOfRows = slotConfiguration[0].length;//this is the number of slots the board has

        //gets the width and height of the board
        int widthOfBoard = getWidthOfBoard();
        int heightOfBoard =  getHeightOfBoard();
        g.setColor(Color.BLACK);//makes the color black
        //draws the board itself, which is just a black rectangle and then we will place the blank disks on top of it
        //TODO make it so it draws the picture of the empty board
        g.fillRect(getOriginOfBoard().x, getOriginOfBoard().y - (numberOfRows+1)*spaceBetweenSlots, widthOfBoard , heightOfBoard);

        //call the drawTileAtPosition an n number of time if it is not empty
        //draws all the disks on the board
        for(int rowCounter = 0; rowCounter < slotConfiguration.length; rowCounter++){
            for(int colCounter = 0; colCounter < slotConfiguration[0].length; colCounter++){
                drawTileAtPosition(g, new Point(colCounter, rowCounter), slotConfiguration[rowCounter][colCounter], slotConfiguration.length);
            }//end row counter loop
        }//end col counter loop
    }//end function
    //PURPOSE: draws one tile at the specified location
    private void drawTileAtPosition(Graphics g, Point pos, ConnectFourModel.Slot type, int numberOfRows){
        //need to convert the old position to new position
        pos = getGameCoordinate(pos, pos.y);

        //TODO make it so it draws the tile at this position with a picture instead of graphics
        //draw the tile depending on the type and position
        Color c = new Color((type==ConnectFourModel.Slot.Blue)? 0:255,(type==ConnectFourModel.Slot.Empty)? 255:0,(type==ConnectFourModel.Slot.Red)? 0:255);
        g.setColor(c);//sets the color of the slot
        g.fillOval(pos.x, pos.y, diameterOfDisk, diameterOfDisk);
    }//end draw tile at position function

    //PURPOSE: gets the origin of the board meaning the bottom left coordinate of the board
    private Point getOriginOfBoard(){
        //get the origin again because this is broken
        Dimension screenSize = this.getRootPane().getSize();//gets the screen size
        //calculate the bottom left coordinate of the board and return that point
        return new Point(screenSize.width/2 - getWidthOfBoard()/2 ,screenSize.height/2 + getHeightOfBoard()/2);
    }

    //PURPOSE: converts the position with respect to the array to the position with respect to the game screen
    public Point getGameCoordinate(Point slotPosition, int numberOfRows){
        //this function will take in the position of the board and return the actual position on the screen
        int sizeOfSlotPlusExtraSpace = diameterOfDisk + spaceBetweenDisks;
        return new Point(slotPosition.x *sizeOfSlotPlusExtraSpace + getOriginOfBoard().x + spaceBetweenDisks,
                getOriginOfBoard().y - (slotPosition.y+1)*sizeOfSlotPlusExtraSpace + spaceBetweenDisks);
    }
    //PURPOSE: converts the position to becoming with respect to the array instead of the game screen
    public Point getBoardCoordinateOfPoint(Point mousePosition){
        //convert the point mousePosition into a tile position where the x represents the column and y represents the row
        Point tilePosition = new Point((mousePosition.x- getOriginOfBoard().x)/(diameterOfDisk + spaceBetweenDisks),
                (getOriginOfBoard().y - mousePosition.y)/(diameterOfDisk + spaceBetweenDisks));
        //this is just making the tilePosition transition from different grids
        return new Point(tilePosition.x, rows - 1 - tilePosition.y);
    }

    //these two private functions are to get the width and height of the board, they are used to draw the board
    private int getWidthOfBoard(){  return (columns)*(diameterOfDisk +spaceBetweenDisks) + spaceBetweenDisks;}
    private int getHeightOfBoard(){return (rows)*(diameterOfDisk + spaceBetweenDisks) + spaceBetweenDisks; }
}//end class
