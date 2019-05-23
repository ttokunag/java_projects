/**
 * @author      Tomoya Tokunaga <ttokunag@ucsd.edu>
 */
public class UserDecrypterUtil extends Thread {
	private UserDetails obj = null;

	/*
     * Constructor
     *
     * Description: this class decrypts a password and a user name
     *
     * @param:  UserDetails
     * @return: UserDecrypterUtil class
     */
	public UserDecrypterUtil(UserDetails obj) {
		this.obj = obj;
	}

	/*
     * Name:      run
     * Purpose:   to decrypt a password and a user name
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