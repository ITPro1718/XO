package de.hdm.partnerboerse.client;

import java.util.ArrayList;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

public class EigenschaftsView extends VerticalPanel{
	
	private final PartnerboerseAdministrationAsync partnerAdmin =
		      GWT.create(PartnerboerseAdministration.class);
	
	LoadEigenschaften lb = new LoadEigenschaften();
	FlexTable profilFlexTable = new FlexTable();
	int row = 1;
	
	Profil profil;
	Suchprofil suchprofil;
	
	@Override
	public void onLoad() {
		
		/**
		 * Unterscheidet, ob die Eigenschaften für ein Profil oder Suchprofil angezeigt werden sollen
		 */
		if (profil != null){
			Grid infoGrid = lb.loadEigen(profil);
			this.add(infoGrid);
			Button goButton = new Button("Auf gehts!");
			this.add(goButton);
			
			goButton.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					Editor ed = new Editor();
					RootPanel.get("contwrap").clear();
					ed.onModuleLoad();
				}	
			});
		}
		
		/**
		 * Unterscheidet, ob die Eigenschaften für ein Profil oder Suchprofil angezeigt werden sollen
		 */
		if (suchprofil != null){
			Grid infoGrid = lb.loadEigen(suchprofil);
			this.add(infoGrid);
			Button goButton = new Button("Lets go!");
			this.add(goButton);
			
			goButton.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					
					/**
					 * Zeigt die Ergebnisse des eben erstellten Suchprofils an.
					 */
					RootPanel.get("contwrap").clear();
					HTMLPanel fpvPanel = new HTMLPanel("<h2>" + "Ergebnisse: " + "</h2>");
					RootPanel.get("contwrap").add(fpvPanel);
					RootPanel.get("contwrap").add(generateErgebnisTable());
					partnerAdmin.getSuchProfilErgebnisse(suchprofil, new GetSuchProfilErgebnisseCallback());
					
				}
			});
			
		}
	}

	public void egFor(Profil p) {
		this.profil = p;
		
	}
	
	public void egFor(Suchprofil sp){
		this.suchprofil = sp;
	}
	
	/**
	 * Callback, welcher alle Ergebnisprofile eines Suchprofils zurückliefert und in der onSuccess-
	 * Methode der ErgebnisTable hinzufügt
	 * @see addProfileToErgebniseTable
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
	 * Generiert eine Ergebnistable mit 3 Spalten
	 * @return FlexTable
	 */
	private FlexTable generateErgebnisTable(){
		
		profilFlexTable.setText(0, 0, "Vorname");
	    profilFlexTable.setText(0, 1, "Nachname");
	    profilFlexTable.setText(0, 2, "Ähnlichkeit");
	    
	    return profilFlexTable;
	}
	
	/**
	 * Fügt ein Profil der Ergebnistable hinzu. Ebenso wird ein showProfileButton hinzugefügt,
	 * durch welchen man auf dem Profil der angezeigten Users kommt.
	 * @param p
	 */
	private void addProfileToErgebniseTable(Profil p){

		Button showProfileButton = new Button("Profil ansehen");
		
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
	 * Clickhandler, welcher den Klick auf den ShowProfilButton handelt.
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
