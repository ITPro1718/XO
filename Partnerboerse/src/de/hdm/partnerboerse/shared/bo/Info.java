package de.hdm.partnerboerse.shared.bo;

public class Info extends BusinessObjekt {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String text;

	private int eigenschaftId;

	private int epId;

	private int suchprofilId;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getSuchprofilId() {
		return suchprofilId;
	}

	public void setSuchprofilId(int suchprofilId) {
		this.suchprofilId = suchprofilId;
	}

	public int geteigenschaftId() {
		return eigenschaftId;
	}

	public void seteigenschaftId(int eigenschaftId) {
		this.eigenschaftId = eigenschaftId;
	}

	public int getepId() {
		return epId;
	}

	public void setepId(int epId) {
		this.epId = epId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + epId;
		result = prime * result + eigenschaftId;
		result = prime * result + suchprofilId;
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
		if (epId != other.epId)
			return false;
		if (eigenschaftId != other.eigenschaftId)
			return false;
		if (suchprofilId != other.suchprofilId)
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
		return "Info [text=" + text + ", suchprofilId=" + suchprofilId + ", eigenschaftId=" + eigenschaftId + ", epId="
				+ epId + "]";
	}

}
