package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import helpers.DBC;
import helpers.Hash;
import helpers.Response;

public class User {

	private static DBC dbc = new DBC();
	private static String uemail, upass;
	
	
	
	public static String registerUser(String name, String email, String password) {
		if(name.isEmpty()||email.isEmpty()||password.isEmpty()) {
			return "Empty field detected.";
		}
		else {
			try {
				dbc.addParameter(email);
				dbc.prepare(dbc.getSentence("searchUser"));
				ResultSet rs = dbc.exQuery();
				try {
					if(rs.next()) {
						return "There exists an account with this email.";	
					}
					else {
						String encrypted = Hash.encrypt(password);
						
						dbc.addParameter(name);
						dbc.addParameter(email);
						dbc.addParameter(encrypted);
						dbc.prepare(dbc.getSentence("registerUser"));
						try {
							dbc.exQuery();
							return"{'message': 'user already exist', 'status': 503 }";
						}
						catch(SQLException s) {
							return "Error detected. Try again.";
						}
					}
					
				}
				catch(NullPointerException e) {
					
					String encrypted = Hash.encrypt(password);
					
					dbc.addParameter(name);
					dbc.addParameter(email);
					dbc.addParameter(encrypted);
					dbc.prepare(dbc.getSentence("registerUser"));
					try {
						dbc.exQuery();
						//return "User registered.";
						return "{message:'User registered.',status:200}";
						  //return"{'message': 'user already exist', 'status': 503 }";
					}
					catch(SQLException s) {
						return "Error detected. Try again.";
					}
				}
			}
			catch(SQLException e) {
				return "Error detected. Try again.";
			}
		}
	}
	
	public static Boolean logIn(String email, String password) {
		if(email.equals("")||password.equals("")) {
			System.out.println("Empty field. Login denied.");
			return false;
		}
		else {
			dbc.addParameter(email);
			dbc.prepare(dbc.getSentence("searchUser"));
			try {
				ResultSet rs = dbc.exQuery();
				if(rs.next()) {
					uemail = rs.getString("email");
					upass = rs.getString("password");
					if(uemail.equals(email)&&upass.equals(password)) {
						System.out.println("Successfuly logged in.");
						return true;
					}
					else {
						System.out.println("Incorrect email or password.");
						return false;
					}	
				}
				else {
					System.out.println("User not found.");
					return false;
				}
			} catch(SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
	}
	
	public static String getUserInfo(Object item) {
		
		dbc.addParameter((String)item);
		dbc.prepare(dbc.getSentence("searchUser"));
		try {
			ResultSet rs = dbc.exQuery();
			return"{'message': 'User Found', 'email': " + rs.getString("email") + ", 'name': " + rs.getString("name") + ", 'status': 200 }";
		}
		catch(SQLException s) {
			return "Error detected. Try again.";
		}
	}
	
	public static String getUserList(Object item) {
		
		String answer ="";
		dbc.addParameter((String)item);
		dbc.prepare(dbc.getSentence("searchUserList"));
		try {
			ResultSet rs = dbc.exQuery();
			answer = Response.userList(rs);
			return answer;
		}catch(SQLException | NullPointerException e) {
			return answer;
		}
	}

	public static String modifyUser(String name, String email, String password, Object original) {
		
		String encrypted = Hash.encrypt(password);
		
		dbc.addParameter(name);
		dbc.addParameter(email);
		dbc.addParameter(encrypted);
		dbc.addParameter((String) original);
		dbc.prepare(dbc.getSentence("modifyUser"));
		try {
			dbc.exQuery();
			return "{message:'User modified.',status:200}";
		}
		catch(SQLException s) {
			return "Error detected. Try again.";
		}
	}
	
	public static String deleteUser(Object object) {
		
		dbc.addParameter((String) object);
		dbc.prepare(dbc.getSentence("deleteUser"));
		try {
			dbc.exQuery();
			return "{message:'User deleted.',status:200}";
		}
		catch(SQLException s) {
			return "Error detected. Try again.";
		}
	}
	
}
