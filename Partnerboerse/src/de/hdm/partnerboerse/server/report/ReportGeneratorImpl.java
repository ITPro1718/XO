package de.hdm.partnerboerse.server.report;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.i18n.shared.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import de.hdm.partnerboerse.server.PartnerboerseAdministrationImpl;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.ReportGeneratorService;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;
import de.hdm.partnerboerse.shared.report.*;


/**
 * Implementierung des synchonen Interfaces ReportGeneratorService. In dieser Klasse befindet 
 * sich die komplette Logik des ReportGenerators. Hier werden die auszugebenden Reports erstellt.
 * 
 * @author Burghardt
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
	
	/**
	 * Fügt dem übergebenem Report ein Impressum hinzu
	 * @param r
	 */
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
	
	/**
	 * Erstellt einen Report für ein einzelnes Profil. Dieser ist ein SimpleReport und wird
	 * später von den anderen Reports aufgerufen und verwendet. Der Report hat einen Titel,
	 * die Profildaten wie z.B. Vorname & Nachname und die Profilinhalte wie z.B. Max Mustermann
	 * @param p
	 * @return SingleProfilReport, der erstellte Report
	 */
	public SingleProfilReport createSingleProfilReport(Profil p){
		
		if (this.getPartnerboerseVerwaltung() == null){
			return null;
		}
		
		/**
		 * Legt einen neuen SingleProfilReport an
		 */
		SingleProfilReport result = new SingleProfilReport();
		
		/**
		 * Setzt den Titel des Reports
		 */
		result.setTitle("Profil von " + p.getVorname() + " " + p.getNachname());
		
		/**
		 * Setzt die Profildaten als einzelne SimpleParagraphs in einen CompositeParagraph
		 */
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
		
		/**
		 * Setzt die Profilinhalte als einzelne SimpleParagraphs in einen CompositeParagraph
		 */
		CompositeParagraph profilInhalt = new CompositeParagraph();
		profilInhalt.addSubParagraph(new SimpleParagraph(p.getVorname()));
		profilInhalt.addSubParagraph(new SimpleParagraph(p.getNachname()));
		profilInhalt.addSubParagraph(new SimpleParagraph(p.getEmail()));
		profilInhalt.addSubParagraph(new SimpleParagraph(this.parseToGermanDatum(p.getGeburtsdatum())));
		profilInhalt.addSubParagraph(new SimpleParagraph(p.getHaarfarbe()));
		profilInhalt.addSubParagraph(new SimpleParagraph(String.valueOf(p.getKoerpergroesse())));
		profilInhalt.addSubParagraph(new SimpleParagraph(String.valueOf(p.isRaucher())));
		profilInhalt.addSubParagraph(new SimpleParagraph(p.getReligion()));
		profilInhalt.addSubParagraph(new SimpleParagraph(String.valueOf(p.getÄhnlichkeit()) + "%"));
		
		result.setProfilInhalt(profilInhalt);
		
		/**
		 * Gibt den erstellten Report zurück
		 */
		return result;
	}

	/**
	 * Erstellt einen Report, welcher alle Partnervorschläge zurückgibt, welche der User nicht angesehen hat. 
	 * Dieser ist ein CompositeReport und wird besteht aus mehreren SingleProfilReports.
	 * @param Profil p,
	 * @return AllNotSeenProfilesReport, der erstellte Report
	 */
	@Override
	public AllNotSeenProfilesReport createAllNotSeenProfilesReport(Profil p) throws IllegalArgumentException {
		
		if (this.getPartnerboerseVerwaltung() == null)
		      return null;
		
		/**
		 * Legt einen neuen AllNotSeenProfilesReport an
		 */
	    AllNotSeenProfilesReport result = new AllNotSeenProfilesReport();
	    
	    /**
	     * setzt den Titel des Reports
	     */
	    result.setTitle("Alle nicht angesehenen Profile");

	    /**
	     * fügt dem Report ein Impressum hinzu
	     */
	    this.addImprint(result);	    
	    result.setCreated(new Date());
	    
	    /**
	     * Holt sich alle Partnervorschläge, welche der User noch nicht angesehen hat
	     */
	    ArrayList<Profil> notSeenProfiles = this.administration.getNotSeenPartnervorschläge(p);
	    
	    for (Profil pr : notSeenProfiles) {
	      /*
	       * Anlegen des jew. Teil-Reports und Hinzufügen zum Gesamt-Report.
	       */
	      result.addSubReport(this.createSingleProfilReport(pr));
	    }

	    /**
	     * Gibt den fertigen Report zurück
	     */
	    return result;
		
	}

	/**
	 * Erstellt einen Report, welcher alle Partnervorschläge anhand eines Suchprofilszurückgibt. 
	 * Dieser ist ein CompositeReport und wird besteht aus mehreren SingleProfilReports.
	 * @param Suchprofil sp,
	 * @return AllProfilesBySuchprofil, der erstellte Report
	 */
	@Override
	public AllProfilesBySuchprofil createSuchprofilReport(Suchprofil suchprofil) throws IllegalArgumentException {
		
		if (this.getPartnerboerseVerwaltung() == null)
		      return null;

		/**
		 * Legt einen neuen AllProfilesBySuchprofil Report an
		 */
	    AllProfilesBySuchprofil result = new AllProfilesBySuchprofil();
	    
	    /**
	     * Holt sich das Eigenprofil des Suchprofils
	     */
	    Profil owner = administration.getProfilByID(suchprofil.getEigenprofilID());
	    
	    /**
	     * Setzt einen Titel und einen Header für den Report
	     */
	    result.setTitle("Alle Profile anhand Suchprofil: " + suchprofil.getTitle());	    
	    CompositeParagraph header = new CompositeParagraph();
	    header.addSubParagraph(new SimpleParagraph("Suchprofil: " + suchprofil.getTitle()));
	    header.addSubParagraph(new SimpleParagraph(owner.getNachname() + ", " + owner.getVorname()));
	    header.addSubParagraph(new SimpleParagraph("E-Mail: " + owner.getEmail()));
	    result.setHeaderData(header);
	    
	    /**
	     * Fügt dem Report ein Impressum hinzu
	     */
	    this.addImprint(result);
	    result.setCreated(new Date());

	    /**
	     * Holt sich alle Partnervorschläge anhand eines Suchprofils
	     */
	    ArrayList<Profil> allProfile = this.administration.getSuchProfilErgebnisse(suchprofil);
	    

	    for (Profil p : allProfile) {
	      /*
	       * Anlegen des jew. Teil-Reports und Hinzufügen zum Gesamt-Report.
	       */
	      result.addSubReport(this.createSingleProfilReport(p));
	    }

	    /**
	     * Gibt den Report zurück.
	     */
	    return result;
	  }
	
	/**
	 * Nimmt das Geburtsdatum und wandelt es für den Report Generator in das deutsche Datenformat um
	 * @param datum
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private String parseToGermanDatum(Date datum){
		
		String year = String.valueOf(1900 + datum.getYear());
		String month = String.valueOf(datum.getMonth() + 1);
		String day = String.valueOf(datum.getDate());
		
		if (Integer.parseInt(month) < 10){
			month = "0" + month;
		}
		if (Integer.parseInt(day) < 10){
			day = "0" + day;
		}
		
		
		String result = day + "." + month + "." + year;
		return result;
				
	}
}
