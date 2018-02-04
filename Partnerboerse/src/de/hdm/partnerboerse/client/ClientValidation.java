package de.hdm.partnerboerse.client;

import com.google.gwt.user.client.Window;
import de.hdm.partnerboerse.shared.bo.Info;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

public class ClientValidation {

  /**
   * Name Pattern Definition
   * Quelle: https://stackoverflow.com/questions/2385701/regular-expression-for-first-and-last-name
   */
  private final String regExFirstname = "[A-Z][a-zA-Zäöüß]*";
  private final String regExLastName = "[a-zA-zäöüß]+([ '-][a-zA-Zäöüß]+)*";
  private final String regExSuchprofilTitel = "[a-zA-zäöüß]+([ '-][a-zA-Zäöüß]+)*";
  private final int MIN_STRING_LENGTH = 2;
  private final int MAX_NAME_LENGHT = 15;
  private final int MIN_KOERPERGROESSE = 50;
  private final int MAX_KOERPERGROESSE = 250;
  private final int MAX_SUCHPROFIL_TITEL_LENGHT = 40;
  private final int MAX_INFOTEXT_LENGHT = 99;

  /**
   * Methode prüft, ob alle Profileingaben korrekt nach dieser Methode sind.
   * 
   * @param profil
   * @return boolean, wenn die Eingaben des Users falsch sind wird false zurückgebene und eine
   *         Benachrichtigung für den User erscheint. Bei korrekten Einagebn wird true zurückgegeben
   */
  public boolean isProfilValid(Profil profil) {

    /**
     * Prüft die String-Länge bei Vornamen
     */
    if (profil.getVorname().length() <= MIN_STRING_LENGTH
        || profil.getVorname().length() >= MAX_NAME_LENGHT) {
      
      Window.alert("Der Vorname muss mehr als " + MIN_STRING_LENGTH + " und weniger als "
          + MAX_NAME_LENGHT + " Zeichen haben.");
      return false;
    }
    /**
     * Prüft die Regular Expression bei Vornamen
     */
    if (!profil.getVorname().matches(regExFirstname)) {
      Window.alert(profil.getVorname()
          + " entspricht nicht unseren Konventionen eines Vornamens. Der Vorname muss mit einem Großbuchstaben anfangen"
          + " und darf keine Zahlen enthalten.");
      return false;
    }
    /**
     * Prüft die String-Länge bei Nachnamen
     */
    if (profil.getNachname().length() <= MIN_STRING_LENGTH
        || profil.getNachname().length() >= MAX_NAME_LENGHT) {
      Window.alert("Der Nachname muss mehr als " + MIN_STRING_LENGTH + " und weniger als "
          + MAX_NAME_LENGHT + " Zeichen haben.");
      return false;
    }
    /**
     * Prüft die Regular Expression bei Vornamen
     */
    if (!profil.getNachname().matches(regExLastName)) {
      Window.alert(profil.getNachname()
          + " entspricht nicht unseren Konventionen eines Nachnamens. Der Nachname muss mit einem Großbuchstaben anfangen"
          + " und darf keine Zahlen enthalten.");
      return false;
    }
    /**
     * Prüft die den Int Wert bei Körpergröße
     */
    if (profil.getKoerpergroesse() <= MIN_KOERPERGROESSE
        || profil.getKoerpergroesse() >= MAX_KOERPERGROESSE) {
      Window.alert("Die Körpergröße muss mehr als " + MIN_KOERPERGROESSE + " und weniger als "
          + MAX_KOERPERGROESSE + " cm betragen.");
      return false;
    }

    return true;
  }

  /**
   * Methode prüft, ob ein Suchprofil Titel korrekt vom User nach dieser Methode eigegeben wurde.
   * 
   * @param suchprofil
   * @return boolean, wenn die Eingaben des Users falsch sind wird false zurückgebene und eine
   *         Benachrichtigung für den User erscheint. Bei korrekten Einagebn wird true zurückgegeben
   */
  public boolean isSuchprofilValid(Suchprofil suchprofil) {

    if (suchprofil.getTitle().length() <= MIN_STRING_LENGTH
        || suchprofil.getTitle().length() >= MAX_SUCHPROFIL_TITEL_LENGHT) {
      Window.alert("Das Suchprofil muss mehr als " + MIN_STRING_LENGTH + " und weniger als "
          + MAX_SUCHPROFIL_TITEL_LENGHT + " Zeichen haben.");
      return false;
    }
    /**
     * Prüft die Regular Expression bei Suchprofiltitel
     */
    if (!suchprofil.getTitle().matches(regExSuchprofilTitel)) {
      Window.alert(suchprofil.getTitle()
          + " entspricht nicht unseren Namenskonventionen. Der Name des Suchprofils muss mit einem Großbuchstaben anfangen"
          + " und darf keine Zahlen enthalten.");
      return false;
    }

    return true;
  }

  /**
   * Methode prüft, ob der Info Text korrekt vom User nach dieser Methode eigegeben wurde.
   * 
   * @param info
   * @return boolean, wenn die Eingaben des Users falsch sind wird false zurückgebene und eine
   *         Benachrichtigung für den User erscheint. Bei korrekten Einagebn wird true zurückgegeben
   */
  public boolean isInfoValid(Info info) {

    /**
     * Prüft die Länge des Infotextes
     */
    if (info.getText().length() <= MIN_STRING_LENGTH
        || info.getText().length() >= MAX_INFOTEXT_LENGHT) {
      Window.alert("Die Info muss mehr als " + MIN_STRING_LENGTH + " und weniger als "
          + MAX_INFOTEXT_LENGHT + " Zeichen haben.");
      return false;
    }
    return true;
  }

  /**
   * Methode prüft, ob der Info-Text eine korrekte Eingabe hat 
   * @param text
   * @return boolean, wenn die Eingaben des Users falsch sind wird false zurückgebene und eine
   *         Benachrichtigung für den User erscheint. Bei korrekten Einagebn wird true zurückgegeben
   */
  public boolean isInfoValid(String text) {

    /**
     * Prüft die String-Länge bei dem Infotext den Anforderungen entspricht.
     */
    if (text.length() <= MIN_STRING_LENGTH || text.length() >= MAX_INFOTEXT_LENGHT) {
      Window.alert("Die Info muss mehr als " + MIN_STRING_LENGTH + " und weniger als "
          + MAX_INFOTEXT_LENGHT + " Zeichen haben.");
      return false;
    }
    return true;
  }
}

