package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
import de.hdm.partnerboerse.client.CreateWidget;

public class FremdProfilView extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	Button merkButton = new Button("Profil merken");
	Button sperrButton = new Button("Profil sperren");
	Button entsperrButton = new Button("Profil entsperren");
	Button entmerkButton = new Button("Profil entmerken");
	
	CreateWidget cw = new CreateWidget();
	
	LoadEigenschaften le = new LoadEigenschaften();
	
	Profil fremdprofil;
	Grid profilIntGrid = new Grid(2, 3);
	
	public FremdProfilView(Profil profil){
		this.fremdprofil = profil;
	}

	@Override
	public void onLoad() {

		updateProfilTable(fremdprofil);
		Grid info = le.loadEigenRead(fremdprofil);
		this.add(info);
		
		partnerAdmin.createBesuch(ClientSideSettings.getProfil(), fremdprofil, new CreateBesuchCallback());
		changeButton(merkButton, "Merken");
		changeButton(sperrButton, "Sperre");
	}
	
	private void updateProfilTable(Profil result) {
		Profil fremdProfil = result;

		
		profilIntGrid.setStyleName("itable");
		this.add(profilIntGrid);

//		profilIntGrid.setWidget(0, 0, merkButton);
//		profilIntGrid.setWidget(0, 1, sperrButton);

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
		profilGrid.setText(2, 2, fremdProfil.getGeburtsdatum().toString());

		profilGrid.setWidget(2, 3, cw.getHcolorLabel());
		profilGrid.setText(2, 4, fremdProfil.getHaarfarbe());

		// Spalte 3
		profilGrid.setWidget(3, 1, cw.getHeightLabel());
		profilGrid.setText(3, 2, String.valueOf(fremdProfil.getKoerpergroesse()));

		profilGrid.setWidget(3, 3, cw.getSmokerLabel());
		profilGrid.setText(3, 4, GuiUtils.getJaNein(fremdProfil.isRaucher()));

		// Spalte 4
		profilGrid.setWidget(4, 1, cw.getReligionLabel());
		profilGrid.setText(4, 2, fremdProfil.getReligion());

	}
	
	private class MerkButtonClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			partnerAdmin.createMerkzettelEintrag(ClientSideSettings.getProfil(), fremdprofil, new MerkProfilCallback());
			changeButton(entmerkButton, "Merken");
		}
		
	}
	
	private class SperrButtonClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			partnerAdmin.createKontaksperreEintrag(ClientSideSettings.getProfil(), fremdprofil, new SperrProfilCallback());
			changeButton(entsperrButton, "Sperre");
		}
	}

	private class MerkProfilCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Void result) {
		}
		
	}
	
	private class SperrProfilCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Void result) {
		}
		
	}
	
	private class CreateBesuchCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Void result) {
		}
		
	}
	
	public void changeButton(Button button, String what){
		
		if (what.equals("Sperre")){
			profilIntGrid.setWidget(0, 1, button);
			if (button.getText().equals("Profil entsperren")){
				button.addClickHandler(new EntsperrungClickhandler());
			}
			else {
				button.addClickHandler(new SperrButtonClickhandler());
			}
		}
		
		if (what.equals("Merken")){
			profilIntGrid.setWidget(0, 0, button);
			if (button.getText().equals("Profil entmerken")){
				button.addClickHandler(new EntmerkungsClickhandler());
			}
			else {
				button.addClickHandler(new MerkButtonClickhandler());
			}
		}
		
	}
	
	private class EntsperrungClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Kontaktsperre k = new Kontaktsperre();
			k.setEigenprofilID(ClientSideSettings.getProfil().getId());
			k.setFremdprofilID(fremdprofil.getId());
			partnerAdmin.deleteKontaktsperreEintraege(k, new DeleteSperrungCallback());
		}
		
	}
	
	private class DeleteSperrungCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Void result) {
			changeButton(sperrButton, "Sperre");
		}
		
	}
	
	private class EntmerkungsClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Merkzettel m = new Merkzettel();
			m.setEigenprofilID(ClientSideSettings.getProfil().getId());
			m.setFremdprofilID(fremdprofil.getId());
			partnerAdmin.deleteMerkzettelEintrag(m, new DeleteMerkungCallback());
		}
		
	}
	
	private class DeleteMerkungCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Void result) {
			changeButton(merkButton, "Merken");
		}
		
	}

}
