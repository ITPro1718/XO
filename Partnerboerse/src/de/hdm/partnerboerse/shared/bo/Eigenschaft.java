package de.hdm.partnerboerse.shared.bo;

public class Eigenschaft extends BusinessObjekt {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String erlaeterung;
  
  // Fremdschlüssel aus der Auswahl Tabelle
  private int auswahlID;
  
  // Fremdschlüssel aus der Freitext Tabelle
  private int freitextID;
  
  private String is_a;

public String getErlaeterung() {
	return erlaeterung;
}

public void setErlaeterung(String erlaeterung) {
	this.erlaeterung = erlaeterung;
}

public int getAuswahlID() {
	return auswahlID;
}

public void setAuswahlID(int auswahlID) {
	this.auswahlID = auswahlID;
}

public int getFreitextID() {
	return freitextID;
}

public void setFreitextID(int freitextID) {
	this.freitextID = freitextID;
}

public String getIs_a() {
	return is_a;
}

public void setIs_a(String is_a) {
	this.is_a = is_a;
}
  
  

}
