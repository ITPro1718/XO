package de.hdm.partnerboerse.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Asynchroner AUfruf des LoginService 
 * @author
 *
 */
public interface LoginServiceAsync {

  /**
   * 
   * @param requestUri
   * @param callback
   */
  void login(String requestUri, AsyncCallback<LoginInfo> callback);

  /**
   * 
   * @param userEmail
   * @param callback
   */
  void getEmailFromProfil(String userEmail, AsyncCallback<Boolean> callback);

}
