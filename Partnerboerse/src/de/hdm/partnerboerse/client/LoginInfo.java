package de.hdm.partnerboerse.client;

	import java.io.Serializable;

	/**
	 * LoginInfo BO
	 * @author
	 *
	 */
	public class LoginInfo implements Serializable {


	  /**
	   * Serialisierung des LoginInfo-Objektes.
	   */
      private static final long serialVersionUID = 1L;
      /**
       * Wert gibt an, ob der User eingeloggt ist.
       */
      private boolean loggedIn = false;
      /**
       * String für LoginUri
       */
	  private String loginUrl;
	  /**
	   * String für LogoutUri
	   */
	  private String logoutUrl;
	  /**
	   * String für Email
	   */
	  private String emailAddress;
	  /**
	   * String für nickname
	   */
	  private String nickname;

	  /**
	   * @return loggedIn
	   */
	  public boolean isLoggedIn() {
	    return loggedIn;
	  }

	  /**
	   * 
	   * @param loggedIn
	   */
	  public void setLoggedIn(boolean loggedIn) {
	    this.loggedIn = loggedIn;
	  }

	  /**
	   * 
	   * @return String loginURL
	   */
	  public String getLoginUrl() {
	    return loginUrl;
	  }

	  /**
	   * 
	   * @param loginUrl
	   */
	  public void setLoginUrl(String loginUrl) {
	    this.loginUrl = loginUrl;
	  }

	  /**
	   * 
	   * @return logoutUrl
	   */
	  public String getLogoutUrl() {
	    return logoutUrl;
	  }

	  /**
	   * 
	   * @param logoutUrl
	   */
	  public void setLogoutUrl(String logoutUrl) {
	    this.logoutUrl = logoutUrl;
	  }

	  /**
	   * 
	   * @return emailAddress
	   */
	  public String getEmailAddress() {
	    return emailAddress;
	  }

	  /**
	   * 
	   * @param emailAddress
	   */
	  public void setEmailAddress(String emailAddress) {
	    this.emailAddress = emailAddress;
	  }

	  /**
	   * 
	   * @return nickname
	   */
	  public String getNickname() {
	    return nickname;
	  }

	  /**
	   * 
	   * @param nickname
	   */
	  public void setNickname(String nickname) {
	    this.nickname = nickname;
	  }
	}
