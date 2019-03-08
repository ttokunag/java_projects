/**
 * @author      Tomoya Tokunaga <ttokunag@ucsd.edu>
 */
public class EncrypterUtil extends Thread {
	private Password obj = null;

	/*
     * Constructor
     *
     * Description: this class encrypts a password
     *
     * @param:  Password
     * @return: EncrypterUtil class
     */
	public EncrypterUtil(Password obj) {
		this.obj = obj;
	}

	/*
     * Name:      run
     * Purpose:   to encrypt a password
     * Parameter: None
     * Return:    void
     */
	@Override
	public void run() {
		synchronized(this.obj) {
			for (int i = 0; i < 10; i++) {
				this.obj.encrypt();
			}
		}
	}
}