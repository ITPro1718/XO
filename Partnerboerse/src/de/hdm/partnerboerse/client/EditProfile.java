package de.hdm.partnerboerse.client;

import java.util.Date;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

public class EditProfile extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	Profil getProfilFromServer;

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */

	Button deleteButton = new Button("Profil löschen");
	Button safeButton = new Button("Profil speichern");

	// Label, Textboxen und ListBoxen für das ProfilGrid
	Label mailLabel = new Label("E-Mail: ");
	Label pwLabel = new Label("Passwort: ");
	Label vnameLabel = new Label("Vorname: ");
	Label lnameLabel = new Label("Nachname: ");
	Label bdayLabel = new Label("Geburtstag: ");
	Label hcolorLabel = new Label("Haarfarbe: ");
	Label heightLabel = new Label("Größe " + "(in cm): ");
	Label smokerLabel = new Label("Raucher: ");
	Label religionLabel = new Label("Religion: ");

	TextBox mailTextBox = new TextBox();
	TextBox pwTextBox = new TextBox();
	TextBox vnameTextBox = new TextBox();
	TextBox lnameTextBox = new TextBox();
	DateBox bdayTextBox = new DateBox();
	TextBox hcolorTextBox = new TextBox();
	TextBox heightTextBox = new TextBox();
	TextBox religionTextBox = new TextBox();
	ListBox smokerListBox = new ListBox();

	// Label, Textboxen und ListBoxen für das InfoGrid
	Label sdescriptLab = new Label("Beschreibe dich kurz: ");
	Label hobbyLab = new Label("Deine Hobbies: ");
	Label jobLab = new Label("Dein Beruf: ");
	Label nationLab = new Label("Deine Nationalität: ");
	Label eduLab = new Label("Dein Bildungsniveau: ");
	Label musicLab = new Label("Deine lieblings Musik: ");

	Label sexPrefLab = new Label("Deine sexuellen Vorlieben: ");
	Label searchForLab = new Label("Du Bist auf der Suche Nach? ");
	Label sexOrientLab = new Label("Deine sexuelle Ausrichtung: ");

	TextBox hobby = new TextBox();
	TextBox job = new TextBox();
	TextBox nation = new TextBox();
	TextBox music = new TextBox();
	TextArea sdescript = new TextArea();
	TextBox sexPref = new TextBox();

	ListBox edu = new ListBox();
	ListBox sexOrient = new ListBox();
	ListBox searchFor = new ListBox();

	/*
	 * Beim Anzeigen werden die anderen Widgets erzeugt. Alle werden in einem
	 * Raster angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen
	 * Widgets bestimmt.
	 */
	@Override
	public void onLoad() {

		/*
		 * Grid für die Attribute
		 * 
		 */
		Grid profilIntGrid = new Grid(2, 3);
		profilIntGrid.setStyleName("itable");
		this.add(profilIntGrid);

		profilIntGrid.setWidget(1, 0, safeButton);
		profilIntGrid.setWidget(1, 2, deleteButton);

		Grid profilGrid = new Grid(6, 6);
		profilGrid.setStyleName("etable");
		this.add(profilGrid);

		// Spalte 1
		profilGrid.setWidget(1, 1, vnameLabel);
		profilGrid.setWidget(1, 2, vnameTextBox);
		vnameTextBox.setValue(getProfilFromServer.getVorname());

		profilGrid.setWidget(1, 3, lnameLabel);
		profilGrid.setWidget(1, 4, lnameTextBox);
		lnameTextBox.setValue(getProfilFromServer.getNachname());

		// Spalte 2
		DateTimeFormat dateFormat = DateTimeFormat.getFormat("MM/dd/yyyy");
		bdayTextBox.setFormat(new DateBox.DefaultFormat(dateFormat));

		profilGrid.setWidget(2, 1, bdayLabel);
		profilGrid.setWidget(2, 2, bdayTextBox);
		//bdayTextBox.setValue(getProfilFromServer.getGeburtsdatum().toString());

		profilGrid.setWidget(2, 3, mailLabel);
		profilGrid.setWidget(2, 4, mailTextBox);
		mailTextBox.setValue(getProfilFromServer.getEmail());

		// Spalte 3
		profilGrid.setWidget(3, 1, pwLabel);
		profilGrid.setWidget(3, 2, pwTextBox);
		pwTextBox.setValue(getProfilFromServer.getPasswort());

		// Spalte 4
		profilGrid.setWidget(3, 3, hcolorLabel);
		profilGrid.setWidget(3, 4, hcolorTextBox);
		hcolorTextBox.setValue(getProfilFromServer.getHaarfarbe());

		profilGrid.setWidget(4, 1, heightLabel);
		profilGrid.setWidget(4, 2, heightTextBox);
		heightTextBox.setValue(String.valueOf(getProfilFromServer.getKoerpergroesse()));

		// Spalte 5
		smokerListBox.addItem("Ja", "YSmoker");
		smokerListBox.addItem("Nein", "NSmoker");

		profilGrid.setWidget(4, 3, smokerLabel);
		profilGrid.setWidget(4, 4, smokerListBox);

		/*
		 * Grid für die Eingenschaftsobjekte. Zur besseren Beschreibung eines
		 * Profils.
		 * 
		 */
		sdescript.setHeight("200");
		sdescript.setWidth("400");

		FlexTable descripton = new FlexTable();
		descripton.setStyleName("desctable");
		this.add(descripton);

		descripton.setWidget(0, 0, sdescriptLab);
		descripton.setWidget(0, 1, sdescript);
		// sdescript.setValue(getProfilFromServer.getVorname());

		Grid infoGrid = new Grid(4, 6);
		infoGrid.setStyleName("etable");
		this.add(infoGrid);

		infoGrid.setWidget(0, 1, hobbyLab);
		infoGrid.setWidget(0, 2, hobby);
		// hobby.setValue(getProfilFromServer.getNachname());

		// Spalte 2
		infoGrid.setWidget(0, 3, jobLab);
		infoGrid.setWidget(0, 4, job);
		// job.setValue(getProfilFromServer.getGeburtsdatum().toString());

		infoGrid.setWidget(1, 1, nationLab);
		infoGrid.setWidget(1, 2, nation);
		// nation.setValue(getProfilFromServer.getEmail());

		// Spalte 3
		edu.addItem("Universität", "uni");
		edu.addItem("Abitur", "abi");
		edu.addItem("Fachhochschulreife", "fh");
		edu.addItem("Realschulabschluss", "real");
		edu.addItem("Hauptschulabschluss", "haupt");
		edu.addItem("Andere", "andere");

		infoGrid.setWidget(1, 3, eduLab);
		infoGrid.setWidget(1, 4, edu);

		// Spalte 4
		infoGrid.setWidget(2, 1, musicLab);
		infoGrid.setWidget(2, 2, music);
		// music.setValue(getProfilFromServer.getHaarfarbe());

		infoGrid.setWidget(2, 3, sexPrefLab);
		infoGrid.setWidget(2, 4, sexPref);
		// heightTextBox.setValue(String.valueOf(getProfilFromServer.getKoerpergroesse()));

		// Spalte 5
		searchFor.addItem("Beziehung", "beziehung");
		searchFor.addItem("One-Night-Stand", "ons");
		searchFor.addItem("Swinger", "swinger");

		sexOrient.addItem("Heterosexuell", "hetero");
		sexOrient.addItem("Homosexuell", "homo");
		sexOrient.addItem("Bisexuell", "bi");
		sexOrient.addItem("Andere", "andere");

		infoGrid.setWidget(3, 1, sexOrientLab);
		infoGrid.setWidget(3, 2, sexOrient);

		infoGrid.setWidget(3, 3, searchForLab);
		infoGrid.setWidget(3, 4, searchFor);

		/*
		 * https://stackoverflow.com/questions/3793650/convert-boolean-to-int-in
		 * -java Konvertiert bei true zu 1 und bei false zu 0
		 */
		// ToDo: Methode Funktioniert nicht, noch ausbessern
		// int smokerToInt = (getProfilFromServer.isRaucher()) ? 1 : 0;
		// switch (smokerToInt) {
		// case 1:
		// smokerListBox.getValue(0);
		// break;
		// case 2:
		// smokerListBox.getValue(1);
		// break;
		// }

		// smokerListBox.setValue(1, getProfilFromServer.isRaucher());

		// Spalte 6
		profilGrid.setWidget(6, 1, religionLabel);
		profilGrid.setWidget(6, 2, religionTextBox);
		religionTextBox.setValue(getProfilFromServer.getReligion());

		/*
		 * deleteButton.addClickHandler(new DeleteClickHandler());
		 * deleteButton.setEnabled(false); profilGrid.setWidget(0, 1,
		 * deleteButton);
		 * 
		 * newButton.addClickHandler(new NewClickHandler());
		 * newButton.setEnabled(false); profilGrid.setWidget(1, 1, newButton);
		 * 
		 * HorizontalPanel profilButtonsPanel = new HorizontalPanel();
		 * profilGrid.setWidget(4, 1, profilButtonsPanel);
		 * 
		 * Button changeButton = new Button("Name ändern");
		 * changeButton.addClickHandler(new ChangeClickHandler());
		 * profilButtonsPanel.add(changeButton);
		 * 
		 * Button searchButton = new Button("Suchen");
		 * profilButtonsPanel.add(searchButton);
		 */

		/*
		 * Button zum Speichern des eigenen geändertem Profils
		 */
		safeButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				updateProfileOnServer();
			}

			private void updateProfileOnServer() {
				// ToDo Profil darf nicht Übergabeparameter sein, um sein Profil
				// zu bekommen. Muss später
				// Abgeändert werden
				Profil setProfil = getProfileValuesFromFormular();

				partnerAdmin.updateProfil(setProfil, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Profil wurde nicht gespeichert.");

					}

					@Override
					public void onSuccess(Void result) {
						Window.alert(vnameTextBox.getValue() + " Profil wurde gespeichert.");

					}
				});

			}
		});

		deleteButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				deleteProfilOnServer();

			}

			private void deleteProfilOnServer() {

				Profil profileToDelete = getProfileValuesFromFormular();

				partnerAdmin.deleteProfil(profileToDelete, new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Profil konnte nicht gelöscht werden");

					}

					@Override
					public void onSuccess(Void result) {
						// ToDo: Bei erfolgreicher Löschung wird dem Nutzer was
						// angezeigt?
						// Sobald das geklart ist mit der Gruppe, muss dies
						// implementiert werden
						// ToDo: alle Abhängigkeiten in der DB müssen auch
						// gelöscht werden
						Window.alert("Profil wurde gelöscht");

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
		//Date bDayConvert = DateTimeFormat.getFormat("yyyy-MM-dd").parse(bdayTextBox.getValue());
		/*
		 * Integer.parseInt wandelt String in int um
		 */
		int heightConvert = Integer.parseInt(heightTextBox.getValue());

		// ToDo: setId muss raus, sobald Google Login Implementiert ist und
		// Übergabeparameter stimmt.
		setProfil.setId(1);
		setProfil.setVorname(vnameTextBox.getValue());
		setProfil.setNachname(lnameTextBox.getValue());
		//setProfil.setGeburtsdatum(bDayConvert);
		setProfil.setEmail(mailTextBox.getValue());
		setProfil.setPasswort(pwTextBox.getValue());
		setProfil.setKoerpergroesse(heightConvert);
		setProfil.setReligion(religionTextBox.getValue());
		setProfil.setHaarfarbe(hcolorTextBox.getValue());

		/*
		 * String-Wert von Raucher wird ausgelesen und durch eine
		 * Switch-Anweisung wird der Wert zu einem Boolean konvertiert.
		 */
		String raucherSelectedValue = smokerListBox.getSelectedValue();

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
}
