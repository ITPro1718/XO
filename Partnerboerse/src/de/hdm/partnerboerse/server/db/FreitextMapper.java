package de.hdm.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import de.hdm.partnerboerse.shared.bo.Freitext;
import de.hdm.partnerboerse.shared.bo.Info;

/**
 * Diese Mapper Klasse dient zur Abbildung von {@link Freitext} Objekten auf
 * eine relationale Datenbank. Das Mapping ist bidirektional, Objekte werden auf
 * DB-Strukturen abgebildet und DB-Strukturen auf Java-Objekte.
 * 
 * @author Mikulic
 *
 */
public class FreitextMapper {

	/**
	 * Stellt sicher, dass die Klasse nur ein mal instanziiert wird
	 */
	private static FreitextMapper freitextMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected FreitextMapper() {
	}

	/**
	 * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann
	 * nicht mit <code>new</code> instanziiert werden
	 * 
	 * @return <code>FreitextMapper</code>-Objekt.
	 */
	public static FreitextMapper freitextMapper() {
		if (freitextMapper == null) {
			freitextMapper = new FreitextMapper();
		}

		return freitextMapper;
	}

	/**
	 * Speichert ein Freitext-Objekt in der Datenbank
	 * 
	 * @param freitext
	 *            - {@link Freitext} Element
	 * @return TODO
	 */
	public Freitext insertFreitext(Freitext freitext) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			// Es wird der momentan höchste Wert des Primearschluessels
			// ausgelesen
			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM freitext");

			if (rs.next()) {
				// a bekommt den neuen höchsten Primaerschluesselwert
				freitext.setId(rs.getInt("maxid") + 1);
				stmt = con.createStatement();

				// Das ist die eigentliche Einfüg-Funktion
				stmt.executeUpdate("INSERT INTO freitext(id, beschreibung) " + "VALUES (" + freitext.getId() + ", '"
						+ freitext.getBeschreibung() + "')");
				return freitext;
			}
			
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return null;
	}

	/**
	 * Ändert/Updated einen Freitext
	 * 
	 * @param freitext
	 *            - Das zu ändernde Freitext-Element
	 */
	public void updateFreitext(Freitext freitext) {
		Connection con = DBConnection.getConnection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("UPDATE freitext SET beschreibung=\"" + freitext.getBeschreibung() + "\"" + " WHERE id="
					+ freitext.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Löscht einen Freitext-Eintrag aus der DB
	 * 
	 * @param freitext
	 *            - Das zu löschende Freitext Element
	 */
	public void deleteFreitext(Freitext freitext) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM freitext WHERE id=" + freitext.getId());
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Ermittelt einen Freitext-Eintrag anhand der übergebenen ID.
	 * 
	 * @param id
	 *            - Die ID des Freitextes
	 * @return Liefert das {@link Freitext} Element zu der übergebenen ID.
	 */
	public Freitext findByKey(int id) {
		Connection con = DBConnection.getConnection();

		try {
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT id, beschreibung FROM freitext WHERE id=" + id);
			if (rs.next()) {
				Freitext a = new Freitext();
				a.setId(rs.getInt("id"));
				a.setBeschreibung(rs.getString("beschreibung"));
				return a;
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * Liefert alle Freitexte aus der DB.
	 * 
	 * @return Liste von {@link Freitext}.
	 */
	public List<Freitext> findAll() {
		Connection con = DBConnection.getConnection();
		List<Freitext> result = new ArrayList<>();

		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, beschreibung FROM freitext " + "ORDER BY id");

			while (rs.next()) {
				Freitext a = new Freitext();
				a.setId(rs.getInt("id"));
				a.setBeschreibung(rs.getString("beschreibung"));

				result.add(a);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}
		return result;
	}

	public Freitext findFreitextOfInfo(Info info) {

		Connection con = DBConnection.getConnection();
		try {

			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT * FROM freitext WHERE id = " + info.getFreitextID());

			if (rs.next()) {
				Freitext f = new Freitext();
				f.setId(rs.getInt("id"));
				f.setBeschreibung(rs.getString("beschreibung"));
				return f;

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}