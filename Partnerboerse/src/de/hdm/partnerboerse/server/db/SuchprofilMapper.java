package de.hdm.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Logger;
import de.hdm.partnerboerse.server.ServerSideSettings;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;


/**
 * Mapper-Klasse, die Suchprofil Objekte in die Datenbank schreibt, bzw. Daten aus der Datenbank holt
 * und in Objekten speichert. Diese Klasse enthält die typischen CRUD-Methoden
 * 
 * 
 */
public class SuchprofilMapper {
  
  Logger logger = ServerSideSettings.getLogger();

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
	
	/**
	 * 
	 * Suchprofil wird übergeben und in die Datenbank eingefügt.
	 * 
	 * @param suchprofil
	 * @return Suchprofil
	 */
	public Suchprofil insertSuchprofil(Suchprofil suchprofil) {

	    Connection con = DBConnection.getConnection();

		try {
			Statement getid = con.createStatement();
			ResultSet rs = getid.executeQuery("SELECT MAX(id) as maxid FROM suchprofil");
			if (rs.next()){
				suchprofil.setId(rs.getInt("maxid") +1 );
			}
			
			PreparedStatement stmt = con.prepareStatement("INSERT INTO suchprofil (id, titel, haarfarbe, koerpergroesse, raucher, age, epID, religion) "
				    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

		  stmt.setInt(1, suchprofil.getId());
	      stmt.setString(2, suchprofil.getTitle());
	      stmt.setString(3, suchprofil.getHaarFarbe());
	      stmt.setDouble(4, suchprofil.getKoerpergroesse());
	      stmt.setBoolean(5, suchprofil.isRaucher());
	      stmt.setInt(6, suchprofil.getAlter());
	      stmt.setInt(7, suchprofil.getEigenprofilID());
	      stmt.setString(8, suchprofil.getReligion());
	      stmt.executeUpdate();
	      return suchprofil;
			
		} catch (SQLException e) {
			logger.severe("SuchProfilMapper.insert konnte Daten nicht in die DB gespeichert werden: " + e.getMessage() + e.getCause());
		}
		return suchprofil;
	}

	/**
	 * Suchprofil wird übergeben und in der DB geupdatet.
	 * @param suchprofil
	 */
	public void updateSuchprofil(Suchprofil suchprofil) {
		Connection con = DBConnection.getConnection();
		String sql = "UPDATE suchprofil SET titel = ?, religion = ?, "
		    + "haarfarbe = ?, koerpergroesse = ?, raucher = ?, age = ? WHERE suchprofil.id = ?;"; 

		try(PreparedStatement stmt = con.prepareStatement(sql)) {
		  
		  stmt.setString(1, suchprofil.getTitle());
		  stmt.setString(2, suchprofil.getReligion());
		  stmt.setString(3, suchprofil.getHaarFarbe());
		  stmt.setInt(4, (int) suchprofil.getKoerpergroesse());
		  stmt.setBoolean(5, suchprofil.isRaucher());
		  stmt.setInt(6, suchprofil.getAlter());
		  stmt.setInt(7, suchprofil.getId());
		  
		  stmt.executeUpdate();

		} catch (SQLException e) {
		  logger.severe("SuchProfilMapper.update konnte Daten nicht in die DB speichern: " + e.getMessage() + e.getCause());
		}

	}
	
	/**
	 * Suchprofil wird übergeben und in der DB gelöscht.
	 * @param suchprofil
	 */
	public void deleteSuchprofil(Suchprofil suchprofil) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM suchprofil " + "WHERE id=" + suchprofil.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gibt ein Suchprofil anhand der ID zurück.
	 * 
	 * @param id
	 * @return Suchprofil
	 */
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

	/**
	 * Gibt ein Suchprofil anhand des Titels zurück.
	 * 
	 * @param title
	 * @return Suchprofil
	 */
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
	
	/**
	 * Gibt alle Suchprofile zurück.
	 * 
	 * 
	 * @return ArrayList<Suchprofil>
	 */
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

	/**
	 * Gibt alle Suchprofile eines Profils zurück.
	 * 
	 * @param profilowner
	 * @return ArrayList<Suchprofil>
	 */
	public ArrayList<Suchprofil> findSuchprofileOf(Profil profilowner) {
		ArrayList<Suchprofil> result = new ArrayList<>();

		Connection con = DBConnection.getConnection();
		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM suchprofil WHERE epID = " + profilowner.getId());

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

	/**
	 * Gibt ein Suchprofil aus der DB zurück.
	 * 
	 * @param suchprofil
	 * @return Suchprofil
	 */
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
