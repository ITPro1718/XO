package de.hdm.partnerboerse.shared.bo;

public class Merkzettel extends BusinessObjekt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Fremdschlüssel aus Profil Tabelle
	private int fremdprofilID;

	// Fremdschlüssel aus Profil Tabelle
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
		Merkzettel other = (Merkzettel) obj;
		if (eigenprofilID != other.eigenprofilID)
			return false;
		if (fremdprofilID != other.fremdprofilID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Merkzettel [fremdprofilID=" + fremdprofilID + ", eigenprofilID=" + eigenprofilID + "]";
	}

}
