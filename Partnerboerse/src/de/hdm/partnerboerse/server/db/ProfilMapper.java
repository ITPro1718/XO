package de.hdm.partnerboerse.server.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Profil;

/**
 * Mapper-Klasse, die Profil Objekte in die Datenbank schreibt, bzw. Daten aus
 * der Datenbank holt und in Objekten speichert. Diese Klasse enth�lt die
 * typischen CRUD-Methoden
 * 
 * @author Burghardt, Mikulic
 */

public class ProfilMapper {

	/**
	 * Stellt sicher, dass die Klasse nur ein mal instanziiert wird *
	 */
	private static ProfilMapper profilMapper = null;

	/**
	 * Geschützter Konstruktor - verhindert die Möglichkeit, mit
	 * <code>new</code> neue Instanzen dieser Klasse zu erzeugen.
	 */
	protected ProfilMapper() {
	}

	/**
	 * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann
	 * nicht mit <code>new</code> instanziiert werden
	 * 
	 * @return <code>ProfilMapper</code>-Objekt.
	 */
	public static ProfilMapper profilMapper() {
		if (profilMapper == null) {
			profilMapper = new ProfilMapper();
		}

		return profilMapper;
	}

	/**
	 * Suchen eines Profils anhand seiner ID
	 * 
	 * @param id
	 *            Primärschlüssel
	 * @return Konto-Objekt, das in der DB gefunden wurde
	 */
	public Profil findProfilByKey(int id) {
		// DB-Verbindung holen
		Connection con = DBConnection.getConnection();

		try {

			// PreparedStatement anlegen, welches den SQL Befehl enthült
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM profil WHERE id =?");

			// Platzhalter (Fragezeichen) mit Werten befüllen
			stmt.setInt(1, id);

			// Query ausführen und Rückgabe dem ResultSet rs zuweisen
			ResultSet rs = stmt.executeQuery();

			/*
			 * Tupel aus der Datenbank abfangen
			 */
			if (rs.next()) {
				// Ergebnis-Tupel in Objekt umwandeln und durch Setter befüllen.
				Profil p = new Profil();
				p.setId(rs.getInt("id"));
				p.setVorname(rs.getString("vorname"));
				p.setNachname(rs.getString("nachname"));
				p.setEmail(rs.getString("email"));
				p.setPasswort(rs.getString("passwort"));
				p.setGeburtsdatum(rs.getDate("geburtstag"));
				p.setRaucher(rs.getBoolean("raucher"));
				p.setHaarfarbe(rs.getString("haarfarbe"));
				p.setKoerpergroesse(rs.getInt("koerpergroesse"));
				p.setReligion(rs.getString("religion"));
				return p;
			}
		}
		// Exception abfangen
		catch (SQLException e2) {
			e2.printStackTrace();
			return null;
		}

		return null;
	}

	/**
	 * Auslesen aller Konten.
	 * 
	 * @return Ein Vektor mit Account-Objekten, die sämtliche Konten
	 *         repräsentieren. Bei evtl. Exceptions wird ein partiell gefüllter
	 *         oder ggf. auch leerer Vetor zurückgeliefert.
	 */
	public ArrayList<Profil> findAllProfiles() {
		Connection con = DBConnection.getConnection();

		// Ergebnis ArrayList vorbereiten
		ArrayList<Profil> result = new ArrayList<Profil>();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM profil");

			ResultSet rs = stmt.executeQuery();

			// Für jeden Eintrag im Suchergebnis wird nun ein Account-Objekt
			// erstellt.
			while (rs.next()) {
				Profil p = new Profil();
				p.setId(rs.getInt("id"));
				p.setVorname(rs.getString("vorname"));
				p.setNachname(rs.getString("nachname"));
				p.setEmail(rs.getString("email"));
				p.setPasswort(rs.getString("passwort"));
				p.setGeburtsdatum(rs.getDate("geburtstag"));
				p.setRaucher(rs.getBoolean("raucher"));
				p.setHaarfarbe(rs.getString("haarfarbe"));
				p.setKoerpergroesse(rs.getInt("koerpergroesse"));
				p.setReligion(rs.getString("religion"));

				// Hinzufügen des neuen Objekts zum Ergebnisarraylist
				result.add(p);
			}
		} catch (SQLException e2) {
			e2.printStackTrace();
		}

		// Ergebnisvektor zurückgeben
		return result;
	}

	public ArrayList<Profil> findProfilesByName(String name) {
		Connection con = DBConnection.getConnection();
		ArrayList<Profil> result = new ArrayList<Profil>();

		try {
			PreparedStatement stmt = con.prepareStatement("SELECT * FROM profil WHERE nachname = ?");
			stmt.setString(1, name);

			ResultSet rs = stmt.executeQuery();

			// Für jeden Eintrag im Suchergebnis wird nun ein Customer-Objekt
			// erstellt.
			while (rs.next()) {
				while (rs.next()) {
					Profil p = new Profil();
					p.setId(rs.getInt("id"));
					p.setVorname(rs.getString("vorname"));
					p.setNachname(rs.getString("nachname"));
					p.setEmail(rs.getString("email"));
					p.setPasswort(rs.getString("passwort"));
					p.setGeburtsdatum(rs.getDate("geburtstag"));
					p.setRaucher(rs.getBoolean("raucher"));
					p.setHaarfarbe(rs.getString("haarfarbe"));
					p.setKoerpergroesse(rs.getInt("koerpergroesse"));
					p.setReligion(rs.getString("religion"));

					// Hinzufügen des neuen Objekts zum Ergebnisvektor
					result.add(p);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Ergebnisvektor zurückgeben
		return result;
	}

	/**
	 * Einfügen eines Profils in die Datenbank
	 * 
	 * @param p
	 *            das zu speichernde Profil-Objekt
	 * 
	 */
	public void insert(Profil profil) {
		Connection con = DBConnection.getConnection();

		try {
			// Erstellt Prepared Statement, um herauszufinden, was die höchste
			// benutzte ID ist
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("SELECT MAX(id) AS maxid " + "FROM profil");

			// Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
			if (rs.next()) {
				// Profil-Objekt erhält die höchste ID + 1

				profil.setId(rs.getInt("maxid") + 1);
				stmt = con.createStatement();

	            java.util.Date utilDate = profil.getGeburtsdatum();
	            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				
				Statement stmt1 = con.createStatement();
				stmt1.executeUpdate(
						"INSERT INTO profil (id, email, passwort, vorname, nachname, geburtstag, haarfarbe, koerpergroesse, raucher, religion) "
								+ "VALUES (" + profil.getId() + "," + profil.getEmail() + "," + profil.getPasswort()
								+ "," + profil.getVorname() + "," + profil.getNachname() + ","
								+ sqlDate + "," + profil.getHaarfarbe() + ","
								+ (int) profil.getKoerpergroesse() + "," + profil.isRaucher() + ","
								+ profil.getReligion() + ")");

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Updaten eines Objektes aus der Datenbank
	 * 
	 * @param p
	 *            das Profil-Objekt, das in die DB geschrieben werden soll
	 */
	public void update(Profil p) {
		Connection con = DBConnection.getConnection();

		try {
		  
	        java.util.Date utilDate = p.getGeburtsdatum();
	        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

			// PreparedStatement inkl. SQL Befehl erstellen
			PreparedStatement stmt = con
					.prepareStatement("UPDATE profil SET email=?, passwort=?, vorname=?, nachname=?, geburtstag=?,"
							+ "haarfarbe=?, koerpergroesse=?, raucher=?, religion=? WHERE id = ?");
			stmt.setString(1, p.getEmail());
			stmt.setString(2, p.getPasswort());
			stmt.setString(3, p.getVorname());
			stmt.setString(4, p.getNachname());
			stmt.setDate(5, sqlDate);
			stmt.setString(6, p.getHaarfarbe());
			stmt.setInt(7, (int) p.getKoerpergroesse());
			stmt.setBoolean(8, p.isRaucher());
			stmt.setString(9, p.getReligion());
			stmt.setInt(10, p.getId());
			
		     stmt.executeUpdate();

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * Löschen der Daten eines Profils aus der Datenbank
	 * 
	 * @param p,
	 *            das zu löschende Objekt
	 */
	public void delete(Profil p) {
		Connection con = DBConnection.getConnection();

		try {
			// PreparedStatement inkl SQL Befehl erstellen
			PreparedStatement stmt = con.prepareStatement("DELETE FROM profil WHERE id = ?");

			// Platzhalter mit ID aus dem Objekt befüllen
			stmt.setInt(1, p.getId());

			// Statement ausführen
			stmt.executeUpdate();

		} catch (SQLException e2) {
			e2.printStackTrace();
		}
	}
}