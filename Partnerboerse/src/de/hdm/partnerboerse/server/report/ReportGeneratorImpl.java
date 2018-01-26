package de.hdm.partnerboerse.server.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

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

	@Override
	public AllNotSeenProfilesReport createAllNotSeenProfilesReport(Profil p) throws IllegalArgumentException {
		
		if (this.getPartnerboerseVerwaltung() == null)
		      return null;
		
		AllNotSeenProfilesReport result = new AllNotSeenProfilesReport();
		
		result.setTitle("Alle vom dir nicht angesehenen Profile");
		
		this.addImprint(result);
		
		result.setCreated(new Date());
		
		CompositeParagraph header = new CompositeParagraph();
		
		header.addSubParagraph(new SimpleParagraph(p.getNachname() + ", " + p.getVorname()));
		header.addSubParagraph(new SimpleParagraph("E-Mail Adresse: " + p.getEmail()));
		result.setHeaderData(header);
		
		// Kopfzeile für die Profil Tabelle
		
	    
	    // TODO: Methode fehlt
	    // ArrayList<Profil> profile = administration.getAllNichtBesuchteProfileOf(p);
	    
	    ArrayList<Profil> profile = administration.getAllProfils();
	    Profil profil = administration.getProfilByID(16);
	    profile.add(profil);
	    
	    Row headline = new Row();
		
		headline.addColumn(new Column("Vorname"));
	    headline.addColumn(new Column("Nachname"));
	    headline.addColumn(new Column("Geburtstag"));
	    headline.addColumn(new Column("Haarfarbe"));
	    headline.addColumn(new Column("Körpergröße"));
	    headline.addColumn(new Column("Raucher"));
	    headline.addColumn(new Column("Religion"));
	    headline.addColumn(new Column("Ähnlichkeit"));
	    
	    result.addRow(headline);
	    
	    for (Profil pr : profile){
	    	
	    	Row profilrow = new Row();
	    	
	    	profilrow.addColumn(new Column(String.valueOf(pr.getVorname())));
	    	profilrow.addColumn(new Column(String.valueOf(pr.getNachname())));
	    	profilrow.addColumn(new Column(String.valueOf(pr.getGeburtsdatum())));
	    	profilrow.addColumn(new Column(String.valueOf(pr.getHaarfarbe())));
	    	profilrow.addColumn(new Column(String.valueOf(pr.getKoerpergroesse())));
	    	profilrow.addColumn(new Column(String.valueOf(pr.isRaucher())));
	    	profilrow.addColumn(new Column(String.valueOf(pr.getReligion())));
	    	
	    	result.addRow(profilrow);
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
		      result.addSubReport(this.createAllNotSeenProfilesReport(p));
		    }

		    
		    return result;
		  }

}
