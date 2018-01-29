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
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

public class SuchprofilView extends VerticalPanel {
	
	private final PartnerboerseAdministrationAsync partnerAdmin =
		      GWT.create(PartnerboerseAdministration.class);

	Profil profil = ClientSideSettings.getProfil();
	Suchprofil suchprofil = ClientSideSettings.getSuchprofil();

	Button editButton = new Button("bearbeiten");
	Button deleteButton = new Button("löschen");
	Button searchButton = new Button("suchen");
	
	CreateWidget cw = new CreateWidget();

	Suchprofil newsuchprofil = new Suchprofil();
	FlexTable profilFlexTable = new FlexTable();
	
	int row = 1;
	
    LoadEigenschaften loadEigenschaften = new LoadEigenschaften();


	/**
	 * TODO Wenn EigenschaftsModell steht Eigenschaften vom Suchprofil
	 * 
	 * Label sdescriptLab = new Label("Beschreibe dich kurz: "); Label hobbyLab
	 * = new Label("Deine Hobbies: "); Label musicLab = new Label("Deine
	 * Lieblings Musik: "); Label searchForLab = new Label("Du bist auf der
	 * Suche nach? "); Label sexOrientLab = new Label("Deine sexuelle
	 * Ausrichtung: ");
	 **/

	@Override
	public void onLoad() {
		new HTML("<h3>" + "Suchprofil" + "</h3>");
		updateSpTable(newsuchprofil);

		editButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				loadEditSuchprofilView(newsuchprofil);

			}
		});
		
		/**
		 * Lädt das Alle Eigenschaften mit den zugehörigen Infos
		 * zu einem Suchprofil.
		 */
	    Grid info = loadEigenschaften.loadEigenRead(newsuchprofil);
		this.add(info);
		this.add(searchButton);
		
		searchButton.addClickHandler(new SearchButtonClickhandler());
	}

	private void updateSpTable(Suchprofil result) {
		Suchprofil suchProfil = result;

		FlexTable sProfilIntGrid = new FlexTable();
		sProfilIntGrid.setStyleName("itable");
		this.add(sProfilIntGrid);

		sProfilIntGrid.setWidget(1, 0, editButton);
		sProfilIntGrid.setWidget(1, 1, deleteButton);

		Grid sProfilGrid = new Grid(4, 6);
		sProfilGrid.setStyleName("etable");
		this.add(sProfilGrid);

		
		/**
		 * Attribute von Suchprofil
		 */
		// Spalte 1
		sProfilGrid.setWidget(1, 1, cw.getAlterLabel());

		sProfilGrid.setText(1, 2, Integer.toString(suchProfil.getAlter()));

		sProfilGrid.setWidget(1, 3, cw.getHcolorLabel());
		sProfilGrid.setText(1, 4, suchProfil.getHaarFarbe());

		// Spalte 2
		sProfilGrid.setWidget(2, 1, cw.getHeightLabel());
		sProfilGrid.setText(2, 2, String.valueOf(suchProfil.getKoerpergroesse()));

		sProfilGrid.setWidget(2, 3, cw.getSmokerLabel());
		sProfilGrid.setText(2, 4, GuiUtils.getJaNein(suchProfil.isRaucher()));

		// Spalte 3
		sProfilGrid.setWidget(3, 1, cw.getReligionLabel());
		sProfilGrid.setText(3, 2, suchProfil.getReligion());
		
		FlexTable sProfilIntGrid2 = new FlexTable();
		sProfilIntGrid2.setStyleName("itable");
		this.add(sProfilIntGrid2);

	}

	public Suchprofil getNewSuchprofil() {
		return newsuchprofil;
	}

	public void setNewSuchprofil(Suchprofil newsuchprofil) {
		this.newsuchprofil = newsuchprofil;
	}

	private void loadEditSuchprofilView(Suchprofil result) {

		EditSuchprofil editsp = new EditSuchprofil();

		// Profile Edit - Panel wird erzeugt und eingefügt.
		HTMLPanel editSuchprofilPanel = new HTMLPanel(
				"<h3>" + "Hier können Sie ihre Profilinformationen bearbeiten." + "</h3>");

		editSuchprofilPanel.add(editsp);

		RootPanel.get("contwrap").clear();
		RootPanel.get("contwrap").add(editSuchprofilPanel);

	}
	
	private class SearchButtonClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("contwrap").add(generateErgebnisTable());
			// partnerAdmin.getSuchProfilErgebnisse(suchprofil, new getSuchProfilErgebnisseCallback());
			partnerAdmin.getSuchProfilErgebnisse(suchprofil, new GetSuchProfilErgebnisseCallback());
		}
		
	}
	
	
	private class GetSuchProfilErgebnisseCallback implements AsyncCallback<ArrayList<Profil>>{

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(ArrayList<Profil> result) {
			for (Profil p : result){
				addProfileToErgebniseTable(p);
			}
		}
		
	}
	
	private FlexTable generateErgebnisTable(){
		
		profilFlexTable.setText(0, 0, "Vorname");
	    profilFlexTable.setText(0, 1, "Nachname");
	    profilFlexTable.setText(0, 2, "Ähnlichkeit");
	    
	    return profilFlexTable;
	}
	
	
	
	private void addProfileToErgebniseTable(Profil p){

		Button showProfileButton = new Button("Checkout Profile!");
		
		profilFlexTable.setText(row, 0, p.getVorname());
		profilFlexTable.setText(row, 1, p.getNachname());
		profilFlexTable.setText(row, 2, String.valueOf(p.getÄhnlichkeit()));
		profilFlexTable.setWidget(row, 4, showProfileButton);
		ShowProfileClickhandler sp = new ShowProfileClickhandler();
		sp.setProfile(p);
		showProfileButton.addClickHandler(sp);
		
		row++;
		
	}
	
	private class ShowProfileClickhandler implements ClickHandler{
		
		Profil p;
		
		public void setProfile(Profil p){
			this.p = p;
		}

		@Override
		public void onClick(ClickEvent event) {
			FremdProfilView fpv = new FremdProfilView(p);
			
			HTMLPanel fpvPanel = new HTMLPanel("<h2>" + "Profil von " + p.getVorname() + "</h2>");
            fpvPanel.add(fpv);

            RootPanel.get("contwrap").clear();
            RootPanel.get("contwrap").add(fpvPanel);
		}
		
	}

}
