package de.hdm.partnerboerse.server.db;

import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Freitext;

public class FreitextMapper {

	
	/**
	   * Stellt sicher, dass die Klasse nur ein mal instanziiert wird   * 
	   */
	  private static FreitextMapper freitextMapper = null;

	  /**
	   * GeschÃ¼tzter Konstruktor - verhindert die MÃ¶glichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen.
	   */
	  protected FreitextMapper() {
	  }

	  /**
	   * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann nicht mit <code>new</code> instanziiert werden
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
	   * @param freitext
	   */
	  public void insertFreitext(Freitext freitext){
		  
	  }
	  
	  /**
	   * Ändert/Updated einen Freitext
	   * @param freitext
	   */
	  public void updateFreitext(Freitext freitext){
		  
	  }
	  
	  /**
	   * Löscht einen Freitext-Eintrag aus der DB
	   * @param freitext
	   */
	  public void deleteFreitext(Freitext freitext){
		  
	  }
	  
	  /**
	   * sucht einen Freitext-Eintrag per ID (Primärschlüssel) und gibt diesen zurück
	   * @param id
	   * @return
	   */
	  public Freitext findByKey(int id){
		  return null;
	  }
	  
	  /**
	   * Gibt alle Freitexte zurück
	   * @return
	   */
	  public ArrayList<Freitext> findAll(){
		  return null;
	  }
	  
	  

}
