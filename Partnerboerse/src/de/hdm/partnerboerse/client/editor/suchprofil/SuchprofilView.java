package de.hdm.partnerboerse.client.editor.suchprofil;

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

import de.hdm.partnerboerse.client.ClientSideSettings;
import de.hdm.partnerboerse.client.editor.eigenschaften.LoadEigenschaften;
import de.hdm.partnerboerse.client.editor.forms.CreateWidget;
import de.hdm.partnerboerse.client.editor.forms.GuiUtils;
import de.hdm.partnerboerse.client.editor.profil.FremdProfilView;
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


	@Override
	public void onLoad() {
		
		new HTML("<h3>" + "Suchprofil" + "</h3>");
		
		/**
		 * Befüllt den Suchprofilgrid für ein übergebenes Profil
		 */
		updateSpTable(newsuchprofil);
		
		editButton.addClickHandler(new EditButtonClickhandler());
		
		/**
		 * Lädt das Alle Eigenschaften mit den zugehörigen Infos
		 * zu einem Suchprofil.
		 */
	    Grid info = loadEigenschaften.loadEigenRead(newsuchprofil);
		this.add(info);
		this.add(searchButton);
		
		searchButton.addClickHandler(new SearchButtonClickhandler());
		deleteButton.addClickHandler(new DeleteProfilClickhandler());
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
		 * Attribute vom Suchprofil
		 */
		// Zeile 1
		sProfilGrid.setWidget(1, 1, cw.getAlterLabel());

		sProfilGrid.setText(1, 2, Integer.toString(suchProfil.getAlter()));

		sProfilGrid.setWidget(1, 3, cw.getHcolorLabel());
		sProfilGrid.setText(1, 4, suchProfil.getHaarFarbe());

		// Zeile 2
		sProfilGrid.setWidget(2, 1, cw.getSpheightLabel());
		sProfilGrid.setText(2, 2, String.valueOf(suchProfil.getKoerpergroesse()));

		sProfilGrid.setWidget(2, 3, cw.getSmokerLabel());
		sProfilGrid.setText(2, 4, GuiUtils.getJaNein(suchProfil.isRaucher()));

		// Zeile 3
		sProfilGrid.setWidget(3, 1, cw.getReligionLabel());
		sProfilGrid.setText(3, 2, suchProfil.getReligion());
		
		FlexTable sProfilIntGrid2 = new FlexTable();
		sProfilIntGrid2.setStyleName("itable");
		this.add(sProfilIntGrid2);

	}
	
	/**
	 * Clickhandler für den editButton, ruft in onClick den EditSuchprofilView auf
	 *
	 */
	private class EditButtonClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			loadEditSuchprofilView(newsuchprofil);
		}
		
	}


	public Suchprofil getNewSuchprofil() {
		return newsuchprofil;
	}

	public void setNewSuchprofil(Suchprofil newsuchprofil) {
		this.newsuchprofil = newsuchprofil;
	}
	
	/**
	 * Clickhandler für den DeleteProfilButton, in onClick wird das Suchprofil gelöscht
	 */
	private class DeleteProfilClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {

			partnerAdmin.deleteSuchprofil(suchprofil, new DeleteSuchprofilCallback());
		}
		
	}
	
	/**
	 * Löscht das ausgewählte Suchprofil und lädt die Suchprofil Liste
	 */
	private class DeleteSuchprofilCallback implements AsyncCallback<Void>{

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Void result) {
			ClientSideSettings.setSuchprofil(null);
			ListViewSuchProfil lvsp = new ListViewSuchProfil();
			HTMLPanel splistViewPanel = new HTMLPanel(
					"<h3>" + "Hier können Sie Ihre Suchprofile verwalten." + "</h3>");
			splistViewPanel.add(lvsp);

			RootPanel.get("contwrap").clear();
			RootPanel.get("contwrap").add(splistViewPanel);
		}
		
	}

	/**
	 * Lädt den EditSuchprofilView
	 * @param result
	 */
	private void loadEditSuchprofilView(Suchprofil result) {

		EditSuchprofil editsp = new EditSuchprofil();

		// Profile Edit - Panel wird erzeugt und eingefügt.
		HTMLPanel editSuchprofilPanel = new HTMLPanel(
				"<h3>" + "Hier können Sie ihre Suchprofilattribute bearbeiten." + "</h3>");

		editSuchprofilPanel.add(editsp);

		RootPanel.get("contwrap").clear();
		RootPanel.get("contwrap").add(editSuchprofilPanel);

	}
	
	/**
	 * Clickhandler für den suchenButton, lädt in onClick die Ergebnistabelle
	 *
	 */
	private class SearchButtonClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			RootPanel.get("contwrap").add(generateErgebnisTable());
			partnerAdmin.getSuchProfilErgebnisse(suchprofil, new GetSuchProfilErgebnisseCallback());
		}
		
	}
	
	/**
	 * Callback, der alle Profile anhand eines Suchprofils ausgibt
	 */
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
	
	/**
	 * Initialisiert eine FlexTable für Ergebnisse
	 * @return, die erstellte Tabelle
	 */
	private FlexTable generateErgebnisTable(){
		
		profilFlexTable.setText(0, 0, "Vorname");
	    profilFlexTable.setText(0, 1, "Nachname");
	    profilFlexTable.setText(0, 2, "Ähnlichkeit");
	    
	    return profilFlexTable;
	}
	
	
	/**
	 * Fügt ein übergebenes Profil der ErgebnisTabelle hinzu.
	 * @param p
	 */
	private void addProfileToErgebniseTable(Profil p){

		Button showProfileButton = new Button("Profil ansehen!");
		
		profilFlexTable.setText(row, 0, p.getVorname());
		profilFlexTable.setText(row, 1, p.getNachname());
		profilFlexTable.setText(row, 2, String.valueOf(p.getÄhnlichkeit()) + "%");
		profilFlexTable.setWidget(row, 4, showProfileButton);
		ShowProfileClickhandler sp = new ShowProfileClickhandler();
		sp.setProfile(p);
		showProfileButton.addClickHandler(sp);
		row++;
		
	}
	
	/**
	 * Clickhandler für den ShowProfile Button, lädt in onClick den FremdProfilView
	 *
	 */
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
