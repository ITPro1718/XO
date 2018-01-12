package de.hdm.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.google.appengine.api.utils.SystemProperty;

/**
 * 
 * @author Burghardt, Gundermann, Rathke
 *
 */

public class DBConnection {
		

	private static Connection con = null;
	
	private static String localurl = "jdbc:mysql://localhost:3306/partnerboerse?user=root&password=";
	private static String googleurl = "jdbc:google:mysql://testprojekt-187820:testprojekt/partnerboerse?user=root&password=123";
	private static String url;

	
	
	public static Connection getConnection() {
		// Wenn es bisher keine Conncetion zur DB gab, ... 
		if ( con == null ) {
			try {
				

				if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {
					
					Class.forName("com.mysql.jdbc.GoogleDriver");
					url = googleurl;
					} 
				else {
					 // Local MySQL instance to use during development.
					 
					  url = localurl;
					}
				
				
				con = DriverManager.getConnection(url);
			} 
			
			catch (Exception e) {
				con = null;
				e.printStackTrace();
			} 
		}
		
		// Zurückgegeben der Verbindung
		return con;
	}

}
