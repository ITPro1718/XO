package de.hdm.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Besuch;
import de.hdm.partnerboerse.shared.bo.Profil;

/**
 * Diese Mapper Klasse dient zur Abbildung von {@link Besuch} Objekten auf eine
 * relationale Datenbank. Das Mapping ist bidirektional, Objekte werden auf
 * DB-Strukturen abgebildet und DB-Strukturen auf Java-Objekte.
 *
 * @author Mikulic
 *
 */
public class BesuchMapper {

	/**
	 * Stellt sicher, dass die Klasse nur ein mal instanziiert wird *
	 */
	private static BesuchMapper besuchMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected BesuchMapper() {

	}

	/**
	 * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann
	 * nicht mit <code>new</code> instanziiert werden
	 * 
	 * @return <code>BesuchMapper</code>-Objekt.
	 */
	public static BesuchMapper besuchMapper() {
		if (besuchMapper == null) {
			besuchMapper = new BesuchMapper();
		}

		return besuchMapper;
	}

	/**
	 * Ermittelt einen {@link Besuch} Eintrag anhand der ID
	 * 
	 * @param id
	 * @return
	 */
	public Besuch findByKey(int id) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM visit WHERE id= " + id);
			if (rs.next()) {
				Besuch besuch = new Besuch();
				besuch.setId(rs.getInt("id"));
				besuch.setEigenprofilID(rs.getInt("epID"));
				besuch.setFremdprofilID(rs.getInt("fpID"));
				return besuch;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;

		}
		return null;
	}

	/**
	 * Gibt alle Besuch Eintraege zurueck
	 * 
	 * @return
	 */
	public ArrayList<Besuch> findAll() {
		Connection con = DBConnection.getConnection();

		ArrayList<Besuch> result = new ArrayList<>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM visit");

			while (rs.next()) {
				Besuch besuch = new Besuch();
				besuch.setId(rs.getInt("id"));
				besuch.setEigenprofilID(rs.getInt("epId"));
				besuch.setFremdprofilID(rs.getInt("fpID"));
				result.add(besuch);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;
	}

	/**
	 * Gibt alle Besuch-Eintraege eines bestimmten Profils aus
	 * 
	 * @param p
	 * @return
	 */
	public ArrayList<Besuch> findByEigenprofil(Profil p) {
		Connection con = DBConnection.getConnection();
		ArrayList<Besuch> results = new ArrayList<>();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM visit WHERE epID=" + p.getId());
			
			while (rs.next()) {
				Besuch besuch = new Besuch();
				besuch.setId(rs.getInt("id"));
				besuch.setEigenprofilID(rs.getInt("epID"));
				besuch.setFremdprofilID(rs.getInt("fpID"));
				results.add(besuch);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return results;
	}

	/**
	 * Speichert einen Profilbesuch in der Datenbank
	 * 
	 * @param b
	 */
	public void insertBesuch(Besuch besuch) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			// Es wird der momentatn Höchste Wert des Primärschluessels
			// ausgelesen
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM visit");

			if (rs.next()) {
				besuch.setId(rs.getInt("maxid") + 1);
				stmt = con.createStatement();

				stmt.executeUpdate("INSERT INTO visit (id, epID, fpID) " + "VALUES (" + besuch.getId() + ","
						+ besuch.getEigenprofilID() + "," + besuch.getFremdprofilID() + ")");

			}

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	public void deleteBesuch(Besuch besuch) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM visit WHERE id=" + besuch.getId());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
