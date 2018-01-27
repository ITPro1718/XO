package de.hdm.partnerboerse.server.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import com.gargoylesoftware.htmlunit.Cache;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.partnerboerse.server.PartnerboerseAdministrationImpl;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.ReportGeneratorService;
import de.hdm.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.partnerboerse.shared.bo.Info;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;
import de.hdm.partnerboerse.shared.report.*;


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
		PartnerboerseAdministrationImpl partnerAdmin = new PartnerboerseAdministrationImpl();
		partnerAdmin.init();
		this.administration = partnerAdmin;
	}

	/**
	 * Auslesen der zugehörigen PartnerboerseAdministration (interner Gebrauch).
	 * 
	 * @return das PartnerboerseVerwaltungsobjekt
	 */
	protected PartnerboerseAdministration getPartnerboerseVerwaltung() {
		return this.administration;
	}
	
	protected void addImprint(Report r) {
	    /*
	     * Das Impressum soll wesentliche Informationen über unsere Partnerboerse enthalten.
	     * Das Imressum soll mehrzeilig sein.
	     */
	    CompositeParagraph imprint = new CompositeParagraph();

	    imprint.addSubParagraph(new SimpleParagraph("XO-Partnerboerse"));
	    imprint.addSubParagraph(new SimpleParagraph("Gruppe 06"));
	    imprint.addSubParagraph(new SimpleParagraph("IT Projekt WS1718"));

	    r.setImprint(imprint);

	  }
	
	public SingleProfilReport createSingleProfilReport(Profil p){
		
		if (this.getPartnerboerseVerwaltung() == null){
			return null;
		}
		
		SingleProfilReport result = new SingleProfilReport();
		
		result.setTitle("Profil von " + p.getVorname() + " " + p.getNachname());
		
		CompositeParagraph profileData = new CompositeParagraph();
		
		profileData.addSubParagraph(new SimpleParagraph("Vorname"));
		profileData.addSubParagraph(new SimpleParagraph("Nachname"));
		profileData.addSubParagraph(new SimpleParagraph("E-Mail"));
		profileData.addSubParagraph(new SimpleParagraph("Geburtstag"));
		profileData.addSubParagraph(new SimpleParagraph("Haarfarbe"));
		profileData.addSubParagraph(new SimpleParagraph("Körpergröße"));
		profileData.addSubParagraph(new SimpleParagraph("Raucher"));
		profileData.addSubParagraph(new SimpleParagraph("Religion"));
		profileData.addSubParagraph(new SimpleParagraph("Ähnlichkeit"));
		
		result.setProfilData(profileData);
		
		CompositeParagraph profilInhalt = new CompositeParagraph();
		profilInhalt.addSubParagraph(new SimpleParagraph(p.getVorname()));
		profilInhalt.addSubParagraph(new SimpleParagraph(p.getNachname()));
		profilInhalt.addSubParagraph(new SimpleParagraph(p.getEmail()));
		profilInhalt.addSubParagraph(new SimpleParagraph(String.valueOf(p.getGeburtsdatum())));
		profilInhalt.addSubParagraph(new SimpleParagraph(p.getHaarfarbe()));
		profilInhalt.addSubParagraph(new SimpleParagraph(String.valueOf(p.getKoerpergroesse())));
		profilInhalt.addSubParagraph(new SimpleParagraph(String.valueOf(p.isRaucher())));
		profilInhalt.addSubParagraph(new SimpleParagraph(p.getReligion()));
		profilInhalt.addSubParagraph(new SimpleParagraph(String.valueOf(p.getÄhnlichkeit())));
		
		result.setProfilInhalt(profilInhalt);
		
		return result;
	}

	@Override
	public AllNotSeenProfilesReport createAllNotSeenProfilesReport(Profil p) throws IllegalArgumentException {
		
		if (this.getPartnerboerseVerwaltung() == null)
		      return null;

		    AllNotSeenProfilesReport result = new AllNotSeenProfilesReport();
		    
		    result.setTitle("Alle nicht angesehenen Profile");

		    this.addImprint(result);

		    
		    result.setCreated(new Date());

		    // TODO: Das muss man updaten!
		    ArrayList<Profil> allProfile = this.administration.getAllProfils();
		    

		    for (Profil pr : allProfile) {
		      /*
		       * Anlegen des jew. Teil-Reports und Hinzufügen zum Gesamt-Report.
		       */
		      result.addSubReport(this.createSingleProfilReport(pr));
		    }

		    
		    return result;
		
	}

	@Override
	public AllProfilesBySuchprofil createSuchprofilReport(Suchprofil suchprofil) throws IllegalArgumentException {
		
		if (this.getPartnerboerseVerwaltung() == null)
		      return null;

		    AllProfilesBySuchprofil result = new AllProfilesBySuchprofil();
		    

		    result.setTitle("Alle Profile anhand eines Suchprofils");

		    this.addImprint(result);

		    
		    result.setCreated(new Date());

		    
		    ArrayList<Profil> allProfile = this.administration.getAllProfils();
		    

		    for (Profil p : allProfile) {
		      /*
		       * Anlegen des jew. Teil-Reports und HinzufÃ¼gen zum Gesamt-Report.
		       */
		      result.addSubReport(this.createSingleProfilReport(p));
		    }

		    
		    return result;
		  }

}
