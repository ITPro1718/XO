package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;

public class ListViewFremdprofilSP extends VerticalPanel{
	
	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	/**
	 * Ausgabe der Fremdprofile basierend auf den Suchprofilen
	 * 
	 */

	@Override
	public void onLoad() {
		HTML reports = new HTML("<h3>" + "Gefundene Profile" + "</h3>");
		
		/**
		 * TODO in CSS einbinden
		 */
		reports.addStyleName("fpwrap");

		FlexTable fpListTable = new FlexTable();
		
		/**
		 * TODO in CSS einbinden
		 */
		fpListTable.setStyleName("fptable");
		this.add(fpListTable);

		// Spalte 1
		fpListTable.setText(0, 0, "Vorname");
		fpListTable.setText(0, 1, "Nachname");
		fpListTable.setText(0, 2, "Alter");
		fpListTable.setText(0, 3, "Haarfarbe");
		fpListTable.setText(0, 4, "Körpergröße");
		fpListTable.setText(0, 5, "Raucher");
		fpListTable.setText(0, 6, "Religion");
		fpListTable.setText(0, 7, "Match in %");

	}

	/**
	 * Click Handlers.
	 */
}
