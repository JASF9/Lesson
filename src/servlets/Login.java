package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controllers.LoginController;
import helpers.Cors;

/**
 * Servlet implementation class Login
 */
@MultipartConfig()
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cors.apply(response);
		String search = request.getParameter("search");
		request.getSession(false).setAttribute("search", search);
		response.sendRedirect("public/views/userList.html");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cors.apply(response);
		String message = LoginController.login(request);
		if(message.equals("{'message': 'Login Successful', 'redirect': '/home', 'email': " + request.getParameter("email") + ", 'status': 200 }")) {
			response.sendRedirect("public/views/home.html");
		}
		else {
			response.sendRedirect("public/views/login.html");
		}
		
		//response.setContentType("application/json");
	    //PrintWriter out = response.getWriter();
	    //out.println(message);
	}

}
