package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import helpers.Auth;

public class LoginController {
	
	 public static String login(HttpServletRequest request) {
	        if(Auth.authCheck(request.getParameter("email"), request.getParameter("pass"))) {
			    HttpSession session = request.getSession();
			    session.setAttribute("user", request.getParameter("email"));
			    return"{'message': 'Login Successful', 'redirect': '/home', 'email': " + request.getParameter("email") + ", 'status': 200 }";
			} else {
			    return"{'message': 'Credentials Error', 'status': 500 }";
			}
	    }
}


