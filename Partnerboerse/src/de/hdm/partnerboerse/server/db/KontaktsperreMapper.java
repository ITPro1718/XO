package de.hdm.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Kontaktsperre;
import de.hdm.partnerboerse.shared.bo.Profil;

/**
 * Mapper-Klasse, die Kontaktsperren-Objekte in die Datenbank schreibt, bzw. Daten aus der Datenbank holt
 * und in Objekten speichert. Diese Klasse enthält die typischen CRUD-Methoden
 * 
 * 
 */
public class KontaktsperreMapper {

	/**
	 * Stellt sicher, dass die Klasse nur ein mal instanziiert wird *
	 */
	private static KontaktsperreMapper kontaktsperreMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected KontaktsperreMapper() {
	}

	/**
	 * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann
	 * nicht mit <code>new</code> instanziiert werden
	 * 
	 * @return <code>KontaktsperreMapper</code>-Objekt.
	 */
	public static KontaktsperreMapper kontaktsperreMapper() {
		if (kontaktsperreMapper == null) {
			kontaktsperreMapper = new KontaktsperreMapper();
		}

		return kontaktsperreMapper;
	}

	/**
	 * Speichert einen KontaktsperrenEintrag in der DB
	 * 
	 * @param kontaktsperre
	 */
	public void insertKontaktsperreEintrag(Kontaktsperre kontaktsperre) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM kontaktsperre");

			if (rs.next()) {

				kontaktsperre.setId(rs.getInt("maxid") + 1);
				stmt = con.createStatement();

				stmt.executeUpdate("INSERT INTO kontaktsperre (id, epID, fpID) " + "VALUES (" + kontaktsperre.getId()
						+ "," + kontaktsperre.getEigenprofilID() + "," + kontaktsperre.getFremdprofilID() + ")");
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	/**
	 * Löscht einen KontaktsperrenEintrag in der DB
	 * 
	 * @param kontaktsperre
	 * 
	 */
	public void deleteKontaktsperreEintrag(Kontaktsperre kontaktsperre) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM kontaktsperre WHERE id=" + kontaktsperre.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Gibt eine Kontaktsperre zurück per ID (Primärschlüssel)
	 * 
	 * @param id
	 * @return Kontaktsperre
	 */
	public Kontaktsperre findByKey(int id) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM kontaktsperre WHERE id=" + id);
			if (rs.next()) {
				Kontaktsperre k = new Kontaktsperre();
				k.setId(id);
				k.setEigenprofilID(rs.getInt("epID"));
				k.setFremdprofilID(rs.getInt("fpID"));

				return k;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * Gibt alle Kontaktsperren-Einträge zurück
	 * 
	 * @return ArrayList<Kontaktsperre>
	 */
	public ArrayList<Kontaktsperre> findAll() {
		ArrayList<Kontaktsperre> result = new ArrayList<>();

		Connection con = DBConnection.getConnection();
		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM kontaktsperre");

			while (rs.next()) {

				Kontaktsperre k = new Kontaktsperre();
				k.setId(rs.getInt("id"));
				k.setEigenprofilID(rs.getInt("epID"));
				k.setFremdprofilID(rs.getInt("fpID"));

				result.add(k);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;

	}

	/**
	 * Gibt alle Kontaktsperren-Einträge eines Profils zurück.
	 * 
	 * @param profilowner
	 * @return ArrayList<Kontaktsperre>
	 */
	public ArrayList<Kontaktsperre> findKontaktsperrenOf(Profil profilowner) {
		ArrayList<Kontaktsperre> result = new ArrayList<>();

		Connection con = DBConnection.getConnection();
		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt
					.executeQuery("SELECT * FROM kontaktsperre WHERE epID = " + profilowner.getId() + " ORDER BY id");

			while (rs.next()) {
				Kontaktsperre k = new Kontaktsperre();
				k.setId(rs.getInt("id"));
				k.setEigenprofilID(rs.getInt("epID"));
				k.setFremdprofilID(rs.getInt("fpID"));

				result.add(k);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
}