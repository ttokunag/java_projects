/**
 * Author Tomoya Tokunaga(mailto: ttokunag@ucsd.edu)
 * About this file:
 * This class contains constructors & methods which implement 
 * the heap sort algorithm for String type in java.
 */
public class HeapSortString implements Sorting<String> {
	
	/*
	* description: a method returns a sorted array
	*
	* @param String[] array: a String array to be sorted
	* @param boolean ascending: if true returns the ascending order array
	* if false returns the descending order array
	*/
	@Override
	public void sort(String[] array, boolean ascending) {
		// heapify the string array
		StringHeap heap = new StringHeap(array);

		for (int i = array.length; i > 0; i--) {
			array[i-1] = heap.heappop();
		}

		if (!ascending) {
			// swap elements so as to obtain reverse data
			for (int i = 0; i < array.length / 2; i++) {
				String temp = array[i];
				array[i] = array[array.length - 1 - i];
				array[array.length - 1 - i] = temp;
			}
		}
	}

}
