package de.hdm.partnerboerse.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.*;

/**
 * Mapper-Klasse, die Profil test Objekte in die Datenbank schreibt, bzw. Daten aus der Datenbank holt
 * und in Objekten speichert. Diese Klasse enth�lt die typischen CRUD-Methoden
 * 
 * @author Burghardt, Mikulic
 */

public class ProfilMapper {

  /**
   * Stellt sicher, dass die Klasse nur ein mal instanziiert wird   * 
   */
  private static ProfilMapper profilMapper = null;

  /**
   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
   * neue Instanzen dieser Klasse zu erzeugen.
   */
  protected ProfilMapper() {
  }

  /**
   * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann nicht mit <code>new</code> instanziiert werden
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
   * @param id Primärschlüssel
   * @return Konto-Objekt, das in der DB gefunden wurde
   */
  public Profil findProfilByKey(int id) {
    // DB-Verbindung holen
    Connection con = DBConnection.getConnection();

    try {
      
      // PreparedStatement anlegen, welches den SQL Befehl enth�lt
      PreparedStatement stmt = con.prepareStatement("SQL Befehl WHERE id = ?");
      
      // Platzhalter (Fragezeichen) mit Werten bef�llen
      stmt.setInt(1, id);

      // Query ausf�hren und R�ckgabe dem ResultSet rs zuweisen
      ResultSet rs = stmt.executeQuery();

      /*
       * Tupel aus der Datenbank abfangen
       */
      if (rs.next()) {
        // Ergebnis-Tupel in Objekt umwandeln und durch Setter bef�llen.
        Profil p = new Profil();
        p.setId(rs.getInt("id"));
        p.setVorname(rs.getString("vorname"));
        p.setNachname(rs.getString("nachname"));
        p.setRaucher(rs.getString("raucher"));
        // und so weiter....
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
      PreparedStatement stmt = con.prepareStatement("SQL BEFEHL");

      ResultSet rs = stmt.executeQuery();

      // Für jeden Eintrag im Suchergebnis wird nun ein Account-Objekt erstellt.
      while (rs.next()) {
    	  Profil p = new Profil();
          p.setId(rs.getInt("id"));
          p.setVorname(rs.getString("vorname"));
          p.setNachname(rs.getString("nachname"));
          p.setRaucher(rs.getString("raucher"));
          // und so weiter....

          // Hinzufügen des neuen Objekts zum Ergebnisvektor
          result.add(p);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Ergebnisvektor zurückgeben
    return result;
  }
  
 public ArrayList<Profil> findProfilesByName(String name) {
	    Connection con = DBConnection.getConnection();
	    ArrayList<Profil> result = new ArrayList<Profil>();

	    try {
	      PreparedStatement stmt = con.prepareStatement("SQL BEFEHL");
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
	              p.setRaucher(rs.getString("raucher"));
	              // und so weiter....

	              // Hinzufügen des neuen Objekts zum Ergebnisvektor
	              result.add(p);
	    	  }
	      }
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	    // Ergebnisvektor zurückgeben
	    return result;
	  }

  

  /**
   * Einf�gen eines Profils in die Datenbank
   * 
   * @param p das zu speichernde Profil-Objekt
   * 
   */
  public void insert(Profil p) {
	  Connection con = DBConnection.getConnection();
	  
	  try {	
		  // Erstellt Prepared Statement, um herauszufinden, was die h�chste benutzte ID ist
		  PreparedStatement stmt = con.prepareStatement("SQL BEFEHL");
	      
	      ResultSet rs = stmt.executeQuery();

	      // Wenn wir etwas zurückerhalten, kann dies nur einzeilig sein
	      if (rs.next()) {
	        // Profil-Objekt erh�lt die h�chste ID + 1
	        p.setId(rs.getInt("maxid") + 1);
	        
	    /**
	     * Nun fangen wir erst an, das Profil in die Datenbank zu schreiben!    
	     */
	        
	    // Prepared-Statement erstellen, inklusive SQL Befehl
	    PreparedStatement stmt1 = con.prepareStatement("INSERT INTO profil (id, name) VALUES (?,?)");
	    
	    // Platzhalter (Fragezeichen), mit Werten aus dem Objekt bef�llen
		stmt1.setInt(1, p.getId());
		stmt1.setString(2, p.getVorname());
		
		// Ausf�hren des SQL Befehls
		stmt1.executeUpdate();
		
	  } 
	  }
	  catch (SQLException e) {
		e.printStackTrace();
	}

  }

  /**
   * Updaten eines Objektes aus der Datenbank
   * 
   * @param p das Profil-Objekt, das in die DB geschrieben werden soll
   */
  public void update(Profil p) {
    Connection con = DBConnection.getConnection();

    try {
    	
    	// PreparedStatement inkl. SQL Befehl erstellen
    	PreparedStatement stmt = con.prepareStatement("SQL BEFEHL");
    	
    	// Platzhalter (Fragezeichen mit Werten aus dem Objekt bef�llen
    	stmt.setString(1, p.getNachname());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }

  /**
   * L�schen der Daten eines Profils aus der Datenbank
   * 
   * @param p, das zu l�schende Objekt
   */
  public void delete(Profil p) {
    Connection con = DBConnection.getConnection();

    try {
    	// PreparedStatement inkl SQL Befehl erstellen
      PreparedStatement stmt = con.prepareStatement("SQL BEFEHL");
      
      // Platzhalter mit ID aus dem Objekt bef�llen
      stmt.setInt(1, p.getId());     

      // Statement ausf�hren
      stmt.executeUpdate();

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }
}