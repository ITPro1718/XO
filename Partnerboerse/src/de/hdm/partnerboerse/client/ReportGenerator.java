package de.hdm.partnerboerse.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ReportGenerator implements EntryPoint{

	@Override
	public void onModuleLoad() {

		HTML html1 = new HTML("This is first GWT HTML widget using <b> tag of html.");
		HTML html2 = new HTML("This is second GWT HTML widget using <i> tag of html.");

		// use UIObject methods to set HTML widget properties.
		html1.addStyleName("gwt-Green-Border");
		html2.addStyleName("gwt-Blue-Border");

		// add widgets to the root panel.
		VerticalPanel panel = new VerticalPanel();
		panel.setSpacing(10);
		panel.add(html1);
		panel.add(html2);

		RootPanel.get("mwrap").add(panel);
		
	}
}
