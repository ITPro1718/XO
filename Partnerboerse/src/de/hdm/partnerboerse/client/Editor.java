package de.hdm.partnerboerse.client;

import java.util.ArrayList;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;
import com.google.gwt.user.client.ui.Anchor;

public class Editor implements EntryPoint {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	//Loginattribute
	private LoginInfo loginInfo = null;
	private VerticalPanel loginPanel = new VerticalPanel();
	private Label loginLabel = new Label(
			"Please sign in to your Google Account to access the StockWatcher application.");
	private Anchor signInLink = new Anchor("Sign In");
	
	//Logoutattribute
	  private Anchor signOutLink = new Anchor("Sign Out");

	  //Unnötige Attribute?! Muss angepasst werden
	private VerticalPanel mainPanel = new VerticalPanel();
	private FlexTable profilFlexTable = new FlexTable();
	private HorizontalPanel addPanel = new HorizontalPanel();
	private TextBox newSymbolTextBox = new TextBox();
	private Button addProfilButton = new Button("Add");
	private Label lastUpdatedLabel = new Label();

	Navigation nav = new Navigation();
	EigenProfilView epv = new EigenProfilView();
	EditProfile ep = new EditProfile();
	EditSuchprofil sp = new EditSuchprofil();

	@Override
	public void onModuleLoad() {
		// Check login status using login service.
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {
			}

			public void onSuccess(LoginInfo result) {
				loginInfo = result;
				if (loginInfo.isLoggedIn()) {
					loadXO();
				} else {
					loadLogin();
				}
			}
		});
	}

	private void loadXO() {

		
		// Set up sign out hyperlink.
	    signOutLink.setHref(loginInfo.getLogoutUrl());
	    
		// Navigation Area
		RootPanel.get("navwrap").add(nav);

		// Profile Edit - Panel wird erzeugt und eingefügt.
		// HTMLPanel editProfilePanel = new HTMLPanel(
		// "<h3>" + "Hier können Sie ihre Profilinformationen bearbeiten." +
		// "</h3>");
		// editProfilePanel.add(ep);
		//
		// RootPanel.get("contwrap").add(editProfilePanel);

		// Search Profile

		// HTMLPanel spPanel = new HTMLPanel("<h3>" + "Hier können Sie Ihr
		// Suchprofil erstellen." + "</h3>");
		// spPanel.add(sp);
		//
		// RootPanel.get("contwrap").add(spPanel);

		// Create table for Profil
		profilFlexTable.setText(0, 0, "Vorname");
		profilFlexTable.setText(0, 1, "Nachname");
		profilFlexTable.setText(0, 2, "Haarfarbe");
		profilFlexTable.setText(0, 3, "Körpergröße");
		profilFlexTable.setText(0, 4, "Raucher");
		profilFlexTable.setText(0, 5, "Religion");
		profilFlexTable.setText(0, 6, "Geburtsdatum");
		profilFlexTable.setText(0, 7, "Passwort");
		profilFlexTable.setText(0, 8, "Email");

		// Assemble Add Stock panel.
		addPanel.add(newSymbolTextBox);
		addPanel.add(addProfilButton);

	    // Assemble Main panel 4 logout
	    mainPanel.add(signOutLink);
		// Assemble Main panel.
		mainPanel.add(profilFlexTable);
		mainPanel.add(addPanel);
		mainPanel.add(lastUpdatedLabel);



		// Associate the Main panel with the HTML host page.
		RootPanel.get("contwrap").add(mainPanel);

		// Move cursor focus to the input box.
		newSymbolTextBox.setFocus(true);

		/*
		 * Neues Button Widget erzeugen und eine Beschriftung festlegen.
		 */
		final Button findProfilButton = new Button("Finde Profile");
		VerticalPanel navPanel = new VerticalPanel();

		RootPanel.get("contwrap").add(navPanel);
		navPanel.add(findProfilButton);

		addProfilButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				// TODO Auto-generated method stub

				getProfileFromServer();

			}

			private void getProfileFromServer() {

				partnerAdmin.getAllProfils(new AsyncCallback<ArrayList<Profil>>() {

					@Override
					public void onFailure(Throwable caught) {
						// Show the RPC error message to the user
					}

					@Override
					public void onSuccess(ArrayList<Profil> result) {
						for (Profil p : result) {
							addToTable(p);
						}
					}
				});
			}

			private void addToTable(Profil p) {
				int row = profilFlexTable.getRowCount();
				profilFlexTable.setText(row, 0, p.getNachname());
				profilFlexTable.setText(row, 1, p.getVorname());
				profilFlexTable.setText(row, 2, p.getHaarfarbe());
				int kgr = (int) p.getKoerpergroesse();
				String s = "" + kgr;
				profilFlexTable.setText(row, 3, s);
				profilFlexTable.setText(row, 4, "" + p.isRaucher());
				profilFlexTable.setText(row, 5, p.getReligion());
				profilFlexTable.setText(row, 6, "" + p.getGeburtsdatum());
				profilFlexTable.setText(row, 7, p.getPasswort());
				profilFlexTable.setText(row, 8, p.getEmail());

				// profilFlexTable.setWidget(row, 2, new Label());
				// profilFlexTable.getCellFormatter().addStyleName(row, 1,
				// "watchListNumericColumn");
				// profilFlexTable.getCellFormatter().addStyleName(row, 2,
				// "watchListNumericColumn");
				// profilFlexTable.getCellFormatter().addStyleName(row, 3,
				// "watchListRemoveColumn");
			}
		});

	}

	private void loadLogin() {
		// Assemble login panel.
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		RootPanel.get("contwrap").add(loginPanel);
	}

}
