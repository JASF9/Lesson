package controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.http.Part;
import helpers.DBC;
import helpers.Response;

public class VideoController {
	
	private static FileOutputStream fileOut;
	private static InputStream in;
	private  static DBC dbc;
	
	public static String setVideo(Part video, String email){
		
		try {
			File newFolderVideo = new File("public/media/"+email+"/");
			if(newFolderVideo.mkdirs()) {
				System.out.println("Creada");
			}
			String[] s = video.getContentType().split("/");
			String uri = "public/media/"+email+"/"+video.getSubmittedFileName()+"."+s[1];
			fileOut = new FileOutputStream(new File(uri));
			in = video.getInputStream();
			upload(in);
			dbc.addParameter(email);
			dbc.addParameter(uri);
			dbc.addParameter(video.getSubmittedFileName());
			dbc.prepare(dbc.getSentence("saveVideo"));
			dbc.exQuery();
			return "{message:'Video saved.',status:200}";
		}catch(IOException | SQLException e) {
			return "Video not saved";
		}
	}
	
	private static void upload(InputStream in) {
		
		int read=0;
		final byte[] bytes = new byte[1024];
		try {
			while ((read=in.read(bytes))!=-1) {
				fileOut.write(bytes,0,read);
			}
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static String getVideos(String email) {
		String answer="";
		dbc.addParameter(email);
		dbc.prepare(dbc.getSentence("showUserVideo"));
		try {
			ResultSet rs = dbc.exQuery();
			answer = Response.videoList(rs);
			return answer;
		}
		catch(SQLException e){
			return answer;
		}
	}
	
	public static String setImage(Part image, String email){
		
		try {
			File newFolderVideo = new File("public/media/"+email+"/");
			if(newFolderVideo.mkdirs()) {
				System.out.println("Creada");
			}
			String[] s = image.getContentType().split("/");
			String uri = "public/media/"+email+"/"+image.getSubmittedFileName()+"."+s[1];
			fileOut = new FileOutputStream(new File(uri));
			in = image.getInputStream();
			upload(in);
			dbc.addParameter(uri);
			dbc.addParameter(email);
			dbc.prepare(dbc.getSentence("setImage"));
			dbc.exQuery();
			return "{message:'Image saved.',status:200}";
		}catch(IOException | SQLException e) {
			return "Image not saved";
		}
	}
}

