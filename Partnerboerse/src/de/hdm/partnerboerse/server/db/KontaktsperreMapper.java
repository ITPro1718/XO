package de.hdm.partnerboerse.server.db;

import java.util.ArrayList;

import de.hdm.partnerboerse.shared.bo.Kontaktsperre;

public class KontaktsperreMapper {

	/**
	   * Stellt sicher, dass die Klasse nur ein mal instanziiert wird   * 
	   */
	  private static KontaktsperreMapper kontaktsperreMapper = null;

	  /**
	   * Geschützter Konstruktor - verhindert die Möglichkeit, mit <code>new</code>
	   * neue Instanzen dieser Klasse zu erzeugen.
	   */
	  protected KontaktsperreMapper() {
	  }

	  /**
	   * Stellt sicher, dass es nur eine Instanz der Klasse gibt. Die Klasse kann nicht mit <code>new</code> instanziiert werden
	   * 
	   * @return <code>KontaktsperreMapper</code>-Objekt.
	   */
	  public static KontaktsperreMapper kontaktsperreMapper() {
	    if (kontaktsperreMapper == null) {
	      kontaktsperreMapper = new KontaktsperreMapper();
	    }

	    return kontaktsperreMapper;
	  }
	  
	  
	  /**
	   * Speichert einen KontaktsperrenEintrag in der DB
	   * @param kontaktsperre
	   */
	  public void insertKontaktsperreEintrag(Kontaktsperre kontaktsperre){
		  
	  }
	  
	  /**
	   * L�scht einen KontaktsperrenEintrag in der DB
	   * @param kontaktsperre
	 * @return 
	   */
	  public Kontaktsperre deleteKontaktsperreEintrag(Kontaktsperre kontaktsperre){
		return null;
		  
	  }
	  
	  /**
	   * Gibt eine Kontaktsperre zur�ck per ID (Prim�rschl�ssel)
	   * @param id
	   * @return
	   */
	  public Kontaktsperre findByKey(int id){
		  return null;
	  }
	  
	  /**
	   * Gibt alle Kontaktsperren-Eintr�ge zur�ck
	   * @return
	   */
	  public ArrayList<Kontaktsperre> findAll(){
		  return null;
	  }

}
