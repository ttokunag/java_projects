/**
 * @author      Tomoya Tokunaga <ttokunag@ucsd.edu>
 */
public class UserDetails {
	
	private char username[];
	private Password password;
	private boolean encrypted;
	
	/*
     * Constructor
     *
     * Description: this class is about a user name and a password
     *
     * @param:  String(for a user name), String(for a password)
     * @return: UserDetails class
     */
	public UserDetails(String username, String password) {
		this.username = username.toCharArray();
		this.password = new Password(password);
	}
		
	/*
     * Name:      getUserDetails
     * Purpose:   to get user details
     * Parameter: None
     * Return:    String
     */
	public String getUserDetails() {
		return new String(this.showUserName() + "|" + this.showPassword());
	}

	/*
     * Name:      getUserName
     * Purpose:   to get a user name
     * Parameter: None
     * Return:    String
     */
	public String showUserName()
	{
		return new String(this.username);
	}

	/*
     * Name:      showPassword
     * Purpose:   to get a password
     * Parameter: None
     * Return:    String
     */
	public String showPassword() {
		return password.showPassword();
	}

	
	/*
     * Name:      encrypt
     * Purpose:   to encrypt a password
     * Parameter: None
     * Return:    void
     */
	public void encrypt()
	{
		if (encrypted) {
			return;
		}
		else
		{
			synchronized(this) {
				Thread threadUser = new Thread(new Runnable() {
					public void run() {
						for (int i = 0; i < username.length; i++) {

							username[i] = (char) ((int) username[i] + 5);
						}
						try {
							Thread.sleep(6);
						} catch (InterruptedException ie) {
							System.out.println(ie);
						}
					}
				});
				Thread threadPass = new Thread(new Runnable() {
					public void run() {
						password.encrypt();
					}
				});
				threadUser.start();
				threadPass.start();

				try {
					threadUser.join();
					threadPass.join();
				} catch (InterruptedException ie) {
					System.out.println(ie);
				}

			}
			encrypted = true;
		}
	}
	
	/*
     * Name:      decrypt
     * Purpose:   to decrypt a password
     * Parameter: None
     * Return:    void
     */
	public void decrypt()
	{
		if (!encrypted) {
			return;
		}
		else
		{
			synchronized(this) {
				Thread threadUser = new Thread(new Runnable() {
					public void run() {
						for (int i = 0; i < username.length; i++) {
							username[i] = (char) ((int) username[i] - 5);
						}
						try {
							Thread.sleep(11);
						} catch (InterruptedException ie) {
							System.out.println(ie);
						}
					}
				});
				Thread threadPass = new Thread(new Runnable() {
					public void run() {
						password.decrypt();
					}
				});
				threadUser.start();
				threadPass.start();

				try {
					threadUser.join();
					threadPass.join();
				} catch (InterruptedException ie) {
					System.out.println(ie);
				}

			}
			encrypted = false;
		}
	}
}
