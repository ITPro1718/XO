package de.hdm.partnerboerse.shared.bo;

public class Auswahl extends BusinessObjekt {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String titel;

  /**
   * @return the titel
   */
  public String getTitel() {
    return titel;
  }

  /**
   * @param titel the titel to set
   */
  public void setTitel(String titel) {
    this.titel = titel;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((titel == null) ? 0 : titel.hashCode());
    return result;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
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

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Auswahl [titel=" + titel + "]";
  }
  
  

}
