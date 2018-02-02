package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

public class Editor implements EntryPoint {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	// Loginattribute
	private LoginInfo loginInfo = null;

	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label("Melde dich mit deinem Google-Konto an, dann kann es schon losgehen!");
	private Anchor signInLink = new Anchor("anmelden");

	// Unnötige Attribute?! Muss angepasst werden
	private VerticalPanel mainPanel = new VerticalPanel();
	private TextBox newSymbolTextBox = new TextBox();

	Navigation nav = new Navigation();
	EigenProfilView epv = new EigenProfilView();
	EditProfile ep = new EditProfile();
	EditSuchprofil sp = new EditSuchprofil();

	@Override
	public void onModuleLoad() {
		// Check login status using login service.
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {
			}

			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				ClientSideSettings.setLoginInfo(loginInfo);
				if (loginInfo.isLoggedIn()) {

					// loadXO();
					hasProfile(result);

				} else {
					loadLogin();
				}
			}
		});
	}

	public void loadXO() {
		
		// Navigation Area
		RootPanel.get("navwrap").add(nav);

		// Associate the Main panel with the HTML host page.
		RootPanel.get("contwrap").add(mainPanel);
		HTMLPanel eigenProfilViewPanel = new HTMLPanel("<h3>" + "Hier können Sie ihr Profil sehen." + "</h3>");
        eigenProfilViewPanel.add(epv);
		// Associate the Main panel with the HTML host page.
		RootPanel.get("contwrap").add(epv);

		// Move cursor focus to the input box.
		newSymbolTextBox.setFocus(true);

		/*
		 * Neues Button Widget erzeugen und eine Beschriftung festlegen.
		 */
		VerticalPanel navPanel = new VerticalPanel();

		RootPanel.get("contwrap").add(navPanel);

	}

	private void getProfilesFromServer() {
		partnerAdmin.getProfilByEmail(loginInfo.getEmailAddress(), new AsyncCallback<Profil>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Das Laden des Profils ist fehlgeschlagen!");

			}

			@Override
			public void onSuccess(Profil result) {

				ClientSideSettings.setProfil(result);
				loadXO();
			}
		});
	}

	public void loadLogin() {
		// Assemble login panel.
		HTMLPanel eigenProfilViewPanel = new HTMLPanel("<h1>" + "Willkommen bei der XO-Partnerboerse" + "</h1>");
        
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		eigenProfilViewPanel.add(loginPanel);
		RootPanel.get("contwrap").add(eigenProfilViewPanel);
	}

	private void hasProfile(final LoginInfo result) {

		loginInfo = result;

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
						"<h3>" + "Hier können Sie ihre Profil erstellen." + "</h3>");

				createEigenProfilPanel.add(cep);

				RootPanel.get("contwrap").clear();
				RootPanel.get("contwrap").add(createEigenProfilPanel);

			}
		});

	}

}
