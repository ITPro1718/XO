package de.hdm.partnerboerse.server.db;

import java.sql.*;

import com.google.appengine.api.rdbms.AppEngineDriver;
import com.google.cloud.sql.jdbc.Connection;

/**
 * 
 * @author Burghardt, Gundermann, Rathke
 *
 */

public class DBConnection {
	
	
	private static Connection con = null;
	
	private static String url = "";
	
	public static Connection getConnection() {
		// Wenn es bisher keine Conncetion zur DB gab, ... 
		if ( con == null ) {
			try {
				// Ersteinmal muss der passende DB-Treiber geladen werden
				DriverManager.registerDriver(new AppEngineDriver());

				/*
				 * Dann erst kann uns der DriverManager eine Verbindung mit den oben
				 * in der Variable url angegebenen Verbindungsinformationen aufbauen.
				 * 
				 * Diese Verbindung wird dann in der statischen Variable con 
				 * abgespeichert und fortan verwendet.
				 */
				con = (Connection) DriverManager.getConnection(url);
			} 
			
			catch (SQLException e1) {
				con = null;
				e1.printStackTrace();
			}
		}
		
		// Zurückgegeben der Verbindung
		return con;
	}

}
