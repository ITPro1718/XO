package de.hdm.partnerboerse.shared.bo;

public class Besuch extends BusinessObjekt {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private int fremdprofilID;
  
  private int eigenprofilID;

public int getEigenprofilID() {
	return eigenprofilID;
}

public void setEigenprofilID(int eigenprofilID) {
	this.eigenprofilID = eigenprofilID;
}

public int getFremdprofilID() {
	return fremdprofilID;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + eigenprofilID;
	result = prime * result + fremdprofilID;
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
	Besuch other = (Besuch) obj;
	if (eigenprofilID != other.eigenprofilID)
		return false;
	if (fremdprofilID != other.fremdprofilID)
		return false;
	return true;
}

public void setFremdprofilID(int fremdprofilID) {
	this.fremdprofilID = fremdprofilID;
}

@Override
public String toString() {
	return "Besuch [fremdprofilID=" + fremdprofilID + ", eigenprofilID=" + eigenprofilID + "]";
}
  
}
