package helpers;

import javax.servlet.http.HttpServletResponse;

public class Cors {
	
	public static void apply(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age",  "86400" );
        response.setHeader("Access-Control-Allow-Headers", "Content-Type,Authorization,Accept");
	}
}
