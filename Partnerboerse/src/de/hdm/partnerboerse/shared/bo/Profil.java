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
  
  private float koerpergröße;
  
  private String raucher;
  
  private String religion;
  
  private Date geburtsdatum;
  
  private String passwort;
  
  private String email;

  public String getVorname() {
    return vorname;
  }

  public void setVorname(String vorname) {
    this.vorname = vorname;
  }

  public String getNachname() {
    return nachname;
  }

  public void setNachname(String nachname) {
    this.nachname = nachname;
  }

  public String getHaarfarbe() {
    return haarfarbe;
  }

  public void setHaarfarbe(String haarfarbe) {
    this.haarfarbe = haarfarbe;
  }

  public float getKoerpergröße() {
    return koerpergröße;
  }

  public void setKoerpergröße(float koerpergröße) {
    this.koerpergröße = koerpergröße;
  }

  public String getRaucher() {
    return raucher;
  }

  public void setRaucher(String raucher) {
    this.raucher = raucher;
  }

  public String getReligion() {
    return religion;
  }

  public void setReligion(String religion) {
    this.religion = religion;
  }

  public Date getGeburtsdatum() {
    return geburtsdatum;
  }

  public void setGeburtsdatum(Date geburtsdatum) {
    this.geburtsdatum = geburtsdatum;
  }

  public String getPasswort() {
    return passwort;
  }

  public void setPasswort(String passwort) {
    this.passwort = passwort;
  }

  public String getEmail() {
    return email;
  }

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
    result = prime * result + Float.floatToIntBits(koerpergröße);
    result = prime * result + ((nachname == null) ? 0 : nachname.hashCode());
    result = prime * result + ((passwort == null) ? 0 : passwort.hashCode());
    result = prime * result + ((raucher == null) ? 0 : raucher.hashCode());
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
    if (Float.floatToIntBits(koerpergröße) != Float.floatToIntBits(other.koerpergröße))
      return false;
    if (nachname == null) {
      if (other.nachname != null)
        return false;
    } else if (!nachname.equals(other.nachname))
      return false;
    if (passwort == null) {
      if (other.passwort != null)
        return false;
    } else if (!passwort.equals(other.passwort))
      return false;
    if (raucher == null) {
      if (other.raucher != null)
        return false;
    } else if (!raucher.equals(other.raucher))
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
        + ", koerpergröße=" + koerpergröße + ", raucher=" + raucher + ", religion=" + religion
        + ", geburtsdatum=" + geburtsdatum + ", passwort=" + passwort + ", email=" + email + "]";
  }
  
  

  
}
