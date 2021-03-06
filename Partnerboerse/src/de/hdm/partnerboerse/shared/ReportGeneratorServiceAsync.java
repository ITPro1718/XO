package de.hdm.partnerboerse.shared;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;
import de.hdm.partnerboerse.shared.report.AllNotSeenProfilesReport;
import de.hdm.partnerboerse.shared.report.AllProfilesBySuchprofil;
import de.hdm.partnerboerse.shared.report.SingleProfilReport;

public interface ReportGeneratorServiceAsync {

	void createAllNotSeenProfilesReport(Profil p, AsyncCallback<AllNotSeenProfilesReport> callback);

	void createSingleProfilReport(Profil profil, AsyncCallback<SingleProfilReport> callback);

	void createSuchprofilReport(Suchprofil suchprofil, AsyncCallback<AllProfilesBySuchprofil> callback);

	void init(AsyncCallback<Void> callback);

}
