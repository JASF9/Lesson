package helpers;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Response {
	
	public static String videoList(ResultSet rs) {
		String response = "";
		try {
			while(rs.next()) {
				response = response + "<div class='vid'><h2>"+rs.getString("videoname")+"</h2><video controls><source src="+rs.getString("videou")+"type='video/mp4/'><p>Browser doesn't suppor HTML5 video.</p></video></div>";
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		return response;
	}
	
	public static String userList(ResultSet rs) {
		String response="";
		try {
			while(rs.next()) {
				response = response + "<div class='row'><div class='result'><div class='column side'><img src="+rs.getString("image")+"></div>";
				response = response + "<div class='column middle'><h1>"+rs.getString("name")+"</h1>";
				response = response + "<form method='get' action='http://localhost:8080/WebLesson/Media'><input type='hidden' id='email' name='email' value="+rs.getString("email")+" /><input type='submit' value='See the videos of this User.'/></form></div></div>"; ;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return response;
	}
}
