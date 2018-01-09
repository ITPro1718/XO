package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

public class EigenProfilView extends VerticalPanel{
	
	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	/*
	 * Widgets, deren Inhalte variable sind, werden als Attribute angelegt.
	 */

	Button editButton = new Button("Profil bearbeiten");

	String vnameLabel = new String("Vorname: ");
	String lnameLabel = new String("Nachname: ");
	String bdayLabel = new String("Geburtstag: ");
	String hcolorLabel = new String("Haarfarbe: ");
	String heightLabel = new String("Größe "+ "(in cm): ");
	String smokerLabel = new String("Raucher: ");
	String religionLabel = new String("Religion: ");
	
	public void onLoad(Profil getEmail){
		
		Grid profilIntGrid = new Grid(2, 3);
		profilIntGrid.setStyleName("itable");
		this.add(profilIntGrid);

		profilIntGrid.setWidget(1, 0, editButton);

		Grid profilGrid = new Grid(7, 6);
		profilGrid.setStyleName("etable");
		this.add(profilGrid);

		// Spalte 1
		profilGrid.setText(1, 1, vnameLabel);
		profilGrid.setText(1, 2, getEmail.getVorname());

		profilGrid.setText(1, 3, lnameLabel);
		profilGrid.setText(1, 4, getEmail.getNachname());

		// Spalte 2
		profilGrid.setText(2, 1, bdayLabel);
		profilGrid.setText(2, 2, getEmail.getGeburtsdatum());

		profilGrid.setText(2, 3, hcolorLabel);
		profilGrid.setText(2, 4, getEmail.getHaarfarbe());

		// Spalte 3
		profilGrid.setText(3, 1, heightLabel);
		profilGrid.setText(3, 1, getEmail.getKoerpergroesse());

		profilGrid.setText(3, 3, smokerLabel);
		profilGrid.setText(3, 4, getEmail.isRaucher());

		// Spalte 4
		profilGrid.setText(4, 1, religionLabel);
		profilGrid.setText(4, 2, getEmail.getReligion());

		
		
	}
	
	
	
	

}
