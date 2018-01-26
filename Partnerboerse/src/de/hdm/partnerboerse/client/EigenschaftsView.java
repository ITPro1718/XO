package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
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

					ListViewSuchProfil lvs = new ListViewSuchProfil();
					

					HTMLPanel evPanel = new HTMLPanel(
							"<h3>" + "Hier sehen sie ihre Suchprofile!" + "</h3>");
					evPanel.add(lvs);

					RootPanel.get("contwrap").clear();
					RootPanel.get("contwrap").add(evPanel);
					
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

}
