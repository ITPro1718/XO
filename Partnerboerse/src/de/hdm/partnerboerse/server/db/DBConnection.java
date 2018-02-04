package de.hdm.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.DriverManager;

import com.google.appengine.api.utils.SystemProperty;

/**
 * 
 * @author Burghardt, Gundermann, Rathke
 *
 */

public class DBConnection {

	private static Connection con = null;

	private static String localurl = "jdbc:mysql://localhost:3306/partnerboerse?user=root&password=";
	private static String googleurl = "jdbc:google:mysql://crested-dialect-193613:partnerboerse/partnerboerse?user=root&password=root";
	private static String url;

	public static Connection getConnection() {
		
		if (con == null) {
			try {

				if (SystemProperty.environment.value() == SystemProperty.Environment.Value.Production) {

					Class.forName("com.mysql.jdbc.GoogleDriver");
					url = googleurl;
				} else {
				

					url = localurl;
				}

				con = DriverManager.getConnection(url);
			}

			catch (Exception e) {
				con = null;
				e.printStackTrace();
			}
		}

		return con;
	}

}
