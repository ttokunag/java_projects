import heaps.Heap;
import heaps.Tuple;

/**
 * Author Tomoya Tokunaga(mailto: ttokunag@ucsd.edu)
 * About this file:
 * This class contains constructors & methods which implement 
 * the max heap data structure in java.
 */
public class TriageFacility {
	
	private Heap heap;
	
	/*
	* description: a constructor which initialize a heap
	*
	* @param String[] patients: an array of data to be sorted
	*/
	public TriageFacility(String[] patients) {

		Tuple[] patientsList = new Tuple[patients.length];
		// split the data into patients' names and ages
		for (int i = 0; i < patients.length; i++) {
			String[] split = patients[i].split(":");
			for (int j = 0; j < split.length; j++) {
				split[j] = split[j].trim();
			}
			patientsList[i] = new Tuple(Math.abs(95 - Integer.parseInt(split[1])), split[0]);
		}
		// heapify the data
		heap = new Heap(patientsList);
	}
	
	/*
	* description: a method returns the name of the highest-priority paitent
	*
	* @param None
	*/
	public String serviceNextPatient() {
		// pops the max element from the heap
		Tuple highestPatient = heap.heappop();
		return String.valueOf(highestPatient.value);
	}
	
	/*
	* description: a method which add a new patient to the heap
	*
	* @param String patient: a patient information to be added to the heap
	*/
	public void addNewPatient(String patient) {
		// splits the data into the name and the age
		String[] patientData = patient.split(":");
		for (int i = 0; i < patientData.length; i++) {
			patientData[i] = patientData[i].trim();
		}
		Tuple newPatient = new Tuple(Math.abs(95 - Integer.parseInt(patientData[1])), patientData[0]);
		// heapify the patient information
		heap.heappush(newPatient);
	}
	
	/*
	* description: a method which returns the number of patients
	*
	* @param None
	*/
	public int getTriageLength() {
		return heap.getSize();
	}
}
