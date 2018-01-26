package de.hdm.partnerboerse.client;

import java.util.ArrayList;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;

import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.PartnerboerseAdministrationAsync;
import de.hdm.partnerboerse.shared.ReportGeneratorService;
import de.hdm.partnerboerse.shared.ReportGeneratorServiceAsync;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

public class ReportGenerator implements EntryPoint {

	Navigation nav = new Navigation();
	// SPReport spr = new SPReport();
	
	ListBox suchprofilListBox = new ListBox();
	ArrayList<Profil> profile = new ArrayList<Profil>();
	ArrayList<Profil> suchProfilErgebnisse = new ArrayList<Profil>();
	
	Suchprofil suchprofil = new Suchprofil();
	ArrayList<Suchprofil> suchprofileOfUser = new ArrayList<Suchprofil>();
	
	private final ReportGeneratorServiceAsync reportGenerator = GWT.create(ReportGeneratorService.class);
	private final PartnerboerseAdministrationAsync partnerAdmin = GWT.create(PartnerboerseAdministration.class);
	private final LoginServiceAsync loginService = GWT.create(LoginService.class);

	@Override
	public void onModuleLoad() {

		// Navigation Area
		RootPanel.get("navwrap").add(nav);

		// Content Area
		HTMLPanel reports = new HTMLPanel("<h3>" + "Hier finden Sie Ihre Reports" + "</h3>");
		// reports.add(spr);
		reports.addStyleName("repwrap");
		RootPanel.get("contwrap").add(reports);
		
		RootPanel.get("contwrap").add(suchprofilListBox);
		
		partnerAdmin.findSuchprofileOf(ClientSideSettings.getProfil(), new SuchProfileOfUserCallback());
		

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
}
