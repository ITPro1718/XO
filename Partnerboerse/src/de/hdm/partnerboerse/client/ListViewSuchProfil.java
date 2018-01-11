package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;

public class ListViewSuchProfil extends VerticalPanel{
	
	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);
	
	Button createButton = new Button("Suchprofil erstellen");
	Button deleteButton = new Button("<img src=\"" + "../img/deletePic.png" + "\">");
	Button editButton = new Button("<img src=\"" + "../img/editPic.png" + "\">");

	/**
	 * Aufbau Suchprofilliste mit Löschfunktion
	 */
	
	@Override
	public void onLoad() {
		HTML splist = new HTML("<h3>" + "Suchprofilliste" + "</h3>");
		splist.addStyleName("mlwrap");
		
//		HTML deletePic = new HTML("<img src=\"" + "../img/deletePic.png" + "\">");
//		HTML editPic = new HTML("<img src=\"" + "../img/editPic.png" + "\">");
//
//		ppic.setTitle("Profilbild");
//		ppic.addStyleName("ppic");

		FlexTable splistGrid = new FlexTable();
		splistGrid.setStyleName("mltable");
		this.add(splistGrid);

		// Zeile 1

		splistGrid.setWidget(0, 0, createButton);

		//Zeile 2
		splistGrid.setText(1, 0, "Name des Suchprofils");
		splistGrid.setWidget(1, 1, editButton);
		splistGrid.setWidget(1, 2, deleteButton);	
		
	}
}
