package de.hdm.partnerboerse.server.db;

import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Besuch;
import de.hdm.partnerboerse.shared.bo.Profil;

/**
 * Mapper-Klasse.
 * 
 * @author sanjamikulic
 *
 */
public class BesuchMapper {
	
	/**
	   * Stellt sicher, dass die Klasse nur ein mal instanziiert wird   * 
	   */
	  private static BesuchMapper besuchMapper = null;

	  /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen.
	   */
	  protected BesuchMapper() {
	  }

	  /**
	   * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann nicht mit <code>new</code> instanziiert werden
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
	   * Sucht nach einem Besuch-Eintrag nach der ID (Prim�rschl�ssen
	   * @param id
	   * @return
	   */
	  public Besuch findByKey(int id){
		return null;
	  }
	  
	  /**
	   * Gibt alle Besuch Eintr�ge zur�ck
	   * @return
	   */
	  public ArrayList<Besuch> findAll(){
			return null;
		  }
	  
	  /**
	   * Gibt alle Besuch-Eintr�ge eines bestimmten Profils aus
	   * @param p
	   * @return
	   */
	  public ArrayList<Besuch> findByEigenprofil(Profil p){
			return null;
		  }
	  
	  /**
	   * Speichert einen Profilbesuch in der Datenbank
	   * @param b
	   */
	  public void insert(Besuch b){
		  }
	  
}
