package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;

public class EditMerkliste extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	/**
	 * Aufbau Merkzettelseite mit Editierfunktion
	 */
	
	@Override
	public void onLoad() {
		HTML merkliste = new HTML("<h3>" + "Merkliste" + "</h3>");
		merkliste.addStyleName("mlwrap");

		FlexTable merklisteGrid = new FlexTable();
		merklisteGrid.setStyleName("mltable");
		this.add(merklisteGrid);
		
		Hyperlink link1 = new Hyperlink("Link zum Profil","E-Mail");
		Button deleteButton = new Button("Profil löschen");

		// Zeile 1
		merklisteGrid.setText(0, 0, "Vorname");
		merklisteGrid.setText(0, 1, "Nachname");
		merklisteGrid.setWidget(0, 2, link1);
		merklisteGrid.setWidget(0, 3, deleteButton);


	}
}
