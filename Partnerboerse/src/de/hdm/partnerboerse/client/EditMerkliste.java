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
import de.hdm.partnerboerse.client.CreateWidget;

/**
 * 
 * @author
 *
 */
public class EditMerkliste extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	/**
	 * aktuelles Profil wird aus der globalen ClientSideSettings geholt.
	 */
	Profil profil = ClientSideSettings.getProfil();

	FlexTable merklisteGrid = new FlexTable();
	
	CreateWidget cw = new CreateWidget();

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
		merklisteGrid.setWidget(0, 0, cw.getVnameLabel());
		merklisteGrid.setWidget(0, 1, cw.getLnameLabel());
		merklisteGrid.setWidget(0, 2, cw.getEmailLabel());

		loadMerklisteFromServer();
	}

	/**
	 * Merkliste des Users wird aus der DB geholt.
	 */
	private void loadMerklisteFromServer() {

	    /**
	     * AsyncCallback an den Server.
	     * Profil des Users ist der Übergabeparameter. 
	     */
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

	    /**
	     * Array von Profilen werden jeweils in ein Grid befüllt.
	     */
		for (Profil p : result) {

			Button deleteButton = new Button("löschen");
			Button showButton = new Button("anzeigen");
			int row = merklisteGrid.getRowCount();

			merklisteGrid.setText(row, 0, p.getVorname());
			merklisteGrid.setText(row, 1, p.getNachname());
			merklisteGrid.setText(row, 2, p.getEmail());
			merklisteGrid.setWidget(row, 3, showButton);
			merklisteGrid.setWidget(row, 4, deleteButton);
			
			final Profil fin = p;
			
			/**
			 * Klasse erstellt die FremdprofilView für das Profil.
			 * Es bekommt eine Profil als Übergabeparameter
			 * @author
			 *
			 */
			class ShowProfileClickhandler implements ClickHandler{
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
			
			/**
			 * View für FrendprofilView wird aufgerufen.
			 */
			ShowProfileClickhandler sp = new ShowProfileClickhandler();
			sp.setProfile(p);
			showButton.addClickHandler(sp);
			
			/**
			 * Löschen eines Profiles aus der Merkliste.
			 */
			deleteButton.addClickHandler(new ClickHandler(){

				@Override
				public void onClick(ClickEvent event) {
				    /**
				     * Erstellt eine neue Instanz eines Merkzettels.
				     */
					Merkzettel m = new Merkzettel();
					/**
					 * Id des Eigenprofils wird in den Merkzettel zur Referenzierung gespeichert.
					 */
					m.setEigenprofilID(ClientSideSettings.getProfil().getId());
					/**
					 * Id des Fremdprofils wird in die Merkliste zur Referenzierung gespeichert.
					 */
					m.setFremdprofilID(fin.getId());
					/**
					 * Übergibt einen Merkzetteleintrag um ihn aus der DB des Users zu löschen.
					 */
					partnerAdmin.deleteMerkzettelEintrag(m, new AsyncCallback<Void>(){

						@Override
						public void onFailure(Throwable caught) {
							  Window.alert("Merkliste wurde nicht gespeichert.");
						}

						/**
						 * Bei erfolgreicher Löschung eines Merkzetteleintrages, wird die
						 * View neu geladen.
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
	 * View zur Editierung des Merkzettels wird neu geladen.
	 */
	public void reload(){
		/**
		 * Erstellt eine Instanz eines Merkzettels
		 */
		EditMerkliste em = new EditMerkliste();
        
        HTMLPanel emPanel = new HTMLPanel("<h3>" + "Hier können Sie ihre Merkliste editieren" + "</h3>");
        emPanel.add(em);
        /**
         * Rootpanel wird geleert und die aktuelle Merkliste wird dem User angezeigt.
         */
        RootPanel.get("contwrap").clear();
        RootPanel.get("contwrap").add(emPanel);
	}
}