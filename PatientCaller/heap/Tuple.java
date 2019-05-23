/**
 * Author Tomoya Tokunaga(mailto: ttokunag@ucsd.edu)
 * About this file:
 * This class contains constructors & methods which implement 
 * the Tuple data structure in java.
 */
public class Tuple<E> {

	public final int priority;
	public final E value;

	public Tuple(int priority, E value) {
		this.priority = priority;
		this.value = value;
	}
	
	@Override
	public String toString() {
		if(null != value)
			return "("+priority+","+value.toString()+")";
		else
			return "("+priority+","+value+")";
	}
}
