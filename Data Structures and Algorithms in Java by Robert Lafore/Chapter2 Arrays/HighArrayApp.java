package chapter2arrays;
// HighArrayApp.java
//demonstrates array class with high-level interface
//to run this program: C:>java HighArrayApp

class HighArray {
	private long[] a;                        //ref to array a
	private int nElems;                      //number of data items
	//---------------------------------------
	public HighArray(int max) {              //constructor
		a = new long[max];
		nElems = 0;
	}
	//---------------------------------------
	public boolean find(long searchKey) {    //looking for a data
		int j;
		for(j=0; j<nElems; j++)
			if(a[j] == searchKey)
				break;
		if(j == nElems)
			return false;                    //can't find it
		else 
			return true;                     //found it
	} //end find()
	//---------------------------------------
	public void insert(long value) {         //insert a data
		a[nElems] = value;
		nElems++;
	}
	//---------------------------------------
	public boolean delete(long value) {		//delete a data
		int j;
		for(j=0; j<nElems; j++)				
			if(a[j] == value)				//find the location
				break;
		if(j == nElems)
			return false;
		else {							//delete the data
			for(int k=j; k<nElems; k++)
				a[k] = a[k+1];
			nElems--;					//decrement size
			return true;
		}
	} // end delete()
	//---------------------------------------
	public void display() {
		for(int j=0; j<nElems; j++)
			System.out.print(a[j] + " ");
		System.out.println("");
	}
	//---------------------------------------
} // end class HighArray

//***************************************************************************
public class HighArrayApp {

	public static void main(String[] args) {
		int maxSize = 100;								//array size
		HighArray arr;									//reference to array
		arr = new HighArray(maxSize);					//create the array

		arr.insert(77);									//insert itmes
		arr.insert(99);
		arr.insert(44);
		arr.insert(55);
		arr.insert(22);
		arr.insert(88);
		arr.insert(11);
		arr.insert(00);
		arr.insert(66);
		arr.insert(33);

		arr.display();									//display items

		int searchKey = 35;								//search for items
		if(arr.find(searchKey))
			System.out.println("Found " + searchKey);
		else
			System.out.println("Can't find " + searchKey);

		arr.delete(00);									//delete items
		arr.delete(55);
		arr.delete(99);

		arr.display();									//display items again
	}  // end main()

} // end class HighArrayApp
