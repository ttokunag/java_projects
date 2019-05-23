/**
 * Author Tomoya Tokunaga(mailto: ttokunag@ucsd.edu)
 * About this file:
 * This class contains constructors & methods which implement 
 * the heap sort algorithm in java.
 */
public class HeapSortGeneric<E> {
	
	private E[] data;
	private int[] priority;
	private Heap heap;
	
	/*
	* description: a constructor which initialize a heap
	* This sort the given elements based of their priorirty
	*
	* @param E[] data: a data array to be sorted
	* @param int[] priority: an array of priority of each element in the data array
	*/
	public HeapSortGeneric(E[] data, int[] priority) {
		this.data = data;
		this.priority = priority;
		
		Tuple[] arr = new Tuple[data.length];
		for (int i = 0; i < data.length; i++) {
			arr[i] = new Tuple(priority[i], data[i]);
		}

		heap = new Heap(arr);
		for (int i = data.length; i > 0; i--) {
			data[i-1] = (E)heap.heappop().value;
		}
	}
	
	/*
	* description: a method returns a sorted array
	*
	* @param boolean ascending: if true returns the ascending order array
	* if false returns the descending order array
	*/
	public E[] sort(boolean ascending) {
		if (ascending) {
			return this.data;
		}
		else {
			for (int i = 0; i < data.length / 2; i++) {
				E temp = data[i];
				data[i] = data[data.length - 1 - i];
				data[data.length - 1 - i] = temp;
			}
			return data;
		}
	}
}
