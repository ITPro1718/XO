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

/**
 * 
 * @author
 *
 */
public class EditKontaktsperre extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);
	/**
	 * Profil wird aus der globalen ClientSidesettings geholt.
	 */
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

	/**
	 * Kontaktsperren des Users werden aus der DB geladen und dem User in 
	 * einer View angezeigt.
	 */
	private void loadKontaktsperreFromServer() {

		partnerAdmin.getProfileForKontaktsperre(profil, new AsyncCallback<ArrayList<Profil>>() {

		    /**
		     * Es wird eine View erstellt um ein Array von Profilen anzeigen zu lassen.
		     */
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

	/**
	 * Erstellt die View für Kontaktsperren, dei aus ein Array von Suchprofilen besteht.
	 * @param result
	 */
	private void loadEditKontaktsperreView(ArrayList<Profil> result) {

	    /**
	     * Foreach Schleife geht durch das Profil-Array und setzt es in das Grid.
	     */
		for (Profil p : result) {

			Button deleteButton = new Button("entsperren");
			int row = kontaktsperreGrid.getRowCount();

			kontaktsperreGrid.setText(row, 0, p.getVorname());
			kontaktsperreGrid.setText(row, 1, p.getNachname());
			kontaktsperreGrid.setText(row, 2, p.getEmail());
			kontaktsperreGrid.setWidget(row, 3, deleteButton);
			
			final Profil fin = p;
			
			/**
			 * Es wird jedes mal ein Button Erstellt zum löschen des
			 * zugehörigen Profils aus der Kontaktsperre.
			 */
			deleteButton.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
				    /**
				     * neie Instanz einer Kontaktsperre wird erstellt zur Übergabe in den Callback.
				     */
					Kontaktsperre k = new Kontaktsperre();
					
					/**
					 * Die eigene ID des Users wird in die Kontaktsperre übergeben damit man
					 * die Referenzierung kennt auf die Kontaktsperre.
					 */
					k.setEigenprofilID(ClientSideSettings.getProfil().getId());
					k.setFremdprofilID(fin.getId());
					
					/**
					 * Es wird die Kontaktsperre übergeben damit ein User aus der Kontaktsperre
					 * entfernt wird.
					 */
					partnerAdmin.deleteKontaktsperreEintraege(k, new AsyncCallback<Void>(){

						@Override
						public void onFailure(Throwable caught) {
						}

						/**
						 * Bei Erfolg wird die Methode reload() aufgerufen, die dem User die aktuelle
						 * Kontaktsperre anzeigt.
						 */
						@Override
						public void onSuccess(Void result) {
							reload();
						}
						
					});
					
				}
				
			});

		}

	}

	/**
	 * Dem User wird die aktuelle Kontaktsperre angezeigt.
	 */
	public void reload(){
		
		EditKontaktsperre ek = new EditKontaktsperre();
        
        HTMLPanel ekPanel = new HTMLPanel("<h3>" + "Hier können Sie ihre Kontaktsperren editieren" + "</h3>");
        ekPanel.add(ek);
        
        RootPanel.get("contwrap").clear();
        RootPanel.get("contwrap").add(ekPanel);
	}

}