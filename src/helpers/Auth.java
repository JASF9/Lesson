package helpers;

import controllers.User;

public class Auth {
	
	public static Boolean authCheck(String email, String password) {
		
			String epass = "";
			
			Hash hash = new Hash();
			if(!password.equals("")){
			epass = hash.encrypt(password);
			}
			
			User user = new User();
			 
			if(user.logIn(email, epass)) {
				System.out.println("Successfull log.");
				return true;
			}
			else {
				System.out.println("Could not log.");
				return false;
			}
	}

}
