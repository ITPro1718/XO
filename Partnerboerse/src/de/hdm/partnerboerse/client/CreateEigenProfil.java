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
 * Die Klasse "CreateEigenProfil" wird im Editor nur dann aufgerufen, wenn
 * es unter der, im Login angegebenen, E-Mail-Adresse noch kein Profil in
 * der Partnerbörse XO existiert. Mit Hilfe dieser Klasse kann der User dann das Profil anlegen.
 */

public class CreateEigenProfil extends VerticalPanel {

	//Erfragen der PartnerboereseAdministration.
	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	/**
	 * Klassenattribute, beginnend mit der Instanziierung der loginInfo, des
	 * Buttons ("Profil erstellen"), der CreateWidget Klasse und der
	 * loadEigenschaften-Klasse
	 **/
	private LoginInfo loginInfo = null;
	Button createButton = new Button("Profil erstellen");
	CreateWidget cw = new CreateWidget();
	LoadEigenschaften loadEig = new LoadEigenschaften();

	/**
	 * Beim Anzeigen werden die anderen Widgets erzeugt. Alle werden in einem
	 * Raster angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen
	 * Widgets bestimmt.
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

		profilGrid.setWidget(4, 1, cw.getReligionLabel());
		profilGrid.setWidget(4, 2, cw.setReligionListBox());

		/**
		 * Button zum Speichern des eigenen geänderten Profils
		 */
		createButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				createProfileOnServer();

			}

			private void createProfileOnServer() {

				Profil setProfil = getProfileValuesFromFormular();
				ClientValidation cv = new ClientValidation();
				
				if(cv.isProfilValid(setProfil)) {
				partnerAdmin.createProfil(setProfil, new AsyncCallback<Profil>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("fehler");
					}

					@Override
					public void onSuccess(Profil result) {
						ClientSideSettings.setProfil(result);

						EigenschaftsView ev = new EigenschaftsView();
						ev.egFor(result);

						HTMLPanel evPanel = new HTMLPanel(
								"<h3>" + "Hier können sie ein relevante Infos angeben!" + "</h3>");
						evPanel.add(ev);

						RootPanel.get("contwrap").clear();
						RootPanel.get("contwrap").add(evPanel);

					}
				});
				} else {
				  return;
				}
			}
		});

	}

	/**
	 * Werte aus den geänderten Formularen wird ausgelesen und in ein Profil
	 * gespeichert und zurück gegeben
	 **/
	private Profil getProfileValuesFromFormular() {

		Profil setProfil = new Profil();

		// DateTimerFromat wandelt den Wert von bdayTextBox in Date um

		Date bDayConvert = DateTimeFormat.getFormat("yyyy-MM-dd").parse(cw.getBdayTextBox().getValue());
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
	      Window.alert("Körpgergöße muss eine natürliche Zahl sein");
	      return setProfil;
		}
		
		setProfil.setVorname(cw.getVnameTextBox().getValue());
		setProfil.setNachname(cw.getLnameTextBox().getValue());
		setProfil.setGeburtsdatum(bDayConvert);
		setProfil.setKoerpergroesse(heightConvert);
		setProfil.setReligion(cw.getReligionListBox().getSelectedValue());
		setProfil.setHaarfarbe(cw.getHcolorListBox().getSelectedValue());
		setProfil.setEmail(loginInfo.getEmailAddress());
		String raucherSelectedValue = cw.getSmokerListBox().getSelectedValue();

		/*
		 * String-Wert von Raucher wird ausgelesen und durch eine
		 * Switch-Anweisung wird der Wert zu einem Boolean konvertiert.
		 */
		switch (raucherSelectedValue) {
		case "YSmoker":
			setProfil.setRaucher(true);
			break;
		case "NSmoker":
			setProfil.setRaucher(false);
			break;
		}

		return setProfil;

	}

	Grid infoGrid = new Grid(10, 3);
	int row = 1;
	int column = 1;

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}
}
