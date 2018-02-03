package de.hdm.partnerboerse.client.editor.profil;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.client.ClientSideSettings;
import de.hdm.partnerboerse.client.editor.eigenschaften.LoadEigenschaften;
import de.hdm.partnerboerse.client.editor.forms.CreateWidget;
import de.hdm.partnerboerse.client.editor.forms.GuiUtils;
import de.hdm.partnerboerse.client.login.LoginInfo;
import de.hdm.partnerboerse.shared.bo.Profil;

public class EigenProfilView extends VerticalPanel {

	LoginInfo loginInfo = ClientSideSettings.getLoginInfo();
	LoadEigenschaften loadEigenschaften = new LoadEigenschaften();

	/**
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 **/

	Button editButton = new Button("Profil bearbeiten");

	CreateWidget cw = new CreateWidget();
	LoadEigenschaften l = new LoadEigenschaften();

	@Override
	public void onLoad() {

		updateProfilTable(ClientSideSettings.getProfil());

		Grid info = loadEigenschaften.loadEigenRead(ClientSideSettings.getProfil());
		info.setStyleName("inftab");
		this.add(info);

		/**
		 * Aufruf des EditButtonClickHandlers
		 */
		editButton.addClickHandler(new EditButtonClickhandler());

	}

	/**
	 * ClickHandler für den EditButton
	 *
	 */
	private class EditButtonClickhandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {

			loadEditProfilView(ClientSideSettings.getProfil());

		}
	}

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

		// Zeile 1
		profilGrid.setWidget(1, 1, cw.getVnameLabel());
		profilGrid.setText(1, 2, meinProfil.getVorname());

		profilGrid.setWidget(1, 3, cw.getLnameLabel());
		profilGrid.setText(1, 4, meinProfil.getNachname());

		// Zeile 2

		String dateString = DateTimeFormat.getFormat("dd.MM.yyyy").format(meinProfil.getGeburtsdatum());
		cw.getBdayTextBox().setValue(dateString);

		profilGrid.setWidget(2, 1, cw.getBdayLabel());
		profilGrid.setText(2, 2, dateString);

		profilGrid.setWidget(2, 3, cw.getHcolorLabel());
		profilGrid.setText(2, 4, meinProfil.getHaarfarbe());

		// Zeile 3
		profilGrid.setWidget(3, 1, cw.getPHeightLabel());
		profilGrid.setText(3, 2, String.valueOf(meinProfil.getKoerpergroesse()));

		profilGrid.setWidget(3, 3, cw.getSmokerLabel());
		profilGrid.setText(3, 4, GuiUtils.getJaNein(meinProfil.isRaucher()));

		// Zeile 4
		profilGrid.setWidget(4, 3, cw.getReligionLabel());
		profilGrid.setText(4, 4, meinProfil.getReligion());

		// Zeile 5
		profilGrid.setWidget(4, 1, cw.getSexLabel());
		profilGrid.setText(4, 2, meinProfil.getGeschlecht());

		// Zeile 6
		profilGrid.setWidget(5, 1, cw.getSearchForLabel());
		profilGrid.setText(5, 2, meinProfil.getSucheNach());

	}

	Grid infoGrid = new Grid(10, 4);
	int row = 1;
	int column = 2;

	/**
	 * METHODEN
	 *
	 */

	/**
	 * 
	 * TODO Kommentieren!
	 */
	private void loadEditProfilView(Profil result) {

		EditEigenProfil ep = new EditEigenProfil();
		Profil meinProfil = result;

		ep.getProfilFromServer = result;

		// Profile Edit - Panel wird erzeugt und eingefügt.
		HTMLPanel editProfilePanel = new HTMLPanel(
				"<h3>" + " Hier können Sie Ihre Profilinformationen bearbeiten." + "</h3>");

		editProfilePanel.add(ep);

		RootPanel.get("contwrap").clear();
		RootPanel.get("contwrap").add(editProfilePanel);

	}

}
