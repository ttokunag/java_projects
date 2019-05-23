import java.util.Arrays;

/**
 * Author Tomoya Tokunaga(mailto: ttokunag@ucsd.edu)
 * About this file:
 * This class contains constructors & methods which implement 
 * the max heap data structure in java.
 */

public class Heap {
	
	private Tuple[] data; //This stores data in accoradance with heap rules.
	private int last_idx; //This keeps track of last index where an element was inserted.
	
	/*
	* description: a constructor which initialize a heap
	*
	* @param None
	*/
	public Heap() {
		data = new Tuple[1];
		last_idx = 0;
	}
	
	/*
	* description: a constructor which initialize a heap. This heapifies
	* the given Tuple array
	*
	* @param Tuple[]: 
	* contains priority represented by an integer (larger is higher priority) and values
	*/
	public Heap(Tuple arr[]) {
		// last_idx is the number of elements in a heap
		last_idx = arr.length;
		// we set the 0-index element null
		data = new Tuple[last_idx + 1];
		data[0] = null;
		// assign values in the given array to the data
		for (int i = 1; i <= last_idx; i++) {
			data[i] = arr[i-1];
		}
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
		int value = data[i].priority;
		int maxValue = value;
		int maxIndex = -1;
		for (int j = 0; (j < 2) && (i + j < last_idx + 1); j++) {
			if (data[childIndex + j].priority > maxValue) {
				maxValue = data[childIndex + j].priority;
				maxIndex = childIndex + j;
			}
		}
		if (maxValue == value) {
			return;
		}
		else {
			Tuple temp = data[i];
			data[i] = data[maxIndex];
			data[maxIndex] = temp;
		}
	}
	
	/*
	* description: a method returns the max-prioritized element
	*
	* @param None
	*/
	public Tuple getMax() {
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
	public Tuple heappop() {
		if (last_idx == 0) {
			return null;
		}
		// swap the root and the last node, then remove the last index
		Tuple popped = data[1];
		data[1] = data[last_idx];
		data[last_idx] = new Tuple(Integer.MIN_VALUE, null);
		last_idx--;

		for (int i = last_idx / 2; i >= 1; i--) {
			this.maxHeapPercolateDown(i);
		}

		return popped;
	}
	
	/*
	* description: a method increases the priority of the given element
	*
	* @param int i: an index of an element to be prioritized
	* @param int priority: new integer valued priority
	*/
	public void increasePriority(int i, int priority) {
		// throw an exception if the given index is out of the range
		if (i <= 0) {
			throw new IllegalArgumentException("The index must be larger than 0.");
		}
		// throw an exception if the given priority is invalid
		if (priority <= this.data[i].priority) {
			throw new IllegalArgumentException("Priority less than Current");
		}
		// assign  a new tuple object
		this.data[i] = new Tuple(priority, this.data[i].value);
		// heapifies the data
		for (int j = last_idx / 2; j >= 1; j--) {
			this.maxHeapPercolateDown(j);
		}
	}
	
	/*
	* description: a method pushes a new element to the heap
	*
	* @param Tuple arg: an element to be pushed
	*/
	public void heappush(Tuple arg) {
		// if the array is full, expand it
		if (last_idx == data.length - 1) {
			Tuple[] newHeapArray = new Tuple[data.length + 1];
			for (int i = 0; i < data.length; i++) {
				newHeapArray[i] = data[i];
			}
			newHeapArray[data.length] = arg;
			// updata the last index
			last_idx = data.length;
			// replace the heap array with the new one
			data = newHeapArray;
		}
		// if the data has the space for the given element
		else {
			last_idx++;
			data[last_idx] = arg;
		}
		// places the new element in an appropriate position
		this.maxHeapPercolateUp(last_idx);
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
		int value = data[nodeIndex].priority;
	 
		while (childIndex < last_idx + 1) {
		   // Find the max among the node and all the node's children
		   int maxValue = value;
		   int maxIndex = -1;
		   for (int i = 0; (i < 2) && (childIndex + i < last_idx + 1); i++) {
			  if (data[childIndex + i].priority > maxValue) {
				  maxValue = data[childIndex + i].priority;
				  maxIndex = childIndex + i;
			  }
		   }
	 
		   if (maxValue == value) {
			  return;
		   }
		   else {
			   Tuple temp = data[nodeIndex];
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
		   if (data[nodeIndex].priority <= data[parentIndex].priority) {
			   return;
		   }
		   else {
				Tuple temp = data[parentIndex];
				data[parentIndex] = data[nodeIndex];
				data[nodeIndex] = temp;
				nodeIndex = parentIndex;
		   }
		}
	}
}
