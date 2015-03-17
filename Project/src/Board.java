import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * Created by ahmed on 3/6/15.
 */
public class Board extends JPanel implements ActionListener {

    //this JPanel holds all the data
    private int diameterOfDisk = 30;			//represents the diameter of one disk
    private int spaceBetweenDisks = 7;		    //represents the space between the disk
    private int rows;                           //represents the number of rows in the board
    private int columns;                        //represents the number of columns in the board
    private ConnectFourController controller;

    private Point animatingPoint;
    private ConnectFourModel.Slot animatingSlot;
    private Point stopAnimationPoint;

    public Board(int rows, int columns){
        super(null, true);//calls the super constructor

        //set its rows and columns
        this.rows = rows;
        this.columns = columns;
        animatingPoint = new Point(-1,-1);
        animatingSlot = ConnectFourModel.Slot.Empty;
        stopAnimationPoint = new Point(-1,-1);

        //make it not have a layout
        setLayout(null);
    }//end function

    public void setBoard(ConnectFourModel.Slot[][] slots){
        Graphics g = getGraphics();
        drawTilesFromBoardConfiguration(getGraphics(), slots);
    }//end function

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        if(controller != null)
            drawTilesFromBoardConfiguration(g, controller.getConfiguration());
    }//end function

    public void setController(ConnectFourController c){ controller = c;}

    //Purpose: calls the drawTileAtPosition in a nested loop to tell the drawTileAtPosition(); so all the tiles can be drawn if there is something to draw
    private void drawTilesFromBoardConfiguration(Graphics g, ConnectFourModel.Slot[][] slotConfiguration){
        //when we draw stuff we put the bottom right position in the function
        //super.paintComponent(g);

        int spaceBetweenSlots = diameterOfDisk + spaceBetweenDisks;//k represents the distance between two slots
        int numberOfRows = slotConfiguration[0].length;//this is the number of slots the board has

        //gets the width and height of the board
        int widthOfBoard = getWidthOfBoard();
        int heightOfBoard =  getHeightOfBoard();

        g.setColor(Color.BLACK);//makes the color black
        //draws the board itself, which is just a black rectangle and then we will place the blank disks on top of it
        //TODO make it so it draws the picture of the empty board
        //g.fillRect(getOriginOfBoard().x, getOriginOfBoard().y - (numberOfRows+1)*spaceBetweenSlots, widthOfBoard , heightOfBoard);
        g.fillRect(0,0, widthOfBoard, heightOfBoard);

        //call the drawTileAtPosition an n number of time if it is not empty
        //draws all the disks on the board
        for(int rowCounter = 0; rowCounter < slotConfiguration.length; rowCounter++){
            for(int colCounter = 0; colCounter < slotConfiguration[0].length; colCounter++){
                if(animatingPoint.x == colCounter && animatingPoint.y == rowCounter) drawTileAtPosition(g, new Point(colCounter, rowCounter), animatingSlot, slotConfiguration.length);
                else drawTileAtPosition(g, new Point(colCounter, rowCounter), slotConfiguration[rowCounter][colCounter], slotConfiguration.length);
            }//end row counter loop
        }//end col counter loop
    }//end function
    //PURPOSE: draws one tile at the specified location
    private void drawTileAtPosition(Graphics g, Point pos, ConnectFourModel.Slot type, int numberOfRows){
        //need to convert the old position to new position
        pos = getGameCoordinate(pos, pos.y);

        //TODO make it so it draws the tile at this position with a picture instead of graphics
        //draw the tile depending on the type and position
        Color c;
        switch (type){
            case Blue :c = new Color(17, 45, 255);         break;
            case Red:   c = new Color(255, 2, 7);           break;
            case Empty:
            default:
                c = new Color(220, 220, 220);
                break;
        }
        g.setColor(c);//sets the color of the slot
        g.fillOval(pos.x, pos.y, diameterOfDisk, diameterOfDisk);
    }//end draw tile at position function

    //PURPOSE: gets the origin of the board meaning the bottom left coordinate of the board
    private Point getOriginOfBoard(){
        //get the origin again because this is broken
        //Dimension screenSize = this.getRootPane().getSize();//gets the screen size
        //calculate the bottom left coordinate of the board and return that point
        return new Point(0,0);//new Point(screenSize.width/2 - getWidthOfBoard()/2 ,screenSize.height/2 + getHeightOfBoard()/2);
    }

    //PURPOSE: converts the position with respect to the array to the position with respect to the game screen
    public Point getGameCoordinate(Point slotPosition, int numberOfRows){
        //this function will take in the position of the board and return the actual position on the screen
        int sizeOfSlotPlusExtraSpace = diameterOfDisk + spaceBetweenDisks;
        return new Point(
                getOriginOfBoard().x + slotPosition.x*sizeOfSlotPlusExtraSpace + spaceBetweenDisks,
                getOriginOfBoard().y + (slotPosition.y)*sizeOfSlotPlusExtraSpace + spaceBetweenDisks);
    }
    //PURPOSE: converts the position to becoming with respect to the array instead of the game screen
    public Point getBoardCoordinateOfPoint(Point mousePosition){
        //convert the point mousePosition into a tile position where the x represents the column and y represents the row
        Point tilePosition = new Point((mousePosition.x- getOriginOfBoard().x)/(diameterOfDisk + spaceBetweenDisks),
                (mousePosition.y-getOriginOfBoard().y)/(diameterOfDisk + spaceBetweenDisks));
        //this is just making the tilePosition transition from different grids
        return tilePosition;
    }

    //these two private functions are to get the width and height of the board, they are used to draw the board
    public int getWidthOfBoard(){  return (columns)*(diameterOfDisk +spaceBetweenDisks) + spaceBetweenDisks;}
    public int getHeightOfBoard(){return (rows)*(diameterOfDisk + spaceBetweenDisks) + spaceBetweenDisks; }

    public boolean isAnimating(){ return (animatingSlot != ConnectFourModel.Slot.Empty); }
    public void insertDisc(Point point, ConnectFourModel.Slot type){
        Timer t = new Timer(100, this);
        animatingPoint = new Point(point.x, 0);
        stopAnimationPoint = point;
        animatingSlot = type;
        t.start();
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Timer t = (Timer)e.getSource(); // get the timer
        if(animatingPoint.x == stopAnimationPoint.x && animatingPoint.y == stopAnimationPoint.y){
            ConnectFourModel.Slot[][] config = controller.getConfiguration();
            config[stopAnimationPoint.y][stopAnimationPoint.x] = animatingSlot;
            controller.showWinner(animatingSlot);
            animatingSlot = ConnectFourModel.Slot.Empty;
            animatingPoint = new Point(-1,-1);
            stopAnimationPoint = animatingPoint;
            t.stop();//stop calling this function
        }else animatingPoint.y++; // make animating point go down
        repaint();//repaint the entire screen
    }
}//end class
