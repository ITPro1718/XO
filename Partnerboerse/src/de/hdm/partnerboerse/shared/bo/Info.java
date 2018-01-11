package de.hdm.partnerboerse.shared.bo;

public class Info extends BusinessObjekt {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String text;
  
  private String is_a;
  
  private int freitextID;
  
  private int auswahlID;

public String getText() {
	return text;
}

public void setText(String text) {
	this.text = text;
}

public String getIs_a() {
	return is_a;
}

public void setIs_a(String is_a) {
	this.is_a = is_a;
}

public int getFreitextID() {
	return freitextID;
}

public void setFreitextID(int freitextID) {
	this.freitextID = freitextID;
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
	result = prime * result + freitextID;
	result = prime * result + ((is_a == null) ? 0 : is_a.hashCode());
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
	if (auswahlID != other.auswahlID)
		return false;
	if (freitextID != other.freitextID)
		return false;
	if (is_a == null) {
		if (other.is_a != null)
			return false;
	} else if (!is_a.equals(other.is_a))
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
	return "Info [text=" + text + ", is_a=" + is_a + ", freitextID=" + freitextID + ", auswahlID=" + auswahlID + "]";
}
  
  

}

