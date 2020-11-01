package helpers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
	
	public static String encrypt(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			String pass = password+"secret";
			md.update(pass.getBytes());
			byte[] digest = md.digest();
			StringBuffer hex = new StringBuffer();
			for(int i=0; i<digest.length; i++) {
				hex.append(Integer.toHexString(0xFF & digest[i]));
			}
			String epass = hex.toString();
			md.reset();
			return epass;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}
}
