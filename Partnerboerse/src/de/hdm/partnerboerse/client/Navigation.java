package de.hdm.partnerboerse.client;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class Navigation extends VerticalPanel {

	LoginInfo loginInfo = ClientSideSettings.getLoginInfo();
	
	/*
	 * Navigation zeichnen und bereitstellen
	 */
	final Button meinProfil = new Button("Mein Profil");
	final Button meineMerkliste = new Button("Merkliste");
	final Button meineKontaktsperren = new Button("Kontaktsperren");
	final Button meineSuchprofile = new Button("Suchprofile");

	HTML reports = new HTML("<h3>" + "REPORTS" + "</h3>");

	final Button Report = new Button("Reports");

	final Anchor logoutUser = new Anchor("Abmelden");
	final HTML spr = new HTML(" | ");
	final Anchor impr = new Anchor("Impressum");
	final Anchor dataS = new Anchor("Datenschutz");
	
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
		Report.setTitle("Report");
		Report.addStyleName("button");
		logoutUser.setTitle("Abmelden");
		logoutUser.addStyleName("offbutton");
		impr.addStyleName("impdat");
		dataS.addStyleName("spr");
		dataS.addStyleName("impdat");


		VerticalPanel navi = new VerticalPanel();
		navi.addStyleName("navi");
		this.add(navi);
		navi.add(ppic);
		navi.add(meinProfil);
		navi.add(meineMerkliste);
		navi.add(meineKontaktsperren);
		navi.add(meineSuchprofile);
		navi.add(reports);
		navi.add(Report);
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
            
            loadEigenProfilView();
            
          }

          /*
           * Methode zeigt das eigene Profil an
           */
          private void loadEigenProfilView() {
            
            EigenProfilView epv = new EigenProfilView();
            
            HTMLPanel eigenProfilViewPanel = new HTMLPanel("<h3>" + "Hier können Sie ihr Profil sehen." + "</h3>");
            eigenProfilViewPanel.add(epv);
            
            RootPanel.get("contwrap").clear();
            RootPanel.get("contwrap").add(eigenProfilViewPanel);
                     
          }
        });
		
	      /*
         * Methode lädt die View der Merkliste
         */
		meineMerkliste.addClickHandler(new ClickHandler() {
          
          @Override
          public void onClick(ClickEvent event) {
            
            EditMerkliste em = new EditMerkliste();
            
            HTMLPanel emPanel = new HTMLPanel("<h3>" + "Hier können Sie ihre Merkliste editieren" + "</h3>");
            emPanel.add(em);
            
            RootPanel.get("contwrap").clear();
            RootPanel.get("contwrap").add(emPanel);
            
          }
        });
		
		/*
		 * Methode lädt die View der Kontaktsperren
		 */
		meineKontaktsperren.addClickHandler(new ClickHandler() {
          
          @Override
          public void onClick(ClickEvent event) {
            
            EditKontaktsperre ep = new EditKontaktsperre();
            
            HTMLPanel epPanel = new HTMLPanel("<h3>" + "Hier können Sie ihre Kontaktsperren editieren" + "</h3>");
            epPanel.add(ep);
            
            RootPanel.get("contwrap").clear();
            RootPanel.get("contwrap").add(epPanel);
            
            
            
            
          }
        });
		
		meineSuchprofile.addClickHandler(new ClickHandler() {
          
          @Override
          public void onClick(ClickEvent event) {
            
           ListViewSuchProfil sp = new ListViewSuchProfil();
            
            HTMLPanel spPanel = new HTMLPanel("<h3>" + "Hier können Sie Ihre Suchprofile verwalten." + "</h3>");
            spPanel.add(sp);

            RootPanel.get("contwrap").clear();
            RootPanel.get("contwrap").add(spPanel);
            
          }
        });
		
		logoutUser.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
					  
				//logoutUser.setHref(loginInfo.getLogoutUrl());
				ClientSideSettings.getLoginInfo();
				logoutUser.setHref(ClientSideSettings.getLoginInfo().getLogoutUrl());
				
			}
		});
		
		// Aufruf der Report HTML - Hier wird der Report aufgerufen
		
	
		Report.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.assign("PartnerboerseReport.html");
				
				
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
