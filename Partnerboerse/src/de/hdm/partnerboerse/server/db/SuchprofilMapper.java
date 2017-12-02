package de.hdm.partnerboerse.server.db;

import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Suchprofil;

public class SuchprofilMapper {

	/**
	   * Stellt sicher, dass die Klasse nur ein mal instanziiert wird   * 
	   */
	  private static SuchprofilMapper suchprofilMapper = null;

	  /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen.
	   */
	  protected SuchprofilMapper() {
	  }

	  /**
	   * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann nicht mit <code>new</code> instanziiert werden
	   * 
	   * @return <code>SuchprofilMapper</code>-Objekt.
	   */
	  public static SuchprofilMapper suchprofilMapper() {
	    if (suchprofilMapper == null) {
	    	suchprofilMapper = new SuchprofilMapper();
	    }

	    return suchprofilMapper;
	  }
	  
	  public void insertSuchprofil(Suchprofil suchprofil){
		  
	  }
	  
	  public void updateSuchprofil(Suchprofil suchprofil){
		  
	  }
	  
	  public void deleteSuchprofil(Suchprofil suchprofil){
		  
	  }
	  
	  public Suchprofil findByKey(int id){
		  return null;
	  }

	  public ArrayList<Suchprofil> findAll(){
		  return null;
	  }
	  
	  
}
