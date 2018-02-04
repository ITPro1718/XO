package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

/**
 * Startpunkt für die den Editor-Client. Diese Klasse wird als erstes aufgerufen, beim
 * Compilieren des Editor-Clients.
 * @author
 *
 */
public class Editor implements EntryPoint {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	// Loginattribute
	private LoginInfo loginInfo = null;

	/**
	 * Verticalpanel für Login
	 */
	private VerticalPanel loginPanel = new VerticalPanel();
	private HTML loginLabel = new HTML("<h3 class=\"log\">"+"Melden Sie sich mit Ihrem Google-Konto an, dann kann es schon losgehen!"+"<h3>");
	/**
	 * Anchor damit sich User anmelden kann.
	 */
	private Anchor signInLink = new Anchor("anmelden");
	


	// Unnötige Attribute?! Muss angepasst werden
	private VerticalPanel mainPanel = new VerticalPanel();
	private TextBox newSymbolTextBox = new TextBox();

	/**
	 * Instanz der Navigation für den Editor wird erstellt.
	 */
	Navigation nav = new Navigation();
	/**
	 * Instanz des EigenProfilView wird erstellt.
	 */
	EigenProfilView epv = new EigenProfilView();
	/**
	 * Instanz einer Editprofile wird erstellt.
	 */
	EditProfile ep = new EditProfile();
	/**
	 * Instanz einer EditSuchprofil wird erstellt.
	 */
	EditSuchprofil sp = new EditSuchprofil();

	@Override
	public void onModuleLoad() {
		// Check login status using login service.
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {
			}

			/**
			 * Bei erfolgreichem Login wird das Ergebnis die loginInfo gespeichert
			 * 
			 */
			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				/**
				 * Logininfo wird global gespeichert im Client.
				 */
				ClientSideSettings.setLoginInfo(loginInfo);
				/**
				 * Überprüft, ob der User eingeloggt ist.
				 */
				if (loginInfo.isLoggedIn()) {

				    /**
				     * Nach erfolgreichen Login, wird geprüft, ob der User
				     * schon ein Profil hat.
				     */
					hasProfile(result);

				} else {
				    /**
				     * Falls der User nicht eingeloggt ist, wird die Login-Seite aufgerufen.
				     */
					loadLogin();
				}
			}
		});
	}

	/**
	 * Hauptseite der Partnerbörse. Navigation und Hauptseite werden geladen
	 */
	public void loadXO() {
		
		/**
		 * Navigation Area
		 */
		RootPanel.get("navwrap").add(nav);

		/**
		 * Associate the Main panel with the HTML host page.
		 */
		RootPanel.get("contwrap").add(mainPanel);
		HTMLPanel eigenProfilViewPanel = new HTMLPanel("<h3>" + "Hier können Sie ihr Profil ansehen." + "</h3>");
        eigenProfilViewPanel.add(epv);
		/**
		 * Associate the Main panel with the HTML host page.
		 */
		RootPanel.get("contwrap").add(epv);

		/**
		 * Move cursor focus to the input box.
		 */
		newSymbolTextBox.setFocus(true);

		/*
		 * Neues Button Widget erzeugen und eine Beschriftung festlegen.
		 */
		VerticalPanel navPanel = new VerticalPanel();

		RootPanel.get("contwrap").add(navPanel);

	}

	/**
	 * Anhand der loginInfo der Emailaddresse wird das Profil geladen aus der DB.
	 */
	private void getProfilesFromServer() {
	  
	    /**
	     * AsncCall um Profil aus der DB zubekommen.
	     */
		partnerAdmin.getProfilByEmail(loginInfo.getEmailAddress(), new AsyncCallback<Profil>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Das Laden des Profils ist fehlgeschlagen!");

			}

			/**
			 * Bei erfolg hat der Client das Profil.
			 */
			@Override
			public void onSuccess(Profil result) {

			    /**
			     * Profil wird in die ClientSideSettings gespeichert damit es global im Client
			     * zur Verfügung steht.
			     */
				ClientSideSettings.setProfil(result);
				/**
				 * Methode lädt die Hauptseite der Partnerbörse.
				 */
				loadXO();
			}
		});
	}

	/**
	 * Methode lädt den Userlogin. Implementierung läuft pber Google+
	 */
	public void loadLogin() {
		// Assemble login panel.
		HTMLPanel eigenProfilViewPanel = new HTMLPanel("<h1>" + "Willkommen bei der XO-Partnerboerse" + "</h1>");
		eigenProfilViewPanel.addStyleName("logpanel");
		signInLink.addStyleName("logbtn");
        
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		eigenProfilViewPanel.add(loginPanel);
		RootPanel.get("contwrap").add(eigenProfilViewPanel);
	}

	/**
	 * Methode prüft, ob der eingeloggte User schon ein Profil auf der Partnerbörse hat.
	 * @param result
	 */
	private void hasProfile(final LoginInfo result) {

		loginInfo = result;

		/**
		 * Methode gibt ein Boolean zum Client zurück und der Server prüft, ob die Email in der Logininfo
		 * schon als Profil in der Db existiert.
		 */
		loginService.getEmailFromProfil(result.getEmailAddress(), new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Daten konnten nicht geladen werden.");
			}

			@Override
			public void onSuccess(Boolean isResult) {
				if (isResult) {
					getProfilesFromServer();
				} else {
					loadCreateEigenProfil(loginInfo);
				}

			}

			private void loadCreateEigenProfil(LoginInfo result) {

				CreateEigenProfil cep = new CreateEigenProfil();
				cep.setLoginInfo(result);

				HTMLPanel createEigenProfilPanel = new HTMLPanel(
						"<h3>" + "Hier können Sie ihr Profil erstellen." + "</h3>");

				createEigenProfilPanel.add(cep);

				RootPanel.get("contwrap").clear();
				RootPanel.get("contwrap").add(createEigenProfilPanel);

			}
		});

	}

}
