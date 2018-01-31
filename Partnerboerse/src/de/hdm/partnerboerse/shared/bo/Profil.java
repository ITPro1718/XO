package de.hdm.partnerboerse.shared.bo;

import java.util.Date;


public class Profil extends BusinessObjekt {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private String vorname;
  
  private String nachname;
  
  private String haarfarbe;
  
  private double koerpergroesse;
  
  private boolean raucher;
  
  private String religion;
  
  private Date geburtsdatum;
 
  private int ähnlichkeit;

  private String geschlecht;
  
  private String sucheNach;
  
  private String email;

 




  /** @return the vorname
   */
  public String getVorname() {
    return vorname;
  }

  /**
   * @param vorname the vorname to set
   */
  public void setVorname(String vorname) {
    this.vorname = vorname;
  }

  /**
   * @return the nachname
   */
  public String getNachname() {
    return nachname;
  }

  /**
   * @param nachname the nachname to set
   */
  public void setNachname(String nachname) {
    this.nachname = nachname;
  }

  /**
   * @return the haarfarbe
   */
  public String getHaarfarbe() {
    return haarfarbe;
  }

  /**
   * @param haarfarbe the haarfarbe to set
   */
  public void setHaarfarbe(String haarfarbe) {
    this.haarfarbe = haarfarbe;
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
   * @return the religion
   */
  public String getReligion() {
    return religion;
  }

  /**
   * @param religion the religion to set
   */
  public void setReligion(String religion) {
    this.religion = religion;
  }

  /**
   * @return the geburtsdatum
   */
  public Date getGeburtsdatum() {
    return geburtsdatum;
  }

  /**
   * @param geburtsdatum the geburtsdatum to set
   */
  public void setGeburtsdatum(Date geburtsdatum) {
    this.geburtsdatum = geburtsdatum;
  }


  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }


/* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((email == null) ? 0 : email.hashCode());
    result = prime * result + ((geburtsdatum == null) ? 0 : geburtsdatum.hashCode());
    result = prime * result + ((haarfarbe == null) ? 0 : haarfarbe.hashCode());
    long temp;
    temp = Double.doubleToLongBits(koerpergroesse);
    result = prime * result + (int) (temp ^ (temp >>> 32));
    result = prime * result + ((nachname == null) ? 0 : nachname.hashCode());
    result = prime * result + (raucher ? 1231 : 1237);
    result = prime * result + ((religion == null) ? 0 : religion.hashCode());
    result = prime * result + ((vorname == null) ? 0 : vorname.hashCode());
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
    Profil other = (Profil) obj;
    if (email == null) {
      if (other.email != null)
        return false;
    } else if (!email.equals(other.email))
      return false;
    if (geburtsdatum == null) {
      if (other.geburtsdatum != null)
        return false;
    } else if (!geburtsdatum.equals(other.geburtsdatum))
      return false;
    if (haarfarbe == null) {
      if (other.haarfarbe != null)
        return false;
    } else if (!haarfarbe.equals(other.haarfarbe))
      return false;
    if (Double.doubleToLongBits(koerpergroesse) != Double.doubleToLongBits(other.koerpergroesse))
      return false;
    if (nachname == null) {
      if (other.nachname != null)
        return false;
    } else if (!nachname.equals(other.nachname))
      return false;
    
    if (raucher != other.raucher)
      return false;
    if (religion == null) {
      if (other.religion != null)
        return false;
    } else if (!religion.equals(other.religion))
      return false;
    if (vorname == null) {
      if (other.vorname != null)
        return false;
    } else if (!vorname.equals(other.vorname))
      return false;
    return true;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Profil [vorname=" + vorname + ", nachname=" + nachname + ", haarfarbe=" + haarfarbe
        + ", koerpergroesse=" + koerpergroesse + ", raucher=" + raucher + ", religion=" + religion
        + ", geburtsdatum=" + geburtsdatum + ", passwort=" + ", email=" + email + "]";
  }

public int getÄhnlichkeit() {
	return ähnlichkeit;
}

public void setÄhnlichkeit(int ähnlichkeit) {
	this.ähnlichkeit = ähnlichkeit;
}

/**
 * @return the geschlecht
 */
public String getGeschlecht() {
	return geschlecht;
}

/**
 * @param geschlecht the geschlecht to set
 */
public void setGeschlecht(String geschlecht) {
	this.geschlecht = geschlecht;
}

/**
 * @return the sucheNach
 */
public String getSucheNach() {
	return sucheNach;
}

/**
 * @param sucheNach the sucheNach to set
 */
public void setSucheNach(String sucheNach) {
	this.sucheNach = sucheNach;
}
  
}
