package de.hdm.partnerboerse.shared.bo;

public class Eigenschaft extends BusinessObjekt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String erlaeuterung;
	
	private int infoID;
	
	private int epID;

	

	public String getErlaeuterung() {
		return erlaeuterung;
	}

	public void setErlaeuterung(String erlaeuterung) {
		this.erlaeuterung = erlaeuterung;
	}

	public int getInfoID() {
		return infoID;
	}

	public void setInfoID(int infoID) {
		this.infoID = infoID;
	}

	public int getEpID() {
		return epID;
	}

	public void setEpID(int epID) {
		this.epID = epID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + epID;
		result = prime * result + ((erlaeuterung == null) ? 0 : erlaeuterung.hashCode());
		result = prime * result + infoID;
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
		Eigenschaft other = (Eigenschaft) obj;
		if (epID != other.epID)
			return false;
		if (erlaeuterung == null) {
			if (other.erlaeuterung != null)
				return false;
		} else if (!erlaeuterung.equals(other.erlaeuterung))
			return false;
		if (infoID != other.infoID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Eigenschaft [erlaeuterung=" + erlaeuterung + ", infoID=" + infoID + ", epID=" + epID + "]";
	}

	
	

}