package de.hdm.partnerboerse.client.report;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TabPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.client.ClientSideSettings;
import de.hdm.partnerboerse.client.impressum.Datenschutz;
import de.hdm.partnerboerse.client.impressum.Impressum;
import de.hdm.partnerboerse.client.login.LoginInfo;

public class NavigationReport extends VerticalPanel {

	LoginInfo loginInfo = ClientSideSettings.getLoginInfo();

	/*
	 * Navigation zeichnen und bereitstellen
	 */
	final Button meinProfil = new Button("Zurück zum Profil");
	final Button Reports = new Button("Reports");
	final Anchor logoutUser = new Anchor("Abmelden");
	final HTML spr = new HTML(" | ");
	final Anchor impr = new Anchor("Impressum");
	final Anchor dataS = new Anchor("Datenschutz");
	
	HTML reports = new HTML("<h3>" + "REPORTS" + "</h3>");
	
	@Override
	public void onLoad() {

		// Navigation Area
		HTML ppic = new HTML("<img src=\"" + "../img/ppic.jpg" + "\">");

		ppic.setTitle("Profilbild");
		ppic.addStyleName("ppic");
		meinProfil.setTitle("Zurück zum Profil");
		meinProfil.addStyleName("button");

		Reports.addStyleName("button");
		Reports.setTitle("Partnervorschläge");
		Reports.addStyleName("button");

		logoutUser.setTitle("Abmelden");
		logoutUser.addStyleName("offbutton");
		
		impr.addStyleName("impdat");
		dataS.addStyleName("spr");
		dataS.addStyleName("impdat");

		final VerticalPanel navi = new VerticalPanel();
		navi.addStyleName("naviRep");
		this.add(navi);
		navi.add(ppic);
		navi.add(meinProfil);
		navi.add(Reports);
		navi.add(logoutUser);
		navi.add(impr);
		navi.add(spr);
		navi.add(dataS);

		/*
		 * Methode lädt die View des eigenen Profils
		 */
		meinProfil.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				Window.Location.assign("Partnerboerse.html");

			}
		});

		Reports.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {


					Window.Location.assign("PartnerboerseReport.html");

				}
			});

		logoutUser.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {

				ClientSideSettings.getLoginInfo();
				logoutUser.setHref(ClientSideSettings.getLoginInfo().getLogoutUrl());

			}
		});
		
		impr.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
					  
				Impressum ip = new Impressum();
				ip.addStyleName("impress");
				
				HTMLPanel ipPanel = new HTMLPanel("<h3>" + "Impressum" + "</h3>");
	            ipPanel.add(ip);

	            RootPanel.get("contwrap").clear();
	            RootPanel.get("contwrap").add(ipPanel);
			}
		});
		dataS.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
					  
				Datenschutz ds = new Datenschutz();
				ds.addStyleName("datasec");
				
				HTMLPanel dsPanel = new HTMLPanel("<h3>" + "Datenschutz" + "</h3>");
	            dsPanel.add(ds);

	            RootPanel.get("contwrap").clear();
	            RootPanel.get("contwrap").add(dsPanel);
			}
		});

	}

}
