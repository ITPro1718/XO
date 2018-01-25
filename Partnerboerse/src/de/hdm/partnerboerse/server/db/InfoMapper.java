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

			ResultSet rs = stmt.executeQuery("SELECT * FROM info WHERE id=" + id);
			if (rs.next()) {
				Info i = new Info();
				i.setId(id);
				i.setText(rs.getString("bezeichnung"));
				i.setEigenschaftId(rs.getInt("eigenschaftID"));
				i.setepId(rs.getInt("epId"));
				i.setSuchprofilId(rs.getInt("suchprofilID"));
				return i;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * Gibt alle Info-Einträge zurück
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
				i.setText(rs.getString("bezeichnung"));
				i.setEigenschaftId(rs.getInt("eigenschaftID"));
				i.setepId(rs.getInt("epId"));
				i.setSuchprofilId(rs.getInt("suchprofilID"));

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

			ResultSet rs = stmt.executeQuery("SELECT * FROM info WHERE epID = " + profilowner.getId() + " ORDER BY id");

			while (rs.next()) {
				Info i = new Info();
				i.setId(rs.getInt("id"));
				i.setText(rs.getString("bezeichnung"));
				i.setEigenschaftId(rs.getInt("eigenschaftID"));
				i.setepId(rs.getInt("epId"));
				i.setSuchprofilId(rs.getInt("suchprofilID"));

				result.add(i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	public ArrayList<Info> findInfoOfSuchprofil(int suchprofilid) {
		ArrayList<Info> result = new ArrayList<>();

		Connection con = DBConnection.getConnection();
		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM info WHERE suchprofilID = " + suchprofilid + " ORDER BY id");

			while (rs.next()) {
				Info i = new Info();
				i.setId(rs.getInt("id"));
				i.setText(rs.getString("bezeichnung"));
				i.setEigenschaftId(rs.getInt("eigenschaftID"));
				i.setepId(rs.getInt("epId"));
				i.setSuchprofilId(rs.getInt("suchprofilID"));

				result.add(i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public Info findEigenschaftsInfosOf(Eigenschaft eigenschaft) {

		Connection con = DBConnection.getConnection();
		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM info WHERE id = " + eigenschaft.getId());

			while (rs.next()) {
				Info i = new Info();
				i.setId(rs.getInt("id"));
				i.setText(rs.getString("bezeichnung"));
				i.setEigenschaftId(rs.getInt("eigenschaftsID"));
				i.setepId(rs.getInt("epId"));
				i.setSuchprofilId(rs.getInt("suchprofilID"));
				return i;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Info insertInfo(Info i) {

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

				if (i.getepId() == 0) {
					stmt.executeUpdate("INSERT INTO info (id, bezeichnung, epID, ) VALUES (" + i.getId() + ",'"
							+ i.getText() + "'," + i.getepId() + "," + null + ")");
				}

				if (i.getEigenschaftId() == 0) {
					stmt.executeUpdate("INSERT INTO info (id, bezeichnung, epID, eigenschaftID, suchprofilID) VALUES ("
							+ i.getId() + ",'" + i.getText() + "','" + i.getepId() + "'," + i.getEigenschaftId()
							+ i.getSuchprofilId() + "," + null + ")");
				}

				if (i.getSuchprofilId() == 0) {
					stmt.executeUpdate("INSERT INTO info (id, bezeichnung, epID, eigenschaftId, suchprofilID) VALUES ("
							+ i.getId() + ",'" + i.getText() + "','" + i.getepId() + "'," + null + ","
							+ i.getEigenschaftId() + i.getSuchprofilId() + ")");
				}

				return i;

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;

	}

}