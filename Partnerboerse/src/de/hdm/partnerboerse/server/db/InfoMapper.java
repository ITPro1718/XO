package de.hdm.partnerboerse.server.db;

import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Info;

public class InfoMapper {

	/**
	   * Stellt sicher, dass die Klasse nur ein mal instanziiert wird   * 
	   */
	  private static InfoMapper infoMapper = null;

	  /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
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
	   * Gibt ein Info-Objekt zur�ck, dass per ID gesucht wird (Prim�rschl�ssel)
	   * @param id
	   * @return
	   */
	  public Info findByKey(int id){
		  return null;
	  }
	  
	  /**
	   * Gibt alle Info-Eintr�ge zur�ck
	   * @return
	   */
	  public ArrayList<Info> findAll(){
		  return null;
	  }

    public void deleteInfo(Info info) {
      // TODO Auto-generated method stub
      
    }

}
