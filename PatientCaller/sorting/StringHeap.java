import java.util.Arrays;

/**
 * Author Tomoya Tokunaga(mailto: ttokunag@ucsd.edu)
 * About this file:
 * This class contains constructors & methods which implement 
 * the heap data structure for String type in java.
 */
public class StringHeap {
	
	private String[] data;
	private int last_idx;
	
	/*
	* description: a constructor which initialize a heap
	*
	* @param None
	*/
	public StringHeap() {
		data = new String[1];
		last_idx = 0;
	}
	
	/*
	* description: a constructor which initialize a heap. This heapifies
	* the given String array
	*
	* @param String[] arr: a String array to be sorted
	*/
	public StringHeap(String arr[]) {
		// initializes an array to be sorted
		data = new String[arr.length + 1];
		last_idx = arr.length;
		// index at zero will not be used, so set it empty
		data[0] = "null";
		// copy elements to the data
		for (int i = 1; i <= last_idx; i++) {
			data[i] = arr[i-1];
		}
		// heapifies the array
		for (int i = last_idx / 2; i >= 1; i--) {
			this.maxHeapPercolateDown(i);
		}
	}
	
	/*
	* description: a method heapifies the given element and its children
	*
	* @param int: an index of an element to be heapified
	*/
	public void heapify(int i) {
		// throw an exception if i is out of bounds
		if (i > data.length - 1) {
			throw new IllegalArgumentException("Cannot Heapify index greater than heap size.");
		}
		if (i <= 0) {
			throw new IllegalArgumentException("The index must be larger than 0.");
		}

		int childIndex = 2 * i;
		String value = data[i];
		String maxValue = value;
		int maxIndex = -1;
		for (int j = 0; (j < 2) && (i + j < last_idx + 1); j++) {
			if (data[childIndex + j].compareTo(data[i]) > 0) {
				maxValue = data[childIndex + j];
				maxIndex = childIndex + j;
			}
		}
		if (maxValue == value) {
			return;
		}
		else {
			String temp = data[i];
			data[i] = data[maxIndex];
			data[maxIndex] = temp;
		}
	}
	
	/*
	* description: a method returns the max-prioritized element
	*
	* @param None
	*/
	public String getMax() {
		if (data.length == 1) {
			return null;
		}
		else {
			// the root of the heap is stored in the first index
			return data[1];
		}
	}
	
	/*
	* description: a method pops the max-prioritized element
	*
	* @param None
	*/
	public String heappop() {
		if (last_idx == 0) {
			return null;
		}
		// swap the root and the last node, then remove the last index
		String popped = data[1];
		data[1] = data[last_idx];
		data[last_idx] = "";
		last_idx--;

		for (int i = last_idx / 2; i >= 1; i--) {
			this.maxHeapPercolateDown(i);
		}

		return popped;
	}
	
	/*
	* description: a method returns the number of elements in the heap
	*
	* @param int: an index of an element to be heapified
	*/
	public int getSize()
	{
		// the element at the index 0 is not a part of the heap
		return last_idx;
	}
		
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return Arrays.toString(data);
	}

	/*
	* description: a helper method which compares an element at the 
	* given index with its children elements until it's placed at proper 
	* position in the heap
	*
	* @param int nodeIndex: the index of the element to be heapified
	*/
	public void maxHeapPercolateDown(int nodeIndex) {
		int childIndex = 2 * nodeIndex;
		String value = data[nodeIndex];
	 
		while (childIndex < last_idx + 1) {
		   // Find the max among the node and all the node's children
		   String maxValue = value;
		   int maxIndex = -1;
		   for (int i = 0; (i < 2) && (childIndex + i < last_idx + 1); i++) {
			  if (data[childIndex + i].compareTo(maxValue) > 0) {
				  maxValue = data[childIndex + i];
				  maxIndex = childIndex + i;
			  }
		   }
	 
		   if (maxValue == value) {
			  return;
		   }
		   else {
			   String temp = data[nodeIndex];
			   data[nodeIndex] = data[maxIndex];
			   data[maxIndex] = temp;
			   nodeIndex = maxIndex;
			   childIndex = 2 * nodeIndex;
		   }
		}
	}
	
	/*
	* description: a helper method which compares an element at the 
	* given index with its parent elements until it's placed at proper 
	* position in the heap
	*
	* @param int nodeIndex: the index of the element to be heapified
	*/
	public void maxHeapPercolateUp(int nodeIndex) {
		while (nodeIndex > 1) {
		   int parentIndex = (nodeIndex) / 2;
		   if (data[nodeIndex].compareTo(data[parentIndex]) <= 0) {
			   return;
		   }
		   else {
				String temp = data[parentIndex];
				data[parentIndex] = data[nodeIndex];
				data[nodeIndex] = temp;
				nodeIndex = parentIndex;
		   }
		}
	}
}
