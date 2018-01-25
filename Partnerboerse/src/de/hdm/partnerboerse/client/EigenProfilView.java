package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

public class EigenProfilView extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	LoginInfo loginInfo = ClientSideSettings.getLoginInfo();

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */

	Button editButton = new Button("Profil bearbeiten");


  String vnameLabel = new String("Vorname: ");
  String lnameLabel = new String("Nachname: ");
  String bdayLabel = new String("Geburtstag: ");
  String hcolorLabel = new String("Haarfarbe: ");
  String heightLabel = new String("Größe (in cm): ");
  String smokerLabel = new String("Raucher: ");
  String religionLabel = new String("Religion: ");
  
  
  
  FlexTable descripton = new FlexTable();
  Grid infoGrid = new Grid(4, 6);



	@Override
	public void onLoad() {

		updateProfilTable(ClientSideSettings.getProfil());
		loadInfoTable(ClientSideSettings.getProfil());

		editButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				loadEditProfilView(ClientSideSettings.getProfil());

			}
		});
	}

	private void updateProfilTable(Profil result) {
		Profil meinProfil = result;

		FlexTable profilIntGrid = new FlexTable();
		profilIntGrid.setStyleName("itable");
		this.add(profilIntGrid);

		profilIntGrid.setWidget(1, 0, editButton);

		Grid profilGrid = new Grid(7, 6);
		profilGrid.setStyleName("etable");
		this.add(profilGrid);

		// Spalte 1
		profilGrid.setText(1, 1, vnameLabel);
		profilGrid.setText(1, 2, meinProfil.getVorname());

		profilGrid.setText(1, 3, lnameLabel);
		profilGrid.setText(1, 4, meinProfil.getNachname());

		// Spalte 2
		profilGrid.setText(2, 1, bdayLabel);
		profilGrid.setText(2, 2, meinProfil.getGeburtsdatum().toString());

		profilGrid.setText(2, 3, hcolorLabel);
		profilGrid.setText(2, 4, meinProfil.getHaarfarbe());

		// Spalte 3
		profilGrid.setText(3, 1, heightLabel);
		profilGrid.setText(3, 2, String.valueOf(meinProfil.getKoerpergroesse()));


		profilGrid.setText(3, 3, smokerLabel);
		profilGrid.setText(3, 4, GuiUtils.getJaNein(meinProfil.isRaucher()));

		// Spalte 4
		profilGrid.setText(4, 1, religionLabel);
		profilGrid.setText(4, 2, meinProfil.getReligion());

	}

	private void loadInfoTable(Profil profil) {
		// TODO: Get all Infos of User Callback, dann Infos in Grid einfügen

		descripton.setStyleName("desctable");
		this.add(descripton);

	
		

		infoGrid.setStyleName("etable");
		this.add(infoGrid);
	}

		
		

	// ToDo: Überlegen wie man den Parameter für die neue View übertragen kann
	private void loadEditProfilView(Profil result) {

		EditProfile ep = new EditProfile();

		// ToDo: Sollte man umÃ¤ndern, wirkt ziemlich unsicher
		ep.getProfilFromServer = result;

		// Profile Edit - Panel wird erzeugt und eingefügt.
		HTMLPanel editProfilePanel = new HTMLPanel(
				"<h3>" + "Hier können Sie ihre Profilinformationen bearbeiten." + "</h3>");

		editProfilePanel.add(ep);

		RootPanel.get("contwrap").clear();
		RootPanel.get("contwrap").add(editProfilePanel);

	}

}
