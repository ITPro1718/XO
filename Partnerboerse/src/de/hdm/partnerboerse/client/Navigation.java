package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;

public class Navigation extends VerticalPanel {

	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);

	/*
	 * Navigation zeichnen und bereitstellen
	 */

	final Button meinProfil = new Button("Mein Profil");
	final Button meineMerkliste = new Button("Merkliste");
	final Button meineKontaktsperren = new Button("Kontaktsperren");
	final Button meineSuchprofile = new Button("Suchprofile");

	HTML reports = new HTML("<h3>" + "REPORTS" + "</h3>");

	final Button meineSuchprofilReports = new Button("Suchprofil Report");
	final Button meinePartnervorschläge = new Button("Partnervorschläge");

	@Override
	public void onLoad() {

		// Navigation Area
		HTML ppic = new HTML("<img src=\"" + "../img/ppic.jpg" + "\">");

		ppic.setTitle("Profilbild");
		ppic.addStyleName("ppic");
		meinProfil.setTitle("Mein Profil");
		meinProfil.addStyleName("button");
		meineMerkliste.setTitle("Merkliste");
		meineMerkliste.addStyleName("button");
		meineKontaktsperren.setTitle("Kontaktsperre");
		meineKontaktsperren.addStyleName("button");
		meineSuchprofile.setTitle("Suchprofile");
		meineSuchprofile.addStyleName("button");
		meineSuchprofile.setTitle("Suchprofil Report");
		meineSuchprofile.addStyleName("button");
		meineSuchprofile.setTitle("Partnervorschläge");
		meineSuchprofile.addStyleName("button");

		VerticalPanel navi = new VerticalPanel();
		this.add(navi);
		navi.add(ppic);
		navi.add(meinProfil);
		navi.add(meineMerkliste);
		navi.add(meineKontaktsperren);
		navi.add(meineSuchprofile);
		navi.add(reports);
		navi.add(meineSuchprofilReports);
		navi.add(meinePartnervorschläge);

	}

	/*
	 * Click Handlers.
	 */

}
