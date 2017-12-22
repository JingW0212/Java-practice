package homework;
import java.util.*;
//------------------------------------------------------------------------------------
//Class containing methods to perform analytics on an array of numbers
//Methods are:  count(), sum(), avg(), median(), mode(), min(), max(), range(), stdDev()
//             slice():     takes a 2dim array, and returns a row or col slice 
//             transpose(): takes a 2dim array, and transpose it. x/y becomes y/x 
//------------------------------------------------------------------------------------
public class Analytics
{
	//Create fields, one for a double[] array, and others for count, sum, average, median, min, max, stdDev
	private double[] array;
	private int count;
	private double sum;
	private double average;
	private double median;
	private double mode;
	private double min;
	private double max;
	private double range;
	private double stdDev;
	//--------------------------------------------------------------------------------------------
	//Create a constructor that takes a double[] array, normalizes it (cleans it up), and stores it in the array field above 
	//Constructor should call all the instance methods to compute the count, sum, average, median, min, max, stdDev
	//--------------------------------------------------------------------------------------------
	public Analytics (double[] arr)
	{
		array = normalize(arr);
		count = count();
		sum = sum();
		average = avg();
		median = median();
		mode = mode();
		min = min();
		max = max();
		range = range();
		stdDev = stdDev();

	}
	//--------------------------------------------------------------------------------------------
	//Create a getter method for each of the fields count, sum, average, median, min, max, stdDev
	//--------------------------------------------------------------------------------------------
	int getCount()
	{
		return(count);
	}
	double getSum()
	{
		return(sum);
	}
	double getAvg()
	{
		return(average);
	}
	double getMed()
	{
		return(median);
	}
	double getMode()
	{
		return(mode);
	}
	double getMin()
	{
		return(min);
	}
	double getMax()
	{
		return(max);
	}
	double getRange()
	{
		return(range);
	}
	double getStdDev()
	{
		return(stdDev);
	}

	//--------------------------------------------------------------------------------
	//normalize: Returns a new clean array that has all the bad/no data removed 
	//--------------------------------------------------------------------------------
	public double[] normalize(double[] array)
	{
		int i     = 0;
		int count = 0;

		for (double col : array)                    //loop through all columns                  
			if (col != Double.MIN_VALUE)            //if smallest double value (assume bad/no data)
				count += 1;                         //add 1 to counter

		double[] array2 = new double[count];        //create a new array

		for (double col : array)                    //loop through array                  
			if (col != Double.MIN_VALUE)            //if not bad data
				array2[i++] = col;                  //copy into array2                        

		return array2;                              
	}

	//--------------------------------------------------------------------------------
	//count: Count all elements of a 1 dim array
	//--------------------------------------------------------------------------------
	int count()
	{
		return array.length;                        //count = array.length                              
	}
	//--------------------------------------------------------------------------------
	//sum: Sum all elements of an array
	//--------------------------------------------------------------------------------
	double sum()
	{
		double total = 0;
		for (double col : array)                     //loop through all columns                  
			total += col;                            //add to total
		return total;                                
	}
	//--------------------------------------------------------------------------------
	//avg: Average all elements of an array
	//--------------------------------------------------------------------------------
	double avg()
	{
		int    count = count();                 //call count method
		double sum   = sum();                   //call sum method
		return sum/count;                                
	}
	//--------------------------------------------------------------------------------
	//median: Returns the median value on an array
	//--------------------------------------------------------------------------------
	double median()
	{        
		double[] array2 = Arrays.copyOf(array, array.length);   //copy the array into array2                   
		Arrays.sort(array2);                                    //sort array2

		int count = count();                   					//call count method

		int mid1 = count/2;
		int mid2 = (count-1)/2;
		double median = (count%2 == 1)             				//if count is odd
				?  array2[mid1]                         		//median= mid point                                                               
						: (array2[mid1] + array2[mid2]) / 2;    //median= average of 2 mid points                                                               

				return median;                              
	}
	//--------------------------------------------------------------------------------
	//mode: Returns the value that occurs most
	//--------------------------------------------------------------------------------
	double mode()
	{
		int maxCount = 0;
		double maxValue = 0;
		for (int i = 0; i < array.length; ++i) {
			int count = 0;
			for (int j = 0; j < array.length; ++j) {
				if (array[j] == array[i]) ++count;
			}
			if (count > maxCount) {
				maxCount = count;
				maxValue = array[i];
			}
		}
		return maxValue;
	}
	//--------------------------------------------------------------------------------
	//min: Returns the minimum value within an array
	//--------------------------------------------------------------------------------
	double min()
	{
		double minimum = Double.POSITIVE_INFINITY;   //start with largest possible value
		for (double col : array)                     //loop through all columns                  
			if (col < minimum) minimum = col;        //if col is less than minimum, save it in mimimum
		return minimum;                              
	}
	//--------------------------------------------------------------------------------
	//max: Returns the maximum value within an array
	//--------------------------------------------------------------------------------
	double max()
	{
		double maximum = Double.NEGATIVE_INFINITY;   //start with lowest possible value
		for (double col : array)                     //loop through all columns                  
			if (col > maximum) maximum = col;        //if col is more than maximum, save it in maximum
		return maximum;                              
	}
	//--------------------------------------------------------------------------------
	//range: Returns the difference between the largest and smallest values
	//--------------------------------------------------------------------------------
	double range()
	{
		return max - min;
	}
	//--------------------------------------------------------------------------------
	//stdDev: Returns the standard deviation of an array
	//       It is a measure of the amount of variation of a set of data values
	//       Low stdDev means the values are close to the average (or are tight) 
	//       1. take average of array
	//       2. take the difference (delta) of each element to the average
	//       3. take the square of that delta
	//       4. add all those square of deltas
	//       5. divide the square of deltas by count of elements
	//       6. take the square root of item 5.  
	//--------------------------------------------------------------------------------
	double stdDev()
	{
		int    count   = count();              //call count method
		double sum     = sum();                //call sum method
		double average = sum/count;

		double sqDelta = 0;                         //square of deltas      
		for (double col : array)                    //loop through all columns                  
			sqDelta += Math.pow(col-average,2);     //add to square of delta

		double std_dev = Math.sqrt(sqDelta/count);  //square root of average(square of deltas)
		return std_dev;                              
	}
	//--------------------------------------------------------------------------------
	//toString: Returns the array as well as all the analytic computations 
	//--------------------------------------------------------------------------------
	public String toString()
	{
		String str = "Data points: " + Arrays.toString(this.array);
		str += "\nCount......: " + count(); 
		str += "\nSum........: " + sum(); 
		str += "\nAverage....: " + avg(); 
		str += "\nMedian.....: " + median(); 
		str += "\nMode.......: " + mode();
		str += "\nMinimum....: " + min(); 
		str += "\nMaximum....: " + max(); 
		str += "\nRange......: " + range();
		str += "\nStd.Dev....: " + stdDev(); 	
		return str;                              
	}

	//--------------------------------------------------------------------------------
	//slice: Takes  a 2 dimensional array, slice type (row/col/all), and index
	//       Return a single dimention array for that row or column or all cells
	//--------------------------------------------------------------------------------
	static double[] slice(double[][] array2dim, String type, int idx)
	{
		int      size  = 0;
		double[] array = null;

		if (type.equals("row"))                            		//ROW slice
		{
			size  = array2dim[idx].length;                      //determine the needed array size           
			array = Arrays.copyOf(array2dim[idx], size);       	//copy that row into a 1dim array
		}
		if (type.equals("col"))                            		//COL slice
		{           
			size  = array2dim.length;                           //determine the needed array size           
			array = new double[size];                           //create a new array of that size
			for (int i=0; i < size ; i++)                       //loop through all rows
			{
				try {                                           //try to: 
					array[i] = array2dim[i][idx];               //copy cell from 2dim into 1dim array                     
				}
				catch (Exception e) {                           //if exception:
					array[i] = Double.MIN_VALUE;                //cell does not exist, populate with min value
				}
			}           
		}
		if (type.equals("all"))                            		//ALL slice (turn a 2dim array to 1 dim)
		{           
			for (double[] row : array2dim)                      //loop through all rows                     
				size += row.length;                             //compute the needed array size                              
			array = new double[size];                           //create a new array of that size
			int i = 0;
			for (double[] row : array2dim)                      //loop through all rows
				for (double col : row)                          //loop through all columns
					array[i++] = col;                           //copy cell into 1dim array                         
		}

		//    System.out.println(size + Arrays.toString(array));//debug only
		return array;
	}
	//--------------------------------------------------------------------------------
	//transpose: Takes  a 2 dimensional array
	//          Return a transposed 2 dimensional array
	//--------------------------------------------------------------------------------
	static double[][] transpose(double[][] array2dim)
	{
		int rowNum  = array2dim.length;                     //compute number of rows
		int colNum  = 0;                                    //compute number of columns

		for (double[] row : array2dim)                      //loop through all rows                     
			if (row.length > colNum)                        //take the size of the longest row                              
				colNum = row.length;                        //this becomes the number of columns        

		double[][] newArray = new double[colNum][rowNum];   //create new array
		//notice [row][col] dimensions are transposed
		int col2 = 0;
		for (int row=0; row < array2dim.length; row++)              //loop thru original rows
		{
			int row2 = 0;                                           
			for (int col=0; col < array2dim[row].length; col++)     //loop thru original columns
			{
				newArray[row2][col2] = array2dim[row][col];         //copy into new array           
				row2++;                                             //add 1 to row of new array
			}
			col2++;                                                 //add 1 to col of new array
		}   
		return newArray;
	}
	//--------------------------------------------------------------------------------
}
