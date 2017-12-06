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

@Override
public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + auswahlID;
	result = prime * result + ((bezeichnung == null) ? 0 : bezeichnung.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (!super.equals(obj))
		return false;
	if (getClass() != obj.getClass())
		return false;
	Element other = (Element) obj;
	if (auswahlID != other.auswahlID)
		return false;
	if (bezeichnung == null) {
		if (other.bezeichnung != null)
			return false;
	} else if (!bezeichnung.equals(other.bezeichnung))
		return false;
	return true;
}

@Override
public String toString() {
	return "Element [bezeichnung=" + bezeichnung + ", auswahlID=" + auswahlID + "]";
}
  
}