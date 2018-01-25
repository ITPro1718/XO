package de.hdm.partnerboerse.client;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Auswahl;
import de.hdm.partnerboerse.shared.bo.Freitext;
import de.hdm.partnerboerse.shared.bo.Info;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.client.CreateWidget;

public class CreateEigenProfil extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	// Loginattribute
	private LoginInfo loginInfo = null;

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */

	Button createButton = new Button("Profil erstellen");
	
	CreateWidget cw = new CreateWidget();

	/**Label vnameLabel = new Label("Vorname: ");
	Label lnameLabel = new Label("Nachname: ");
	Label bdayLabel = new Label("Geburtstag: ");
	Label hcolorLabel = new Label("Haarfarbe: ");
	Label heightLabel = new Label("Größe (in cm): ");
	Label smokerLabel = new Label("Raucher: ");
	Label religionLabel = new Label("Religion: ");

	TextBox vnameTextBox = new TextBox();
	TextBox lnameTextBox = new TextBox();
	TextBox bdayTextBox = new TextBox();
	ListBox hcolorListBox = new ListBox();
	TextBox heightTextBox = new TextBox();
	ListBox religionListBox = new CreateWidget();


	ListBox smokerListBox = new ListBox();**/



	/*
	 * Beim Anzeigen werden die anderen Widgets erzeugt. Alle werden in einem
	 * Raster angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen
	 * Widgets bestimmt.
	 */
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

/*		hcolorListBox.addItem("schwarz", "schwarz");
		hcolorListBox.addItem("braun", "braun");
		hcolorListBox.addItem("blond", "blond");
		hcolorListBox.addItem("grau", "grau");
		hcolorListBox.addItem("sonstige", "sonstige");*/

		profilGrid.setWidget(2, 3, cw.getHcolorLabel());
		profilGrid.setWidget(2, 4, cw.getHcolorListBox());

		// Spalte 3
		profilGrid.setWidget(3, 1, cw.getHeightLabel());
		profilGrid.setWidget(3, 2, cw.getHeightTextBox());

/*		smokerListBox.addItem("Ja", "YSmoker");
		smokerListBox.addItem("Nein", "NSmoker");
*/
		profilGrid.setWidget(3, 3, cw.getSmokerLabel());
		profilGrid.setWidget(3, 4, cw.getSmokerListBox());

		profilGrid.setWidget(4, 1, cw.getReligionLabel());
		profilGrid.setWidget(4, 2, cw.getReligionListBox());

		FlexTable descripton = new FlexTable();
		descripton.setStyleName("desctable");
		this.add(descripton);



		Grid infoGrid = new Grid(4, 6);
		infoGrid.setStyleName("etable");
		this.add(infoGrid);



    // infoGrid.setWidget(0, 1, hobbyLab);
    // infoGrid.setWidget(0, 2, hobby);
    // hobby.setValue(getProfilFromServer.getNachname());

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
				// System.out.println("tes01" + setProfil.toString());

				partnerAdmin.createProfil(setProfil, new AsyncCallback<Profil>() {

					@Override
					public void onFailure(Throwable caught) {

					}

					@Override
					public void onSuccess(Profil result) {
						ClientSideSettings.setProfil(result);


						Editor ed = new Editor();
						RootPanel.get("contwrap").clear();
						ed.onModuleLoad();

					}
				});

			}

		});

	}

	/*
	 * Werte aus den geänderten Formularen wird ausgelesen und in ein Profil
	 * gespeichert und zurück gegeben
	 */
	private Profil getProfileValuesFromFormular() {

		Profil setProfil = new Profil();

		/*
		 * DateTimerFromat wandelt den Wert von bdayTextBox in Date um
		 */
		Date bDayConvert = DateTimeFormat.getFormat("yyyy-MM-dd").parse(cw.getBdayTextBox().getValue());
		/*
		 * Integer.parseInt wandelt String in int um
		 */
		int heightConvert = Integer.parseInt(cw.getHeightTextBox().getValue());

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

	  

 	
	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}
}
