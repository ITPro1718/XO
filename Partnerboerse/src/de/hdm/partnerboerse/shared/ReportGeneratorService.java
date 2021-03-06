package de.hdm.partnerboerse.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;
import de.hdm.partnerboerse.shared.report.AllNotSeenProfilesReport;
import de.hdm.partnerboerse.shared.report.AllProfilesBySuchprofil;
import de.hdm.partnerboerse.shared.report.SingleProfilReport;


@RemoteServiceRelativePath("reportgenerator")
public interface ReportGeneratorService extends RemoteService {
	
	/**
	   * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von GWT
	   * RPC zusätzlich zum No Argument Constructor der implementierenden Klasse
	   * PartnerboerseAdministrationImpl notwendig. 
	   *  
	   * @throws IllegalArgumentException
	   */
	  public void init() throws IllegalArgumentException;

	  
	  /**
	   * Erstellen eines <code>AllNotSeenProfilesReport</code>-Reports. Dieser
	   * Report-Typ stellt sämtliche Nicht-Angesehenen Profile eines Users an.
	   * 
	   * @param p eine Referenz auf das Profilobjekt bzgl. dessen der Report
	   *          erstellt werden soll.
	   * @return das fertige Reportobjekt
	   * @throws IllegalArgumentException
	   * @see AllNotSeenProfilesReport
	   */
	  public abstract AllNotSeenProfilesReport createAllNotSeenProfilesReport(
	      Profil p) throws IllegalArgumentException;

	  /**
	   * Erstellen eines <code>SuchprofilReport</code>-Reports.
	   * Dieser Report-Typ stellt alle Profile nach einem Suchprofil dar.
	   * 
	   * @return das fertige Reportobjekt
	   * @throws IllegalArgumentException
	   * @see AllAccountsOfAllCustomersReport
	   */
	  public abstract AllProfilesBySuchprofil createSuchprofilReport(Suchprofil suchprofil)
	      throws IllegalArgumentException;
	  
	  /**
	   * Erstellt einen <code>SingleProfilReport>-Report.
	   * Dieser Report-Typ stellt ein einzelnes Profil dar und ist die Basis für die
	   * anderen Reports, welches alles aus SingleProfilReport's zusammengestellt sind.
	   * @param profil
	   * @return
	   * @throws IllegalArgumentException
	   */
	  public abstract SingleProfilReport createSingleProfilReport(Profil profil) throws IllegalArgumentException;


}
