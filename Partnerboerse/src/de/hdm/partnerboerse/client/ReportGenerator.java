package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DockPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ReportGenerator implements EntryPoint {
	
	Navigation nav = new Navigation();	
	SPReport spr = new SPReport();
	
	@Override
	public void onModuleLoad() {


		//Navigation Area			
		RootPanel.get("navwrap").add(nav);	

		// Content Area		
		HTMLPanel reports = new HTMLPanel("<h3>" + "Hier finden Sie Ihre Reports" + "</h3>");
		reports.add(spr);
		reports.addStyleName("repwrap");
		RootPanel.get("contwrap").add(reports);
		
	}
}
