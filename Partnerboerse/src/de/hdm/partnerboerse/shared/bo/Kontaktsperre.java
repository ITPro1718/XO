package de.hdm.partnerboerse.shared.bo;

public class Kontaktsperre extends BusinessObjekt {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private int fremdprofilID;
  
  private int eigenprofilID;

public int getFremdprofilID() {
	return fremdprofilID;
}

public void setFremdprofilID(int fremdprofilID) {
	this.fremdprofilID = fremdprofilID;
}

public int getEigenprofilID() {
	return eigenprofilID;
}

public void setEigenprofilID(int eigenprofilID) {
	this.eigenprofilID = eigenprofilID;
}
  
  

}
