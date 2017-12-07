package de.hdm.partnerboerse.server.db;

import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Info;

public class InfoMapper {

	/**
	   * Stellt sicher, dass die Klasse nur ein mal instanziiert wird   * 
	   */
	  private static InfoMapper infoMapper = null;

	  /**
	   * GeschÃ¼tzter Konstruktor - verhindert die MÃ¶glichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen.
	   */
	  protected InfoMapper() {
	  }

	  /**
	   * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann nicht mit <code>new</code> instanziiert werden
	   * 
	   * @return <code>InfoMapper</code>-Objekt.
	   */
	  public static InfoMapper infoMapper() {
	    if (infoMapper == null) {
	      infoMapper = new InfoMapper();
	    }

	    return infoMapper;
	  }
	  
	  /**
	   * Gibt ein Info-Objekt zurück, dass per ID gesucht wird (Primärschlüssel)
	   * @param id
	   * @return
	   */
	  public Info findByKey(int id){
		  return null;
	  }
	  
	  /**
	   * Gibt alle Info-Einträge zurück
	   * @return
	   */
	  public ArrayList<Info> findAll(){
		  return null;
	  }

    public void deleteInfo(Info info) {
      // TODO Auto-generated method stub
      
    }

}
