/**
 * @author      Tomoya Tokunaga <ttokunag@ucsd.edu>
 */
public class UserEncrypterUtil extends Thread {
	private UserDetails obj = null;

	/*
     * Constructor
     *
     * Description: this class encrypts a password and a user name
     *
     * @param:  UserDetails
     * @return: UserEncrypterUtil class
     */
	public UserEncrypterUtil(UserDetails obj) {
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