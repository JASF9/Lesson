package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.User;
import helpers.Auth;
import helpers.Cors;

/**
 * Servlet implementation class Register
 */
@MultipartConfig()
@WebServlet("/Register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("_method").equals("DELETE")) {
			doDelete(request,response);
		}
		else {
			if(request.getParameter("_method").equals("PUT")) {
				doPut(request,response);
			}
			else {
				String message = User.getUserList(request.getSession().getAttribute("search"));
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println(message);
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		String message = User.registerUser(name, email, password);
		
		if(message.equals("{message:'User registered.',status:200}")) {
			response.sendRedirect("public/views/login.html");
		}
		else {
			response.sendRedirect("public/views/register.html");
		}
		
		//response.setContentType("application/json");
		//PrintWriter out = response.getWriter();
		//out.println(message);
		
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cors.apply(response);
		HttpSession session = request.getSession();
		if(request.getSession(false) != null) {
			
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String newpass = request.getParameter("newpass");
			
			if(Auth.authCheck((String)session.getAttribute("email"), password)) {
				//String message = User.modifyUser(name, email, newpass, session.getAttribute("email"));
				User.modifyUser(name, email, newpass, session.getAttribute("email"));
				response.sendRedirect("public/views/login.html");
			}
			else {
				response.sendRedirect("public/views/updateUser.html");
			}
			
			//response.setContentType("application/json");
			//PrintWriter out = response.getWriter();
			//out.println(message);
		}
		else {
			response.sendRedirect("public/views/login.html");
		}	
	}
	
	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		if(request.getSession(false) != null) {	
			String message = User.deleteUser(session.getAttribute("email"));
			if(message.equals("{message:'User deleted.',status:200}")) {
				response.sendRedirect("public/views/register.html");
			}
			else {
				response.sendRedirect("public/views/deleteUser.html");
			}
			//response.setContentType("application/json");
			//PrintWriter out = response.getWriter();
			//out.println(message);
			
		}
		else {
			response.sendRedirect("public/views/login.html");
		}
	}

}
