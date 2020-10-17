package controllers;

import java.sql.ResultSet;
import java.sql.SQLException;

import helpers.DBC;
import helpers.Hash;

public class User {

	private DBC dbc;
	private String uemail, upass;
	
	public User() {
		dbc = new DBC();
	}
	
	public String registerUser(String name, String email, String password) {
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
						Hash hash = new Hash();
						String encrypted = hash.encrypt(password);
						
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
					
					Hash hash = new Hash();
					String encrypted = hash.encrypt(password);
					
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
	
	public Boolean logIn(String email, String password) {
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
					this.uemail = rs.getString("username");
					this.upass = rs.getString("password");
					if(uemail.equals(email)&&upass.equals(password)) {
						System.out.println("Successfuly logged in.");
						return true;
					}
					else {
						System.out.println("Incorrect username or password.");
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
}
