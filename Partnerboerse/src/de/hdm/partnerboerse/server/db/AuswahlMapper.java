package de.hdm.partnerboerse.server.db;

import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Auswahl;

public class AuswahlMapper {

		
	/**
	   * Stellt sicher, dass die Klasse nur ein mal instanziiert wird   * 
	   */
	  private static AuswahlMapper auswahlMapper = null;

	  /**
	   * GeschÃ¼tzter Konstruktor - verhindert die MÃ¶glichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen.
	   */
	  protected AuswahlMapper() {
	  }

	  /**
	   * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann nicht mit <code>new</code> instanziiert werden
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
	   * Schreibt ein Auswahlobjekt in die Datenbank
	   * @param a
	   */
	  public void insertAuswahl(Auswahl a){
		  
	  }
	  
	  /**
	   * Ändert/Updatet ein Auswahlobjekt
	   * @param a
	   */
	  public void updateAuswahl(Auswahl a){
		  
	  }
	  
	  /**
	   * Löscht eine Auswahl aus der Datenbank
	   * @param a
	   */
	  public void deleteAuswahl(Auswahl a){
		  
	  }
	  
	  /**
	   * Sucht einen Auswahleintrag per ID (Primärschlüssel) und gibt diesen Zurück
	   * @param id
	   * @return
	   */
	  public Auswahl findByKey(int id){
		  return null;
		  
	  }
	  
	  /**
	   * Gibt alle Auswahlen zurück
	   * @return
	   */
	  public ArrayList<Auswahl> findAll(){
		  return null;		  
	  }
	
	

}
