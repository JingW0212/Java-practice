//LISTING 2.5
package chapter2arrays;
//classDataArray.java
//data items as class objects
//to run this program: C:>java ClassDataApp
///////////////////////////////////////////////

class Person {
	private String lastName;
	private String firstName;
	private int age;
	//-------------------------------------
	public Person(String last, String first, int a) {
		lastName = last;
		firstName = first;
		age = a;
	}
	//-------------------------------------
	public void displayPerson() {
		System.out.print(" Last name: " + lastName);
		System.out.print(", First name: " + firstName);
		System.out.println(", Age " + age);
	}
	//-------------------------------------
	public String getLast() {
		return lastName;
	}	
}	//end class Person
//////////////////////////////////////////////
class ClassDataArray {
	
	private Person[] a;
	private int nElems;
	
	public ClassDataArray(int max) {	//constructor
		a = new Person[max];	//create the array	
		nElems = 0;		//no items yet
	}
	//-------------------------------------
	public Person find(String searchName) {		//find specified value
		int j;
		for(j=0; j<nElems; j++)
			if( a[j].getLast().equals(searchName))
				break;
		if(j == nElems)		//not found
			return null;
		else
			return a[j];
	}	//end find
	//------------------------------------
	public void insert(String last, String first, int age)	{	//put person in array
		a[nElems] = new Person(last, first, age);
		nElems++;		//increment size
	}
	//------------------------------------
	public boolean delete(String searchName) {	//delete a person
		int j;
		for(j=0; j<nElems; j++)		//look for it
			if(a[j].getLast().equals(searchName))
				break;
		if(j==nElems)
			return false;	//Can't find it
		else {
			for(int k=j; k<nElems; k++)
				a[k]=a[k+1];
			nElems--;
			return true;
		}
	}	//end delete()
	//------------------------------------
	public void displayA() {		//display array contents
		for(int j=0; j<nElems; j++)
			a[j].displayPerson();
	}
	
}	//end class ClassDataArray


public class ClassDataApp {
	public static void main(String[] args) {
		int maxSize = 100;	//array size
		ClassDataArray arr;	//reference to array
		arr = new ClassDataArray(maxSize);
		
		arr.insert("Evans", "Patty", 24);
		arr.insert("Smith", "Lorraine", 37);
		arr.insert("Yee", "Tom", 43);
		arr.insert("Adams", "Henry", 63);
		arr.insert("Hashimoto", "Sato", 21);
		arr.insert("Stimson", "Henry", 29);
		arr.insert("Velasquez", "Jose", 72);
		arr.insert("Lamarque", "Henry", 54);
		arr.insert("Vang", "Minh", 22);
		arr.insert("Creswell", "Lucinda", 18);
		
		arr.displayA();		//display items
		
	
		String searchKey = "Stimson";
		Person found = arr.find(searchKey);
		if(found != null) {
			System.out.println("Found");
			found.displayPerson();
		}
		else
			System.out.println("Can't find " + searchKey);
		
		System.out.println("Deleting Smith, Yee, and Creswell");
		arr.delete("Smith");
		arr.delete("Yee");
		arr.delete("Creswell");
		
		arr.displayA();
	}	//end main()

}	//end class ClassDataApp
