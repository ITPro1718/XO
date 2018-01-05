package de.hdm.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

public class SuchprofilMapper {

	/**
	 * Stellt sicher, dass die Klasse nur ein mal instanziiert wird *
	 */
	private static SuchprofilMapper suchprofilMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected SuchprofilMapper() {
	}

	/**
	 * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann
	 * nicht mit <code>new</code> instanziiert werden
	 * 
	 * @return <code>SuchprofilMapper</code>-Objekt.
	 */
	public static SuchprofilMapper suchprofilMapper() {
		if (suchprofilMapper == null) {
			suchprofilMapper = new SuchprofilMapper();
		}

		return suchprofilMapper;
	}

	public void insertSuchprofil(Suchprofil suchprofil) {

		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM suchprofil");

			if (rs.next()) {

				suchprofil.setId(rs.getInt("maxid") + 1);
				stmt = con.createStatement();

				stmt.executeUpdate(
						"INSERT INTO suchprofil(id, titel, religion, haarfarbe, koerpergroesse, raucher, age, epID) "
								+ "VALUES (" + suchprofil.getId() + "," + suchprofil.getTitle() + ","
								+ suchprofil.getReligion() + "," + suchprofil.getHaarFarbe() + ","
								+ suchprofil.getKoerpergroesse() + "," + suchprofil.isRaucher() + ","
								+ suchprofil.getAlter() + "," + suchprofil.getEigenprofilID() + ")");
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	public void updateSuchprofil(Suchprofil suchprofil) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("UPDATE suchprofil " + "SET titel=\"" + suchprofil.getTitle() + "\", " + "religion=\""
					+ suchprofil.getReligion() + "\", " + "haarfarbe=\"" + suchprofil.getHaarFarbe() + "\", "
					+ "koerpergroesse=\"" + suchprofil.getKoerpergroesse() + "\" " + "raucher=\""
					+ suchprofil.isRaucher() + "\", " + "age=\"" + suchprofil.getAlter() + "WHERE id="
					+ suchprofil.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	public void deleteSuchprofil(Suchprofil suchprofil) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM suchprofil " + "WHERE id=" + suchprofil.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Suchprofil findByKey(int id) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM suchprofil" + "WHERE id=" + id);
			if (rs.next()) {
				Suchprofil s = new Suchprofil();
				s.setId(id);
				s.setTitle(rs.getString("titel"));
				s.setHaarFarbe(rs.getString("haarfarbe"));
				s.setKoerpergroesse(rs.getFloat("koerpergroesse"));
				s.setRaucher(rs.getBoolean("raucher"));
				s.setAlter(rs.getInt("age"));
				s.setEigenprofilID(rs.getInt("epID"));
				s.setReligion(rs.getString("religion"));

				return s;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}

	public Suchprofil findSuchprofilByTitle(String title) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM suchprofil" + "WHERE titel=" + title);
			if (rs.next()) {
				Suchprofil s = new Suchprofil();
				s.setId(rs.getInt("id"));
				s.setTitle(rs.getString("titel"));
				s.setHaarFarbe(rs.getString("haarfarbe"));
				s.setKoerpergroesse(rs.getFloat("koerpergroesse"));
				s.setRaucher(rs.getBoolean("raucher"));
				s.setAlter(rs.getInt("age"));
				s.setEigenprofilID(rs.getInt("epID"));
				s.setReligion(rs.getString("religion"));

				return s;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;

	}

	public ArrayList<Suchprofil> findAll() {
		ArrayList<Suchprofil> result = new ArrayList<>();

		Connection con = DBConnection.getConnection();
		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM suchprofil" + "ORDER BY id");

			while (rs.next()) {
				Suchprofil s = new Suchprofil();
				s.setId(rs.getInt("id"));
				s.setTitle(rs.getString("titel"));
				s.setHaarFarbe(rs.getString("haarfarbe"));
				s.setKoerpergroesse(rs.getFloat("koerpergroesse"));
				s.setRaucher(rs.getBoolean("raucher"));
				s.setAlter(rs.getInt("age"));
				s.setEigenprofilID(rs.getInt("epID"));
				s.setReligion(rs.getString("religion"));

				result.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

	public ArrayList<Suchprofil> findSuchprofileOf(Profil profilowner) {
		ArrayList<Suchprofil> result = new ArrayList<>();

		Connection con = DBConnection.getConnection();
		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM suchprofil" + "WHERE epID=" + profilowner.getId() + "ORDER BY id");

			while (rs.next()) {
				Suchprofil s = new Suchprofil();
				s.setId(rs.getInt("id"));
				s.setTitle(rs.getString("titel"));
				s.setHaarFarbe(rs.getString("haarfarbe"));
				s.setKoerpergroesse(rs.getFloat("koerpergroesse"));
				s.setRaucher(rs.getBoolean("raucher"));
				s.setAlter(rs.getInt("age"));
				s.setEigenprofilID(rs.getInt("epID"));
				s.setReligion(rs.getString("religion"));

				result.add(s);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public Suchprofil getSuchprofil(Suchprofil suchprofil) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM suchprofil" + "WHERE id=" + suchprofil.getId());
			if (rs.next()) {
				Suchprofil s = new Suchprofil();
				s.setId(rs.getInt("id"));
				s.setTitle(rs.getString("titel"));
				s.setHaarFarbe(rs.getString("haarfarbe"));
				s.setKoerpergroesse(rs.getFloat("koerpergroesse"));
				s.setRaucher(rs.getBoolean("raucher"));
				s.setAlter(rs.getInt("age"));
				s.setEigenprofilID(rs.getInt("epID"));
				s.setReligion(rs.getString("religion"));

				return s;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}

}
