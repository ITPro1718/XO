package de.hdm.partnerboerse.shared.report;

import java.io.Serializable;
import java.util.Date;

/*
 * Disclaimer: Diese Klasse wurde aus dem Bankprojekt übernommen und auf unser 
 * Projekt angepasst.
 */

/**
 * <p>
 * Basisklasse aller Reports. Reports sind als <code>Serializable</code>
 * deklariert, damit sie von dem Server an den Client gesendet werden können.
 * Der Zugriff auf Reports erfolgt also nach deren Bereitstellung lokal auf dem
 * Client.
 * </p>
 * <p>
 * Ein Report besitzt eine Reihe von Standardelementen. Sie werden mittels
 * Attributen modelliert und dort dokumentiert.
 * </p>
 * 
 * @see Report
 * @author Thies, Burghardt
 */
public class Report implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	   * Ein kleines Impressum, das eine Art Briefkopf darstellt. Jedes Unternehmen
	   * einige Daten wie Firmenname, Adresse, Logo, etc. auf GeschÃ¤ftsdokumenten
	   * ab. Dies gilt auch fÃ¼r die hier realisierten Reports.
	   */
	  private Paragraph imprint = null;

	  /**
	   * Kopfdaten des Berichts.
	   */
	  private Paragraph headerData = null;
	  
	  private Paragraph profilData = null;
	  
	  private Paragraph profilInhalt = null;
	  
	  private Paragraph erlaeuterungen = null;
	  
	  private Paragraph info = null;

	  /**
	   * Jeder Bericht kann einen individuellen Titel besitzen.
	   */
	  private String title = "Report";

	  /**
	   * Datum der Erstellung des Berichts.
	   */
	  private Date created = new Date();

	  /**
	   * Auslesen des Impressums.
	   * 
	   * @return Text des Impressums
	   */
	  public Paragraph getImprint() {
	    return this.imprint;
	  }

	  /**
	   * Setzen des Impressums.
	   * 
	   * @param imprint Text des Impressums
	   */
	  public void setImprint(Paragraph imprint) {
	    this.imprint = imprint;
	  }

	  /**
	   * Auslesen der Kopfdaten.
	   * 
	   * @return Text der Kopfdaten.
	   */
	  public Paragraph getHeaderData() {
	    return this.headerData;
	  }

	  /**
	   * Setzen der Kopfdaten.
	   * 
	   * @param headerData Text der Kopfdaten.
	   */
	  public void setHeaderData(Paragraph headerData) {
	    this.headerData = headerData;
	  }
	  
	  /**
	   * Setzen der Profildata, damit sind die Beschreibungen 
	   * der Profilattribute gemeint
	   * @param profilData
	   */
	  public void setProfilData(Paragraph profilData){
		  this.profilData = profilData;
	  }
	  
	  /**
	   * Auslesen der Profildata
	   * @return
	   */
	  public Paragraph getProfilData(){
		  return this.profilData;
	  }

	  /**
	   * Auslesen des Berichtstitels.
	   * 
	   * @return Titeltext
	   */
	  public String getTitle() {
	    return this.title;
	  }

	  /**
	   * Setzen des Berichtstitels.
	   * 
	   * @param title Titeltext
	   */
	  public void setTitle(String title) {
	    this.title = title;
	  }

	  /**
	   * Auslesen des Erstellungsdatums.
	   * 
	   * @return Datum der Erstellung des Berichts
	   */
	  public Date getCreated() {
	    return this.created;
	  }

	  /**
	   * Setzen des Erstellungsdatums. <b>Hinweis:</b> Der Aufruf dieser Methoden
	   * ist nicht unbedingt erforderlich, da jeder Report bei seiner Erstellung
	   * automatisch den aktuellen Zeitpunkt festhÃ¤lt.
	   * 
	   * @param created Zeitpunkt der Erstellung
	   */
	  public void setCreated(Date created) {
	    this.created = created;
	  }

	  /**
	   * Auslesen der Profilinhalte
	   */
	public Paragraph getProfilInhalt() {
		return profilInhalt;
	}

	/**
	 * Setzten der Profilinhalte als Paragraph
	 * Damit sind die Werte gemeint, die ein Profil als Attribut eingetragen hat
	 */
	public void setProfilInhalt(Paragraph profilInhalt) {
		this.profilInhalt = profilInhalt;
	}

	public Paragraph getErlaeuterungen() {
		return erlaeuterungen;
	}

	public void setErlaeuterungen(Paragraph erlaeuterungen) {
		this.erlaeuterungen = erlaeuterungen;
	}

	public Paragraph getInfo() {
		return info;
	}

	public void setInfo(Paragraph info) {
		this.info = info;
	}

}
