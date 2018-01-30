package de.hdm.partnerboerse.client;

import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Window;
import de.hdm.partnerboerse.shared.bo.Profil;

public class ClientValidation {
  
  /**
   * Name Pattern Definition
   * https://stackoverflow.com/questions/2385701/regular-expression-for-first-and-last-name
   */
  private final String regExFirstname = "[A-Z][a-zA-Z]*";
  private final String regExLastName = "[a-zA-z]+([ '-][a-zA-Z]+)*";
  private final int MIN_STING_LENGTH = 2;
  private final int MAX_NAME_LENGHT = 15;
  private final int MIN_KOERPERGROESSE = 50;
  private final int MAX_KOERPERGROESSE = 250;

  public boolean isProfilValid (Profil profil) {
    
    if(profil.getVorname().length() <= MIN_STING_LENGTH || profil.getVorname().length() >=  MAX_NAME_LENGHT ) {    
      Window.alert("Der Vorname muss mehr als " + MIN_STING_LENGTH + " und weniger als " + MAX_NAME_LENGHT + " Zeichen haben");
      return false;
    }
    if(!profil.getVorname().matches(regExFirstname)) {
      Window.alert(profil.getVorname() + " entspricht nicht unseren Konventionen eines Vornamens");
      return false;
    }
    if(profil.getNachname().length() <= MIN_STING_LENGTH || profil.getNachname().length() >= MAX_NAME_LENGHT  ) {
      Window.alert("Der Nachname muss mehr als " + MIN_STING_LENGTH + " und weniger als " + MAX_NAME_LENGHT + " Zeichen haben");
      return false;
    }
    if(!profil.getNachname().matches(regExLastName)){
      Window.alert(profil.getNachname() + " entspricht nicht unseren Konventionen eines Nachnamens");
      return false;
    }
    if(profil.getKoerpergroesse() <= MIN_KOERPERGROESSE || profil.getKoerpergroesse() >= MAX_KOERPERGROESSE ) {
      Window.alert("Die Körpergröße muss mehr als " + MIN_KOERPERGROESSE + " und weniger als " + MAX_KOERPERGROESSE 
          + " cm betragen" );
      return false;
    }
    
    
    return true;
  }
}

