/**
 * @author      Tomoya Tokunaga <ttokunag@ucsd.edu>
 */
public class Password
{
	char array[];

	/*
     * Constructor
     *
     * Description: this class is about a password
     *
     * @param:  String, a password
     * @return: Password class
     */
	public Password(String password) {
		this.array = password.toCharArray();
	}

	/*
     * Name:      showPassword
     * Purpose:   to return a password in a string form
     * Parameter: None
     * Return:    String
     */
	public String showPassword()
	{
		return new String(this.array);
	}

	/*
     * Name:      getArray
     * Purpose:   to return a character array of a password
     * Parameter: None
     * Return:    character array
     */
	public char[] getArray() {
		return this.array;
	}

	/*
     * Name:      encrypt
     * Purpose:   to encrypt a password
     * Parameter: None
     * Return:	  void
     */
	public void encrypt()
	{
		Thread t = new Thread(new Runnable() {
			public void run() {
				synchronized(array) {

					for (int i = 0; i < array.length; i++) {
						array[i] = (char) ((int) array[i] + 5);
					}

					try {
						Thread.sleep(5);
					} catch (InterruptedException ie) {
						System.out.println(ie);
					}
				}
			}
		});
		t.start();
		try {
			t.join();
			t.join();
		} catch (InterruptedException ie) {
			System.out.println(ie);
		}
	}

	
	/*
     * Name:      decrypt
     * Purpose:   to decrypt a password
     * Parameter: None
     * Return:	  void
     */
	public void decrypt()
	{
		Thread t = new Thread(new Runnable() {
			public void run() {
				synchronized(array) {
					for (int i = 0; i < array.length; i++) {
						array[i] = (char) ((int) array[i] - 5);
					}
					try {
						Thread.sleep(10);
					} catch (InterruptedException ie) {
						System.out.println(ie);
					}
				}
			}
		});
		t.start();
		try {
			t.join();
			t.join();
		} catch (InterruptedException ie) {
			System.out.println(ie);
		}
	}
}