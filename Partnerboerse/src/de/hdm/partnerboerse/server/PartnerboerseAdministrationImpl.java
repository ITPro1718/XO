package de.hdm.partnerboerse.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.partnerboerse.server.db.AuswahlMapper;
import de.hdm.partnerboerse.server.db.BesuchMapper;
import de.hdm.partnerboerse.server.db.EigenschaftMapper;
import de.hdm.partnerboerse.server.db.InfoMapper;
import de.hdm.partnerboerse.server.db.KontaktsperreMapper;
import de.hdm.partnerboerse.server.db.MerkzettelMapper;
import de.hdm.partnerboerse.server.db.ProfilMapper;
import de.hdm.partnerboerse.server.db.SuchprofilMapper;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
import de.hdm.partnerboerse.shared.bo.Aehnlichkeitsmass;
import de.hdm.partnerboerse.shared.bo.Auswahl;
import de.hdm.partnerboerse.shared.bo.Besuch;
import de.hdm.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.partnerboerse.shared.bo.Freitext;
import de.hdm.partnerboerse.shared.bo.Info;
import de.hdm.partnerboerse.shared.bo.Kontaktsperre;
import de.hdm.partnerboerse.shared.bo.Merkzettel;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

/**
 * Implementierung des synchronen Interfaces PartnerboerseAdministration. In dieser Klasse befindet 
 * sich die komplette Logik des Editors. Hier werden sämtliche Funktionen des Editors durchgeführt.  
 */

@SuppressWarnings("serial")
public class PartnerboerseAdministrationImpl extends RemoteServiceServlet implements PartnerboerseAdministration {
	/**
	 * Referenz auf die Datenbankmapper, die Objekte mit der Datenbank abgleichen.
	 */
	private AuswahlMapper aMapper = null;

	private EigenschaftMapper eiMapper = null;

	private InfoMapper iMapper = null;

	private KontaktsperreMapper kMapper = null;

	private MerkzettelMapper mMapper = null;

	private ProfilMapper pMapper = null;

	private SuchprofilMapper sMapper = null;

	private BesuchMapper bMapper = null;

	public PartnerboerseAdministrationImpl() throws IllegalArgumentException {

	}
	
	/**
	 *  Initialisierung der Mapper.Aufrufen der Singleton-Methode des Mappers, um sicherzustellen, 
	 *  das nur diese einzige Instanz existiert
	 * @see javax.servlet.GenericServlet#init()
	 */
	
	@Override
	public void init() throws IllegalArgumentException {

		this.aMapper = AuswahlMapper.auswahlMapper();
		this.eiMapper = EigenschaftMapper.eigenschaftMapper();
		this.iMapper = InfoMapper.infoMapper();
		this.kMapper = KontaktsperreMapper.kontaktsperreMapper();
		this.mMapper = MerkzettelMapper.merkzettelMapper();
		this.pMapper = ProfilMapper.profilMapper();
		this.sMapper = SuchprofilMapper.suchprofilMapper();
		this.bMapper = BesuchMapper.besuchMapper();

	}
	
	/**
	 * Die Methode getAge errechnet aus dem übergebenen Datumsformat das Alter.
	 * Hierbei wird ein neuer gregorianischer Kalender erstellt, welcher das aktuelle Jahr bereits enthält.
	 * Es wird ein zweiter Kalender erstellt, welchem das Datum zugewiesen wird. Es folgt ein Abgleich beider Kalender.
	 * @param date
	 * @return int
	 */

	public int getAge(Date date) {

		GregorianCalendar birthday = new GregorianCalendar();
		birthday.setTime(date);

		GregorianCalendar today = new GregorianCalendar();
		/**
		 *  Differenz aus aktuellem Jahr und Geburtsjahr
		 */
		int age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
		/**
		 * Abfrage falls das aktuelle Datum sich im selben Jahr des Geburtsjahrs befindet, 
		 * aber noch vor dem Geburtstag, dann reduziere das Alter um den Faktor 1.
		 */
		if (today.get(Calendar.MONTH) <= birthday.get(Calendar.MONTH)) {
			if (today.get(Calendar.DATE) < birthday.get(Calendar.DATE)) {
				age -= 1;
			}
		}

		if (age < 0)
			throw new IllegalArgumentException("invalid age: " + age);

		return age;
	}
	
	/**
	 * Erstellt ein Profil, in dem das Objekt an die Datenbank-Methode insert übergeben wird.
	 * @param p
	 * @return Profil
	 */
	@Override
	public Profil createProfil(Profil p) throws IllegalArgumentException {

		return this.pMapper.insert(p);
	}

	/**
	 * Aktualisiert ein Profil, in dem das Objekt an die Datenbank-Methode update übergeben wird.
	 * @param p
	 */
	@Override
	public void updateProfil(Profil p) throws IllegalArgumentException {
		pMapper.update(p);
	}

	/**
	 * Löscht ein Profil, in dem das Objekt an die Datenbank-Methode delete übergeben wird.
	 * @param p
	 */
	@Override
	public void deleteProfil(Profil p) throws IllegalArgumentException {

		/**
		 * Hier werden alle Abhängigkeiten geprüft, bevor das
		 * Profil gelöscht wird. z.B. müssen erst alle Merkzettel Einträge gelöscht werden,
		 * in denen das Profil vorkommt. Erst dann kann das Profil gelöscht werden
		 * 
		 * Abhängigkeiten von Profil:
		 * 
		 * Merkzettel Kontaktsperre Visit Suchprofil Info
		 * 
		 */

		ArrayList<Merkzettel> merkzettel = this.findMerkzettelnOf(p);
		ArrayList<Kontaktsperre> kontaktsperren = this.findKontaktsperrenOf(p);
		ArrayList<Besuch> besuche = this.findBesucheOf(p);
		ArrayList<Suchprofil> suchprofile = this.findSuchprofileOf(p);
		ArrayList<Info> infos = this.findInfoOf(p);
		ArrayList<Kontaktsperre> wurdegesperrt = this.getAllKontaktsperreEintraege();
		ArrayList<Merkzettel> wurdegemerkt = this.getAllMerkzettelEintraege();
		ArrayList<Besuch> wurdebesucht = this.findAllBesuche();
		
		if (wurdebesucht != null){
			for (Besuch b : wurdebesucht){
				if (b.getFremdprofilID() == p.getId()){
					this.deleteBesuch(b);
				}
			}
		}
		
		if (wurdegemerkt != null){
			for (Merkzettel m : wurdegemerkt){
				if (m.getFremdprofilID() == p.getId()){
					this.deleteMerkzettelEintrag(m);
				}
			}
		}
		
		if (wurdegesperrt != null){
			for (Kontaktsperre k : wurdegesperrt){
				if (k.getFremdprofilID() == p.getId()){
					this.deleteKontaktsperreEintraege(k);
				}
			}
		}

		if (merkzettel != null) {
			for (Merkzettel m : merkzettel) {
				this.deleteMerkzettelEintrag(m);
			}
		}

		if (kontaktsperren != null) {
			for (Kontaktsperre k : kontaktsperren) {
				this.deleteKontaktsperreEintraege(k);
			}
		}

		if (besuche != null) {
			for (Besuch b : besuche) {
				this.deleteBesuch(b);
			}
		}

		if (suchprofile != null) {
			for (Suchprofil s : suchprofile) {
				this.deleteSuchprofil(s);
			}
		}

		if (infos != null) {
			for (Info i : infos) {
				this.deleteInfo(i);
			}
		}

		this.pMapper.delete(p);
	}

	/**
	 * Gibt alle Profile zurück, die dem Parameter name entsprechen. Aufruf der Profilmapper-Methode findProfileByName.
	 * @param name
	 * @return ArrayList<Profil>
	 */
	@Override
	public ArrayList<Profil> getProfilByName(String name) throws IllegalArgumentException {

		return this.pMapper.findProfileByName(name);

	}
	
	/**
	 * Gibt ein Profil nach ID zurück. Aufruf der Profilmapper-Methode findProfilByKey.
	 * @param id
	 * @return Profil
	 */
	@Override
	public Profil getProfilByID(int id) throws IllegalArgumentException {

		return this.pMapper.findProfilByKey(id);

	}

	/**
	 * Gibt ein Profil nach email zurück. Aufruf der Profilmapper-Methode findProfilByEmail.
	 * @param email
	 * @return Profil
	 */
	@Override
	public Profil getProfilByEmail(String email) throws IllegalArgumentException {
		return this.pMapper.findProfilByEmail(email);
	}

	/**
	 * Gibt alle Profile aus, die sich ein User gemerkt. Profile die der User gesperrt hat oder die
	 * den User gesperrt haben, werden aussortiert.
	 * @param eigenProfil
	 * @return ArrayList mit Profilen
	 */
	@Override
	public ArrayList<Profil> getProfileForMerkzettel(Profil eigenProfil) throws IllegalArgumentException {
		
		ArrayList<Merkzettel> merk = this.findMerkzettelnOf(eigenProfil);
		ArrayList<Profil> result = new ArrayList<Profil>();
		ArrayList<Kontaktsperre> kont = this.findKontaktsperrenOf(eigenProfil);
		ArrayList<Profil> profilesToRemove = new ArrayList<Profil>();
		ArrayList<Kontaktsperre> wurdegesperrt = this.getAllKontaktsperreEintraege();
		
		for (Merkzettel m : merk){
			Profil p = this.getProfilByID(m.getFremdprofilID());
			result.add(p);
		}
		
		for (Kontaktsperre k : kont){
			for (Profil p : result){
				if (k.getFremdprofilID() == p.getId()){
					profilesToRemove.add(p);
				}
			}
		}
		
		for (Kontaktsperre k : wurdegesperrt){
			if (k.getFremdprofilID() == eigenProfil.getId()){
				System.out.println(k.toString());
				profilesToRemove.add(this.getProfilByID(k.getEigenprofilID()));
			}
		}
		result.removeAll(profilesToRemove);		
		System.out.println(result.toString());
		return result;

	}

	/**
	 * Gibt alle Profile zurück, die ein User gesperrt hat.
	 * @param eigenProfil
	 * @return ArrayList mit Profilen
	 */
	@Override
	public ArrayList<Profil> getProfileForKontaktsperre(Profil eigenProfil) throws IllegalArgumentException {

		return this.pMapper.findProfileForKontaktsperre(eigenProfil);

	}

	/**
	 * Gibt alle Profile der Datenbank zurück
	 * @return ArrayList mit Profilen
	 */
	@Override
	public ArrayList<Profil> getAllProfils() throws IllegalArgumentException {

		return this.pMapper.findAllProfiles();

	}

	/**
	 * Speichert einen Merkzetteleintrag von Profil source zu Profil target
	 * @param source, target
	 */
	@Override
	public void createMerkzettelEintrag(Profil source, Profil target) throws IllegalArgumentException {

		Merkzettel m = new Merkzettel();
		m.setEigenprofilID(source.getId());
		m.setFremdprofilID(target.getId());

		this.mMapper.insertMerkzettelEintrag(m);
	}

	/**
	 * Liefert alle Merkzetteleinträge der Datenbank zurück
	 * @return ArrayList mit Merkzetteln
	 */
	@Override
	public ArrayList<Merkzettel> getAllMerkzettelEintraege() throws IllegalArgumentException {

		return this.mMapper.findAll();
	}

	/**
	 * Gibt einen Merkzettel anhand der ID (Primärschlüssel) zurück
	 * @param ID
	 * @return Merkzettel
	 */
	@Override
	public Merkzettel getMerkzettelEintraegeByID(int ID) throws IllegalArgumentException {

		return this.mMapper.findByKey(ID);

	}

	/**
	 * Löscht einen Merkzetteleintrag aus der Datenbank
	 * @param merkzettel
	 */
	@Override
	public void deleteMerkzettelEintrag(Merkzettel merkzettel) throws IllegalArgumentException {

		Profil p = new Profil();
		p.setId(merkzettel.getEigenprofilID());

		ArrayList<Merkzettel> merk = this.findMerkzettelnOf(p);

		for (Merkzettel m : merk) {
			if (m.getFremdprofilID() == merkzettel.getFremdprofilID()) {
				this.mMapper.deleteMerkzettelEintrag(m);
			}
		}

	}

	/**
	 * Fügt eine Kontaktsperre von Profil source zu Profil target hinzu
	 * @param source, target
	 */
	@Override
	public void createKontaksperreEintrag(Profil source, Profil target) throws IllegalArgumentException {

		Kontaktsperre k = new Kontaktsperre();
		k.setEigenprofilID(source.getId());
		k.setFremdprofilID(target.getId());

		this.kMapper.insertKontaktsperreEintrag(k);
	}

	/**
	 * Liefert alle vorhandenen Kontaktsperren zurück
	 * @return ArrayList mit Kontaktsperren
	 */
	@Override
	public ArrayList<Kontaktsperre> getAllKontaktsperreEintraege() throws IllegalArgumentException {

		return this.kMapper.findAll();
	}

	/**
	 * Liefert eine Kontaktsperre anhand der ID (Primärschlüssel) zurück
	 * @param id
	 * @return Kontaktsperre
	 */
	@Override
	public Kontaktsperre getKontaktsperreEintragByID(int id) throws IllegalArgumentException {

		return this.kMapper.findByKey(id);

	}

	/**
	 * Löscht eine Kontaktsperre eines Profils
	 * @param kontaktsperre
	 */
	@Override
	public void deleteKontaktsperreEintraege(Kontaktsperre kontaktsperre) throws IllegalArgumentException {

		Profil p = new Profil();
		p.setId(kontaktsperre.getEigenprofilID());
		ArrayList<Kontaktsperre> kon = this.findKontaktsperrenOf(p);

		for (Kontaktsperre k : kon) {
			if (k.getFremdprofilID() == kontaktsperre.getFremdprofilID()) {
				this.kMapper.deleteKontaktsperreEintrag(k);
			}
		}

	}

	/**
	 * Erstellt ein Suchprofil, welches einem User zugeordnet ist
	 * @param source, titel, haarfarbe, kgr, raucher, religion, alter
	 * @return suchprofil
	 */
	@Override
	public Suchprofil createSuchprofil(Profil source, String titel, String haarfarbe, float kgr, boolean raucher,
			String religion, int alter) throws IllegalArgumentException {

		Suchprofil s = new Suchprofil();
		s.setEigenprofilID(source.getId());
		s.setHaarFarbe(haarfarbe);
		s.setKoerpergroesse(kgr);
		s.setTitle(titel);
		s.setReligion(religion);
		s.setRaucher(raucher);
		s.setAlter(alter);
		s.setEigenprofilID(source.getId());

		return this.sMapper.insertSuchprofil(s);

	}

	/**
	 * Liefert alle vorhandenen Suchprofile zurück
	 * @return ArrayList mit Suchprofilen
	 */
	@Override
	public ArrayList<Suchprofil> getAllSuchprofile() throws IllegalArgumentException {

		return this.sMapper.findAll();
	}

	/**
	 * Liefert ein Suchprofil anhand der ID (Primärschlüssel) zurück
	 * @return Suchprofil
	 */
	@Override
	public Suchprofil getSuchprofilByID(int id) throws IllegalArgumentException {

		return this.sMapper.findByKey(id);
	}

	/**
	 * Liefert ein Suchprofil anhand des Titels zurück
	 * @return Suchprofil
	 */
	@Override
	public Suchprofil getSuchprofilByTitle(String title) throws IllegalArgumentException {

		return this.sMapper.findSuchprofilByTitle(title);

	}

	/**
	 * Aktualisiert ein bearbeitetes Suchprofil
	 * @param suchprofil
	 */
	@Override
	public void updateSuchprofil(Suchprofil suchprofil) throws IllegalArgumentException {
		this.sMapper.updateSuchprofil(suchprofil);

	}

	/**
	 * Löscht ein Suchprofil eines Users. Vorher müssen jedoch die Abhängigkeiten des Suchprofils
	 * gelöscht werden. Hierzu müssen alle Infos des Suchprofils gelöscht werden.
	 * @param suchprofil
	 */
	@Override
	public void deleteSuchprofil(Suchprofil suchprofil) throws IllegalArgumentException {

		ArrayList<Info> infosOfSuchprofil = this.findInfoOf(suchprofil);

		if (infosOfSuchprofil != null) {
			for (Info infos : infosOfSuchprofil) {
				this.deleteInfo(infos);
			}
		}

		this.sMapper.deleteSuchprofil(suchprofil);
	}

	/**
	 * Gibt alle Infos eines Suchprofils zurück
	 * @param suchprofilid
	 * @return ArrayList mit Suchprofilen
	 */
	public ArrayList<Info> getInfoOfSuchprofil(int suchprofilid) {

		return this.iMapper.findInfoOfSuchprofil(suchprofilid);
	}

	/**
	 * Gibt alle Infos eines Profils zurück
	 * @param profil
	 * @return ArrayList mit Suchprofilen
	 */
	public ArrayList<Info> findAllInfosOfProfil(Profil profil) {

		return this.iMapper.findInfoOf(profil);
	}
	
	/**
	 * Berechnet das Ähnlichkeitsmaß im Vergleich zum eigenen Profil, in dem Profilattribute als auch Infoobjekte verglichen werden.
	 * Diese Attribute und Infoobjekte haben eine anfangs gesetzte Gewichtung.
	 * Bei Übereinstimmung zweier Werte, wird die GEwichtung gesetzt und prozentual zur Summe ausgerechnet.
	 * Nach Abgleich aller Werte wird die Ähnlichkeit in Prozent ausgerechnet und dem Profil zugewiesen.
	 * Alle verglichenen Profile werden absteigend nach der Ähnlichkeit sortiert. Die geordnete Liste wird zurückgegeben.
	 * 
	 * @param profil, 
	 * @param ergebnisse
	 * @return ArrayList<Profil>
	 */
	public ArrayList<Profil> berechneAehnlichkeitsmass(Profil profil, ArrayList<Profil> ergebnisse) {
		/**
		 * Erstellung einer Aehnlichkeitsmass-Instanz und Setzung der einzelnen Gewichtungen. 
		 * Die Summe der Gewichtungen muss nicht 100 ergeben, da die Werte zum späteren Zeitpunkt dieder Methode prozentual umgerechnet werden.
		 */
		Aehnlichkeitsmass aehnlichkeitsmass = new Aehnlichkeitsmass();
		aehnlichkeitsmass.setGewichtung1Infoobjekt(10);
		aehnlichkeitsmass.setGewichtungAlter(70);
		aehnlichkeitsmass.setGewichtungHaarfarbe(35);
		aehnlichkeitsmass.setGewichtungRaucher(55);
		aehnlichkeitsmass.setGewichtungReligion(45);

		ArrayList<Profil> comparedProfiles = new ArrayList<>();
		float o1 = aehnlichkeitsmass.getGewichtungHaarfarbe();
		float o2 = aehnlichkeitsmass.getGewichtungReligion();
		float o3 = aehnlichkeitsmass.getGewichtungAlter();
		float o4 = aehnlichkeitsmass.getGewichtungRaucher();
		float o5 = aehnlichkeitsmass.getGewichtung1Infoobjekt();
		float o6 = aehnlichkeitsmass.getGewichtung1Infoobjekt();
		/**
		 * Abgleich sämtlicher Attribute und Infoobjekte jedes Profils einer ArrayList<Profil>
		 * Je nach Eintritt der If-Bedingung wird die Gewichtung gesetzt oder nicht.
		 */
		for (Profil p : ergebnisse) {
			float p1 = 0;
			float p2 = 0;
			float p3 = 0;
			float p4 = 0;
			float p5 = 0;
			float p6 = 0;
			float summe = o1 + o2 + o3 + o4 + o5 + o6;
			/**
			 * Multiplikationsfaktor zur Berechnung des prozentualen Anteils an der Summe.
			 */
			float x = (float) 100 / summe;

			if (p.getHaarfarbe().equals(profil.getHaarfarbe())) {
				p1 = o1;
			}
			if (p.getReligion().equals(profil.getReligion())) {
				p2 = o2;
			}
			if (((getAge(p.getGeburtsdatum())) <= (getAge(profil.getGeburtsdatum()) + 3))
					&& ((getAge(p.getGeburtsdatum())) >= (getAge(profil.getGeburtsdatum()) - 3))) {
				p3 = o3;
			}

			if (p.isRaucher() == profil.isRaucher()) {
				p4 = o4;
			}
			if(((profilInfoHasFreitext(profil).isEmpty()) && (profilInfoHasAuswahl(profil).isEmpty())) ||
					((profilInfoHasFreitext(p).isEmpty()) && (profilInfoHasAuswahl(p).isEmpty()))){
				p5 = o5;
				p6 = o6;
			}	
			if(compareInfos(profil, p) == 0){
				p5 = o5;
			}
			if(compareStrings(profilInfoHasFreitext(profil), profilInfoHasFreitext(p)) == 0){
				p6 = o6;  
			}
			if (compareInfos(profil, p) >= 1) {
				o5 = o5 * compareInfos(profil, p);
				p5 = o5;
			}
			
			if (compareStrings(profilInfoHasFreitext(profil), profilInfoHasFreitext(p)) >=1) {
				o6 = o6 * compareStrings(profilInfoHasFreitext(profil), profilInfoHasFreitext(p));
				p6 = o6;	
			}
			
			/**
			 * Berechnung der Ähnlichkeit, durch Addieren der prozentualen Anteile der abgeglichenen Attribute/Objekte.
			 * Falls die Ähnlichkeit über 100% oder unter 0% beträgt wird diese auf 100% bzw. 0% limitiert.
			 */
			int aehnlichkeit = (int) (((p1 * x) + (p2 * x) + (p3 * x) + (p4 * x) + (p5 * x) + (p6 * x)) + 0.5);
			if (aehnlichkeit > 100) {
				aehnlichkeit = 100;
			}
			if(aehnlichkeit < 0) {
				aehnlichkeit = 0;
			}
			/**
			 * Zuweisung der Ähnlichkeit in Prozent eines Profils.
			 * Hinzufügen des Profilobjekts der ArrayList<Profil> comparedProfiles (abgeglichene Profile)
			 */

			p.setÄhnlichkeit(aehnlichkeit);
			comparedProfiles.add(p);
		}
			/**
			 * Sortierung der abgeglichenen Profile anhand der Ähnlichkeitswerte. Die Sortierung erfolgt hier absteigend nach Ähnlichkeit.
			 */
		Collections.sort(comparedProfiles, new Comparator<Profil>() {
			public int compare(Profil a, Profil b) {
				return Integer.valueOf(b.getÄhnlichkeit()).compareTo(a.getÄhnlichkeit());
			}
		});
		/**
		 * Rückgabe der abgeglichenen,sortierten Profile in einer ArrayList<Profil>.
		 */
		return comparedProfiles;
	}
	
	/**
	 * Abgleich aller Profile anhand eines Suchprofils
	 * 
	 * @param suchprofil
	 * @return ArrayList<Profil>
	 */
	@Override
	public ArrayList<Profil> getSuchProfilErgebnisse(Suchprofil suchprofil) throws IllegalArgumentException {

		ArrayList<Profil> profile = this.getAllProfils();
		Profil suchprofilowner = this.getProfilByID(suchprofil.getEigenprofilID());
		ArrayList<Kontaktsperre> blockedSuchprofilowner = new ArrayList<>();
		ArrayList<Profil> profilsToRemove = new ArrayList<Profil>();
		/**
		 * Abgleich aller Kontaktsperren. Falls Fremdprofil-ID der Kontaktsperre, der eigenen Profil-ID entspricht, wird diese Kontaktsperre in einer ArrayList gespeichert.
		 * Diese sind Kontaktsperren, die das eigene Profil als gesperrtes Profil enthalten.
		 */	
		for (Kontaktsperre s : this.getAllKontaktsperreEintraege()) {
			if (s.getFremdprofilID() == suchprofilowner.getId()) {
				blockedSuchprofilowner.add(s);
			}
			/**
			 * Besitzer der Kontaktsperren finden udn einer ArrayList hinzufügen. Diese Besitzer sollen nicht in den Suchprofilergebnissen angezeigt werden, 
			 * da diese das eigene Profil gesperrt haben.
			 */
			for (Kontaktsperre t : blockedSuchprofilowner) {
				int epidofkkk = t.getEigenprofilID();
				Profil pp = getProfilByID(epidofkkk);
				profilsToRemove.add(pp);
			}
		}
		/**
		 * Abgleich aller Profile aus der Datenbank
		 */
		for (Profil p : profile) {
			/**
			 * Falls sich ein Profil auf der eigenen Kontaktsperrenliste befindet, wird es der Ergebnisliste dieser Methode entfernt.
			 */
			for (Kontaktsperre k : this.findKontaktsperrenOf(suchprofilowner)) {

				if (k.getFremdprofilID() == p.getId()) {
					profilsToRemove.add(p);
				}
			}
			/**
			 * Entfernen des eigenen Profils aus der Ergebnisliste.
			 */
			if (p.getId() == suchprofilowner.getId()) {
				profilsToRemove.add(p);
			}
			/**
			 * Aufrufen der Methode compare, die Suchprofilattribute mit Profilattributen vergleicht. 
			 * Falls nicht genügend Attribute übereinstimmen, wird dieses Fremdprofil nicht angezeigt in den Suchprofilergebnisse.
			 */
			if (compare(suchprofil, p) == false) {
				profilsToRemove.add(p);
			}
			/**
			 * Abgleich des Geschlechts und der sexuellen Orientierung. Bei keiner Übereinstimmung, wird das Fremdprofil nicht in den Suchprofilergebnissen angezeigt.
			 */
			if (compareSexuelleOrientierung(suchprofilowner, p) == false) {
				profilsToRemove.add(p);
			}
		}
		/**
		 * Entfernen aller Profile, die in der Ergebnisliste nicht angezeigt werden sollen.
		 */
		profile.removeAll(profilsToRemove);
		/**
		 * Berechnung der Ähnlichkeiten dieser Suchprofilergebnisse im Vergleich zum eigenen Profil.
		 */
		ArrayList<Profil> comparedProfiles = this.berechneAehnlichkeitsmass(suchprofilowner, profile);
		return comparedProfiles;
	}

	/**
	 * Gibt true zurück, falls mindestens drei der abgeglichenen Eigenschaften des Profils mit dem Suchprofil übereinstimmen.
	 * 
	 * @param suchprofill
	 * @param profil
	 * @return boolean
	 */
	public boolean compare(Suchprofil suchprofill, Profil profil) {
		int countersp = 0;

		if (suchprofill.getHaarFarbe().equals(profil.getHaarfarbe())) {
			countersp++;
		}
		if (suchprofill.isRaucher() == profil.isRaucher()) {
			countersp++;
		}
		if (suchprofill.getReligion().equals(profil.getReligion())) {
			countersp++;
		}

		if (compareProfilAuswahlInfosWith(suchprofill, profil)) {
			countersp++;
		}
		if (compareProfilFreitextInfosWith(suchprofill, profil)) {
			countersp++;
		}
		if (suchprofill.getKoerpergroesse() <= profil.getKoerpergroesse()) {
			countersp++;
		}
		if (suchprofill.getAlter() <= getAge(profil.getGeburtsdatum())) {
			countersp++;
		}
		if (countersp > 3) {
			return true;
		}

		return false;
	}
	
	/**
	 *  Gibt ture zurück, wenn Suchprofil und Profil mindestens ein gleiches Auswahlobjekt haben.
	 *  
	 * @param suchprofil
	 * @param profil
	 * @return boolean
	 */
	public boolean compareProfilAuswahlInfosWith(Suchprofil suchprofil, Profil profil) {

		ArrayList<Info> infos = suchprofilInfoHasAuswahl(suchprofil);
		ArrayList<Info> inf = profilInfoHasAuswahl(profil);
		if (infos.isEmpty()) {
			return false;
		}
		for (Info i : infos) {

			for (Info a : inf) {
				if (i.getText().equals(a.getText()))
					return true;
			}
		}
		
		return false;
	}
	
	/**
	 *  Gibt true zurück, wenn mindestens ein Freitext gleich ist von Suchprofil und Profil.
	 *  
	 * @param suchprofil
	 * @param profil
	 * @return boolean
	 */
	public boolean compareProfilFreitextInfosWith(Suchprofil suchprofil, Profil profil){
		ArrayList<Info> freitextInfosSuchprofil = suchprofilInfoHasFreitext(suchprofil);
		ArrayList<Info> freitextInfosProfil = profilInfoHasFreitext(profil);
		if (freitextInfosSuchprofil.isEmpty()) {
			return false;
		}
		if(compareStrings(freitextInfosSuchprofil, freitextInfosProfil) > 0){
			return true;
		}
		return false;
	}
	
	/**
	 *  Gibt true zurück, wenn mindestens ein Freitext gleich ist von Profil und Profil.
	 *  
	 * @param suchprofil
	 * @param profil
	 * @return boolean
	 */
	public boolean compareProfilFreitextInfosWith(Profil profil1, Profil profil2){
		ArrayList<Info> freitextInfosProfil1 = profilInfoHasFreitext(profil1);
		ArrayList<Info> freitextInfosProfil2 = profilInfoHasFreitext(profil2);
		
		if(compareStrings(freitextInfosProfil1, freitextInfosProfil2) > 0){
			return true;
		}
		return false;
	}
	
	/**
	 * Gleicht zwei ArrayListen mit Freitexten ab. Für jeden Freitext werden alle Leerzeichen und Satzzeichen entfernt. 
	 * Die Strings werden zusätzlich klein geschrieben um diese vergleichbarer zu machen.
	 * Falls zwei Strings übereinstimmen wird ein Zähler hochgezählt. Bei nicht Übereinstimmung wird der Zähler runtergezählt.
	 * Zurückgegeben wird die Anzahl der übereinstimmenden Freitexte.
	 * 
	 * @param freitexte1
	 * @param freitexte2
	 * @return int
	 */
	
	public int compareStrings(ArrayList<Info> freitexte1, ArrayList<Info> freitexte2){
		int counterEqualStrings = 0;
		for(Info i: freitexte1){
			for(Info p: freitexte2){
				String freiText1 = i.getText();
				String freiText1OhneLeerzeichen = freiText1.replaceAll(" ","");
				String freiText1OhneSatzzeichen = freiText1OhneLeerzeichen.replaceAll("\\p{Punct}","");
				String freiText1KleinBuchstaben = freiText1OhneSatzzeichen.toLowerCase();
				String freiText2 = p.getText();
				String freiText2OhneLeerzeichen = freiText2.replaceAll(" ","");
				String freiText2OhneSatzzeichen = freiText2OhneLeerzeichen.replaceAll("\\p{Punct}","");
				String freiText2KleinBuchstaben = freiText2OhneSatzzeichen.toLowerCase();
				if((i.getEigenschaftId() == p.getEigenschaftId()) && (freiText1KleinBuchstaben.equals(freiText2KleinBuchstaben))){
					
					counterEqualStrings++;
				}
				if((i.getEigenschaftId() == p.getEigenschaftId()) && (freiText1KleinBuchstaben.equals(freiText2KleinBuchstaben) == false)){
				
					counterEqualStrings--;
				}
			}
		}
		
		return counterEqualStrings;
	}
	
	/**
	 * Gibt alle Infoobjekte eines Suchprofils zurück, die eine Auswahl enthalten.
	 * 
	 * @param suchprofil
	 * @return ArrayList<Info>
	 */
	public ArrayList<Info> suchprofilInfoHasAuswahl(Suchprofil suchprofil) {

		ArrayList<Info> auswahlInfos = new ArrayList<Info>();

		for (Info i : getInfoOfSuchprofil(suchprofil.getId())) {
			Eigenschaft eigenschaft = getEigenschaftByID(i.getEigenschaftId());
			if (eigenschaft.getIs_a().equals("auswahl")) {
				auswahlInfos.add(i);
			}

		}

		return auswahlInfos;
	}
	
	/**
	 * Gibt alle Infoobjekte eines Suchprofils zurück, die einen Freitext enthalten.
	 * 
	 * @param suchprofil
	 * @return ArrayList<Info>
	 */
	public ArrayList<Info> suchprofilInfoHasFreitext(Suchprofil suchprofil) {

		ArrayList<Info> freitextInfos = new ArrayList<Info>();

		for (Info i : getInfoOfSuchprofil(suchprofil.getId())) {

			Eigenschaft eigenschaft = getEigenschaftByID(i.getEigenschaftId());
			if (eigenschaft.getIs_a().equals("freitext")) {
				freitextInfos.add(i);
			}
		}
		return freitextInfos;
	}

	/**
	 * Gibt alle Infoobjekte eines Profils zurück, die eine Auswahl enthalten.
	 * 
	 * @param profil
	 * @return ArrayList<Info>
	 */
	public ArrayList<Info> profilInfoHasAuswahl(Profil profil) {

		ArrayList<Info> auswahlInfos = new ArrayList<Info>();

		for (Info i : findAllInfosOfProfil(profil)) {

			Eigenschaft eigenschaft = getEigenschaftByID(i.getEigenschaftId());
			if (eigenschaft.getIs_a().equals("auswahl")) {
				auswahlInfos.add(i);
			}
		}
		return auswahlInfos;
	}
	
	/**
	 * Gibt alle Infoobjekte eines Profils zurück, die einen Freitext enthalten.
	 * 
	 * @param profil
	 * @return ArrayList<Info>
	 */
	public ArrayList<Info> profilInfoHasFreitext(Profil profil) {

		ArrayList<Info> freitextInfos = new ArrayList<Info>();

		for (Info i : findAllInfosOfProfil(profil)) {

			Eigenschaft eigenschaft = getEigenschaftByID(i.getEigenschaftId());
			if (eigenschaft.getIs_a().equals("freitext")) {
				freitextInfos.add(i);
			}
		}
		return freitextInfos;
	}
	
	/**
	 * Vergleicht alle möglichen Variationen die eine Übereinstimmung des Geschlechts und der sexuellen Orientierung zwischen zwei Profilen garantieren.
	 * 
	 * @param profil
	 * @param fremdprofil
	 * @return boolean
	 */
	public boolean compareSexuelleOrientierung(Profil profil, Profil fremdprofil) {

		if ((((profil.getGeschlecht().equals("Mann")) && (profil.getSucheNach().equals("Frauen")))
				&& ((fremdprofil.getGeschlecht().equals("Frau")) && (fremdprofil.getSucheNach().equals("Männer"))))	
				|| (((profil.getGeschlecht().equals("Mann")) && (profil.getSucheNach().equals("Männer")))
						&& ((fremdprofil.getGeschlecht().equals("Mann"))
								&& (fremdprofil.getSucheNach().equals("Männer"))))
				|| (((profil.getGeschlecht().equals("Frau")) && (profil.getSucheNach().equals("Männer")))
						&& ((fremdprofil.getGeschlecht().equals("Mann"))
								&& (fremdprofil.getSucheNach().equals("Frauen"))))
				|| (((profil.getGeschlecht().equals("Frau")) && (profil.getSucheNach().equals("Frauen")))
						&& ((fremdprofil.getGeschlecht().equals("Frau"))
								&& (fremdprofil.getSucheNach().equals("Frauen"))))) {
			return true;
		} else
			return false;
	}
	
	/**
	 * Gibt nicht gesehene Partnervorschläge zurück. Dabei werden bereits gesehene Profile herausgefiltert, Profile die sich auf der eigenen Kontaktsperrliste befinden,
	 * Profile, die das eigene Profil gesperrt haben und Profile, die der sexuellen Orientierung nicht übereinstimmen.
	 * 
	 * @param profil
	 * @return ArrayList<Profil>
	 */
	@Override
	public ArrayList<Profil> getNotSeenPartnervorschläge(Profil profil) throws IllegalArgumentException {

		ArrayList<Profil> profile = getAllProfils();

		ArrayList<Kontaktsperre> blockedProfilowner = new ArrayList<Kontaktsperre>();
		ArrayList<Profil> profilBlockedProfilowner = new ArrayList<Profil>();

		for (Kontaktsperre s : this.getAllKontaktsperreEintraege()) {
			if (s.getFremdprofilID() == profil.getId()) {
				blockedProfilowner.add(s);
			}
			for (Kontaktsperre t : blockedProfilowner) {
				int epidofkkk = t.getEigenprofilID();
				Profil pp = getProfilByID(epidofkkk);
				profilBlockedProfilowner.add(pp);
			}
		}
		ArrayList<Profil> profilesToRemove = new ArrayList<Profil>();
		
		for (Profil p : profile) {

			for (Besuch b : this.findBesucheOf(profil)) {
				if (b.getFremdprofilID() == p.getId()) {
					profilesToRemove.add(p);
				}
			}

			for (Kontaktsperre k : this.findKontaktsperrenOf(profil)) {

				if (k.getFremdprofilID() == p.getId()) {
					profilesToRemove.add(p);
				}
			}

			if (profilBlockedProfilowner.contains(p)) {
				profilesToRemove.add(p);
			}

			if (compareSexuelleOrientierung(profil, p) == false) {
				profilesToRemove.add(p);
			}

		}
		profile.remove(profil);
		profile.removeAll(profilesToRemove);
		ArrayList<Profil> comparedProfiles = this.berechneAehnlichkeitsmass(profil, profile);
		return comparedProfiles;
	}

	/**
	 * Gibt True zurück, wenn zwei Profile keine Infoobjekte aufweisen.
	 * 
	 * @param profil
	 * @param fremdprofil
	 * @return boolean
	 */
	public boolean compareInfosIsEmpty(Profil profil, Profil fremdprofil){
	
	if ((findAllInfosOfProfil(profil).isEmpty()) && (findAllInfosOfProfil(fremdprofil).isEmpty())) {
		return true;
	}
	return false;
	}

	/**
	 * Gibt die Anzahl der gleichen Auswahlobjekte zweier Profile zurück.
	 * 
	 * @param profil
	 * @param fremdprofil
	 * @return int
	 */
	public int compareInfos(Profil profil, Profil fremdprofil) {

		int counter = 0;

		for (Info i : profilInfoHasAuswahl(profil)) {

			for (Info o : profilInfoHasAuswahl(fremdprofil)) {

				if ((i.getEigenschaftId() == o.getEigenschaftId()) && (i.getText().equals(o.getText()))) {
					counter++;

				}
				if ((i.getEigenschaftId() == o.getEigenschaftId()) && (i.getText().equals(o.getText()) == false)) {
					counter--;

				}
				
			}
		}
	
		return counter;
	}

	/**
	 * Erstellt ein Info Objekt, welches einem Profil und einer Eigenschaft zugewiesen wird.
	 * Diese Methode wird auch zum Updaten einer Info benutzt. In dieser Methode wird erst überprüft,
	 * ob zu der übergebenen Eigenschaft bereits eine Info besteht. Wenn ja, wird diese Info
	 * geupdated, wenn nicht, wir eine neue Info erstellt
	 * @param profil, text, eigenschaft
	 * @return info (erstellt oder geupdatet)
	 */
	@Override
	public Info createInfo(Profil profil, String text, Eigenschaft eigenschaft) throws IllegalArgumentException {

		ArrayList<Info> infos = this.findInfoOf(profil);
		ArrayList<Info> del = new ArrayList<Info>();

		/**
		 * Erstellt ein Info Objekt, welches in die Datenbank geschrieben wird
		 */
		Info info = new Info();
		info.setepId(profil.getId());
		info.setText(text);
		info.setEigenschaftId(eigenschaft.getId());

		/**
		 * Prüft, ob für diese Eigenschaft bereits ein Info-Objekt für diesen User angelegt wurde
		 */
		for (Info i : infos) {
			/**
			 *  Wenn bereits eine Info für diese Eigenschaft besteht, wird sie aktualisiert.
			 */
			if (i.getEigenschaftId() == info.getEigenschaftId()) {
				info.setId(i.getId());
				this.updateInfo(info);
			} 
			else {
				del.add(i);
			}
		}
		/**
		 *  Entfernt alle bereits vorhandenen Objekte, wenn die Liste leer ist.
		 *  Wenn noch kein neuer Eintrag vorhanden ist, wird die Info angelegt.
		 */
		
		infos.removeAll(del);
		if (infos.isEmpty()) {
			this.iMapper.insertInfo(info);
		}

		return info;

	}

	/**
	 * Erstellt ein Info Objekt, welches einem Suchprofil und einer Eigenschaft zugewiesen wird.
	 * Diese Methode wird auch zum Updaten einer Info benutzt. In dieser Methode wird erst überprüft,
	 * ob zu der übergebenen Eigenschaft bereits eine Info besteht. Wenn ja, wird diese Info
	 * geupdated, wenn nicht, wir eine neue Info erstellt
	 * @param suchprofil, text, eigenschaft
	 * @return info (erstellt oder geupdatet)
	 */
	public Info createInfo(Suchprofil suchprofil, String text, Eigenschaft eigenschaft)
			throws IllegalArgumentException {

		ArrayList<Info> infos = getInfoOfSuchprofil(suchprofil.getId());
		ArrayList<Info> del = new ArrayList<Info>();

		/**
		 * Erstellt ein Info Objekt, welches in die Datenbank geschrieben wird
		 */
		Info info = new Info();
		info.setSuchprofilId(suchprofil.getId());
		info.setText(text);
		info.setEigenschaftId(eigenschaft.getId());

		/**
		 * Prüft, ob für diese Eigenschaft bereits ein Info-Objekt für diesen User angelegt wurde
		 */
		for (Info i : infos) {
			/**
			 *  Wenn bereits eine Info für diese Eigenschaft besteht, wird sie aktualisiert.
			 */
			if (i.getEigenschaftId() == info.getEigenschaftId()) {
				info.setId(i.getId());
				this.updateInfo(info);
			} else {
				del.add(i);
			}
		}
		/**
		 *  Entfernt alle bereits vorhandenen Objekte, wenn die Liste leer ist.
		 *  Wenn noch kein neuer Eintrag vorhanden ist, wird die Info angelegt.
		 */
		infos.removeAll(del);
		if (infos.isEmpty()) {
			this.iMapper.insertInfo(info);
		}

		return info;
	}

	/**
	 * Gibt alle vorhandenen Infos zurück
	 * @return ArrayList mit Infos
	 */
	@Override
	public ArrayList<Info> getAllInfos() throws IllegalArgumentException {
		return this.iMapper.findAll();

	}

	/**
	 * Liefert eine Info anhand ihrer ID (Primärschlüssel) zurück
	 * @param id
	 * @return info
	 */
	@Override
	public Info getInfoByID(int id) throws IllegalArgumentException {
		return this.iMapper.findByKey(id);
	}

	/**
	 * Updated eine übergebene Info. Wird in der createInfo Methode aufgerufen
	 * @param info
	 */
	@Override
	public void updateInfo(Info info) throws IllegalArgumentException {

		this.iMapper.update(info);

	}

	/**
	 * Löscht eine Info aus der Datenbank
	 * @param info
	 */
	@Override
	public void deleteInfo(Info info) throws IllegalArgumentException {

		this.iMapper.deleteInfo(info);

	}

	/**
	 * Liefert alle vorhandenen Eigenschaften zurück
	 * @return ArrayList mit Eigenschaften
	 */
	@Override
	public ArrayList<Eigenschaft> getAllEigenschaften() throws IllegalArgumentException {

		return this.eiMapper.findAll();

	}

	/**
	 * Liefert eine Eigenschaft anhand ihrer ID (Primärschlüssel) zurück
	 * @param id
	 * @return Eigenschaft
	 */
	@Override
	public Eigenschaft getEigenschaftByID(int id) throws IllegalArgumentException {

		return this.eiMapper.findByKey(id);
	}

	/**
	 * Aktualisiert eine übergebene Eigenschaft
	 * @param eigenschaft
	 */
	@Override
	public void updateEigenschaft(Eigenschaft eigenschaft) throws IllegalArgumentException {

		this.eiMapper.updateEigenschaft(eigenschaft);

	}

	/**
	 * Löscht eine Info, welche einer bestimmten Eigenschaft und einem bestimmten Profil zugeordnet ist
	 * @param eigenschaft, p (Profil)
	 */
	@Override
	public void deleteInfoOfEigenschaft(Eigenschaft eigenschaft, Profil p) throws IllegalArgumentException {

		ArrayList<Info> userInfos = this.findInfoOf(p);

		for (Info i : userInfos) {
			if (i.getEigenschaftId() == eigenschaft.getId()) {
				this.iMapper.deleteInfo(i);
			}
		}

	}

	/**
	 * Löscht eine Info, welche einer bestimmten Eigenschaft und einem bestimmten Suchprofil zugeordnet ist
	 * @param eigenschaft, sp (Suchprofil)
	 */
	@Override
	public void deleteInfoOfEigenschaft(Eigenschaft eigenschaft, Suchprofil sp) throws IllegalArgumentException {

		/**
		 * Alle Info-Objekte zu einen Suchprofil werden in einem Array
		 * gespeichert.
		 */
		ArrayList<Info> suchprofilInfo = this.findInfoOf(sp);

		/**
		 * ForEach Schleife geht durch alle Infos durch und löscht sie zu der
		 * angegebenen Eigenschaft
		 */
		for (Info i : suchprofilInfo) {
			if (i.getEigenschaftId() == eigenschaft.getId()) {
				this.iMapper.deleteInfo(i);
			}
		}

	}

	/**
	 * Liefert alle vorhandenen Auswahlen zurück
	 * @return ArrayList mit Auswahlen
	 */
	@Override
	public ArrayList<Auswahl> getAuswahl() throws IllegalArgumentException {

		return this.aMapper.findAll();
	}

	/**
	 * Liefert alle Kontaktsperren eines übergebenen Profils zurück
	 * @param profilowner
	 * @return ArrayList mit Kontaktsperren
	 */
	@Override
	public ArrayList<Kontaktsperre> findKontaktsperrenOf(Profil profilowner) throws IllegalArgumentException {

		return this.kMapper.findKontaktsperrenOf(profilowner);
	}

	/**
	 * Liefert alle Merkzettel eines übergebenen Profils zurück
	 * @param profilowner
	 * @return ArrayList mit Merkzetteln
	 */
	@Override
	public ArrayList<Merkzettel> findMerkzettelnOf(Profil profilowner) throws IllegalArgumentException {

		return this.mMapper.findMerkzettelnOf(profilowner);

	}

	/**
	 * Liefert alle Suchprofile eines übergebenen Profils zurück
	 * @param profilowner
	 * @return ArrayList mit Suchprofilen
	 */
	@Override
	public ArrayList<Suchprofil> findSuchprofileOf(Profil profilowner) throws IllegalArgumentException {

		return this.sMapper.findSuchprofileOf(profilowner);

	}

	/**
	 * Liefert alle Infos eines übergebenen Profils zurück
	 * @param profilowner
	 * @return ArrayList mit Infos
	 */
	@Override
	public ArrayList<Info> findInfoOf(Profil profilowner) throws IllegalArgumentException {

		return this.iMapper.findInfoOf(profilowner);

	}

	/**
	 * Liefert alle Infos eines übergebenen Suchprofils zurück
	 * @param suchprofil
	 * @return ArrayList mit Infos
	 */
	@Override
	public ArrayList<Info> findInfoOf(Suchprofil suchprofil) throws IllegalArgumentException {

		return this.iMapper.findInfoOfSuchprofil(suchprofil.getId());

	}

	/**
	 * Liefter alle Infos einer übergebenen Eigenschaft zurück
	 * @param eigenschaft
	 * @return ArrayList mit Infos
	 */
	@Override
	public Info findInfoOfEigenschaft(Eigenschaft eigenschaft) throws IllegalArgumentException {

		return this.iMapper.findEigenschaftsInfosOf(eigenschaft);

	}

	/**
	 * Erstellt ein Besuch von Profil source zu Profil target. Prüft vorher ab, ob das Profil bereits
	 * besucht wurde und legt ggf. keinen neuen Eintrag an
	 * @param source, target
	 */
	@Override
	public void createBesuch(Profil source, Profil target) throws IllegalArgumentException {

		ArrayList<Besuch> besuche = this.findBesucheOf(source);

		for (Besuch besuch : besuche) {
			if (besuch.getFremdprofilID() == target.getId()) {
				return;
			}
		}

		Besuch b = new Besuch();
		b.setEigenprofilID(source.getId());
		b.setFremdprofilID(target.getId());

		this.bMapper.insertBesuch(b);

	}

	/**
	 * Löscht einen übergebenen Besuch aus der Datenbank
	 * @param besuch
	 */
	@Override
	public void deleteBesuch(Besuch besuch) throws IllegalArgumentException {

		this.bMapper.deleteBesuch(besuch);

	}

	/**
	 * Lieftert alle vorhandenen Besuche zurück
	 * @return ArrayList mit Besuchen
	 */
	@Override
	public ArrayList<Besuch> findAllBesuche() throws IllegalArgumentException {

		return this.bMapper.findAll();
	}

	/**
	 * Liefert einen Besuch anhand der ID (Primärschlüssel) zurück
	 * @param id
	 * @return Besuch
	 */
	@Override
	public Besuch findBesuchByKey(int id) throws IllegalArgumentException {

		return this.bMapper.findByKey(id);
	}

	/**
	 * Liefert alle Besuche eines bestimmten Profils zurück
	 * @param profilowner
	 * @return ArrayList mit Besuchen
	 */
	@Override
	public ArrayList<Besuch> findBesucheOf(Profil profilowner) throws IllegalArgumentException {

		return this.bMapper.findByEigenprofil(profilowner);
	}

	/**
	 * Liefert eine Auswahl anhand ihres Titels zurück
	 * @param auswahl
	 * @return Auswahl
	 */
	@Override
	public Auswahl findAuswahlByTitle(Auswahl auswahl) throws IllegalArgumentException {

		return this.aMapper.findAuswahlByTitle(auswahl);
	}

	/**
	 * Gibt alle Eigenschaften eines Profils zurück.
	 * @param profil
	 * @return ArrayList<Eigenschaft>
	 */
	@Override
	public ArrayList<Eigenschaft> getAllEigenschaftenOf(Profil profil) throws IllegalArgumentException {

		return this.eiMapper.getAllEigenschaftenOf(profil);
	}

}