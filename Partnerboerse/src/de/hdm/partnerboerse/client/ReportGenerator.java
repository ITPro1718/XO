package de.hdm.partnerboerse.client;

import java.util.ArrayList;

import com.google.gwt.aria.client.NavigationRole;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.ReportGeneratorService;
import de.hdm.partnerboerse.shared.ReportGeneratorServiceAsync;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;
import de.hdm.partnerboerse.shared.report.*;

/**
 * Erstellt den View für den ReportGenerator-Client. Es werden sich über AsyncCallbacks
 * reports vom Server geholt und diese werden von den printReport-Methoden in reines
 * HTML umgewandelt und dann ausgegeben.
 * @author Burghardt
 *
 */
public class ReportGenerator implements EntryPoint {

	NavigationReport navrep = new NavigationReport();
	// SPReport spr = new SPReport();
	private LoginInfo loginInfo = null;
	private Label loginLabel = new Label("Melde dich mit deinem Google-Konto an, dann kann es schon losgehen!");
	private Anchor signInLink = new Anchor("anmelden");
	private VerticalPanel loginPanel = new VerticalPanel();
	
	ListBox suchprofilListBox = new ListBox();
	ArrayList<Profil> profile = new ArrayList<Profil>();
	ArrayList<Profil> suchProfilErgebnisse = new ArrayList<Profil>();
	
	Suchprofil suchprofil = new Suchprofil();
	ArrayList<Suchprofil> suchprofileOfUser = new ArrayList<Suchprofil>();
	
	HorizontalPanel hp = new HorizontalPanel();
	Button notSeenProfileButton = new Button("Partnervorschläge");
	Button suchprofilButton = new Button("Profile nach Suchprofil");
	FlexTable suchprofilTable = new FlexTable();
	int row = 1;
	
	private final ReportGeneratorServiceAsync reportGenerator = GWT.create(ReportGeneratorService.class);
	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	@Override
	public void onModuleLoad() {
		
		loginService.login(GWT.getHostPageBaseURL(), new LoginCallback());
		
		

	
	}
	
	/**
	 * Clickhandler für den ProfilesBySuchProfilButton
	 */
	private class ProfilesbySuchprofilClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			/**
			 * Cleart den Content und fügt die Suchprofiltabelle hinzu
			 */
			RootPanel.get("contwrap").clear();
	        RootPanel.get("contwrap").add(suchprofilTable);
		}
		
	}
	
	/**
	 * Clickhandler für den NotSeenProfilesButton
	 */
	private class NotSeenProfilesClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			reportGenerator.createAllNotSeenProfilesReport(ClientSideSettings.getProfil(), new AllNotSeenProfilesReportCallback());
		}
		
	}
	
	/**
	 * Login Callback welcher überprüft ob man eingeloggt ist
	 */
	private class LoginCallback implements AsyncCallback<LoginInfo>{

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(LoginInfo result) {
			loginInfo = result;		        
	        ClientSideSettings.setLoginInfo(result);
	        
	        if (loginInfo.isLoggedIn()) {
	        	
	        	loginService.getEmailFromProfil(result.getEmailAddress(), new HasProfileCallback());
	        	
	        	suchprofilTable.setText(0, 0, "Titel");
	        	suchprofilTable.addStyleName("mltable");
				
				// Navigation Area
				RootPanel.get("navwrap").add(navrep);
				
				// Content Area
				HTMLPanel reports = new HTMLPanel("<h2>" + "Hier finden Sie Ihre Reports" + "</h2>");
				HTMLPanel choice = new HTMLPanel("<h3> Bitte wählen Sie, welchen Report Sie ausgeben wollen!</h3>");
				// reports.add(spr);
				reports.addStyleName("repwrap");
				choice.addStyleName("repwrap");
				RootPanel.get("contwrap").add(reports);
				RootPanel.get("contwrap").add(choice);
				RootPanel.get("contwrap").add(hp);
				hp.addStyleName("repwrp");
				hp.add(notSeenProfileButton);
				hp.add(suchprofilButton);
				notSeenProfileButton.addClickHandler(new NotSeenProfilesClickhandler());
				suchprofilButton.addClickHandler(new ProfilesbySuchprofilClickhandler());
	        }
	        
	        else {
	        	loadLogin();
	        }
		}
		
	}

	/**
	 * Sucht alle Suchprofile eines Users und gibt diese zurück
	 */
	private class SuchProfileOfUserCallback implements AsyncCallback<ArrayList<Suchprofil>>{

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(ArrayList<Suchprofil> result) {
			for (Suchprofil s : result){
				addToTable(s);
			}
		}
		
	}
	
	/**
	 * Prüft, ob eine E-Mail bereits ein Profil hat
	 */
	private class HasProfileCallback implements AsyncCallback<Boolean>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Daten konnten nicht geladen werden.");
		}

		@Override
		public void onSuccess(Boolean result) {
			if (result) {
		       partnerAdmin.getProfilByEmail(loginInfo.getEmailAddress(), new GetProfilFromServerCallback());
		    }
		}
		
	}
	
	/**
	 * Holt sich ein Profil aus der Datenbank
	 *
	 */
	private class GetProfilFromServerCallback implements AsyncCallback<Profil>{
		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Profil result) {
			ClientSideSettings.setProfil(result);
			
	        partnerAdmin.findSuchprofileOf(ClientSideSettings.getProfil(), new SuchProfileOfUserCallback());
	        
	        
		}
		
	}
	
	/**
	 * Holt sich den Report der nicht angesehenen Profilen zurück und printet diesen
	 * in onSuccess
	 */
	private class AllNotSeenProfilesReportCallback implements AsyncCallback<AllNotSeenProfilesReport>{

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(AllNotSeenProfilesReport result) {
			printReport(result);
		}
	}
	
	/**
	 * Holt sich den Report von Profilen anhand eines Suchprofils und printet diesen
	 * in onSuccess
	 */
	private class AllProfilesBySuchprofilCallback implements AsyncCallback<AllProfilesBySuchprofil>{

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(AllProfilesBySuchprofil result) {
			printReport(result);
		}
		
	}
	
	/**
	 * Printet den übergebenen report
	 * @param report
	 */
	private void printReport(AllNotSeenProfilesReport report){
		RootPanel.get("contwrap").clear();
		HTMLReportWriter writer = new HTMLReportWriter();
        writer.process(report);
        HTML htmlreport = new HTML(writer.getReportText());
        RootPanel.get("contwrap").add(htmlreport);
	}
	
	/**
	 * Printet den übergebenen report
	 * @param report
	 */
	private void printReport(AllProfilesBySuchprofil report){
		RootPanel.get("contwrap").clear();
		HTMLReportWriter writer = new HTMLReportWriter();
        writer.process(report);
        HTML htmlreport = new HTML(writer.getReportText());
        RootPanel.get("contwrap").add(htmlreport);
	}
	
	/**
	 * Printet den übergebenen report
	 * @param report
	 */
	private void addToTable(Suchprofil suchprofil){
		Button suchButton = new Button("Report!");
		
		suchprofilTable.setText(row, 0, suchprofil.getTitle());
		suchprofilTable.setWidget(row, 1, suchButton);
		ReportClickhandler rC = new ReportClickhandler();
		rC.setSuchprofil(suchprofil);
		suchButton.addClickHandler(rC);
		row++;
		
	}
	
	/**
	 * Holt sich einen Report anhand von Suchprofilen
	 * 
	 */
	private class ReportClickhandler implements ClickHandler{
		
		Suchprofil s;
		
		public void setSuchprofil(Suchprofil suchprofil){
			this.s = suchprofil;
		}

		@Override
		public void onClick(ClickEvent event) {
			reportGenerator.createSuchprofilReport(s, new AllProfilesBySuchprofilCallback());
		}
		
	}
	
	public void loadLogin() {
		// Assemble login panel.
		HTMLPanel eigenProfilViewPanel = new HTMLPanel("<h1>" + "Willkommen bei der XO-Partnerboerse" + "</h1>");
        
		signInLink.setHref(loginInfo.getLoginUrl());
		loginPanel.add(loginLabel);
		loginPanel.add(signInLink);
		eigenProfilViewPanel.add(loginPanel);
		RootPanel.get("contwrap").add(eigenProfilViewPanel);
	}
}
