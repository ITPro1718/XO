package de.hdm.partnerboerse.shared.bo;

public class Freitext extends BusinessObjekt {


/**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String beschreibung;

public String getBeschreibung() {
	return beschreibung;
}

public void setBeschreibung(String beschreibung) {
	this.beschreibung = beschreibung;
}
  
@Override
public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((beschreibung == null) ? 0 : beschreibung.hashCode());
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
	Freitext other = (Freitext) obj;
	if (beschreibung == null) {
		if (other.beschreibung != null)
			return false;
	} else if (!beschreibung.equals(other.beschreibung))
		return false;
	return true;
}

@Override
public String toString() {
	return "Freitext [beschreibung=" + beschreibung + "]";
}



}
