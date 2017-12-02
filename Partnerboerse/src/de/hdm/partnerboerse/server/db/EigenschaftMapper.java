package de.hdm.partnerboerse.server.db;

import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Eigenschaft;

public class EigenschaftMapper {

		
	/**
	   * Stellt sicher, dass die Klasse nur ein mal instanziiert wird   * 
	   */
	  private static EigenschaftMapper eigenschaftMapper = null;

	  /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen.
	   */
	  protected EigenschaftMapper() {
	  }

	  /**
	   * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann nicht mit <code>new</code> instanziiert werden
	   * 
	   * @return <code>EigenschaftMapper</code>-Objekt.
	   */
	  public static EigenschaftMapper eigenschaftMapper() {
	    if (eigenschaftMapper == null) {
	      eigenschaftMapper = new EigenschaftMapper();
	    }

	    return eigenschaftMapper;
	  }
	  
	  /**
	   * Speichert ein Eigenschaftsobjekt in der Datenbank
	   * @param eigenschaft
	   */
	  public void insertEigenschaft(Eigenschaft eigenschaft){
		  
	  }
	  
	  /**
	   * Updated einen Eigenschafts-Eintrag in der Datenbank
	   * @param eigenschaft
	   */
	  public void updateEigenschaft(Eigenschaft eigenschaft){
		  
	  }
	  
	  /**
	   * L�scht einen Eigenschafts-Eintrag aus der Datenbank
	   * @param eigenschaft
	   */
	  public void deleteEigenschaft(Eigenschaft eigenschaft){
		  
	  }
	  
	  /**
	   * Gibt einen Eigenschafts-Eintrag zur�ck, der per Key gesucht wird (Prim�rschl�ssel)
	   * @param id
	   * @return
	   */
	  public Eigenschaft findByKey(int id){
		  return null;
	  }
	  
	  /**
	   * Gibt alle Eigenschafts-Eintr�ge zur�ck
	   * @return
	   */
	  public ArrayList<Eigenschaft> findAll(){
		  return null;
		  
	  }
	  

}
