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

/**
 * Mapper-Klasse, die Profil Objekte in die Datenbank schreibt, bzw. Daten aus der Datenbank holt
 * und in Objekten speichert. Diese Klasse enthält die typischen CRUD-Methoden
 * 
 * 
 */

public class ProfilMapper {
  
  Logger logger = ServerSideSettings.getLogger();

  /**
   * Stellt sicher, dass die Klasse nur ein mal instanziiert wird *
   */
  private static ProfilMapper profilMapper = null;

  /**
   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code> neue Instanzen
   * dieser Klasse zu erzeugen.
   */
  protected ProfilMapper() {}

  /**
   * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann nicht mit
   * <code>new</code> instanziiert werden
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
   * @return Profil-Objekt, das in der DB gefunden wurde
   */
  public Profil findProfilByKey(int id) {
    Connection con = DBConnection.getConnection();

    try {
      PreparedStatement stmt = con.prepareStatement("SELECT * FROM profil WHERE id =?");

      stmt.setInt(1, id);
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
 
        Profil p = new Profil();
        p.setId(rs.getInt("id"));
        p.setVorname(rs.getString("vorname"));
        p.setNachname(rs.getString("nachname"));
        p.setEmail(rs.getString("email"));
        p.setGeburtsdatum(rs.getDate("geburtstag"));
        p.setRaucher(rs.getBoolean("raucher"));
        p.setHaarfarbe(rs.getString("haarfarbe"));
        p.setKoerpergroesse(rs.getInt("koerpergroesse"));
        p.setReligion(rs.getString("religion"));
        p.setGeschlecht(rs.getString("geschlecht"));
        p.setSucheNach(rs.getString("suche_nach"));
        return p;
      }
    }

    catch (SQLException e2) {
      e2.printStackTrace();
      return null;
    }

    return null;
  }
  
  /**
   * Suchen eines Profils anhand seiner email
   * 
   * @param email
   * @return Profil-Objekt, das in der DB gefunden wurde
   */
  public Profil findProfilByEmail(String email) {
   
    Connection con = DBConnection.getConnection();
    Profil p = new Profil();

    try (PreparedStatement stmt = con.prepareStatement("SELECT * FROM profil WHERE email = ?")) {

      stmt.setString(1, email);

      setProfilInArray(p, stmt);

      return p;

    }

    catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Auslesen aller Profile.
   * 
   * @return ArrayList<Profil> Alle Profilobjekte
   */
  public ArrayList<Profil> findAllProfiles() {
    Connection con = DBConnection.getConnection();

    ArrayList<Profil> result = new ArrayList<Profil>();

    try {
      PreparedStatement stmt = con.prepareStatement("SELECT * FROM profil");

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Profil p = new Profil();
        p.setId(rs.getInt("id"));
        p.setVorname(rs.getString("vorname"));
        p.setNachname(rs.getString("nachname"));
        p.setEmail(rs.getString("email"));
        p.setGeburtsdatum(rs.getDate("geburtstag"));
        p.setRaucher(rs.getBoolean("raucher"));
        p.setHaarfarbe(rs.getString("haarfarbe"));
        p.setKoerpergroesse(rs.getInt("koerpergroesse"));
        p.setReligion(rs.getString("religion"));
        p.setGeschlecht(rs.getString("geschlecht"));
        p.setSucheNach(rs.getString("suche_nach"));

        result.add(p);
      }
    } catch (SQLException e2) {
      e2.printStackTrace();
    }

    return result;
  }
  
  /**
   * Auslesen von Profilen nach Name
   * 
   * @param name
   * @return ArrayList<Profil> 
   */
  public ArrayList<Profil> findProfileByName(String name) {
    Connection con = DBConnection.getConnection();
    ArrayList<Profil> result = new ArrayList<Profil>();

    try {
      PreparedStatement stmt = con.prepareStatement("SELECT * FROM profil WHERE nachname = ?");
      stmt.setString(1, name);

      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        while (rs.next()) {
          Profil p = new Profil();
          p.setId(rs.getInt("id"));
          p.setVorname(rs.getString("vorname"));
          p.setNachname(rs.getString("nachname"));
          p.setEmail(rs.getString("email"));
          p.setGeburtsdatum(rs.getDate("geburtstag"));
          p.setRaucher(rs.getBoolean("raucher"));
          p.setHaarfarbe(rs.getString("haarfarbe"));
          p.setKoerpergroesse(rs.getInt("koerpergroesse"));
          p.setReligion(rs.getString("religion"));
          p.setGeschlecht(rs.getString("geschlecht"));
          p.setSucheNach(rs.getString("suche_nach"));

          result.add(p);
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return result;
  }

  /**
   * Lädt alle Profile aus der DB, die zu einer Merkliste gehören
   * 
   * @param eigenProfil
   * @return ArrayList<Profil>
   */
  public ArrayList<Profil> findProfileForMerkliste(Profil eigenProfil) {
    Connection con = DBConnection.getConnection();
    ArrayList<Profil> results = new ArrayList<>();

    /**
     * Select Befehl holt alle Profile, die sich auf dem Merkzettel befinden. Zusätzlich werden alle
     * Profile rausgenommen, die sich auf irgendeiner Sperrliste befinden.
     */
    try (PreparedStatement stmt = con.prepareStatement("SELECT p.* FROM profil p "
        + "JOIN merkzettel m ON p.id = m.fpID " + "LEFT JOIN kontaktsperre k ON p.id = k.epID "
        + "WHERE m.epID = ? and k.id IS NULL ORDER BY p.vorname")) {

      stmt.setString(1, Integer.toString(eigenProfil.getId()));

      setProfilInArray(results, stmt);

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return results;
  }

  /**
   * Lädt alle Profile aus der DB, die zu einer Kontaktsperre gehören
   * 
   * @param eigenProfil
   * @return ArrayList<Profil>
   */
  public ArrayList<Profil> findProfileForKontaktsperre(Profil eigenProfil) {

    Connection con = DBConnection.getConnection();
    ArrayList<Profil> results = new ArrayList<>();

    try (PreparedStatement stmt = con.prepareStatement("SELECT p.* FROM profil p "
        + "JOIN kontaktsperre k ON p.id = k.fpID WHERE k.epID = ? ORDER BY p.vorname")) {

      stmt.setString(1, Integer.toString(eigenProfil.getId()));

      setProfilInArray(results, stmt);


    } catch (SQLException e) {
      e.printStackTrace();
    }
    return results;

  }

  /**
   * @param p
   * @param stmt Methode ordnet ein Profil in eine ArrayList zu.
   */
  private void setProfilInArray(Profil p, PreparedStatement stmt) {

    try (ResultSet rs = stmt.executeQuery();) {

      while (rs.next()) {
        p.setId(rs.getInt("id"));
        p.setVorname(rs.getString("vorname"));
        p.setNachname(rs.getString("nachname"));
        p.setEmail(rs.getString("email"));
        p.setGeburtsdatum(rs.getDate("geburtstag"));
        p.setRaucher(rs.getBoolean("raucher"));
        p.setHaarfarbe(rs.getString("haarfarbe"));
        p.setKoerpergroesse(rs.getInt("koerpergroesse"));
        p.setReligion(rs.getString("religion"));
        p.setGeschlecht(rs.getString("geschlecht"));
        p.setSucheNach(rs.getString("suche_nach"));


      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  /**
   * @param results
   * @param stmt Methode ordnet eine Menge von Profilen in ein ArrayList zu
   */
  private void setProfilInArray(ArrayList<Profil> results, PreparedStatement stmt) {

    try (ResultSet rs = stmt.executeQuery();) {

    
      while (rs.next()) {
        Profil p = new Profil();
        p.setId(rs.getInt("id"));
        p.setVorname(rs.getString("vorname"));
        p.setNachname(rs.getString("nachname"));
        p.setEmail(rs.getString("email"));
        p.setGeburtsdatum(rs.getDate("geburtstag"));
        p.setRaucher(rs.getBoolean("raucher"));
        p.setHaarfarbe(rs.getString("haarfarbe"));
        p.setKoerpergroesse(rs.getInt("koerpergroesse"));
        p.setReligion(rs.getString("religion"));
        p.setGeschlecht(rs.getString("geschlecht"));
        p.setSucheNach(rs.getString("suche_nach"));

        results.add(p);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Einfügen eines Profils in die Datenbank
   * 
   * @param p das zu speichernde Profil-Objekt
   * @return Profil
   */
  public Profil insert(Profil p) {
    
    Connection con = DBConnection.getConnection();

    try (PreparedStatement stmt = con.prepareStatement("INSERT INTO profil (email, vorname, nachname, geburtstag, "
    	+ "haarfarbe, koerpergroesse, raucher, religion, geschlecht, suche_nach) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
    	
    	Statement stmt1 = con.createStatement();
		ResultSet rs = stmt1.executeQuery("SELECT MAX(id) AS maxid FROM profil");
		if (rs.next()){
			p.setId(rs.getInt("maxid") + 1);
		}

      java.util.Date utilDate = p.getGeburtsdatum();
      java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

      stmt.setString(1, p.getEmail());
      stmt.setString(2, p.getVorname());
      stmt.setString(3, p.getNachname());
      stmt.setDate(4, sqlDate);
      stmt.setString(5, p.getHaarfarbe());
      stmt.setInt(6, (int) p.getKoerpergroesse());
      stmt.setBoolean(7, p.isRaucher());
      stmt.setString(8, p.getReligion());
      stmt.setString(9, p.getGeschlecht());
      stmt.setString(10, p.getSucheNach());

      stmt.executeUpdate();


    } catch (SQLException e) {
      e.printStackTrace();
      logger.severe("ProfilMapper.insert konnte Daten nicht in die DB gespeichert werden: " + e.getMessage() + e.getCause());
    }
    return p;

  }

  /**
   * Updaten eines Objektes aus der Datenbank
   * 
   * @param p das Profil-Objekt, das in die DB geschrieben werden soll
   * @return Profil
   */
  public Profil update(Profil p) {
    Connection con = DBConnection.getConnection();

    try (PreparedStatement stmt = con.prepareStatement(
        "UPDATE profil SET vorname = ?, nachname = ?, geburtstag = ?, "
            + "haarfarbe = ?, koerpergroesse=  ?, raucher = ?, religion = ?, email = ?, geschlecht = ?, suche_nach = ? WHERE id = ?")) {
    	

      java.util.Date utilDate = p.getGeburtsdatum();
      java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

      stmt.setString(1, p.getVorname());
      stmt.setString(2, p.getNachname());
      stmt.setDate(3, sqlDate);
      stmt.setString(4, p.getHaarfarbe());
      stmt.setInt(5, (int) p.getKoerpergroesse());
      stmt.setBoolean(6, p.isRaucher());
      stmt.setString(7, p.getReligion());
      stmt.setString(8, p.getEmail());
      stmt.setString(9, p.getGeschlecht());
      stmt.setString(10, p.getSucheNach());
      stmt.setInt(11, p.getId());
      
      stmt.executeUpdate();
      

    } catch (SQLException e2) {
      e2.printStackTrace();
    }
    
    
    return p;
  }

  /**
   * Löschen der Daten eines Profils aus der Datenbank
   * 
   * @param p ist das zu löschende Objekt
   */
  public void delete(Profil p) {
    Connection con = DBConnection.getConnection();

    try (PreparedStatement stmt = con.prepareStatement("DELETE FROM profil WHERE id = ?")) {

      stmt.setInt(1, p.getId());

      stmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
