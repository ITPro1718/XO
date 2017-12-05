package de.hdm.partnerboerse.server.db;

import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Merkzettel;

public class MerkzettelMapper {

	/**
	   * Stellt sicher, dass die Klasse nur ein mal instanziiert wird   * 
	   */
	  private static MerkzettelMapper merkzettelMapper = null;

	  /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen.
	   */
	  protected MerkzettelMapper() {
	  }

	  /**
	   * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann nicht mit <code>new</code> instanziiert werden
	   * 
	   * @return <code>MerkzettelMapper</code>-Objekt.
	   */
	  public static MerkzettelMapper merkzettelMapper() {
	    if (merkzettelMapper == null) {
	    	merkzettelMapper = new MerkzettelMapper();
	    }

	    return merkzettelMapper;
	  }
	  
	  
	  /**
	   * Speichert einen MerkzettelEintrag in der DB
	   * @param merkzettel
	   */
	  public Merkzettel insertMerkzettelEintrag(Merkzettel merkzettel){
		  return null;
	  }
	  
	  /**
	   * L�scht einen MerkzettelEintrag in der DB
	   * @param merkzettel
	 * @return 
	   */
	  public Merkzettel deleteMerkzettelEintrag(Merkzettel merkzettel){
		  return null;
	  }
	  
	  /**
	   * Gibt eine Merkzettel zur�ck per ID (Prim�rschl�ssel)
	   * @param id
	   * @return
	   */
	  public Merkzettel findByKey(int id){
		  return null;
	  }
	  
	  /**
	   * Gibt alle Merkzettel-Eintr�ge zur�ck
	   * @return
	   */
	  public ArrayList<Merkzettel> findAll(){
		  return null;
	  }
}
