package de.hdm.partnerboerse.server.report;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.partnerboerse.server.PartnerboerseAdministrationImpl;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.ReportGeneratorService;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;
import de.hdm.partnerboerse.shared.report.AllNotSeenProfilesReport;
import de.hdm.partnerboerse.shared.report.AllProfilesBySuchprofil;

/**
 * The server-side implementation of the RPC service.
 */

public class ReportGeneratorImpl extends RemoteServiceServlet implements ReportGeneratorService {

	private static final long serialVersionUID = 1L;

	private PartnerboerseAdministration administration = null;

	public ReportGeneratorImpl() throws IllegalArgumentException {
	}

	/**
	 * Initialsierungsmethode. Siehe dazu Anmerkungen zum
	 * No-Argument-Konstruktor.
	 * 
	 * @see #ReportGeneratorImpl()
	 */
	@Override
	public void init() throws IllegalArgumentException {
		/*
		 * Ein ReportGeneratorImpl-Objekt instantiiert für seinen Eigenbedarf
		 * eine PartnerboerseVerwaltungImpl-Instanz.
		 */
		PartnerboerseAdministrationImpl a = new PartnerboerseAdministrationImpl();
		a.init();
		this.administration = a;
	}

	/**
	 * Auslesen der zugehörigen PartnerboerseAdministration (interner Gebrauch).
	 * 
	 * @return das PartnerboerseVerwaltungsobjekt
	 */
	protected PartnerboerseAdministration getPartnerboerseVerwaltung() {
		return this.administration;
	}

	@Override
	public AllNotSeenProfilesReport createAllNotSeenProfilesReport(Profil p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AllProfilesBySuchprofil createSuchprofilReport(Suchprofil suchprofil) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

}
