package de.hdm.partnerboerse.client;

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

public class NavigationReport extends VerticalPanel {

	LoginInfo loginInfo = ClientSideSettings.getLoginInfo();

	/*
	 * Navigation zeichnen und bereitstellen
	 */
	final Button meinProfil = new Button("Zurück zum Profil");
	final Button Reports = new Button("Reports");
	final Anchor logoutUser = new Anchor("Abmelden");
	
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

		final VerticalPanel navi = new VerticalPanel();
		this.add(navi);
		navi.add(ppic);
		navi.add(meinProfil);
		navi.add(Reports);
		navi.add(logoutUser);

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

	}

}
