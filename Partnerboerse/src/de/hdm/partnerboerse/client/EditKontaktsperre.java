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
import de.hdm.partnerboerse.shared.bo.Kontaktsperre;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.client.CreateWidget;


public class EditKontaktsperre extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);
	Profil profil = ClientSideSettings.getProfil();
	FlexTable kontaktsperreGrid = new FlexTable();

	CreateWidget cw = new CreateWidget();
	

	/**
	 * Aufbau Kontaktsperrenliste mit Editierfunktion
	 */

	public void onLoad() {
		HTML kontaktsperre = new HTML("<h3>" + "Kontaktsperrenliste" + "</h3>");
		kontaktsperre.addStyleName("kswrap");

		kontaktsperreGrid.setStyleName("mltable");
		this.add(kontaktsperreGrid);

		// Zeile 1
		kontaktsperreGrid.setWidget(0, 0, cw.getVnameLabel());
		kontaktsperreGrid.setWidget(0, 1, cw.getLnameLabel());
		kontaktsperreGrid.setWidget(0, 2, cw.getEmailLabel());

		loadKontaktsperreFromServer();

	}

	private void loadKontaktsperreFromServer() {

		partnerAdmin.getProfileForKontaktsperre(profil, new AsyncCallback<ArrayList<Profil>>() {

			@Override
			public void onSuccess(ArrayList<Profil> result) {
				loadEditKontaktsperreView(result);

			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Kontaktsperre konnte nicht geladen werden");

			}
		});

	}

	private void loadEditKontaktsperreView(ArrayList<Profil> result) {

		for (Profil p : result) {

			Button deleteButton = new Button("entsperren");
			int row = kontaktsperreGrid.getRowCount();

			kontaktsperreGrid.setText(row, 0, p.getVorname());
			kontaktsperreGrid.setText(row, 1, p.getNachname());
			kontaktsperreGrid.setText(row, 2, p.getEmail());
			kontaktsperreGrid.setWidget(row, 3, deleteButton);
			
			final Profil fin = p;
			
			deleteButton.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
					Kontaktsperre k = new Kontaktsperre();
					k.setEigenprofilID(ClientSideSettings.getProfil().getId());
					k.setFremdprofilID(fin.getId());
					partnerAdmin.deleteKontaktsperreEintraege(k, new AsyncCallback<Void>(){

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
		
		EditKontaktsperre ek = new EditKontaktsperre();
        
        HTMLPanel ekPanel = new HTMLPanel("<h3>" + "Hier können Sie ihre Kontaktsperren editieren" + "</h3>");
        ekPanel.add(ek);
        
        RootPanel.get("contwrap").clear();
        RootPanel.get("contwrap").add(ekPanel);
	}

}