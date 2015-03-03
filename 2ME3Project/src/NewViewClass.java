import javax.swing.*;

import java.awt.Button;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

/**
 * Created by ahmed on 3/3/15.
 */
public class NewViewClass extends JFrame{

    private JButton customGameButton;
    private JButton newGameButton;
    
    private JButton mainMenuButton;
    private JButton redTurnButton;
    private JButton blueTurnButton;
    private JButton CheckStateButton;
    private JButton resetBoardButton;            //added Feb 26, 2015 sergio cosman
    
    private BoardClass board;
    
    private JPanel mainMenu;
    private JPanel game;
    
    
    public NewViewClass(){ this(600, 600); }    
    public NewViewClass(int width, int height){
        setupBoard(width, height);//setup view class
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//sets the default close operation
        this.setSize(width, height);//sets the size of the screen
        
        
    }

    private void setupBoard(int width, int height){
    	this.setLayout(null);     	
    	//create the two buttons
    	customGameButton = createButton("./images/customGameButton0001.png", 100, 100, 289, 100);
    	newGameButton = createButton("./images/button20001.png", 100, 200, 289, 100);
    	
    	mainMenu = new JPanel();
    	
    	mainMenu.add(customGameButton);
    	mainMenu.add(newGameButton);
    	this.add(mainMenu);
    	mainMenu.setVisible(true);
    }
    // this function creates a button with the given location of the image, x, y and width and height, and then returns the button
    private JButton createButton(String location, int x, int y, int width, int height){
    	ImageIcon img = new ImageIcon(location);
    	JButton newButton = new JButton(img);
        
        newButton.setOpaque(false);
        newButton.setContentAreaFilled(false);
        newButton.setBorderPainted(false);
        newButton.setFocusPainted(false);
        
        newButton.setSize(width,height);
        newButton.setLocation(x, y);
        return newButton;
    }//end create button
    

    public void addCalculateListener(ActionListener actionListener, MouseListener mouseListener){
    	//customGameButton.addActionListener(actionListener);
    }

}//end class
