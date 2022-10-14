/**
 * 
 */
package advancedAlgorithmAssignment;
import java.util.*;
/**
 * @title InterpolationSearch
 * @author Sepehr Rajabian
 * @date Apr 9, 2022
 * @desc This program can return the index value of a value that is desired to be searched and can show the lower and upper
 * bounds of where that value occurs in the ordered array. 
 */
public class InterpolationSearch {
	/**
	 * @pre a previously sorted array and a needle to find in the haystack along with positive lower and upper values. 
	 * @param needle
	 * @param haystack
	 * @return an int representing the index of a certain value in an array. 
	 * @post an int value representing the index of (at least one of) the value being searched for. -1 if none found. 
	 */
	public static int interpolationSearch(int needle, int[] haystack, int lower, int upper) {
		if (haystack[lower] == needle) return lower;
		if (haystack[upper] == needle) return upper;
		
		if (haystack[upper] - haystack[lower] <= 1 || needle > haystack[haystack.length-1] || needle < haystack[0]) return -1;
		//calculate percentile of the needle and narrow down:
		int value = lower + (int)((upper - lower)*((double)(needle - haystack[lower]) /(double)(haystack[upper] - haystack[lower])));
		if (haystack[value] == needle) 
			return value;
		else if (haystack[value] > needle) 
			return interpolationSearch(needle, haystack,lower, value - 1); //recurse with new range based on where 
		else if (haystack[value] < needle) 
			return interpolationSearch(needle, haystack, value + 1, upper);
		return -1;
	}
	/**
	 * @pre a start index  that contains the desired value to be searched for around that index in the initializd array. 
	 * @param haystack - int[]
	 * @param startIndex - int
	 * @return an array where index 0 is the lower bound and index 1 is the upper bound
	 * @post an array is returned with a lower and upper bound, the values are the same if its just one value. 
	 */
	public static int[] interpolationBounds(int[] haystack, int startIndex) {
		int[] bounds = new int[2];
		int indexValue = haystack[startIndex];
		int back_counter = 0;
		int front_counter = 0;
		boolean doneBack = false;
		boolean doneFront = false;
		//loop to find lower and upper bound based on the one value found
		while (!doneBack || !doneFront) {
			if (!doneBack) {
				if (startIndex - back_counter < 0)
					doneBack = true;
				else if (haystack[startIndex - back_counter] == indexValue)
					bounds[0] = startIndex - back_counter;
				else if (haystack[startIndex - back_counter] != indexValue)
					doneBack = true;
				back_counter++;
			}
			if (!doneFront) {
				if (startIndex + front_counter > haystack.length-1)
					doneFront = true;
				else if (haystack[startIndex + front_counter] == indexValue)
					bounds[1] = startIndex + front_counter;
				else if (haystack[startIndex + front_counter] != indexValue)
					doneFront = true;
				front_counter++;
			}
		}
		return bounds; 
	}
	/**
	 * @pre an initialized unordered array. 
	 * @param array - int[]
	 * @param left - int
	 * @param right - int
	 * @post a sorted array from least to greatest. 
	 */
	public static void sort(int[] array, int left, int right) {
		//classic quick sort
		if (left >= right) return;
		int k = left;
		int j = right;
		int pivotValue = array[(left + right) / 2]; 
		while (k < j) {
			while (array[k] < pivotValue)
				k++;
			while (pivotValue < array[j])
				j--;
			if (k <= j) {
				int temp1 = array[k]; 
				array[k] = array[j]; 
				array[j] = temp1;
				k++;
				j--;
			}
		}
		sort(array, left, j); 
		sort(array, k, right);
	}
	/** Main Program
	 * @param args 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] table = new int[500];
		//construct new table and print it out:
		for (int i = 0; i < 500; i++) {
			Random rand = new Random();
			table[i] = rand.nextInt(800, 1000);
		}
		sort(table, 0, table.length-1);
		System.out.println(Arrays.toString(table));
		boolean done = false;
		//ask user for values to check for repeatedly:
		while (!done) {
			try {
				Scanner scanner = new Scanner(System.in);
				System.out.print("\nEnter a positive integer value to search for (enter -1 to exit): ");
				int value = scanner.nextInt();
				if (value == -1) {
					scanner.close();
					System.out.println("Terminating.");
					done = true;
				} else if (value < 0)
					System.out.println("Error, enter positive integers.");
				else {
					int foundIndex = interpolationSearch(value, table, 0, table.length-1);
					if (foundIndex == -1)
						System.out.println("Value not found.");
					else {
						int[] bounds = interpolationBounds(table, foundIndex);
						if (bounds[0] == bounds[1])
							System.out.println("Found at index: " + bounds[0]);
						else {
							System.out.println("Lower Bound: " + bounds[0]);
							System.out.println("Upper Bound: " + bounds[1]);
						}
					}
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input type, enter integers.");
			}
		}
	}		
}

/*

Pseudo Code 

Functions:

function interpolationSearch(needle, haystack, lower, upper) {
	if (haystack[lower] == needle) return lower
	if (haystack[upper] == needle) return upper
	
	if (haystack[upper] - haystack[lower] <= 1 || needle > haystack[haystack.length-1] || needle < haystack[0]) return -1
	//calculate percentile of the needle:
	int value = lower + (int)((upper - lower)*((double)(needle - haystack[lower]) /(double)(haystack[upper] - haystack[lower])))
	if (haystack[value] == needle) 
		return value
	else if (haystack[value] > needle) 
		return interpolationSearch(needle, haystack,lower, value - 1) //recurse with new range based on where 
	else if (haystack[value] < needle) 
		return interpolationSearch(needle, haystack, value + 1, upper)
	return -1
} end interpolationSearch

function interpolationBounds(haystack, startIndex) {
	bounds = new int[2]
	indexValue = haystack[startIndex]
	back_counter = 0
	front_counter = 0
	boolean doneBack = false
	boolean doneFront = false
	loop while (!doneBack || !doneFront) 
		if (!doneBack) 
			if (startIndex - back_counter < 0)
				doneBack = true
			else if (haystack[startIndex - back_counter] == indexValue)
				bounds[0] = startIndex - back_counter
			else if (haystack[startIndex - back_counter] != indexValue)
				doneBack = true
			end if 
			back_counter++
		end if 
		if (!doneFront) 
			if (startIndex + front_counter > haystack.length-1)
				doneFront = true
			else if (haystack[startIndex + front_counter] == indexValue)
				bounds[1] = startIndex + front_counter
			else if (haystack[startIndex + front_counter] != indexValue)
				doneFront = true
			end if 
			front_counter++
		end if 
	end while loop
	return bounds 
} end interpolationBounds

function sort(array, left, right) {
	if (left >= right) return
	k = left
	j = right
	pivotValue = array[(left + right) / 2] 
	loop while (k < j) 
		loop while (array[k] < pivotValue)
			k++
		end while loop
		loop while (pivotValue < array[j])
			j--
		end while loop
		if (k <= j) 
			temp1 = array[k] 
			array[k] = array[j] 
			array[j] = temp1
			k++
			j--
		end if 
	end while loop
	sort(array, left, j) 
	sort(array, k, right)
} end sort

Main Program: 

int[] table = new int[500]
loop for i = 0, 500 
	Random rand = new Random()
	table[i] = rand.nextInt(800, 1000)
end for loop
sort(table, 0, table.length-1)
println(Arrays.toString(table))
done = false
loop while (!done) 
	try {
		initialize scanner 
		print("\nEnter a positive integer value to search for (enter -1 to exit): ")
		read value
		if (value == -1) 
			close scanner
			println("Terminating.")
			done = true
		else if (value < 0)
			println("Error, enter positive integers.")
		else 
			foundIndex = interpolationSearch(value, table, 0, table.length-1)
			if (foundIndex == -1)
				println("Value not found.")
			else {
				bounds = interpolationBounds(table, foundIndex)
				if (bounds[0] == bounds[1])
					println("Found at index: " + bounds[0])
				else 
					println("Lower Bound: " + bounds[0])
					println("Upper Bound: " + bounds[1])
				end if 
			end if 
		end if 
	} catch (InputMismatchException e) {
		println("Invalid input type, enter integers.")
	}
}


*/