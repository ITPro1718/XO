package de.hdm.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Eigenschaft;

/**
 * Diese Mapper Klasse dient zur Abbildung von {@link Eigenschaft} Objekten auf
 * eine relationale Datenbank. Das Mapping ist bidirektional, Objekte werden auf
 * DB-Strukturen abgebildet und DB-Strukturen auf Java-Objekte.
 * 
 * @author Mikulic
 *
 */
public class EigenschaftMapper {

	/**
	 * Stellt sicher, dass die Klasse nur ein mal instanziiert wird *
	 */
	private static EigenschaftMapper eigenschaftMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected EigenschaftMapper() {
	}

	/**
	 * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann
	 * nicht mit <code>new</code> instanziiert werden
	 * 
	 * @return <code>EigenschaftMapper</code>-Objekt.
	 */
	public static EigenschaftMapper eigenschaftMapper() {
		if (eigenschaftMapper == null) {
			eigenschaftMapper = new EigenschaftMapper();
		}

		return eigenschaftMapper;
	}

	/**
	 * Speichert ein Eigenschaftsobjekt in der Datenbank
	 * 
	 * @param eigenschaft
	 */
	public void insertEigenschaft(Eigenschaft eigenschaft) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM eigenschaft");

			if (rs.next()) {

				eigenschaft.setId(rs.getInt("maxid") + 1);
				stmt = con.createStatement();

				stmt.executeUpdate("INSERT INTO eigenschaft (id, erlaeuterung, is_a, auswahlID, freitextID) "
						+ "VALUE (" + eigenschaft.getId() + ", '" + eigenschaft.getErlaeuterung() + "', '"
						+ eigenschaft.getIs_a() + "', '" + eigenschaft.getAuswahlID() + "', '"
						+ eigenschaft.getFreitextID() + " ') ");

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Updated einen Eigenschafts-Eintrag in der Datenbank
	 * 
	 * @param eigenschaft
	 */
	public void updateEigenschaft(Eigenschaft eigenschaft) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE eigenschaft" + " SET erlaeuterung=\"" + eigenschaft.getErlaeuterung() + "\""
					+ " WHERE id =" + eigenschaft.getId());

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Loescht einen Eigenschafts-Eintrag aus der Datenbank
	 * 
	 * @param eigenschaft
	 */
	public void deleteEigenschaft(Eigenschaft eigenschaft) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM eigenschaft WHERE id= " + eigenschaft.getId());

		} catch (SQLException e2) {
			e2.printStackTrace();
		}

	}

	/**
	 * Gibt einen Eigenschafts-Eintrag zurueck, der per Key gesucht wird
	 * (Primärschluessel)
	 * 
	 * @param id
	 * @return
	 */
	public Eigenschaft findByKey(int id) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, erlaeuterung FROM eigenschaft WHERE id=" + id);
			if (rs.next()) {
				Eigenschaft e = new Eigenschaft();
				e.setId(rs.getInt("id"));
				e.setErlaeuterung(rs.getString("erlaeuterung"));
				return e;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Gibt alle Eigenschafts-Eintraege zurueck
	 * 
	 * @return
	 */
	public ArrayList<Eigenschaft> findAll() {
		Connection con = DBConnection.getConnection();

		ArrayList<Eigenschaft> result = new ArrayList<>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, erlaeuterung FROM eigenschaft " + "ORDER BY id");

			while (rs.next()) {
				Eigenschaft e = new Eigenschaft();
				e.setId(rs.getInt("id"));
				e.setErlaeuterung(rs.getString("erlaeuterung"));

				result.add(e);

			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		return result;

	}

}
