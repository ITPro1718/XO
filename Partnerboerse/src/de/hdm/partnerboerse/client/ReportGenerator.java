package de.hdm.partnerboerse.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.ReportGeneratorService;
import de.hdm.partnerboerse.shared.ReportGeneratorServiceAsync;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;
import de.hdm.partnerboerse.shared.report.*;

public class ReportGenerator implements EntryPoint {

	Navigation nav = new Navigation();
	// SPReport spr = new SPReport();
	private LoginInfo loginInfo = null;
	
	
	ListBox suchprofilListBox = new ListBox();
	ArrayList<Profil> profile = new ArrayList<Profil>();
	ArrayList<Profil> suchProfilErgebnisse = new ArrayList<Profil>();
	
	Suchprofil suchprofil = new Suchprofil();
	ArrayList<Suchprofil> suchprofileOfUser = new ArrayList<Suchprofil>();
	
	HorizontalPanel hp = new HorizontalPanel();
	Button notSeenProfileButton = new Button("Not seen Profiles");
	Button suchprofilButton = new Button("Profiles by Suchprofil");
	
	private final ReportGeneratorServiceAsync reportGenerator = GWT.create(ReportGeneratorService.class);
	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	@Override
	public void onModuleLoad() {
		
		loginService.login(GWT.getHostPageBaseURL(), new loginCallback());


		// Navigation Area
		RootPanel.get("navwrap").add(nav);

		// Content Area
		HTMLPanel reports = new HTMLPanel("<h2>" + "Hier finden Sie Ihre Reports" + "</h2>");
		HTMLPanel choice = new HTMLPanel("<h3> Bitte wählen sie, welchen Report sie ausgeben wollen!<h3>");
		// reports.add(spr);
		reports.addStyleName("repwrap");
		RootPanel.get("contwrap").add(reports);
		RootPanel.get("contwrap").add(choice);
		RootPanel.get("contwrap").add(hp);
		hp.add(notSeenProfileButton);
		hp.add(suchprofilButton);
		notSeenProfileButton.addClickHandler(new notSeenProfilesClickhandler());
		suchprofilButton.addClickHandler(new profilesbySuchprofilClickhandler());
		
		
	
	}
	
	private class profilesbySuchprofilClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			Suchprofil sp = new Suchprofil();
			reportGenerator.createSuchprofilReport(sp, new AllProfilesBySuchprofilCallback());
		}
		
	}
	
	private class notSeenProfilesClickhandler implements ClickHandler{

		@Override
		public void onClick(ClickEvent event) {
			reportGenerator.createAllNotSeenProfilesReport(ClientSideSettings.getProfil(), new AllNotSeenProfilesReportCallback());
		}
		
	}
	
	private class loginCallback implements AsyncCallback<LoginInfo>{

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(LoginInfo result) {
			loginInfo = result;		        
	        ClientSideSettings.setLoginInfo(result);
	        
	        if (loginInfo.isLoggedIn()) {
	        	loginService.getEmailFromProfil(result.getEmailAddress(), new hasProfileCallback());
	        }
		}
		
	}
	
	/**
	 * Befüllt ListBox mit Titeln der Suchprofile des Users
	 */
	private class SuchProfileOfUserCallback implements AsyncCallback<ArrayList<Suchprofil>>{

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(ArrayList<Suchprofil> result) {
			suchprofileOfUser = result;
			for (Suchprofil s : suchprofileOfUser){
				suchprofilListBox.addItem(s.getTitle());
			}
		}
		
	}
	private class hasProfileCallback implements AsyncCallback<Boolean>{

		@Override
		public void onFailure(Throwable caught) {
			Window.alert("Daten konnten nicht geladen werden.");
		}

		@Override
		public void onSuccess(Boolean result) {
			if (result) {
		       partnerAdmin.getProfilByEmail(loginInfo.getEmailAddress(), new getProfilFromServerCallback());
		    } 
		}
		
	}
	
	private class getProfilFromServerCallback implements AsyncCallback<Profil>{
		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(Profil result) {
			ClientSideSettings.setProfil(result);
			/**
	         * TODO: Hier die Callbacks reinschreiben
	         */
	        partnerAdmin.findSuchprofileOf(ClientSideSettings.getProfil(), new SuchProfileOfUserCallback());
	        
		}
		
	}
	
	
	private class AllNotSeenProfilesReportCallback implements AsyncCallback<AllNotSeenProfilesReport>{

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(AllNotSeenProfilesReport result) {
			printReport(result);
		}
	}
	
	private class AllProfilesBySuchprofilCallback implements AsyncCallback<AllProfilesBySuchprofil>{

		@Override
		public void onFailure(Throwable caught) {
		}

		@Override
		public void onSuccess(AllProfilesBySuchprofil result) {
			printReport(result);
		}
		
	}
	
	private void printReport(AllNotSeenProfilesReport report){
		RootPanel.get("contwrap").clear();
		HTMLReportWriter writer = new HTMLReportWriter();
        writer.process(report);
        HTML htmlreport = new HTML(writer.getReportText());
        RootPanel.get("contwrap").add(htmlreport);
	}
	
	private void printReport(AllProfilesBySuchprofil report){
		RootPanel.get("contwrap").clear();
		HTMLReportWriter writer = new HTMLReportWriter();
        writer.process(report);
        Window.alert("hello" + writer.getReportText());
        HTML htmlreport = new HTML(writer.getReportText());
        RootPanel.get("contwrap").add(htmlreport);
	}
}
