package de.hdm.partnerboerse.client;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.bo.Profil;

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
