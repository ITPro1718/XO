package de.hdm.partnerboerse.client;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

public class EigenProfilView extends VerticalPanel {

	/**
	 * Überprüfen ob der User eingeloggt ist.
	 * Eigenschaften werden bereitgestellt.
	 */

	LoginInfo loginInfo = ClientSideSettings.getLoginInfo();
	LoadEigenschaften loadEigenschaften = new LoadEigenschaften();
	
	/**
	 * Weittere Elemente der Parofile werden bereitgestellt
	 */

	Button editButton = new Button("Profil bearbeiten");	
	CreateWidget cw = new CreateWidget();
	LoadEigenschaften l = new LoadEigenschaften();

  
	/**
	 * Eigenes Profil laden
	 */
	@Override
	public void onLoad() {

		/**
		 * Das Eigenschaften des Users werden geladen und bereitgestellt
		 */
		updateProfilTable(ClientSideSettings.getProfil());

		Grid info = loadEigenschaften.loadEigenRead(ClientSideSettings.getProfil());
		info.setStyleName("inftab");
		this.add(info);
		

		/**
		 * Editbutton wird erstellt und lädt die EditProfil View,
		 * zum anpassen der Profilinformationen
		 */
		editButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				loadEditProfilView(ClientSideSettings.getProfil());

			}
		});
	}

	/**
	 * Das Profil des User wird in einem Grid ausgegeben, dabei 
	 * werden alle Attirbute und Eigenschaften dem Grid übergeben.
	 * Der Editbutton wird gesondert in einer Flextable ausgegeben.
	 */
	private void updateProfilTable(Profil result) {
		Profil meinProfil = result;
		
		
		FlexTable profilIntGrid = new FlexTable();
		profilIntGrid.setStyleName("itable");
		this.add(profilIntGrid);

		profilIntGrid.setWidget(1, 0, editButton);
		editButton.addStyleName("ebtn");

		Grid profilGrid = new Grid(7, 6);
		profilGrid.setStyleName("etable");
		this.add(profilGrid);

		// Spalte 1
		profilGrid.setWidget(1, 1, cw.getVnameLabel());
		profilGrid.setText(1, 2, meinProfil.getVorname());

		profilGrid.setWidget(1, 3, cw.getLnameLabel());
		profilGrid.setText(1, 4, meinProfil.getNachname());

		// Spalte 2
		
		String dateString = DateTimeFormat.getFormat("dd.MM.yyyy").format(meinProfil.getGeburtsdatum());
	    cw.getBdayTextBox().setValue(dateString);
		
		profilGrid.setWidget(2, 1, cw.getBdayLabel());
		profilGrid.setText(2, 2, dateString);

		profilGrid.setWidget(2, 3, cw.getHcolorLabel());
		profilGrid.setText(2, 4, meinProfil.getHaarfarbe());

		// Spalte 3
		profilGrid.setWidget(3, 1, cw.getPHeightLabel());
		profilGrid.setText(3, 2, String.valueOf(meinProfil.getKoerpergroesse()));


		profilGrid.setWidget(3, 3, cw.getSmokerLabel());
		profilGrid.setText(3, 4, GuiUtils.getJaNein(meinProfil.isRaucher()));

		// Spalte 4
		profilGrid.setWidget(4, 3, cw.getReligionLabel());
		profilGrid.setText(4, 4, meinProfil.getReligion());
		
		// Spalte 5
		profilGrid.setWidget(4, 1, cw.getSexLabel());
		profilGrid.setText(4, 2, meinProfil.getGeschlecht());
		
		// Spalte 6
		profilGrid.setWidget(5, 1, cw.getSearchForLabel());
		profilGrid.setText(5, 2, meinProfil.getSucheNach());


	}

	/**
	 * Das Infogrid welches die Eigenschaften beherbergt wird ausgegeben.
	 */
	Grid infoGrid = new Grid(10,4);
	int row = 1;
	int column = 2;
	
	
	/**
	 * Die Eigenprofil View wird mit dem jeweiligen Userprofil geladen.
	 */
	private void loadEditProfilView(Profil result) {

		EditProfile ep = new EditProfile();
		Profil meinProfil = result;

		ep.getProfilFromServer = result;
		HTMLPanel editProfilePanel = new HTMLPanel(
				"<h3>" + " Hier können Sie Ihre Profilinformationen bearbeiten." + "</h3>");

		editProfilePanel.add(ep);

		RootPanel.get("contwrap").clear();
		RootPanel.get("contwrap").add(editProfilePanel);

	}
	
}
