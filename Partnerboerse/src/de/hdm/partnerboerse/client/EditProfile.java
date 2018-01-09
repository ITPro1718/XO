package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;

public class EditProfile extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */

	Button deleteButton = new Button("Profil löschen");
	Button safeButton = new Button("Profil speichern");

	Label mailLabel = new Label("E-Mail: ");
	Label pwLabel = new Label("Passwort: ");
	Label vnameLabel = new Label("Vorname: ");
	Label lnameLabel = new Label("Nachname: ");
	Label bdayLabel = new Label("Geburtstag: ");
	Label hcolorLabel = new Label("Haarfarbe: ");
	Label heightLabel = new Label("Größe "+ "(in cm): ");
	Label smokerLabel = new Label("Raucher: ");
	Label religionLabel = new Label("Religion: ");

	TextBox mailTextBox = new TextBox();
	TextBox pwTextBox = new TextBox();
	TextBox vnameTextBox = new TextBox();
	TextBox lnameTextBox = new TextBox();
	TextBox bdayTextBox = new TextBox();
	TextBox hcolorTextBox = new TextBox();
	TextBox heightTextBox = new TextBox();
	TextBox religionTextBox = new TextBox();

	CheckBox YsmokerTextBox = new CheckBox("ja");
	CheckBox NsmokerTextBox = new CheckBox("nein");

	
	/*
	 * Beim Anzeigen werden die anderen Widgets erzeugt. Alle werden in einem
	 * Raster angeordnet, dessen Größe sich aus dem Platzbedarf der enthaltenen
	 * Widgets bestimmt.
	 */
	@Override
	public void onLoad() {
		// super.onLoad();

		Grid profilIntGrid = new Grid(2, 3);
		profilIntGrid.setStyleName("itable");
		this.add(profilIntGrid);

		profilIntGrid.setWidget(1, 0, safeButton);
		profilIntGrid.setWidget(1, 2, deleteButton);

		Grid profilGrid = new Grid(7, 6);
		profilGrid.setStyleName("etable");
		this.add(profilGrid);

		// Spalte 1
		profilGrid.setWidget(1, 1, vnameLabel);
		profilGrid.setWidget(1, 2, vnameTextBox);

		profilGrid.setWidget(1, 3, lnameLabel);
		profilGrid.setWidget(1, 4, lnameTextBox);

		// Spalte 2
		profilGrid.setWidget(2, 1, bdayLabel);
		profilGrid.setWidget(2, 2, bdayTextBox);

		profilGrid.setWidget(2, 3, mailLabel);
		profilGrid.setWidget(2, 4, mailTextBox);

		// Spalte 3
		profilGrid.setWidget(3, 1, pwLabel);
		profilGrid.setWidget(3, 2, pwTextBox);

		// Spalte 4
		profilGrid.setWidget(4, 1, hcolorLabel);
		profilGrid.setWidget(4, 2, hcolorTextBox);

		profilGrid.setWidget(4, 3, heightLabel);
		profilGrid.setWidget(4, 4, heightTextBox);

		// Spalte 5
		profilGrid.setWidget(5, 3, smokerLabel);
		profilGrid.setWidget(5, 4, YsmokerTextBox);
		profilGrid.setWidget(5, 5, NsmokerTextBox);

		// Spalte 6
		profilGrid.setWidget(6, 1, religionLabel);
		profilGrid.setWidget(6, 2, religionTextBox);

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

	}

	/*
	 * Click Handlers.
	 */

}
