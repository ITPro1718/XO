package de.hdm.partnerboerse.client.editor.kontaktsperremerkliste;

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
import de.hdm.partnerboerse.client.ClientSideSettings;
import de.hdm.partnerboerse.client.editor.forms.CreateWidget;
import de.hdm.partnerboerse.client.editor.profil.FremdProfilView;

public class EditMerkliste extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

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

			Button deleteButton = new Button("löschen");
			Button showButton = new Button("anzeigen");

			int row = merklisteGrid.getRowCount();

			merklisteGrid.setText(row, 0, p.getVorname());
			merklisteGrid.setText(row, 1, p.getNachname());
			merklisteGrid.setText(row, 2, p.getEmail());
			merklisteGrid.setWidget(row, 3, showButton);
			merklisteGrid.setWidget(row, 4, deleteButton);

			final Profil fin = p;
			ShowProfilClickhandler sp = new ShowProfilClickhandler();
			sp.setProfile(p);
			showButton.addClickHandler(sp);
			deleteButton.addClickHandler(new DeleteButtonClickHandler());

		}
	}

	/**
	 * ClickHandler für den ShowButton
	 * 
	 *
	 */
	private class ShowProfilClickhandler implements ClickHandler {

		Profil p;

		public void setProfile(Profil p) {
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
	 * 
	 * 
	 */
	private class DeleteButtonClickHandler implements ClickHandler {
		@Override
		public void onClick(ClickEvent event) {
			Profil p = new Profil();
			Merkzettel m = new Merkzettel();
			m.setEigenprofilID(ClientSideSettings.getProfil().getId());
			m.setFremdprofilID(p.getId());
			partnerAdmin.deleteMerkzettelEintrag(m, new AsyncCallback<Void>() {

				@Override
				public void onFailure(Throwable caught) {
					Window.alert("Merkliste wurde nicht gespeichert.");
				}

				@Override
				public void onSuccess(Void result) {
					reload();
				}

			});

		}

	}
	
	public void reload() {

		EditMerkliste em = new EditMerkliste();

		HTMLPanel emPanel = new HTMLPanel("<h3>" + "Hier können Sie ihre Merkliste editieren" + "</h3>");
		emPanel.add(em);

		RootPanel.get("contwrap").clear();
		RootPanel.get("contwrap").add(emPanel);
	}

}