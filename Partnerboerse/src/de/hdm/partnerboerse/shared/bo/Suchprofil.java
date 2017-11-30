package de.hdm.partnerboerse.shared.bo;

public class Suchprofil extends BusinessObjekt{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String title;
  
  private String haarFarbe;
  
  private double koerpergroesse;
  
  private boolean raucher;
  
  private int alter;

  /**
   * @return the title
   */
  public String getTitle() {
    return title;
  }

  /**
   * @param title the title to set
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * @return the haarFarbe
   */
  public String getHaarFarbe() {
    return haarFarbe;
  }

  /**
   * @param haarFarbe the haarFarbe to set
   */
  public void setHaarFarbe(String haarFarbe) {
    this.haarFarbe = haarFarbe;
  }

  /**
   * @return the koerpergroesse
   */
  public double getKoerpergroesse() {
    return koerpergroesse;
  }

  /**
   * @param koerpergroesse the koerpergroesse to set
   */
  public void setKoerpergroesse(double koerpergroesse) {
    this.koerpergroesse = koerpergroesse;
  }

  /**
   * @return the raucher
   */
  public boolean isRaucher() {
    return raucher;
  }

  /**
   * @param raucher the raucher to set
   */
  public void setRaucher(boolean raucher) {
    this.raucher = raucher;
  }

  /**
   * @return the alter
   */
  public int getAlter() {
    return alter;
  }

  /**
   * @param alter the alter to set
   */
  public void setAlter(int alter) {
    this.alter = alter;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + alter;
    result = prime * result + ((haarFarbe == null) ? 0 : haarFarbe.hashCode());
    long temp;
    temp = Double.doubleToLongBits(koerpergroesse);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + (raucher ? 1231 : 1237);
    result = prime * result + ((title == null) ? 0 : title.hashCode());
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
    Suchprofil other = (Suchprofil) obj;
    if (alter != other.alter)
      return false;
    if (haarFarbe == null) {
      if (other.haarFarbe != null)
        return false;
    } else if (!haarFarbe.equals(other.haarFarbe))
      return false;
    if (Double.doubleToLongBits(koerpergroesse) != Double.doubleToLongBits(other.koerpergroesse))
      return false;
    if (raucher != other.raucher)
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    return true;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Suchprofil [title=" + title + ", haarFarbe=" + haarFarbe + ", koerpergroesse="
        + koerpergroesse + ", raucher=" + raucher + ", alter=" + alter + "]";
  }
  

}
