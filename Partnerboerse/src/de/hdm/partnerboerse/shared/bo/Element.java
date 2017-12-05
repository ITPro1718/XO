package de.hdm.partnerboerse.shared.bo;

public class Element extends BusinessObjekt {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String bezeichnung;
  
  // Fremdschlüssel aus der Auswahl Tabelle
  private int auswahlID;

public String getBezeichnung() {
	return bezeichnung;
}

public void setBezeichnung(String bezeichnung) {
	this.bezeichnung = bezeichnung;
}

public int getAuswahlID() {
	return auswahlID;
}

public void setAuswahlID(int auswahlID) {
	this.auswahlID = auswahlID;
}
  
  

}
