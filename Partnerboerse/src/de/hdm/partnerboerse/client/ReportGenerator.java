package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ReportGenerator implements EntryPoint {

	@Override
	public void onModuleLoad() {

		// Navigation Area
		HTML ppic = new HTML("<img src=\"" + "../img/ppic.jpg" + "\">");

		Button meinProfil = new Button("Mein Profil");
		Button meineMerkliste = new Button("Merkliste");
		Button meineKontaktsperren = new Button("Kontaktsperren");
		Button meineSuchprofile = new Button("Suchprofile");

		HTML reports = new HTML("<h3>" + "REPORTS" + "</h3>");

		Button meineSuchprofilReports = new Button("Suchprofil Report");
		Button meinePartnervorschläge = new Button("Partnervorschläge");

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
		navi.add(ppic);
		navi.add(meinProfil);
		navi.add(meineMerkliste);
		navi.add(meineKontaktsperren);
		navi.add(meineSuchprofile);
		navi.add(reports);
		navi.add(meineSuchprofilReports);
		navi.add(meinePartnervorschläge);

		RootPanel.get("navwrap").add(navi);

		// Content Area

		DockPanel dockPanel = new DockPanel();
		dockPanel.setStyleName("dockpanel");
		dockPanel.setSpacing(4);
		dockPanel.setHorizontalAlignment(DockPanel.ALIGN_CENTER);

		// Add text all around
		dockPanel.add(new HTML("This is the first north component."), DockPanel.NORTH);
		dockPanel.add(new HTML("This is the first south component."), DockPanel.SOUTH);
		dockPanel.add(new HTML("This is the east component."), DockPanel.EAST);
		dockPanel.add(new HTML("This is the west component."), DockPanel.WEST);
		dockPanel.add(new HTML("This is the second north component."), DockPanel.NORTH);
		dockPanel.add(new HTML("This is the second south component"), DockPanel.SOUTH);

		// Add scrollable text in the center
		HTML contents = new HTML("This is a ScrollPanel contained" + " at the center of a DockPanel. "
				+ " small in order to see the nifty scroll bars!");
		ScrollPanel scroller = new ScrollPanel(contents);
		scroller.setSize("400px", "100px");
		dockPanel.add(scroller, DockPanel.CENTER);

		VerticalPanel vPanel = new VerticalPanel();
		vPanel.add(dockPanel);

		// Add the widgets to the root panel.
		RootPanel.get("contwrap").add(vPanel);

	}
}
