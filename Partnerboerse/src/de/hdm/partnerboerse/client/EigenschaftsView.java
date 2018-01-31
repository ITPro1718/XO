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
		
		if (profil != null){
			Grid infoGrid = lb.loadEigen(profil);
			this.add(infoGrid);
			Button goButton = new Button("Lets go!");
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
		
		if (suchprofil != null){
			Grid infoGrid = lb.loadEigen(suchprofil);
			this.add(infoGrid);
			Button goButton = new Button("Lets go!");
			this.add(goButton);
			
			goButton.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					
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
