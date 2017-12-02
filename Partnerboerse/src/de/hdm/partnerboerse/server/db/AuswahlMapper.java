package de.hdm.partnerboerse.server.db;

import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Auswahl;

public class AuswahlMapper {

		
	/**
	   * Stellt sicher, dass die Klasse nur ein mal instanziiert wird   * 
	   */
	  private static AuswahlMapper auswahlMapper = null;

	  /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
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
	   * �ndert/Updatet ein Auswahlobjekt
	   * @param a
	   */
	  public void updateAuswahl(Auswahl a){
		  
	  }
	  
	  /**
	   * L�scht eine Auswahl aus der Datenbank
	   * @param a
	   */
	  public void deleteAuswahl(Auswahl a){
		  
	  }
	  
	  /**
	   * Sucht einen Auswahleintrag per ID (Prim�rschl�ssel) und gibt diesen Zur�ck
	   * @param id
	   * @return
	   */
	  public Auswahl findByKey(int id){
		  return null;
		  
	  }
	  
	  /**
	   * Gibt alle Auswahlen zur�ck
	   * @return
	   */
	  public ArrayList<Auswahl> findAll(){
		  return null;		  
	  }
	
	

}
