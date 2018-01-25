package de.hdm.partnerboerse.shared.bo;

/**
 * Diese Klasse repräsentiert ein Auswahlelement.
 * 
 * @author Mikulic
 *
 */
public class Auswahl extends BusinessObjekt {

	private static final long serialVersionUID = 1L;

	private String titel;

	private int eigenschaftId;

	/**
	 * Liefert den Titel der Auswahl.
	 * 
	 * @return Titel als String
	 */
	public String getTitel() {
		return titel;
	}

	/**
	 * Setzt den Titel
	 * 
	 * @param titel
	 *            Titel der Auswahl
	 */
	public void setTitel(String titel) {
		this.titel = titel;
	}

	/**
	 * Hashcode-Implementierung einer Auswahl.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((titel == null) ? 0 : titel.hashCode());
		return result;
	}

	/**
	 * Equals Methode einer Auswahl. Vergleich aller Eigenschaften.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Auswahl other = (Auswahl) obj;
		if (titel == null) {
			if (other.titel != null)
				return false;
		} else if (!titel.equals(other.titel))
			return false;
		return true;
	}

	/**
	 * Ausgabe aller Eigenschaften einer {@link Auswahl}.
	 */
	@Override
	public String toString() {
		return "Auswahl [titel=" + titel + "]";
	}

	public int getEigenschaftId() {
		return eigenschaftId;
	}

	public void setEigenschaftId(int eigenschaftId) {
		this.eigenschaftId = eigenschaftId;
	}

}
