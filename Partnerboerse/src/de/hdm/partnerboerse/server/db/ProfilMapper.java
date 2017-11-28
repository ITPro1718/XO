package de.hdm.partnerboerse.server.db;

import java.sql.*;
import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.*;

/**
 * Mapper-Klasse, die Profil test Objekte in die Datenbank schreibt, bzw. Daten aus der Datenbank holt
 * und in Objekten speichert. Diese Klasse enthält die typischen CRUD-Methoden
 * 
 * @author Burghardt, Mikulic
 */

public class ProfilMapper {

  /**
   * Stellt sicher, dass die Klasse nur ein mal instanziiert wird   * 
   */
  private static ProfilMapper profilMapper = null;

  /**
   * GeschÃ¼tzter Konstruktor - verhindert die MÃ¶glichkeit, mit <code>new</code>
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
   * @param id PrimÃ¤rschlÃ¼ssel
   * @return Konto-Objekt, das in der DB gefunden wurde
   */
  public Profil findProfilByKey(int id) {
    // DB-Verbindung holen
    Connection con = DBConnection.getConnection();

    try {
      
      // PreparedStatement anlegen, welches den SQL Befehl enthält
      PreparedStatement stmt = con.prepareStatement("SQL Befehl WHERE id = ?");
      
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
   * @return Ein Vektor mit Account-Objekten, die sÃ¤mtliche Konten
   *         reprÃ¤sentieren. Bei evtl. Exceptions wird ein partiell gefÃ¼llter
   *         oder ggf. auch leerer Vetor zurÃ¼ckgeliefert.
   */
  public ArrayList<Profil> findAllProfiles() {
    Connection con = DBConnection.getConnection();

    // Ergebnis ArrayList vorbereiten
    ArrayList<Profil> result = new ArrayList<Profil>();

    try {
      PreparedStatement stmt = con.prepareStatement("SQL BEFEHL");

      ResultSet rs = stmt.executeQuery();

      // FÃ¼r jeden Eintrag im Suchergebnis wird nun ein Account-Objekt erstellt.
      while (rs.next()) {
    	  Profil p = new Profil();
          p.setId(rs.getInt("id"));
          p.setVorname(rs.getString("vorname"));
          p.setNachname(rs.getString("nachname"));
          p.setRaucher(rs.getString("raucher"));
          // und so weiter....

          // HinzufÃ¼gen des neuen Objekts zum Ergebnisvektor
          result.add(p);
      }
    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }

    // Ergebnisvektor zurÃ¼ckgeben
    return result;
  }
  
 public ArrayList<Profil> findProfilesByName(String name) {
	    Connection con = DBConnection.getConnection();
	    ArrayList<Profil> result = new ArrayList<Profil>();

	    try {
	      PreparedStatement stmt = con.prepareStatement("SQL BEFEHL");
	      stmt.setString(1, name);

	      ResultSet rs = stmt.executeQuery();

	      // FÃ¼r jeden Eintrag im Suchergebnis wird nun ein Customer-Objekt
	      // erstellt.
	      while (rs.next()) {
	    	  while (rs.next()) {
	        	  Profil p = new Profil();
	              p.setId(rs.getInt("id"));
	              p.setVorname(rs.getString("vorname"));
	              p.setNachname(rs.getString("nachname"));
	              p.setRaucher(rs.getString("raucher"));
	              // und so weiter....

	              // HinzufÃ¼gen des neuen Objekts zum Ergebnisvektor
	              result.add(p);
	    	  }
	      }
	    }
	    catch (SQLException e) {
	      e.printStackTrace();
	    }

	    // Ergebnisvektor zurÃ¼ckgeben
	    return result;
	  }

  

  /**
   * Einfügen eines Profils in die Datenbank
   * 
   * @param p das zu speichernde Profil-Objekt
   * 
   */
  public void insert(Profil p) {
	  Connection con = DBConnection.getConnection();
	  
	  try {	
		  // Erstellt Prepared Statement, um herauszufinden, was die höchste benutzte ID ist
		  PreparedStatement stmt = con.prepareStatement("SQL BEFEHL");
	      
	      ResultSet rs = stmt.executeQuery();

	      // Wenn wir etwas zurÃ¼ckerhalten, kann dies nur einzeilig sein
	      if (rs.next()) {
	        // Profil-Objekt erhält die höchste ID + 1
	        p.setId(rs.getInt("maxid") + 1);
	        
	    /**
	     * Nun fangen wir erst an, das Profil in die Datenbank zu schreiben!    
	     */
	        
	    // Prepared-Statement erstellen, inklusive SQL Befehl
	    PreparedStatement stmt1 = con.prepareStatement("INSERT INTO profil (id, name) VALUES (?,?)");
	    
	    // Platzhalter (Fragezeichen), mit Werten aus dem Objekt befüllen
		stmt1.setInt(1, p.getId());
		stmt1.setString(2, p.getVorname());
		
		// Ausführen des SQL Befehls
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
    	
    	// Platzhalter (Fragezeichen mit Werten aus dem Objekt befüllen
    	stmt.setString(1, p.getNachname());

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }

  /**
   * Löschen der Daten eines Profils aus der Datenbank
   * 
   * @param p, das zu löschende Objekt
   */
  public void delete(Profil p) {
    Connection con = DBConnection.getConnection();

    try {
    	// PreparedStatement inkl SQL Befehl erstellen
      PreparedStatement stmt = con.prepareStatement("SQL BEFEHL");
      
      // Platzhalter mit ID aus dem Objekt befüllen
      stmt.setInt(1, p.getId());     

      // Statement ausführen
      stmt.executeUpdate();

    }
    catch (SQLException e2) {
      e2.printStackTrace();
    }
  }
}