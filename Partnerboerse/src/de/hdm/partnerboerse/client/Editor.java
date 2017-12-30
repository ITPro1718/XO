package de.hdm.partnerboerse.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

public class Editor implements EntryPoint {
	
  private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);
  
  private VerticalPanel mainPanel = new VerticalPanel();
  private FlexTable profilFlexTable = new FlexTable();
  private HorizontalPanel addPanel = new HorizontalPanel();
  private Label lastUpdatedLabel = new Label();
  


  @Override
  public void onModuleLoad() {

    // Create table for Profil
    profilFlexTable.setText(0, 0, "Vorname");
    profilFlexTable.setText(0, 1, "Nachname");
    profilFlexTable.setText(0, 2, "Haarfarbe");
    profilFlexTable.setText(0, 3, "Körpergröße");
    profilFlexTable.setText(0, 4, "Raucher");
    profilFlexTable.setText(0, 5, "Religion");
    profilFlexTable.setText(0, 6, "Geburtsdatum");
    profilFlexTable.setText(0, 7, "Passwort");
    profilFlexTable.setText(0, 8, "Email");
    

    // Assemble Main panel.
    mainPanel.add(profilFlexTable);
    mainPanel.add(addPanel);
    mainPanel.add(lastUpdatedLabel);

    // Associate the Main panel with the HTML host page.

    RootPanel.get("mwrap").add(mainPanel);

    
    // Neues Button Widget erzeugen und eine Beschriftung festlegen.
     
    final Button findProfilButton = new Button("Finde Profile");
    VerticalPanel navPanel = new VerticalPanel();
    

    RootPanel.get("mwrap").add(navPanel);
    navPanel.add(findProfilButton);
    
    findProfilButton.addClickHandler(new ClickHandler() {
      
      @Override
      public void onClick(ClickEvent event) {

    	  getProfileFromServer();
  
      }
      
		private void getProfileFromServer() {

			partnerAdmin.getAllProfils(new AsyncCallback<ArrayList<Profil>>(){
				
				public void onFailure(Throwable caught) {					
				}

				@Override
				public void onSuccess(ArrayList<Profil> result) {	
					for (Profil p : result){
						addToTable(p);
					}					
				}
			});
		}
		
	
      
      private void addToTable(Profil p){
		  int row = profilFlexTable.getRowCount();
          profilFlexTable.setText(row, 0, p.getNachname());
          profilFlexTable.setText(row, 1, p.getVorname());
          profilFlexTable.setText(row, 2, p.getHaarfarbe());
          int kgr = (int) p.getKoerpergroesse();
          String s = "" + kgr;
          profilFlexTable.setText(row, 3, s);
          profilFlexTable.setText(row, 4, "" + p.isRaucher());
          profilFlexTable.setText(row, 5, p.getReligion());
          profilFlexTable.setText(row, 6, "" +p.getGeburtsdatum());
          profilFlexTable.setText(row, 7, p.getPasswort());
          profilFlexTable.setText(row, 8, p.getEmail());
          
  	  }
    });
  }
}