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

	final Button meineSuchprofilReports = new Button("Suchprofil Report");
	final Button meinePartnervorschlaege = new Button("Partnervorschläge");

	final Anchor logoutUser = new Anchor("Abmelden");
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
		logoutUser.setTitle("Abmelden");
		logoutUser.addStyleName("offbutton");

		VerticalPanel navi = new VerticalPanel();
		this.add(navi);
		navi.add(ppic);
		navi.add(meinProfil);
		navi.add(meineMerkliste);
		navi.add(meineKontaktsperren);
		navi.add(meineSuchprofile);
		navi.add(reports);
		navi.add(meineSuchprofilReports);
		navi.add(meinePartnervorschlaege);
		navi.add(logoutUser);
		
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
            
            HTMLPanel spPanel = new HTMLPanel("<h3>" + "Hier können Sie Ihr Suchprofil erstellen." + "</h3>");
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
		
		meineSuchprofilReports.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.assign("PartnerboerseReport.html");
				
			}
		});
		
		meinePartnervorschlaege.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent event) {
				Window.Location.assign("PartnerboerseReport.html");
				
			}
		});

	}

}
