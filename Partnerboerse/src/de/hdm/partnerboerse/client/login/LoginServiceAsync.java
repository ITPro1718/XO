package de.hdm.partnerboerse.client.login;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

  void login(String requestUri, AsyncCallback<LoginInfo> callback);

  void getEmailFromProfil(String userEmail, AsyncCallback<Boolean> callback);

}
