/**
 * @author      Tomoya Tokunaga <ttokunag@ucsd.edu>
 */
public class DecrypterUtil extends Thread {
	private Password obj = null;

	/*
     * Constructor
     *
     * Description: this class decrypts a password
     *
     * @param:  Password
     * @return: DecrypterUtil class
     */
	public DecrypterUtil(Password obj) {
		this.obj = obj;
	}

	/*
     * Name:      run
     * Purpose:   to decrypt a password
     * Parameter: None
     * Return:    void
     */
	@Override
	public void run() {
		synchronized(this.obj) {
			for (int i = 0; i < 10; i++) {
				this.obj.decrypt();
			}
		}
	}
}