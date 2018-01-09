package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;

public class SPReport extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	/*
	 * Search Profile Reports zeichnen und bereitstellen
	 */

	@Override
	public void onLoad() {
		HTML reports = new HTML("<h3>" + "Suchprofil Reports" + "</h3>");
		reports.addStyleName("repwrap");

		FlexTable reportGrid = new FlexTable();
		reportGrid.setStyleName("rtable");
		this.add(reportGrid);

		// Spalte 1
		reportGrid.setText(0, 0, "Vorname");
		reportGrid.setText(0, 1, "Nachname");
		reportGrid.setText(0, 2, "Alter");
		reportGrid.setText(0, 3, "Haarfarbe");
		reportGrid.setText(0, 4, "Körpergröße");
		reportGrid.setText(0, 5, "Raucher");
		reportGrid.setText(0, 6, "Religion");
		reportGrid.setText(0, 7, "Match in %");

	}

	/*
	 * Click Handlers.
	 */

}
