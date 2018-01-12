package de.hdm.partnerboerse.client;

import java.util.ArrayList;
import com.google.gwt.cell.client.ButtonCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

public class EditMerkliste extends VerticalPanel {
  
  FlexTable merklisteGrid = new FlexTable();

  private final PartnerboerseAdministrationAsync partnerAdmin =
      GWT.create(PartnerboerseAdministration.class);

  /**
   * Aufbau Merkzettelseite mit Editierfunktion
   */

  @Override
  public void onLoad() {
    
    HTML merkliste = new HTML("<h3>" + "Merkliste" + "</h3>");
    merkliste.addStyleName("mlwrap");

    merklisteGrid.setStyleName("mltable");
    this.add(merklisteGrid);

    Button deleteButton = new Button("Profil löschen");

    // Zeile 1
    merklisteGrid.setText(0, 0, "Vorname");
    merklisteGrid.setText(0, 1, "Nachname");
    merklisteGrid.setText(0, 2, "E-Mail");
    merklisteGrid.setWidget(0, 3, deleteButton);

    loadMerklisteFromServer();
  }

  //ToDo: Methode muss geändert 
  private void loadMerklisteFromServer() {
    
    //ToDo: muss raus, sobald Login implementiert ist.
    Profil simonProfil = new Profil();
    simonProfil.setId(1);

    partnerAdmin.getProfileForMerkzettel(simonProfil, new AsyncCallback<ArrayList<Profil>>() {

      @Override
      public void onFailure(Throwable caught) {
        Window.alert("Merkliste konnte nicht geladen werden");
        
      }

      @Override
      public void onSuccess(ArrayList<Profil> result) {
        loadEditMerklisteView(result);
        
      }});
  }
  
  private void loadEditMerklisteView(ArrayList<Profil> result) {
    
    
    for (Profil p: result) {

      int row = merklisteGrid.getRowCount();


      merklisteGrid.setText(row, 0, p.getVorname());
      merklisteGrid.setText(row, 1, p.getNachname());
      merklisteGrid.setText(row, 2, p.getEmail());

   }
  }
}
