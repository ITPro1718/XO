package de.hdm.partnerboerse.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

/**
 * Die Klasse <code>CreateEigenProfil</code> wird im Editor nur dann aufgerufen,
 * wenn unter der, im Login angegebenen, E-Mail-Adresse noch kein Profil in der
 * Partnerbörse XO existiert. Mit Hilfe dieser Klasse kann der User sein Profil
 * anlegen.
 */

public class CreateEigenProfil extends VerticalPanel {

	// Erfragen der PartnerboereseAdministration.
	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	/**
	 * Zuerst werden alle notwendigen Instanzvariablen deklariert
	 **/
	private LoginInfo loginInfo = null;
	Button createButton = new Button("Profil erstellen");
	CreateWidget cw = new CreateWidget();
	LoadEigenschaften loadEig = new LoadEigenschaften();

	/**
	 * In der <code>onLoad()</code> ein Grid und alle anzuzeigenden Widgets
	 * erstellt und geladen.
	 **/

	@Override
	public void onLoad() {

		Grid profilIntGrid = new Grid(2, 3);
		profilIntGrid.setStyleName("itable");
		this.add(profilIntGrid);

		profilIntGrid.setWidget(1, 0, createButton);

		Grid profilGrid = new Grid(7, 6);
		profilGrid.setStyleName("etable");
		this.add(profilGrid);

		// Spalte 1
		profilGrid.setWidget(1, 1, cw.getVnameLabel());
		profilGrid.setWidget(1, 2, cw.getVnameTextBox());

		profilGrid.setWidget(1, 3, cw.getLnameLabel());
		profilGrid.setWidget(1, 4, cw.getLnameTextBox());

		// Spalte 2
		profilGrid.setWidget(2, 1, cw.getBdayLabel());
		profilGrid.setWidget(2, 2, cw.getBdayTextBox());

		profilGrid.setWidget(2, 3, cw.getHcolorLabel());
		profilGrid.setWidget(2, 4, cw.setHcolorListBox());

		// Spalte 3
		profilGrid.setWidget(3, 1, cw.getPHeightLabel());
		profilGrid.setWidget(3, 2, cw.getHeightTextBox());

		profilGrid.setWidget(3, 3, cw.getSmokerLabel());
		profilGrid.setWidget(3, 4, cw.setSmokerListBox());

		// Spalte 4

		profilGrid.setWidget(4, 3, cw.getReligionLabel());
		profilGrid.setWidget(4, 4, cw.setReligionListBox());
		
		// Spalte 5
		profilGrid.setWidget(4, 1, cw.getSexLabel());
		profilGrid.setWidget(4, 2, cw.setSexListBox());
		
		// Spalte 6
		profilGrid.setWidget(5, 1, cw.getSearchForLabel());
		profilGrid.setWidget(5, 2, cw.setSearchForListBox());
		

		/**
		 * Dieser <code>createButton.ClickHandler(new ClickHandler)</code>
		 * speichert die eingegebenen Daten, des neuen Users, im Profil ab.
		 */
		createButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				createProfileOnServer();

			}

			/**
			 * Methode erstellt ein Userprofil in der DB, ruft die EigenschaftsView und
			 * übergibt das Profil in die EigenprofilView, um es in ein Grid für den User 
			 * anzuzeigen.
			 */
			private void createProfileOnServer() {

			    /*
			     * Werte aus dem Formular werden in ein Profil gespeichert.
			     */
				Profil setProfil = getProfileValuesFromFormular();
				ClientValidation cv = new ClientValidation();

				/**
				 * Methode prüft, ob ein das Profil valide ist.
				 */
				if (cv.isProfilValid(setProfil)) {
				    /**
				     * Callback um ein neues Profil in der DB anzulegen.
				     */
					partnerAdmin.createProfil(setProfil, new AsyncCallback<Profil>() {

						/**
						 * Wenn das Laden fehlgeschlafen ist, wird ein 
						 */
						@Override
						public void onFailure(Throwable caught) {
							Window.alert("Es ist ein Fehler aufgetreten! Ihr Profil wurde nicht gespeichert.");
						}

						/**
						 * Erstellt die EigenschaftsView und übergibt das Profil der Klasse. 
						 */
						@Override
						public void onSuccess(Profil result) {
						    /**
						     * Speichert das Profil global in der ClientSideSettings ab.
						     */
							ClientSideSettings.setProfil(result);
							EigenschaftsView ev = new EigenschaftsView();
							/**
							 * Übergibt das Profil der EigenschaftView zur Nutzung.
							 */
							ev.egFor(result);

							HTMLPanel evPanel = new HTMLPanel("<h1> Hallo " + result.getVorname() + ", hilf uns dabei, dich näher kennen zu lernen.</h1><br>"
											+ "<h3>Diese Angaben sind freiwillig, helfen dir jedoch dabei, den richtigen Partner zu finden!</h3>");
							evPanel.add(ev);

							/**
							 * Rootpanel wird geleert und eine EIgenschaftView wird hinzugefügt.
							 */
							RootPanel.get("contwrap").clear();
							RootPanel.get("contwrap").add(evPanel);

						}
					});
				}
				/**
				 * Wenn die Validerung fehlschlägt, dann wird die Methode abgebrochen.
				 */
				else {
					return;
				}
			}
		});

	}

	/**
	 * Werte aus den geänderten Formularen wird ausgelesen und in ein Profil
	 * gespeichert und zurück gegeben.
	 * @return gibt das Profil mit den Formularinformationen zurück.
	 **/
	private Profil getProfileValuesFromFormular() {

		Profil setProfil = new Profil();
		String bDayConvert = cw.getBdayTextBox().getValue();
        int heightConvert = 0;
        Date date;
		
        /**
         * Prüft, ob der Userwert in <code>getBdayTextBox()</code> eine
         * den Date Format entspricht.
         */
		try {
		  /**
		   * DateTimerFromat wandelt den Wert von bdayTextBox in Date um
		   */
			date = DateTimeFormat.getFormat("dd.MM.yyyy").parse(bDayConvert);
		} catch (IllegalArgumentException e) {
		  Window.alert("Das eingegebene Datumsformat entspricht nicht: \"dd.MM.yyyy\".");
		  return setProfil;
		}

		/**
		 * Prüft, ob der Userwert in <code>getHeightTextBox()</code> eine
		 * int-Zahl ist
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

		/**
		 * Werte aus der Formularfeldern werden in ein Profil zugeordnet.
		 */
		setProfil.setVorname(cw.getVnameTextBox().getValue());
		setProfil.setNachname(cw.getLnameTextBox().getValue());
		setProfil.setGeburtsdatum(date);
		setProfil.setKoerpergroesse(heightConvert);
		setProfil.setReligion(cw.getReligionListBox().getSelectedValue());
		setProfil.setHaarfarbe(cw.getHcolorListBox().getSelectedValue());
		setProfil.setEmail(loginInfo.getEmailAddress());
		setProfil.setGeschlecht(cw.getSexListBox().getSelectedValue());
		setProfil.setSucheNach(cw.getSearchForListBox().getSelectedValue());
		
		String raucherSelectedValue = cw.getSmokerListBox().getSelectedValue();

		/**
		 * String-Wert von Raucher wird ausgelesen und durch die
		 * Switch-Anweisung wird der Wert zu einem Boolean konvertiert, da die
		 * Datenbank für Raucher nur 0 oder 1 abspeichert.
		 **/
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
	 * Methode gibt die LoginInformation zurück.
	 * @return
	 */
	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	/**
	 * LoginInformation kann geändert werden.
	 * @param loginInfo
	 */
	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}
}
