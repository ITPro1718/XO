package de.hdm.partnerboerse.shared.bo;

import java.io.Serializable;

public abstract class BusinessObjekt implements Serializable{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  
  private int id = 0;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    BusinessObjekt other = (BusinessObjekt) obj;
    if (id != other.id)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "BusinessObjekt [id=" + id + "]";
  }

  

}
