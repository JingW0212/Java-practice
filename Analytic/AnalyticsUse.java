// This is a program that calculates a given set of number
package homework;
import java.io.*;
import java.util.*;
//--------------------------------------------------------------------------------
// Retrieve a dataset and perform analytics both across and down
// Input from arg[0], or "vAnalytics1.csv" otherwise
// Re-write "AnalyticsUse.java" to instantiate an Analytics object
// Produce the same result as currently being produced by "zAnalytics" -> "AnalyticsUse.java" 
//--------------------------------------------------------------------------------
public class AnalyticsUse
{
	static double[][] dataPoints = null;                    //2 dim array to hold all the data points

	public static void main(String[ ] args) throws Exception
	{
		String input_file = "/Users/jingwang0212/workspace/Columbia/src/homework/dataset.csv";

		if (args.length > 0)                                //if argument is provided 
			input_file = args[0];                           //use it as an input file

		readFile(input_file);                               //read the file into a 2 dim array
		//      display(dataPoints);      					//print the 2 dim array
		compute(dataPoints);                                //compute and print
		//------------------------------------------------------------------------------------
		//After transposing the 2 dimensional array, repeat all above computation for the transposed array
		//------------------------------------------------------------------------------------
		System.out.print("\nTRANSPOSED");
		double[][] x = Analytics.transpose(dataPoints);     //transpose the 2 dim array 
		//      display(x);                                 //print transposed 2 dim array	
		compute(x);											//compute the transposed array and print
	}
	//--------------------------------------------------------------------------------
	// Read the input file, cleanse it, and store into a 2 dimensional array
	//--------------------------------------------------------------------------------
	static void readFile(String filename)
	{ 
		int nRows = 0;                                  //number of lines/rows in file
		int nCols = 0;                                  //number of columns for each line
		String[] data = null;                           //1 dim array to hold string data for each line

		System.out.println("SOURCE INPUT...");
		try 
		{
			File    f1   = new File(filename);                                  //create a file object               
			Scanner file = new Scanner(f1);                                     //scanner for the input file

			LineNumberReader lnr = new LineNumberReader(new FileReader(f1));    //code to get the number of lines
			lnr.skip(Long.MAX_VALUE);                                           //skip to end
			//          nRows = lnr.getLineNumber() +1;                         //obtain the num of lines/rows      
			nRows = lnr.getLineNumber();                                        //for CSV there is an extra blank line      
			lnr.close();                                                        //close the object                                      

			dataPoints = new double[nRows][];                //create a 2 dim array with as many rows as lines in file 

			int row=0;
			int col=0;

			while (file.hasNextLine())                      //while there are lines in the file
			{
				String line = file.nextLine();              //get next line
				System.out.println(line);
				data            = line.split(",");          //split it on ,
				nCols           = data.length;              //get number of columns
				double[] cols   = new double[nCols];        //create a 1 dim array with as many cols as the line 
				dataPoints[row] = cols;                     //append the 1 dim array into the 2 dim array

				for (col=0; col < nCols; col++)             //loop for all columns
				{
					try
					{
						double num = Double.parseDouble(data[col]);     //convert from String to double 
						dataPoints[row][col] = num;                     //populate the 2 dim table              
					} 
					catch(Exception e)                                  //if error in conversion
					{
						//  System.out.print  ("row:" + (row+1) + ", col:" + (col+1));                      //display error
						//  System.out.println(", value not numeric: " + data[col] + ", assuming null");    //display error

						dataPoints[row][col] = Double.MIN_VALUE;        //assume bad/no data - place smallest double value 
					}   
				}
				row++;                                                  //next row
			}
		}            
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
	//--------------------------------------------------------------------------------
	// Display the 2 dimensional data array
	//--------------------------------------------------------------------------------
	static void display(double[][] dataPoints)
	{
		System.out.println("\nCOLUMN ORIENTED...");

		for (double[] row : dataPoints)                  //loop through all rows of 2 dim array
		{
			for (double col : row)                       //loop through all the columns
				if (col == Double.MIN_VALUE)             //if smallest double value (assume bad/no data)
					System.out.print("\t");              //bypass
				else
					System.out.printf("%7.2f ", col);    //print the column value

			System.out.print("\n");
		}
	}

	//--------------------------------------------------------------------------------
	// Compute and print
	//--------------------------------------------------------------------------------
	static void compute(double[][] dataPoints)
	{
		System.out.println("\nCOMPUTATION...");
		//------------------------------------------------------------------------------------
		//Perform row oriented analytics ------------------
		//------------------------------------------------------------------------------------
		for (double[] row : dataPoints)                 //loop through all rows                 
		{           
			System.out.print("\t");

			for (double col : row)                      //loop through all the columns
				if (col == Double.MIN_VALUE)            //if smallest double value (assume bad/no data)
					System.out.print("\t");             //bypass
				else
					System.out.printf("%7.2f ", col);   //print the column value
			//------------------------------------------------------------------------------------
			//Call the getter methods to obtain the values for count, sum, average, median, mode, min, max, range, stdDev
			//------------------------------------------------------------------------------------
			Analytics rowArray = new Analytics(row); 
			System.out.print (" | Count="    + rowArray.getCount());    //print row count
			System.out.printf("\tSum=%.2f"   , rowArray.getSum());      //print row sum   
			System.out.printf("\tAvg=%.2f"   , rowArray.getAvg());      //print row average
			System.out.printf("\tMedian=%.2f", rowArray.getMed());      //print row median
			System.out.printf("\tMode=%.2f"  , rowArray.getMode()); 	//print row mode
			System.out.printf("\tMin=%.2f"   , rowArray.getMin());      //print row minimum value
			System.out.printf("\tMax=%.2f"   , rowArray.getMax());      //print row maximum value
			System.out.printf("\tRange=%.2f" , rowArray.getRange()); 	//print row range
			System.out.printf("\tstdDev=%.2f", rowArray.getStdDev());   //print row standard deviation
			System.out.println();       
		}                          
		System.out.println("\t --------------------------------------------------------------------------------");
		//------------------------------------------------------------------------------------
		//Perform column oriented analytics --------------- 
		//------------------------------------------------------------------------------------
		int maxCols = 0;                        //determine maximum number of columns
		for (double[] row : dataPoints)         //loop through all rows                 
			if (row.length > maxCols)           //if number of columns for the row > max
				maxCols = row.length;           //take it

		System.out.print("COUNT..:");            
		for (int col=0; col < maxCols; col++)                           //loop through all columns                  
		{           
			double[] cols = Analytics.slice(dataPoints,"col",col);		//slice the array vertically
			Analytics colArray = new Analytics(cols);					//create the object
			System.out.print("   " + colArray.getCount() + "\t");       //print column count
		}
		System.out.print("\nSUM....:");            
		for (int col=0; col < maxCols; col++)                           //loop through all columns                  
		{           
			double[] cols = Analytics.slice(dataPoints,  "col", col);	//slice the array vertically
			Analytics colArray = new Analytics(cols);					//create the object
			System.out.printf("%7.2f ", colArray.getSum());             //print column sum
		}
		System.out.print("\nAVERAGE:");            
		for (int col=0; col < maxCols; col++)                           //loop through all columns                  
		{           
			double[] cols = Analytics.slice(dataPoints,  "col", col);	//slice the array vertically
			Analytics colArray = new Analytics(cols);          			//create the object
			System.out.printf("%7.2f ", colArray.getAvg());             //print column average
		}
		System.out.print("\nMEDIAN.:");            
		for (int col=0; col < maxCols; col++)                           //loop through all columns                  
		{           
			double[] cols = Analytics.slice(dataPoints,  "col", col);	//slice the array vertically
			Analytics colArray = new Analytics(cols);    //create the object
			System.out.printf("%7.2f ", colArray.getMed());             //print column median value
		}
		System.out.print("\nMODE...:");            
		for (int col=0; col < maxCols; col++)                           //loop through all columns                  
		{           
			double[] cols = Analytics.slice(dataPoints,  "col", col);	//slice the array vertically
			Analytics colArray = new Analytics(cols);           		//create the object
			System.out.printf("%7.2f ", colArray.getMode());            //print column mode
		}
		System.out.print("\nMIN....:");            
		for (int col=0; col < maxCols; col++)                           //loop through all columns                  
		{           
			double[] cols = Analytics.slice(dataPoints,  "col", col);	//slice the array vertically
			Analytics colArray = new Analytics(cols);					//create the object
			System.out.printf("%7.2f ", colArray.getMin());             //print column minimum value
		}
		System.out.print("\nMAX....:");            
		for (int col=0; col < maxCols; col++)                           //loop through all columns                  
		{           
			double[] cols = Analytics.slice(dataPoints,  "col", col);	//slice the array vertically
			Analytics colArray = new Analytics(cols);           		//create the object
			System.out.printf("%7.2f ", colArray.getMax());             //print column maximum value
		}
		System.out.print("\nRANGE..:");            
		for (int col=0; col < maxCols; col++)                           //loop through all columns                  
		{           
			double[] cols = Analytics.slice(dataPoints,  "col", col);	//slice the array vertically
			Analytics colArray = new Analytics(cols);            		//create the object
			System.out.printf("%7.2f ", colArray.getRange());           //print column range
		}
		System.out.print("\nSTD-DEV:");            
		for (int col=0; col < maxCols; col++)                           //loop through all columns                  
		{           
			double[] cols = Analytics.slice(dataPoints,  "col", col);	//slice the array vertically
			Analytics colArray = new Analytics(cols);           		//create the object
			System.out.printf("%7.2f ", colArray.getStdDev());          //print column standard deviation
		}
		//------------------------------------------------------------------------------------
		//Call the toString() method to print the overall analytics, and once again for column 1
		//Perform final analytics (entire dataset)------------------ 
		//------------------------------------------------------------------------------------
		System.out.println("\n\n--------OVERALL-------------");
		double[] all = Analytics.slice(dataPoints, "all", 0); 
		Analytics allArray = new Analytics(all);						//create new object
		System.out.println(allArray.toString());                  		//call the instance toString() method 
		//------------------------------------------------------------------------------------
		//Print Analytics for Column 1---------------------------- 
		//------------------------------------------------------------------------------------
		System.out.println("\n--------COLUMN 1-------------");
		double[] col1 = Analytics.slice(dataPoints,"col",0);
		Analytics col1Array = new Analytics(col1);						//create new object
		System.out.println(col1Array.toString());                 		//call the instance toString() method 
	}
}
