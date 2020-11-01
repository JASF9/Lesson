package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import controllers.VideoController;
import helpers.Cors;

/**
 * Servlet implementation class Media
 */
@WebServlet("/Media")
public class Media extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Media() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cors.apply(response);
		if(request.getSession(false)!=null) {
			if(request.getParameter("_method").equals("GET")) {
				String message = VideoController.getVideos((String)request.getSession().getAttribute("useremail"));
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println(message);
			}
			else {
				String email = request.getParameter("email");
				request.getSession().setAttribute("useremail", email);
				response.sendRedirect("public/views/userPage");
			}
		}
		else {
			response.sendRedirect("public/views/login.html");
		}
		
		
		//PrintWriter out = response.getWriter();
	     //response.setContentType("application/json");
	     //out.println(VideoController.getVideo(email));
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cors.apply(response);
		if(request.getParameter("_method").equals("POST")) {
			HttpSession session = request.getSession();
			if(request.getSession(false) != null) {
				 //PrintWriter out = response.getWriter();
			     //response.setContentType("application/json");
			     //out.println(VideoController.setVideo(request.getPart("video"),(String) session.getAttribute("email")));
				String message = VideoController.setVideo(request.getPart("video"),(String) session.getAttribute("email"));
				if(message.equals("{message:'Video saved.',status:200}")) {
					response.sendRedirect("public/views/home.html");
				}
				else {
					response.sendRedirect("public/views/uploadVideo.html");
				}
			}
			else {
				response.sendRedirect("public/views/login.html");
			}
		}
		else {
			doPut(request,response);
		} 
	}
	
	@Override
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cors.apply(response);
		HttpSession session = request.getSession();
		if(request.getSession(false) != null) {
			 //PrintWriter out = response.getWriter();
		     //response.setContentType("application/json");
		     //out.println(VideoController.setVideo(request.getPart("video"),(String) session.getAttribute("email")));
			String message = VideoController.setImage(request.getPart("image"),(String) session.getAttribute("email"));
			if(message.equals("{message:'Image saved.',status:200}")) {
				response.sendRedirect("public/views/home.html");
			}
			else {
				response.sendRedirect("public/views/uploadImage.html");
			}
		}
		else {
			response.sendRedirect("public/views/login.html");
		}
	}

}
