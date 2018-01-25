package de.hdm.partnerboerse.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Auswahl;
import de.hdm.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.partnerboerse.shared.bo.Info;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.client.CreateWidget;

public class EigenProfilView extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	LoginInfo loginInfo = ClientSideSettings.getLoginInfo();

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */

	Button editButton = new Button("Profil bearbeiten");
	
	CreateWidget cw = new CreateWidget();
  

	@Override
	public void onLoad() {

		updateProfilTable(ClientSideSettings.getProfil());
		loadEigenschaften();

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
		profilGrid.setWidget(1, 1, cw.getVnameLabel());
		profilGrid.setText(1, 2, meinProfil.getVorname());

		profilGrid.setWidget(1, 3, cw.getLnameLabel());
		profilGrid.setText(1, 4, meinProfil.getNachname());

		// Spalte 2
		profilGrid.setWidget(2, 1, cw.getBdayLabel());
		profilGrid.setText(2, 2, meinProfil.getGeburtsdatum().toString());

		profilGrid.setWidget(2, 3, cw.getHcolorLabel());
		profilGrid.setText(2, 4, meinProfil.getHaarfarbe());

		// Spalte 3
		profilGrid.setWidget(3, 1, cw.getHeightLabel());
		profilGrid.setText(3, 2, String.valueOf(meinProfil.getKoerpergroesse()));


		profilGrid.setWidget(3, 3, cw.getSmokerLabel());
		profilGrid.setText(3, 4, GuiUtils.getJaNein(meinProfil.isRaucher()));

		// Spalte 4
		profilGrid.setWidget(4, 1, cw.getReligionLabel());
		profilGrid.setText(4, 2, meinProfil.getReligion());

	}

	Grid infoGrid = new Grid(10,3);
	int row = 1;
	int column = 1;
	
	private void loadInfoTable(Profil profil) {
		
		this.add(infoGrid);
		
		partnerAdmin.findInfoOf(profil, new AsyncCallback<ArrayList<Info>>(){

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(ArrayList<Info> result) {
				
				for (Info i : result){
					partnerAdmin.getEigenschaftByID(i.getEigenschaftId(), new AsyncCallback<Eigenschaft>(){

						@Override
						public void onFailure(Throwable caught) {	
						}

						@Override
						public void onSuccess(Eigenschaft result) {
							infoGrid.setText(row, column, result.getErlaeuterung());
							}
						
					});
					
					infoGrid.setText(row, column+1, i.getText());
					row++;
				}
				
			}
			
		});
		
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
	
	private void loadEigenschaften(){
		
		this.add(infoGrid);
		
		partnerAdmin.findInfoOf(ClientSideSettings.getProfil(), new AsyncCallback<ArrayList<Info>>(){

			@Override
			public void onFailure(Throwable caught) {
			}

			@Override
			public void onSuccess(ArrayList<Info> result) {
				Window.alert(result.toString());
				for (Info i : result){
					partnerAdmin.getEigenschaftByID(i.getId(), new AsyncCallback<Eigenschaft>(){

						@Override
						public void onFailure(Throwable caught) {
						}

						@Override
						public void onSuccess(Eigenschaft result) {
							infoGrid.setText(row, column, result.getErlaeuterung());
						}
						
					});
					
					infoGrid.setText(row, column + 1, i.getText());
					row++;
				}
			}
			
		});
		
		
	}

}
