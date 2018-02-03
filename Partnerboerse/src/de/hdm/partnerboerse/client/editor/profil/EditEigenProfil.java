package de.hdm.partnerboerse.client.editor.profil;

import java.util.Date;
import java.util.HashMap;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.client.ClientSideSettings;
import de.hdm.partnerboerse.client.ClientValidation;
import de.hdm.partnerboerse.client.editor.eigenschaften.LoadEigenschaften;
import de.hdm.partnerboerse.client.editor.forms.CreateWidget;
import de.hdm.partnerboerse.client.login.LoginInfo;
import de.hdm.partnerboerse.client.login.LoginService;
import de.hdm.partnerboerse.client.login.LoginServiceAsync;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

public class EditEigenProfil extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	LoginInfo loginInfo = ClientSideSettings.getLoginInfo();

	Profil getProfilFromServer = ClientSideSettings.getProfil();

	CreateWidget cw = new CreateWidget();

	/**
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 **/

	final Anchor deleteButton = new Anchor("Profil löschen");
	Button safeButton = new Button("Profil speichern");

	/**
	 * Beim Anzeigen werden die anderen Widgets erzeugt. Alle werden in einem
	 * Raster angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen
	 * Widgets bestimmt.
	 **/
	@Override
	public void onLoad() {

		deleteButton.addStyleName("offbutton");

		/**
		 * Grid für die Attribute
		 * 
		 **/
		Grid profilIntGrid = new Grid(2, 3);
		profilIntGrid.setStyleName("itable");
		this.add(profilIntGrid);

		profilIntGrid.setWidget(1, 0, safeButton);
		profilIntGrid.setWidget(1, 2, deleteButton);

		Grid profilGrid = new Grid(7, 6);
		profilGrid.setStyleName("etable");
		this.add(profilGrid);

		// Zeile 1
		profilGrid.setWidget(1, 1, cw.getVnameLabel());
		profilGrid.setWidget(1, 2, cw.getVnameTextBox());
		cw.getVnameTextBox().setValue(getProfilFromServer.getVorname());

		profilGrid.setWidget(1, 3, cw.getLnameLabel());
		profilGrid.setWidget(1, 4, cw.getLnameTextBox());
		cw.getLnameTextBox().setValue(getProfilFromServer.getNachname());

		// Zeile 2
		profilGrid.setWidget(2, 1, cw.getBdayLabel());
		profilGrid.setWidget(2, 2, cw.getBdayTextBox());
		String dateString = DateTimeFormat.getFormat("dd.MM.yyyy").format(getProfilFromServer.getGeburtsdatum());
		cw.getBdayTextBox().setValue(dateString);

		profilGrid.setWidget(2, 3, cw.getHcolorLabel());
		profilGrid.setWidget(2, 4, cw.setHcolorListBox());
		setRightWert(cw.getHcolorListBox(), getProfilFromServer.getHaarfarbe());

		// Zeile 3
		profilGrid.setWidget(3, 1, cw.getPHeightLabel());
		profilGrid.setWidget(3, 2, cw.getHeightTextBox());
		cw.getHeightTextBox().setValue(String.valueOf(getProfilFromServer.getKoerpergroesse()));

		profilGrid.setWidget(3, 3, cw.getSmokerLabel());
		profilGrid.setWidget(3, 4, cw.setSmokerListBox());

		String smokerString;
		if (getProfilFromServer.isRaucher() == true)
			smokerString = "Ja";

		else
			smokerString = "Nein";
		setRightWert(cw.getSmokerListBox(), smokerString);

		// Zeile 4
		profilGrid.setWidget(4, 1, cw.getSexLabel());
		profilGrid.setWidget(4, 2, cw.setSexListBox());
		setRightWert(cw.getSexListBox(), getProfilFromServer.getGeschlecht());

		profilGrid.setWidget(4, 3, cw.getReligionLabel());
		profilGrid.setWidget(4, 4, cw.setReligionListBox());
		setRightWert(cw.getReligionListBox(), getProfilFromServer.getReligion());

		// Spalte 5
		profilGrid.setWidget(5, 1, cw.getSearchForLabel());
		profilGrid.setWidget(5, 2, cw.setSearchForListBox());
		setRightWert(cw.getSearchForListBox(), getProfilFromServer.getSucheNach());

		/**
		 *  TODO: benutzen wir diese Methode????
		 */
		int smokerToInt = (getProfilFromServer.isRaucher()) ? 1 : 0;
		switch (smokerToInt) {
		case 1:
			cw.getSmokerListBox().getValue(0);
			break;
		case 2:
			cw.getSmokerListBox().getValue(1);
			break;
		}

		/**
		 * Grid für die Eingenschaftsobjekte zur besseren Beschreibung eines
		 * Profils.
		 **/
		HTMLPanel eigenschaftsViewPanel = new HTMLPanel(
				"<h3>" + "Hier können Sie Ihre eigenen Eigenschaften bearbeiten!"
						+ " Was soll Ihr Traumpartner über Sie wissen?" + "</h3>");

		FlexTable description = new FlexTable();
		description.setStyleName("desctable");
		this.add(eigenschaftsViewPanel);
		this.add(description);

		LoadEigenschaften le = new LoadEigenschaften();
		Grid info = le.loadEigen(ClientSideSettings.getProfil());
		this.add(info);

		/**
		 * Buttonaufruf zum Speichern des eigenen geändertem Profils
		 **/

		safeButton.addClickHandler(new SaveButtonClickhandler());

		/**
		 * Buttonaufruf zum Löschen des Profils
		 */
		deleteButton.addClickHandler(new DeleteButtonClickhandler());
	}

	

	/**
	 * ClickHandler zum Speichern des editierten Profils
	 *
	 */
	private class SaveButtonClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {
			updateProfileOnServer();
		}
	}

	/**
	 * ClickHandler zum Löschen des Profils
	 *
	 */
	private class DeleteButtonClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (Window.confirm("Wollen sie ihr Profil wirklich löschen?")) {
				deleteButton.setHref(ClientSideSettings.getLoginInfo().getLogoutUrl());
				partnerAdmin.deleteProfil(ClientSideSettings.getProfil(), new DeleteProfilCallback());
			}
		}
	}

	private class DeleteProfilCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Void result) {
			Window.alert("Ihr Profil wurde gelöscht");
		}
	}

	/**
	 * METHODEN
	 */
	 
	/**
	 * TODO Kommentieren!
	 */
	private void updateProfileOnServer() {

		getProfilFromServer = getProfileValuesFromFormular();
		ClientValidation cv = new ClientValidation();

		if (cv.isProfilValid(getProfilFromServer)) {

			partnerAdmin.updateProfil(getProfilFromServer, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Profil wurde nicht gespeichert.");

				}

				@Override
				public void onSuccess(Void result) {

					Window.alert(cw.getVnameTextBox().getValue() + " Profil wurde gespeichert.");

					ClientSideSettings.setProfil(getProfilFromServer);

					EigenProfilView epv = new EigenProfilView();

					HTMLPanel eigenProfilViewPanel = new HTMLPanel(
							"<h3>" + "Hier können Sie ihr Profil sehen." + "</h3>");
					eigenProfilViewPanel.add(epv);

					RootPanel.get("contwrap").clear();
					RootPanel.get("contwrap").add(eigenProfilViewPanel);

				}
			});
		} else {
			return;
		}
	}
	
	/**
	 * TODO Kommentieren!
	 */
	private Profil getProfileValuesFromFormular() {

		Profil setProfil = new Profil();
		String bDayConvert = cw.getBdayTextBox().getValue();
		Date date;

		/**
		 * Prüft, ob der Userwert in <code>getBdayTextBox()</code> eine den Date
		 * Format entspricht.
		 */
		try {
			date = DateTimeFormat.getFormat("dd.MM.yyyy").parse(bDayConvert);
		} catch (IllegalArgumentException e) {
			Window.alert("Das eingegebene Datumsformat entspricht nicht: \"dd.MM.yyyy\".");
			return setProfil;
		}

		int heightConvert = 0;

		/**
		 * Prüft, ob der Userwert in Textbox Körpergröße eine Zahl ist
		 */
		try {
			/*
			 * Integer.parseInt wandelt String in int um
			 */
			heightConvert = Integer.parseInt(cw.getHeightTextBox().getValue());
		} catch (NumberFormatException e) {
			Window.alert("Körpergröße muss eine natürliche Zahl sein");
			return setProfil;
		}

		setProfil.setId(getProfilFromServer.getId());
		setProfil.setVorname(cw.getVnameTextBox().getValue());
		setProfil.setNachname(cw.getLnameTextBox().getValue());
		setProfil.setGeburtsdatum(date);
		setProfil.setEmail(loginInfo.getEmailAddress());
		setProfil.setKoerpergroesse(heightConvert);
		setProfil.setReligion(cw.getReligionListBox().getSelectedValue());
		setProfil.setHaarfarbe(cw.getHcolorListBox().getSelectedValue());
		setProfil.setGeschlecht(cw.getSexListBox().getSelectedValue());
		setProfil.setSucheNach(cw.getSearchForListBox().getSelectedValue());

		/*
		 * String-Wert von Raucher wird ausgelesen und durch eine
		 * Switch-Anweisung wird der Wert zu einem Boolean konvertiert.
		 */
		String raucherSelectedValue = cw.getSmokerListBox().getSelectedValue();

	    switch (raucherSelectedValue) {
	      case "Ja":
	        setProfil.setRaucher(true);
	        break;
	      case "Nein":
	        setProfil.setRaucher(false);
	        break;
	    }

	    return setProfil;

	}

	/**
	 * Setzt den richtigen Wert in die ListBox welches das Profil ausgewählt
	 * hatte
	 * 
	 * @param lb
	 * @param string
	 */
	private void setRightWert(ListBox lb, String string) {

		HashMap<Integer, String> hm = new HashMap<Integer, String>();

		for (int i = 0; i < lb.getItemCount(); i++) {
			hm.put(i, lb.getValue(i));
		}

		for (int o = 0; o < hm.size(); o++) {
			if (hm.get(o).equals(string)) {
				lb.setSelectedIndex(o);
			}
		}
	}

}
