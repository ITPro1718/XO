package de.hdm.partnerboerse.shared.bo;

public class Eigenschaft extends BusinessObjekt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String erlaeuterung;
	private String is_a;

	public String getErlaeuterung() {
		return erlaeuterung;
	}

	public void setErlaeuterung(String erlaeuterung) {
		this.erlaeuterung = erlaeuterung;
	}

	public String getIs_a() {
		return is_a;
	}

	public void setIs_a(String is_a) {
		this.is_a = is_a;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((erlaeuterung == null) ? 0 : erlaeuterung.hashCode());
		result = prime * result + ((is_a == null) ? 0 : is_a.hashCode());
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
		if (is_a == null) {
			if (other.is_a != null)
				return false;
		} else if (!is_a.equals(other.is_a))
			return false;
		if (erlaeuterung == null) {
			if (other.erlaeuterung != null)
				return false;
		} else if (!erlaeuterung.equals(other.erlaeuterung))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Eigenschaft [erlaeuterung=" + erlaeuterung + ", is_a=" + is_a + "]";
	}

}