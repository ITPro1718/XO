package de.hdm.partnerboerse.shared.bo;

public class Info extends BusinessObjekt {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String text;
  
  private int eigenprofilID;
  
  private int eigenschaftsID;

  public int getEigenprofilID() {
	return eigenprofilID;
}

public void setEigenprofilID(int eigenprofilID) {
	this.eigenprofilID = eigenprofilID;
}

public int getEigenschaftsID() {
	return eigenschaftsID;
}

public void setEigenschaftsID(int eigenschaftsID) {
	this.eigenschaftsID = eigenschaftsID;
}

/**
   * @return the text
   */
  public String getText() {
    return text;
  }

  /**
   * @param text the text to set
   */
  public void setText(String text) {
    this.text = text;
  }

@Override
public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + eigenprofilID;
	result = prime * result + eigenschaftsID;
	result = prime * result + ((text == null) ? 0 : text.hashCode());
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
	Info other = (Info) obj;
	if (eigenprofilID != other.eigenprofilID)
		return false;
	if (eigenschaftsID != other.eigenschaftsID)
		return false;
	if (text == null) {
		if (other.text != null)
			return false;
	} else if (!text.equals(other.text))
		return false;
	return true;
}

@Override
public String toString() {
	return "Info [text=" + text + ", eigenprofilID=" + eigenprofilID + ", eigenschaftsID=" + eigenschaftsID + "]";
}

}
