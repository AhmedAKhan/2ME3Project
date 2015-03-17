import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;




public class test {

	public static void loadState () {
		try {
		
		
		 Scanner input	=	new Scanner(new File("data/saveStateData.txt"));
		 String line = "";
		 int [][] test = new int [7][6];
		 
				 int x = 0;
				 int y = 0;
				 int z = 0;
				 int i = 0;
				 int j = 0;
				 	for (int k = 0; k<42; k++){
				 		z++;
				 		y++;
				 		x++;
			 
			 
				 		line = input.next();
				 		if ((z==1)) {
				 			line =line.substring(1, line.length());
				 
				 			}
			 
			 
				 		if ((y-1)%6==0){
				 			line =line.substring(1, line.length());
				 		}
				 		if (x%6==0){
				 			line =line.substring(0, line.length()-1);}
				 		line =line.substring(0, line.length()-1);
			 
			 
				 		System.out.println(line);
				 		test [i][j] = Integer.parseInt(line);
				 		j++;
				 		if (j==6) {
				 			i++;
				 			j=0; }
				 		
				 		if (i==7)  {
				 			i=0; }
				 		
				 		
				 		}
		 
				  System.out.println(Arrays.deepToString(test));
		 
		//
		
	}catch(Exception e){}
	


	
}
	
	public static void main (String Args []) {
        PrintStream out;  //this will make all console output be placed in output.txt instead 
    	
		try {				//required exception in case the file is not found (will make it) 
			out = new PrintStream(new FileOutputStream("data/saveStateData.txt"));
			System.setOut(out);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int[][] multi = {{0,1,2,3,4,5}, {6,7,8,9,10,11},{12,13,14,15,16,17}, {18,19,20,21,22,23}, {24,25,26,27,28,29}, {30,31,32,33,34,35}, {36,37,38,39,40,41}
		
		};
		
		
	    System.out.println(Arrays.deepToString(multi));
	    try {				//required exception in case the file is not found (will make it) 
			out = new PrintStream(new FileOutputStream("data/loadStateData.txt"));
			System.setOut(out);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    
	    loadState();
	    
	
		
		
		
		
		
	
		
	}
	
	
}
