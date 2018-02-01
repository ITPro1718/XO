package de.hdm.partnerboerse.shared.report;

import java.io.Serializable;

/*
 * Disclaimer: Diese Klasse wurde aus dem Bankprojekt übernommen.
 */

/**
 * Spalte eines <code>Row</code>-Objekts. <code>Column</code>-Objekte
 * implementieren das <code>Serializable</code>-Interface und kÃ¶nnen daher als
 * Kopie z.B. vom Server an den Client Ã¼bertragen werden.
 * 
 * @see Row
 * @author Thies
 */
public class Column implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	  /**
	   * Der Wert eines Spaltenobjekts entspricht dem Zelleneintrag einer Tabelle.
	   */
	  private String value = "";

	  /**
	   * no argument constructor
	   */
	  public Column() {
	  }

	  /**
	   * Konstruktor, der die Angabe eines Werts (Spalteneintrag) erzwingt.
	   */
	  public Column(String s) {
	    this.value = s;
	  }

	  /**
	   * Auslesen des Spaltenwerts.
	   * 
	   * @return der Eintrag als String
	   */
	  public String getValue() {
	    return value;
	  }

	  /**
	   * Ãœberschreiben des aktuellen Spaltenwerts.
	   * 
	   * @param value neuer Spaltenwert
	   */
	  public void setValue(String value) {
	    this.value = value;
	  }

	  /**
	   * Umwandeln des <code>Column</code>-Objekts in einen String.
	   * 
	   * @see java.lang.Object
	   */
	  public String toString() {
	    return this.value;
	  }

}
