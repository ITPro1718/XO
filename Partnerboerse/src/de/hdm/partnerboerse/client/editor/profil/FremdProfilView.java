package de.hdm.partnerboerse.client.editor.profil;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Kontaktsperre;
import de.hdm.partnerboerse.shared.bo.Merkzettel;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.client.ClientSideSettings;
import de.hdm.partnerboerse.client.editor.eigenschaften.LoadEigenschaften;
import de.hdm.partnerboerse.client.editor.forms.CreateWidget;
import de.hdm.partnerboerse.client.editor.forms.GuiUtils;

public class FremdProfilView extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);
	/**
	 * Klassen Attribute von FremdProfilView
	 */
	Button merkButton = new Button("Profil merken");
	Button sperrButton = new Button("Profil sperren");
	ArrayList<Merkzettel> merkzettelList = new ArrayList<Merkzettel>();

	CreateWidget cw = new CreateWidget();

	LoadEigenschaften le = new LoadEigenschaften();

	Profil fremdprofil;
	Grid profilIntGrid = new Grid(2, 3);

	public FremdProfilView(Profil profil) {
		this.fremdprofil = profil;
	}

	@Override
	public void onLoad() {

		updateProfilTable(fremdprofil);
		Grid info = le.loadEigenRead(fremdprofil);
		info.setStyleName("inftab");
		this.add(info);

		partnerAdmin.findMerkzettelnOf(ClientSideSettings.getProfil(), new GetMerkzettelOfCallback());

		partnerAdmin.createBesuch(ClientSideSettings.getProfil(), fremdprofil, new CreateBesuchCallback());
		profilIntGrid.setWidget(0, 0, merkButton);
		profilIntGrid.setWidget(0, 1, sperrButton);

		merkButton.addClickHandler(new MerkButtonClickhandler());
		sperrButton.addClickHandler(new SperrButtonClickhandler());
	}

	/**
	 * Erstellen der Fremdprofil FlexTable, zur Ausgabe der Fremdprofile
	 */
	private void updateProfilTable(Profil result) {
		Profil fremdProfil = result;

		profilIntGrid.setStyleName("itable");
		this.add(profilIntGrid);

		FlexTable profilGrid = new FlexTable();
		profilGrid.setStyleName("etable");
		this.add(profilGrid);

		// Spalte 1
		profilGrid.setWidget(1, 1, cw.getVnameLabel());
		profilGrid.setText(1, 2, fremdProfil.getVorname());

		profilGrid.setWidget(1, 3, cw.getLnameLabel());
		profilGrid.setText(1, 4, fremdProfil.getNachname());

		// Spalte 2
		profilGrid.setWidget(2, 1, cw.getBdayLabel());
		String dateString = DateTimeFormat.getFormat("dd.MM.yyyy").format(fremdProfil.getGeburtsdatum());
		profilGrid.setText(2, 2, dateString);

		profilGrid.setWidget(2, 3, cw.getHcolorLabel());
		profilGrid.setText(2, 4, fremdProfil.getHaarfarbe());

		// Spalte 3
		profilGrid.setWidget(3, 1, cw.getPHeightLabel());
		profilGrid.setText(3, 2, String.valueOf(fremdProfil.getKoerpergroesse()));

		profilGrid.setWidget(3, 3, cw.getSmokerLabel());
		profilGrid.setText(3, 4, GuiUtils.getJaNein(fremdProfil.isRaucher()));

		// Spalte 4
		profilGrid.setWidget(4, 3, cw.getReligionLabel());
		profilGrid.setText(4, 4, fremdProfil.getReligion());

		// Spalte 5
		profilGrid.setWidget(4, 1, cw.getSexLabel());
		profilGrid.setText(4, 2, fremdProfil.getGeschlecht());

		// Spalte 6
		profilGrid.setWidget(5, 1, cw.getSearchForLabel());
		profilGrid.setText(5, 2, fremdProfil.getSucheNach());

	}

	/**
	 * ClickHandler zum Merken der Fremdprofile
	 */

	private class MerkButtonClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (merkButton.getText().equals("Profil merken")) {
				partnerAdmin.createMerkzettelEintrag(ClientSideSettings.getProfil(), fremdprofil,
						new MerkProfilCallback());
			} else {
				Merkzettel m = new Merkzettel();
				m.setEigenprofilID(ClientSideSettings.getProfil().getId());
				m.setFremdprofilID(fremdprofil.getId());
				partnerAdmin.deleteMerkzettelEintrag(m, new DeleteMerkungCallback());
			}
		}
	}

	/**
	 * 
	 * ClickHandler zum Sperren der Fremdprofile
	 */
	private class SperrButtonClickhandler implements ClickHandler {

		@Override
		public void onClick(ClickEvent event) {

			if (sperrButton.getText().equals("Profil sperren")) {
				partnerAdmin.createKontaksperreEintrag(ClientSideSettings.getProfil(), fremdprofil,
						new SperrProfilCallback());
			}

			else {
				Kontaktsperre k = new Kontaktsperre();
				k.setEigenprofilID(ClientSideSettings.getProfil().getId());
				k.setFremdprofilID(fremdprofil.getId());
				partnerAdmin.deleteKontaktsperreEintraege(k, new DeleteSperrungCallback());
			}
		}
	}

	/**
	 * CALLBACK KLASSEN
	 */

	/**
	 * TODO Kommentieren!
	 *
	 */

	private class MerkProfilCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Void result) {
			sperrButton.setEnabled(false);
			merkButton.setText("Profil entmerken");
		}

	}

	private class SperrProfilCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Void result) {
			merkButton.setEnabled(false);
			sperrButton.setText("Profil entsperren");
		}

	}

	private class CreateBesuchCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Void result) {
		}

	}

	private class DeleteSperrungCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Void result) {
			merkButton.setEnabled(true);
			sperrButton.setText("Profil sperren");
		}
	}

	private class DeleteMerkungCallback implements AsyncCallback<Void> {

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Void result) {
			sperrButton.setEnabled(true);
			merkButton.setText("Profil merken");
		}

	}

	private class GetMerkzettelOfCallback implements AsyncCallback<ArrayList<Merkzettel>> {

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(ArrayList<Merkzettel> result) {
			for (Merkzettel m : result) {
				if (fremdprofil.getId() == m.getFremdprofilID()) {
					sperrButton.setEnabled(false);
					merkButton.setText("Profil entmerken");
				}
			}
		}
	}

}
