package controllers;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import helpers.Auth;

public class LoginController {
	
	 public static String login(HttpServletRequest request) {
	        try {
	            if(Auth.authCheck(request.getParameter("name"), request.getParameter("pass"))) {
	                HttpSession session = request.getSession();
	                session.setAttribute("user", request.getParameter("name"));
	                return"'"message": "Login Successful", "redirect": "/home", "user": "" + request.getParameter("name") + "", "status": 200 }";
	            } else {
	                return"{"message": "Credentials Error", "status": 500 }";
	            }
	        } catch (SQLException e) {
	            // TODO Auto-generated catch block
	            return"{"message": "Login Error", "status": 500 }";
	        }
	    }
}


