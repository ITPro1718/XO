package de.hdm.partnerboerse.server.db;

import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Element;

public class ElementMapper {

	
	/**
	   * Stellt sicher, dass die Klasse nur ein mal instanziiert wird   * 
	   */
	  private static ElementMapper elementMapper = null;

	  /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen.
	   */
	  protected ElementMapper() {
	  }

	  /**
	   * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann nicht mit <code>new</code> instanziiert werden
	   * 
	   * @return <code>ElementMapper</code>-Objekt.
	   */
	  public static ElementMapper elementMapper() {
	    if (elementMapper == null) {
	      elementMapper = new ElementMapper();
	    }

	    return elementMapper;
	  }
	  
	  
	  /**
	   * Gibt ein Element-Eintrag aus der Datenbank zur�ck
	   * @return
	   */
	  public Element findByKey(int id){
		  return null;
	  }
	  
	  /**
	   * Gibt alle Element-Eintr�ge zur�ck
	   * @return
	   */
	  public ArrayList<Element> findAll(){
		  return null;
	  }
		

	
	
	
	
}
