public class Driver {

	public static void main(String[] args) throws InterruptedException {
		Password obj = new Password("ABCDEFGHI");
		System.out.println("ORIGINAL PASSWORD : "+obj.showPassword());
		obj.encrypt();
		System.out.println("ENCRYPTED PASSWORD : "+obj.showPassword());
		obj.decrypt();
		System.out.println("DECRYPTED PASSWORD : "+obj.showPassword());
		
		// Calling encrypt and decrypt multiple times
		Thread t1 = null;
		Thread t2 = null;
		t1 = new EncrypterUtil(obj);
		t1.setName("encrypt_T"+0);
		t2 = new DecrypterUtil(obj);
		t1.setName("decrypt_T"+0);
		
		t1.start();
		t2.start();
		t1.join();
		System.out.println(obj.showPassword());
		t2.join();
		System.out.println(obj.showPassword());
		
		// Calling shift multiple times
		Thread t3 = null;
		Thread t4 = null;
		t3 = new CipherShifter(obj);
		t3.setName("shift_T"+0);
		t3.start();
		System.out.println(obj.showPassword());
		t4 = new CipherShifter(obj);
		t4.setName("shift_T"+0);
		t4.start();
		t3.join();
		System.out.println(obj.showPassword());
		t4.join();
		System.out.println("CORRECT OPERATION RESULT - NO CHANGE IN INPUT:"+obj.showPassword());
	}
}
