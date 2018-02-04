package de.hdm.partnerboerse.client;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.partnerboerse.server.db.ProfilMapper;

/**
 * Implementierung des LoginSerice
 * @author
 *
 */
@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
  
  private ProfilMapper pMapper = null;
  
  @Override
  public void init() throws IllegalArgumentException {
    this.pMapper = ProfilMapper.profilMapper();
  }

  /**
   * Methode prüft, ob der User eingeloggt ist.
   */
  public LoginInfo login(String requestUri) {
    /**
     * UserService wird erstellt.
     */
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    /**
     * Instanz von LoginInfo wird erstellt.
     */
    LoginInfo loginInfo = new LoginInfo();

    /**
     * Falls der User existiert, dann werden die Werte in die LoginInfo gespeiuchert.
     */
    if (user != null) {
      loginInfo.setLoggedIn(true);
      loginInfo.setEmailAddress(user.getEmail());
      loginInfo.setNickname(user.getNickname()); 
      loginInfo.setLogoutUrl(userService.createLogoutURL(requestUri));
    } 
    /**
     * Login ist fehlgeschlagen
     */
    else {
      loginInfo.setLoggedIn(false);
      loginInfo.setLoginUrl(userService.createLoginURL(requestUri));
    }
    return loginInfo;
  }

  /**
   * Anhand der Email wird geprüft, ob der User schon ein Profil in der Partnerbörse hat
   * @return boolean: true, falls das Profil bereits in der DB existiert.
   */
  @Override
  public boolean getEmailFromProfil(String userEmail) {
    
    /**
     * Falls aus der DB ein null Objekt zurück kommt, dann wird false zurückgegeben.
     */
    if(this.pMapper.findProfilByEmail(userEmail).getEmail() == null) {
      return false;
    }else {
      /**
       * Prüft die die Email aus dem Login mit dem Profil aus der DB;
       */
      return this.pMapper.findProfilByEmail(userEmail).getEmail().equals(userEmail);
    }

  }


}
