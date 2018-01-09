package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;

public class PartnerReport extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	/*
	 * Partnervorschläge Reports zeichnen und bereitstellen
	 */

	@Override
	public void onLoad() {
		HTML reports = new HTML("<h3>" + "Partnervorschläge" + "</h3>");
		reports.addStyleName("repwrap");

		FlexTable preportGrid = new FlexTable();
		preportGrid.setStyleName("rtable");
		this.add(preportGrid);

		// Spalte 1
		preportGrid.setText(0, 0, "Vorname");
		preportGrid.setText(0, 1, "Nachname");
		preportGrid.setText(0, 2, "Alter");
		preportGrid.setText(0, 3, "Haarfarbe");
		preportGrid.setText(0, 4, "Körpergröße");
		preportGrid.setText(0, 5, "Raucher");
		preportGrid.setText(0, 6, "Religion");
		preportGrid.setText(0, 7, "Match in %");

	}

	/*
	 * Click Handlers.
	 */

}
