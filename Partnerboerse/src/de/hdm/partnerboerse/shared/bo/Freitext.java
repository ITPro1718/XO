package de.hdm.partnerboerse.shared.bo;

public class Freitext extends BusinessObjekt {

	/**
	   * 
	   */
	private static final long serialVersionUID = 1L;

	private int eigenschaftId;

	public int getEigenschaftId() {
		return eigenschaftId;
	}

	public void setEigenschaftId(int eigenschaftId) {
		this.eigenschaftId = eigenschaftId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + eigenschaftId;
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
		if (eigenschaftId != other.eigenschaftId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Freitext [eigenschaftId=" + eigenschaftId + "]";
	}

}
