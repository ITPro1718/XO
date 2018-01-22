package de.hdm.partnerboerse.client;

import java.util.ArrayList;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

public class EditKontaktsperre extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);
	Profil profil = ClientSideSettings.getProfil();
	FlexTable kontaktsperreGrid = new FlexTable();


	

	/**
	 * Aufbau Kontaktsperrenliste mit Editierfunktion
	 */

	public void onLoad() {
		HTML kontaktsperre = new HTML("<h3>" + "Kontaktsperrenliste" + "</h3>");
		kontaktsperre.addStyleName("kswrap");

		kontaktsperreGrid.setStyleName("kstable");
		this.add(kontaktsperreGrid);

		// Zeile 1
		kontaktsperreGrid.setText(0, 0, "Vorname");
		kontaktsperreGrid.setText(0, 1, "Nachname");
		kontaktsperreGrid.setText(0, 2, "E-Mail");

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

			Button deleteButton = new Button("Profil entsperren");
			int row = kontaktsperreGrid.getRowCount();

			kontaktsperreGrid.setText(row, 0, p.getVorname());
			kontaktsperreGrid.setText(row, 1, p.getNachname());
			kontaktsperreGrid.setText(row, 2, p.getEmail());
			kontaktsperreGrid.setWidget(row, 3, deleteButton);

		}

	}

}