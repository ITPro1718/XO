package de.hdm.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.partnerboerse.shared.bo.Info;
import de.hdm.partnerboerse.shared.bo.Profil;

public class InfoMapper {

	/**
	 * Stellt sicher, dass die Klasse nur ein mal instanziiert wird *
	 */
	private static InfoMapper infoMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected InfoMapper() {
	}

	/**
	 * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann
	 * nicht mit <code>new</code> instanziiert werden
	 * 
	 * @return <code>InfoMapper</code>-Objekt.
	 */
	public static InfoMapper infoMapper() {
		if (infoMapper == null) {
			infoMapper = new InfoMapper();
		}

		return infoMapper;
	}

	/**
	 * Gibt ein Info-Objekt zurueck, dass per ID gesucht wird
	 * (Primäerschluessel)
	 * 
	 * @param id
	 * @return
	 */
	public Info findByKey(int id) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM info" + "WHERE id=" + id);
			if (rs.next()) {
				Info i = new Info();
				i.setId(id);
				i.setEigenprofilID(rs.getInt("epID"));
				i.setEigenschaftsID(rs.getInt("eigenschaftID"));
				i.setText(rs.getString("bezeichnung"));

				return i;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * Gibt alle Info-Eintr�ge zur�ck
	 * 
	 * @return
	 */
	public ArrayList<Info> findAll() {
		ArrayList<Info> result = new ArrayList<>();

		Connection con = DBConnection.getConnection();
		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM info ORDER BY id");

			while (rs.next()) {
				Info i = new Info();

				i.setId(rs.getInt("id"));
				i.setEigenprofilID(rs.getInt("epID"));
				i.setEigenschaftsID(rs.getInt("eigenschaftID"));
				i.setText(rs.getString("bezeichnung"));

				result.add(i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

	public void deleteInfo(Info info) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM info " + "WHERE id=" + info.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ArrayList<Info> findInfoOf(Profil profilowner) {
		ArrayList<Info> result = new ArrayList<>();

		Connection con = DBConnection.getConnection();
		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM info WHERE epID=" + profilowner.getId() + " ORDER BY id");

			while (rs.next()) {
				Info i = new Info();
				i.setId(rs.getInt("id"));
				i.setEigenprofilID(rs.getInt("epID"));
				i.setEigenschaftsID(rs.getInt("eigenschaftID"));
				i.setText(rs.getString("bezeichnung"));

				result.add(i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public ArrayList<Info> findEigenschaftsInfosOf(Eigenschaft eigenschaft) {

		/**
		 * not sure what this does
		 */

		return null;
	}

	public void insertInfo(Info i) {
		
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			// Es wird der momentan höchste Wert des Primearschluessels
			// ausgelesen
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM info");

			if (rs.next()) {
				// a bekommt den neuen höchsten Primaerschluesselwert
				i.setId(rs.getInt("maxid") + 1);
				stmt = con.createStatement();

				// Das ist die eigentliche Einfüg-Funktion
				stmt.executeUpdate("INSERT INTO auswahl(id, bezeichnung, epID, eigenschaftID) " + "VALUES (" + i.getId() + ", "
						+ i.getText() + ", "+ i.getEigenprofilID() + ", " + i.getEigenschaftsID() + ")");
				
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		
	}

}