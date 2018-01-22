package de.hdm.partnerboerse.client;

import java.util.ArrayList;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Merkzettel;
import de.hdm.partnerboerse.shared.bo.Profil;

public class EditMerkliste extends VerticalPanel {

	FlexTable merklisteGrid = new FlexTable();

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	Profil profil = ClientSideSettings.getProfil();

	/**
	 * Aufbau Merkzettelseite mit Editierfunktion
	 */

	@Override
	public void onLoad() {

		HTML merkliste = new HTML("<h3>" + "Merkliste" + "</h3>");
		merkliste.addStyleName("mlwrap");

		merklisteGrid.setStyleName("mltable");
		this.add(merklisteGrid);

		// Zeile 1
		merklisteGrid.setText(0, 0, "Vorname");
		merklisteGrid.setText(0, 1, "Nachname");
		merklisteGrid.setText(0, 2, "E-Mail");

		loadMerklisteFromServer();
	}

	// ToDo: Methode muss geändert
	private void loadMerklisteFromServer() {

		partnerAdmin.getProfileForMerkzettel(profil, new AsyncCallback<ArrayList<Profil>>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Merkliste konnte nicht geladen werden");

			}

			@Override
			public void onSuccess(ArrayList<Profil> result) {
				loadEditMerklisteView(result);

			}
		});
	}

	/*
	 * Schleife setzt alle Profile in das Flextable (merklisteGrid)
	 */
	private void loadEditMerklisteView(ArrayList<Profil> result) {

		for (Profil p : result) {

			Button deleteButton = new Button("Profil löschen");
			int row = merklisteGrid.getRowCount();

			merklisteGrid.setText(row, 0, p.getVorname());
			merklisteGrid.setText(row, 1, p.getNachname());
			merklisteGrid.setText(row, 2, p.getEmail());
			merklisteGrid.setWidget(row, 3, deleteButton);
			
			final Profil fin = p;
			deleteButton.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					Merkzettel m = new Merkzettel();
					m.setEigenprofilID(ClientSideSettings.getProfil().getId());
					m.setFremdprofilID(fin.getId());
					partnerAdmin.deleteMerkzettelEintrag(m, new AsyncCallback<Void>(){

						@Override
						public void onFailure(Throwable caught) {
						}

						@Override
						public void onSuccess(Void result) {
							reload();
						}
						
					});
					
				}
				
			});

		}
	}
	
	public void reload(){
		
		EditMerkliste em = new EditMerkliste();
        
        HTMLPanel emPanel = new HTMLPanel("<h3>" + "Hier können Sie ihre Merkliste editieren" + "</h3>");
        emPanel.add(em);
        
        RootPanel.get("contwrap").clear();
        RootPanel.get("contwrap").add(emPanel);
	}
}