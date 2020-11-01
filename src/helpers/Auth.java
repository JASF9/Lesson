package helpers;

import controllers.User;

public class Auth {
	
	public static Boolean authCheck(String email, String password) {
		
		String epass = "";
		
		if(!password.equals("")){
		epass = Hash.encrypt(password);
		}
		 
		if(User.logIn(email, epass)) {
			return true;
		}
		else {
			return false;
		}
	}

}
