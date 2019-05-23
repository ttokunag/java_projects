/**
 * @author      Tomoya Tokunaga <ttokunag@ucsd.edu>
 */
public class CipherShifter extends Thread
{
	private Password obj = null;
	
	/*
     * Constructor
     *
     * Description: this class encrypts/decrypts a pssword further
     *
     * @param:  Password
     * @return: CipherShifter class
     */
	public CipherShifter(Password obj)
	{
		this.obj = obj;
	}
	
	/*
     * Name:      run
     * Purpose:   to encrypt/decrypt a password
     * Parameter: None
     * Return:    void
     */
	@Override
	public void run() {
		synchronized(this.obj) {
			char[] array = this.obj.getArray();
			for (int i = 0; i < array.length; i++) {
				int ascii = (int) array[i];
				if (ascii%2 == 0) {
					array[i] = (char) (ascii + 1);
				}
				else if (ascii%2 == 1)
				{
					array[i] = (char) (ascii - 1);
				}
			}
			this.obj = new Password(new String(array));
		}
	}
}