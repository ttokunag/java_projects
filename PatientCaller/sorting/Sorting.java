/**
 * Author Tomoya Tokunaga(mailto: ttokunag@ucsd.edu)
 * About this file:
 * This class contains constructors & methods which implement 
 * the heap sort algorithm in java.
 */
public interface Sorting<T> {
	
	public abstract void sort(T[] array, boolean ascending);
	// a method to swap two elemnts
	public default void swap(T array[],int i,int j)
	{
		T tmp = array[i];
		array[i] = array[j];
		array[j] = tmp;
	}
	// a method to print out array elements
	public default void print(T array[])
	{
		for(T val:array)
		{
			System.out.print(val.toString()+" ");
		}
		System.out.println();
	}
}
