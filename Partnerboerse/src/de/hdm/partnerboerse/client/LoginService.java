package de.hdm.partnerboerse.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Service für Login
 * @author
 *
 */
@RemoteServiceRelativePath("login")
public interface LoginService extends RemoteService {
  
  /**
   * 
   * @param requestUri
   * @return LoginInfo
   */
  public LoginInfo login(String requestUri);
  
  /**
   * 
   * @param userEmail
   * @return boolean
   */
  public boolean getEmailFromProfil(String userEmail);
}