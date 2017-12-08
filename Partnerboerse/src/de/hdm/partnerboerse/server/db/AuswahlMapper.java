package de.hdm.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.hdm.partnerboerse.shared.bo.Auswahl;

/**
 * Diese Mapper Klasse dient zur Abbildung von {@link Auswahl} Objekten auf eine
 * relationale Datenbank. Das Mapping ist bidirektional, Objekte werden auf
 * DB-Strukturen abgebildet und DB-Strukturen auf Java-Objekte.
 *
 * @author Mikulic
 *
 */
public class AuswahlMapper {

	/**
	 * Stellt sicher, dass die Klasse nur ein mal instanziiert wird *
	 */
	private static AuswahlMapper auswahlMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 * {@link Auswahl}
	 */
	protected AuswahlMapper() {
	}

	/**
	 * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann
	 * nicht mit <code>new</code> instanziiert werden
	 * 
	 * @return <code>AuswahlMapper</code>-Objekt.
	 */
	public static AuswahlMapper auswahlMapper() {
		if (auswahlMapper == null) {
			auswahlMapper = new AuswahlMapper();
		}

		return auswahlMapper;
	}

	/**
	 * Legt eine {@link Auswahl} in der Datenbank an.
	 * 
	 * @param auswahl
	 *            Die speichernde {@link Auswahl}
	 */
	public void insertAuswahl(Auswahl auswahl) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			// Es wird der momentan höchste Wert des Primearschluessels
			// ausgelesen
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM auswahl");

			if (rs.next()) {
				// a bekommt den neuen höchsten Primaerschluesselwert
				auswahl.setId(rs.getInt("maxid") + 1);
				stmt = con.createStatement();

				// Das ist die eigentliche Einfüg-Funktion
				stmt.executeUpdate("INSERT INTO auswahl(id, titel) " + "VALUES (" + auswahl.getId() + ","
						+ auswahl.getTitel() + ")");
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * aendert/Updatet ein Auswahlobjekt
	 * 
	 * @param auswahl
	 *            Die zu aktualisierende Auswahl
	 */
	public void updateAuswahl(Auswahl auswahl) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(
					"UPDATE auswahl" + "SET titel=\"" + auswahl.getTitel() + "\"" + "WHERE id=" + auswahl.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Loescht eine Auswahl aus der Datenbank
	 * 
	 * @param auswahl
	 *            Die zu loeschende Auswahl.
	 */
	public void deleteAuswahl(Auswahl auswahl) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			stmt.executeUpdate("DELETE FROM auswahl" + "WHERE id=" + auswahl.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Ermittelt einen Auswahleintrag per ID (Primaerschluessel) und gibt diesen
	 * zurueck
	 * 
	 * @param id
	 *            - ID der Auswahl
	 * @return Liefert die ermittelte {@link Auswahl}
	 */
	public Auswahl findByKey(int id) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, titel FROM auswahl" + "WHERE id=" + id );
			if (rs.next()) {
				Auswahl a = new Auswahl();
				a.setId(rs.getInt("id"));
				a.setTitel(rs.getString("titel"));
				return a;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * Liefert alle {@link Auswahl} Eintraege.
	 * 
	 * @return Liefert eine {@link List} von {@link Auswahl}.
	 */
	public List<Auswahl> findAll() {
		Connection con = DBConnection.getConnection();

		List<Auswahl> result = new ArrayList<>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, titel FROM auswahl " + "ORDER BY id");

			while (rs.next()) {
				Auswahl a = new Auswahl();
				a.setId(rs.getInt("id"));
				a.setTitel(rs.getString("titel"));

				result.add(a);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

}
