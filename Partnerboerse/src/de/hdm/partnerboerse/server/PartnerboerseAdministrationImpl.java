package de.hdm.partnerboerse.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.partnerboerse.server.db.AuswahlMapper;
import de.hdm.partnerboerse.server.db.BesuchMapper;
import de.hdm.partnerboerse.server.db.EigenschaftMapper;
import de.hdm.partnerboerse.server.db.FreitextMapper;
import de.hdm.partnerboerse.server.db.InfoMapper;
import de.hdm.partnerboerse.server.db.KontaktsperreMapper;
import de.hdm.partnerboerse.server.db.MerkzettelMapper;
import de.hdm.partnerboerse.server.db.ProfilMapper;
import de.hdm.partnerboerse.server.db.SuchprofilMapper;
import de.hdm.partnerboerse.shared.PartnerboerseAdministration;
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
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class PartnerboerseAdministrationImpl extends RemoteServiceServlet implements PartnerboerseAdministration {

	private AuswahlMapper aMapper = null;

	private EigenschaftMapper eiMapper = null;

	private FreitextMapper fMapper = null;

	private InfoMapper iMapper = null;

	private KontaktsperreMapper kMapper = null;

	private MerkzettelMapper mMapper = null;

	private ProfilMapper pMapper = null;

	private SuchprofilMapper sMapper = null;

	private BesuchMapper bMapper = null;
	private int gewichtungHaarfarbe;
	private int gewichtungReligion;

	private int gewichtungAlter;
	private int gewichtungRaucher;
	private int gewichtungAuswahl;

	public void setGewichtungen(int gwHaarfarbe, int gwReligion, int gwAlter, int gwRaucher, int gwAuswahl) {

		this.gewichtungHaarfarbe = gwHaarfarbe;
		this.gewichtungReligion = gwReligion;

		this.gewichtungRaucher = gwRaucher;
		this.gewichtungAuswahl = gwAuswahl;
	}

	public PartnerboerseAdministrationImpl() throws IllegalArgumentException {

	}

	@Override
	public void init() throws IllegalArgumentException {

		this.aMapper = AuswahlMapper.auswahlMapper();
		this.eiMapper = EigenschaftMapper.eigenschaftMapper();
		this.fMapper = FreitextMapper.freitextMapper();
		this.iMapper = InfoMapper.infoMapper();
		this.kMapper = KontaktsperreMapper.kontaktsperreMapper();
		this.mMapper = MerkzettelMapper.merkzettelMapper();
		this.pMapper = ProfilMapper.profilMapper();
		this.sMapper = SuchprofilMapper.suchprofilMapper();
		this.bMapper = BesuchMapper.besuchMapper();

	}

	public int getAge(Date date) {

		GregorianCalendar birthday = new GregorianCalendar();
		birthday.setTime(date);

		GregorianCalendar today = new GregorianCalendar();

		int age = today.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);

		if (today.get(Calendar.MONTH) <= birthday.get(Calendar.MONTH)) {
			if (today.get(Calendar.DATE) < birthday.get(Calendar.DATE)) {
				age -= 1;
			}
		}

		if (age < 0)
			throw new IllegalArgumentException("invalid age: " + age);

		return age;
	}

	@Override
	public Profil createProfil(Profil p) throws IllegalArgumentException {
		// System.out.println("test02"+p.toString());
		return this.pMapper.insert(p);
	}

	@Override
	public void updateProfil(Profil p) throws IllegalArgumentException {
		pMapper.update(p);
	}

	@Override
	public void deleteProfil(Profil p) throws IllegalArgumentException {

		/**
		 * Hier muss man die ganzen Abhngigkeiten abchecken, bevor man ein
		 * Profil l�scht. z.B. muss man erst alle Merkzettel Eintr�ge l�schen,
		 * in denen das Profil vorkommt. Erst dann kann man ein Profil l�schen
		 * 
		 * Abh�ngigkeiten von Profil:
		 * 
		 * Merkzettel Kontaktsperre Visit Suchprofil Info
		 * 
		 */

		ArrayList<Merkzettel> merkzettel = this.findMerkzettelnOf(p);
		ArrayList<Kontaktsperre> kontaktsperren = this.findKontaktsperrenOf(p);
		ArrayList<Besuch> besuche = this.findBesucheOf(p);
		ArrayList<Suchprofil> suchprofile = this.findSuchprofileOf(p);
		ArrayList<Info> infos = this.findInfoOf(p);

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

	@Override
	public ArrayList<Profil> getProfilByName(String name) throws IllegalArgumentException {

		return this.pMapper.findProfileByName(name);

	}

	@Override
	public Profil getProfilByID(int id) throws IllegalArgumentException {

		return this.pMapper.findProfilByKey(id);

	}

	@Override
	public Profil getProfilByEmail(String email) throws IllegalArgumentException {
		return this.pMapper.findProfilByEmail(email);
	}

	@Override
	public ArrayList<Profil> getProfileForMerkzettel(Profil eigenProfil) throws IllegalArgumentException {

		return this.pMapper.findProfileForMerkliste(eigenProfil);

	}

	@Override
	public ArrayList<Profil> getProfileForKontaktsperre(Profil eigenProfil) throws IllegalArgumentException {

		return this.pMapper.findProfileForKontaktsperre(eigenProfil);

	}

	@Override
	public ArrayList<Profil> getAllProfils() throws IllegalArgumentException {

		return this.pMapper.findAllProfiles();

	}

	@Override
	public void createMerkzettelEintrag(Profil source, Profil target) throws IllegalArgumentException {

		Merkzettel m = new Merkzettel();
		m.setEigenprofilID(source.getId());
		m.setFremdprofilID(target.getId());

		this.mMapper.insertMerkzettelEintrag(m);
	}

	@Override
	public ArrayList<Merkzettel> getAllMerkzettelEintraege() throws IllegalArgumentException {

		return this.mMapper.findAll();
	}

	@Override
	public Merkzettel getMerkzettelEintraegeByID(int ID) throws IllegalArgumentException {

		return this.mMapper.findByKey(ID);

	}

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

	@Override
	public void createKontaksperreEintrag(Profil source, Profil target) throws IllegalArgumentException {

		Kontaktsperre k = new Kontaktsperre();
		k.setEigenprofilID(source.getId());
		k.setFremdprofilID(target.getId());

		this.kMapper.insertKontaktsperreEintrag(k);
	}

	@Override
	public ArrayList<Kontaktsperre> getAllKontaktsperreEintraege() throws IllegalArgumentException {

		return this.kMapper.findAll();
	}

	@Override
	public Kontaktsperre getKontaktsperreEintragByID(int id) throws IllegalArgumentException {

		return this.kMapper.findByKey(id);

	}

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

	@Override
	public void createSuchprofil(Profil source, String titel, String haarfarbe, float kgr, boolean raucher,
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

		this.sMapper.insertSuchprofil(s);

	}

	@Override
	public ArrayList<Suchprofil> getAllSuchprofile() throws IllegalArgumentException {

		return this.sMapper.findAll();
	}

	@Override
	public Suchprofil getSuchprofilByID(int id) throws IllegalArgumentException {

		return this.sMapper.findByKey(id);
	}

	@Override
	public Suchprofil getSuchprofilByTitle(String title) throws IllegalArgumentException {

		return this.sMapper.findSuchprofilByTitle(title);

	}

	@Override
	public void updateSuchprofil(Suchprofil suchprofil) throws IllegalArgumentException {
		this.sMapper.updateSuchprofil(suchprofil);

	}

	@Override
	public void deleteSuchprofil(Suchprofil suchprofil) throws IllegalArgumentException {

		this.sMapper.deleteSuchprofil(suchprofil);
	}

	public ArrayList<Integer> findAllAuswahlIDsOfProfil(Profil profil) {

		return null;
	}

	public ArrayList<String> findAllFreitexteOfProfil(Profil profil) {

		return null;
	}

	@Override
	public ArrayList<Profil> berechneAehnlichkeitsmass(Profil source, Suchprofil suchprofil)
			throws IllegalArgumentException {

		// Alle AuswahlIDs und Freitexte des Sourceprofils die wir abgleichen
		// müssen

		ArrayList<Integer> allAuswahlIDsOfSourceProfil = findAllAuswahlIDsOfProfil(source);
		ArrayList<String> allFreitexteOfSourceProfil = findAllFreitexteOfProfil(source);

		// Die Schleife geht alle Suchprofilergebnisse durch (Profile) und
		// vergleicht Profileigenschaften mit den eigenen Profileigenschaften
		// ab.
		// Die Gewichtungen können durch die oben implementierte MEthode
		// "setGEwichtungen" belieb gesetzt werden und werden am Ende der
		// schleife wieder prozentual umgerechnet
		// (alle Gewichtungen zusammen ergeben 100% egal welche Zahl man gesetzt
		// hat)
		// Bsp. Haarfarbegewichtung 10, Religiongewichtung 20,
		// Koerpergroessegewichtung 0 , Altergewichtung 30, Rauchergewichtung
		// 50,
		// Summe = 110,
		// Ähnlichkeit in Prozent =
		// 10*(100/110))+(20*(100/110))+(0*(100/110))+(30*(100/110))+(50*(100/110)
		// =9.090909% + 18.181818% + 0% + 27.272728% + 45.454548% = 100%
		// Somit kann man wenn man die Gewichtung 0 setzt, trotzdem 100%
		// erreichen weil das Attribut nicht mit einberechnet wird.

		ArrayList<Float> ähnlichkeitInProzent = new ArrayList<>();
		float o1 = this.gewichtungHaarfarbe;
		float o2 = this.gewichtungReligion;
		float o3 = this.gewichtungAlter;

		float o4 = this.gewichtungRaucher;
		float o5 = this.gewichtungAuswahl;

		float summe = o1 + o2 + o3 + o4 + o5;
		float x = 100 / summe;

		for (Profil p : getSuchProfilErgebnisse(suchprofil)) {
			float p1 = 0;
			float p2 = 0;
			float p3 = 0;
			float p4 = 0;
			float p5 = 0;

			if (p.getHaarfarbe() == source.getHaarfarbe()) {
				p1 = o1;
			} else if (p.getReligion() == source.getReligion()) {
				p2 = o2;
			} else if (getAge(p.getGeburtsdatum()) == getAge(source.getGeburtsdatum())) {
				p3 = o3;

			}

			else if (p.isRaucher() == source.isRaucher()) {
				p4 = o4;
			} else if (compareAuswahlen(source, p) == true) {
				p5 = o5;
			}

			ähnlichkeitInProzent.add((p1 * x) + (p2 * x) + (p3 * x) + (p4 * x) + (p5 * x));
		}

		// TODO: Alle Infos von allen Suchprofilergebnissen finden zum Abgleich.
		// TODO: Abgleich AuswahlIDs und Freitexte vom eigenen Profil mit
		// Suchprofilergebnissen.
		// Hinweis: sexuelle Orientierung muss ein Pflichtattribut beim
		// Profilerstellen sein, sonst kann man nicht rausfiltern ob Männer oder
		// Frauen angezeigt werden sollen

		return null;
	}

	@Override
	public ArrayList<Profil> getSuchProfilErgebnisse(Suchprofil suchprofil) throws IllegalArgumentException {

		ArrayList<Profil> profile = getAllProfils();
		Profil suchprofilowner = getProfilByID(suchprofil.getEigenprofilID());
		ArrayList<Kontaktsperre> kontaktsperrenofsuchprofilowner = findKontaktsperrenOf(suchprofilowner);
		ArrayList<Integer> fpids = new ArrayList<>();
		ArrayList<Kontaktsperre> allKontaktsperren = getAllKontaktsperreEintraege();
		ArrayList<Kontaktsperre> blockedSuchprofilowner = new ArrayList<>();
		ArrayList<Profil> profilBlockedSuchprofilowner = new ArrayList<>();
		// Wenn die Fremdprofil-ID einer Kontaktsperre (gesperrte Profil ID) mit
		// der eigenen Profil-ID des "Suchprofilbesitzers"
		// übereinstimmt, wird diese Kontaktsperre zu blockedSuchprofilowner
		// hinzugefügt.( Alle Kontaktsperren die das eigene Profil geblockt
		// haben)
		for (Kontaktsperre s : allKontaktsperren) {
			if (s.getFremdprofilID() == suchprofilowner.getId()) {
				blockedSuchprofilowner.add(s);
			}
			// Besitzer dieser Kontaktsperren finden und einer Arraylist
			// (profilBlockedSuchprofilowner) hinzufügen
			for (Kontaktsperre t : blockedSuchprofilowner) {
				int epidofkkk = t.getEigenprofilID();
				Profil pp = getProfilByID(epidofkkk);
				profilBlockedSuchprofilowner.add(pp);
			}
		}
		for (Kontaktsperre k : kontaktsperrenofsuchprofilowner) {
			int fpid = k.getFremdprofilID();
			fpids.add(fpid);
		}

		for (Profil p : profile) {
			int id = p.getId();

			if (fpids.contains(id)) {
				profile.remove(p);
			}
			// Wenn die Arraylist mit Profilen,( Profilbesitzer die einen selbst
			// geblockt haben), ein Profil der Liste mit allen Profilen
			// enthält(Abgleich)
			// so wird dieses Profil aus der Liste aller Profile gelöscht. Somit
			// sind Profile aus der Liste, die das eigene Profil auf der
			// Kontaaktsperrliste hatten.
			else if (profilBlockedSuchprofilowner.contains(p)) {
				profile.remove(p);
			}

			else if (id == suchprofilowner.getId()) {
				profile.remove(p);
			} else if (compare(suchprofil, p) == false) {
				profile.remove(p);
			} else if (compareSexuelleOrientierung(suchprofilowner, p) == false) {
				profile.remove(p);
			}
		}
		return profile;
	}

	/**
	 * Gibt TRUE zurück, wenn ein Profil GENAU mit einem Suchprofil
	 * übereinstimmt
	 * 
	 * @param suchprofill
	 * @param profil
	 * @return
	 */
	public boolean compare(Suchprofil suchprofill, Profil profil) {

		if ((suchprofill.getHaarFarbe() == profil.getHaarfarbe()) &&

				(suchprofill.getKoerpergroesse() == profil.getKoerpergroesse()) &&

				(suchprofill.isRaucher() == profil.isRaucher()) &&

				(suchprofill.getAlter() == getAge(profil.getGeburtsdatum())) &&

				(suchprofill.getReligion() == profil.getReligion()))
		// TODO: welche asuwahlen sind in eine mSuchprofil vergleichbar? Welche
		// Freitexte?
		{
			return true;
		} else
			return false;

	}

	public boolean compareEigenprofil(Profil profil, Profil fremdprofil) {
		if ((profil.getHaarfarbe() == fremdprofil.getHaarfarbe()) && (profil.isRaucher() == fremdprofil.isRaucher())
				&& (getAge(profil.getGeburtsdatum()) == getAge(fremdprofil.getGeburtsdatum()))
				&& (profil.getReligion() == fremdprofil.getReligion())) {
			return true;

		} else
			return false;
	}

	public boolean compareSexuelleOrientierung(Profil profil, Profil fremdprofil) {
		if (((isMale(profil) && isHetero(profil)) && (isFemale(fremdprofil) && isHetero(fremdprofil)))
				|| ((isMale(profil) && isHomo(profil)) && (isMale(fremdprofil) && isHomo(fremdprofil)))
				|| ((isFemale(profil) && isHetero(profil)) && (isMale(fremdprofil) && isHetero(fremdprofil)))
				|| ((isFemale(profil) && isHomo(profil)) && (isFemale(fremdprofil) && isHomo(fremdprofil)))) {
			return true;
		} else
			return false;
	}

	// TODO: ismale,isfemale,ishetero,ishomo implementieren
	public boolean isMale(Profil profil) {
		return true;
	}

	public boolean isFemale(Profil profil) {
		return true;
	}

	public boolean isHetero(Profil profil) {
		return true;
	}

	public boolean isHomo(Profil profil) {
		return true;
	}

	@Override
	public ArrayList<Profil> getNotSeenPartnervorschläge(Profil profil) throws IllegalArgumentException {

		ArrayList<Profil> profile = getAllProfils();

		ArrayList<Besuch> visitsOfProfilowner = findBesucheOf(profil);
		ArrayList<Integer> visitedProfilids = new ArrayList<>();

		for (Besuch b : visitsOfProfilowner) {
			int visitid = b.getFremdprofilID();
			visitedProfilids.add(visitid);
		}
		for (Profil p : profile) {
			int id = p.getId();

			if (visitedProfilids.contains(id)) {
				profile.remove(p);
			} else if (compareEigenprofil(profil, p) == false) {
				profile.remove(p);
			} else if (compareSexuelleOrientierung(profil, p) == false) {
				profile.remove(p);
			} else if (compareAuswahlen(profil, p) == false) {
				profile.remove(p);
			}
		}
		return profile;
	}

	public boolean compareAuswahlen(Profil profil, Profil fremdprofil) {

		for (Integer z : findAllAuswahlIDsOfProfil(profil)) {
			if (findAllAuswahlIDsOfProfil(fremdprofil).contains(z)) {
				return true;
			} else
				return false;
		}
		return false;
	}

	@Override
	public Info createInfoForFreitext(Info info, Freitext freitext) throws IllegalArgumentException {

		return null;

	}

	@Override
	public Info createInfoForAuswahl(Info info, Auswahl auswahl) throws IllegalArgumentException {

		

		return this.iMapper.insertInfo(info);
	}

	@Override
	public ArrayList<Info> getAllInfos() throws IllegalArgumentException {
		return this.iMapper.findAll();

	}

	@Override
	public Info getInfoByID(int id) throws IllegalArgumentException {
		return this.iMapper.findByKey(id);
	}

	public Info getAllInfosOf(Profil p) {

		/**
		 * TODO: Methode um alle Infos eines Profils zurückzubekommen
		 * 
		 * Durch diese Methode erhält man: Info, Eigenschaft, Freitext/Auswahl,
		 * Element die eine Person angegeben hat.
		 * 
		 * Brauchen wir für die Ähnlichkeitsmaß-Berechnung
		 */

		return null;
	}

	@Override
	public void updateInfo(Info info) throws IllegalArgumentException {
		// Methode fehlt in Mapperklasse
		// TODO: wir brauchen die Methode

	}

	@Override
	public void deleteInfo(Info info) throws IllegalArgumentException {

		/**
		 * Abhängigkeiten: Freitext, Auswahl und Element (Element werden
		 * gelöscht bevor die Auswahl gelöscht werden kann löschen bevor
		 * Eigenschaft gelöscht werden kann
		 */

		Auswahl auswahl = this.findAuswahlOf(info);
		Freitext freitext = this.findFreitextOf(info);

		if (auswahl != null) {
			this.deleteAuswahl(auswahl);
		}

		if (freitext != null) {
			this.deleteFreitext(freitext);
		}

		this.iMapper.deleteInfo(info);

	}

	@Override
	public void createEigenschaftForAuswahl(Profil p, Info i, Auswahl a) throws IllegalArgumentException {

		



	}

	@Override
	public void createEigenschaftForFreitext(Profil profil, Info info, Freitext freitext)
			throws IllegalArgumentException {

		
	}

	@Override
	public ArrayList<Eigenschaft> getAllEigenschaften() throws IllegalArgumentException {

		return this.eiMapper.findAll();

	}

	@Override
	public Eigenschaft getEigenschaftByID(int id) throws IllegalArgumentException {

		return this.eiMapper.findByKey(id);
	}

	@Override
	public void updateEigenschaft(Eigenschaft eigenschaft) throws IllegalArgumentException {

		this.eiMapper.updateEigenschaft(eigenschaft);

	}

	@Override
	public void deleteEigenschaft(Eigenschaft eigenschaft) throws IllegalArgumentException {
		/**
		 * Abhängigkeiten: Freitext, Auswahl und Element (Element werden
		 * gelöscht bevor die Auswahl gelöscht werden kann löschen bevor
		 * Eigenschaft gelöscht werden kann
		 */

		Auswahl auswahl = this.findAuswahlOf(null);
		Freitext freitext = this.findFreitextOf(null);

		if (auswahl != null) {
			this.deleteAuswahl(auswahl);
		}

		if (freitext != null) {
			this.deleteFreitext(freitext);
		}

		this.deleteEigenschaft(eigenschaft);

	}

	@Override
	public Freitext createFreitext(Freitext freitext) throws IllegalArgumentException {

		return this.fMapper.insertFreitext(freitext);

	}

	/**
	 * gibt einen Freitext aus einer Eigenschaft zurück
	 */
	@Override
	public Freitext getFreitext(Eigenschaft eigenschaft) throws IllegalArgumentException {

		return this.fMapper.findFreitextOfInfo(null);
	}

	@Override
	public void updateFreitext(Profil profil, Info info) throws IllegalArgumentException {

		

	}

	@Override
	public void deleteFreitext(Freitext freitext) throws IllegalArgumentException {
		this.fMapper.deleteFreitext(freitext);

	}

	@Override
	public Auswahl createAuswahl(Auswahl auswahl) throws IllegalArgumentException {

		Auswahl a = new Auswahl();
		// wird vorerst auf 1 gesetzt und im Mapper auf die MAX + 1 angepasst
		a.setId(1);

		return this.aMapper.insertAuswahl(a);

	}

	@Override
	public ArrayList<Auswahl> getAuswahl() throws IllegalArgumentException {

		return this.aMapper.findAll();
	}

	@Override
	public void updateAuswahl(Profil profil, Info info) throws IllegalArgumentException {

		

	}

	@Override
	public void deleteAuswahl(Auswahl auswahl) throws IllegalArgumentException {
		/**
		 * Wie löschen wir die Abhängigkeiten bezüglich Element (auswahlID)?
		 * 
		 * Elemente kann man nicht löschen, wir löschen die Auswahl-FremdID aus
		 * dem Element und dann erst die Auswahl - DONE
		 * 
		 * TODO: auswahlID aus der Infotabelle löschen
		 * 
		 */

		this.deleteAuswahl(auswahl);

	}

	@Override
	public ArrayList<Kontaktsperre> findKontaktsperrenOf(Profil profilowner) throws IllegalArgumentException {

		return this.kMapper.findKontaktsperrenOf(profilowner);
	}

	@Override
	public ArrayList<Merkzettel> findMerkzettelnOf(Profil profilowner) throws IllegalArgumentException {

		return this.mMapper.findMerkzettelnOf(profilowner);

	}

	@Override
	public ArrayList<Suchprofil> findSuchprofileOf(Profil profilowner) throws IllegalArgumentException {

		return this.sMapper.findSuchprofileOf(profilowner);

	}

	@Override
	public ArrayList<Info> findInfoOf(Profil profilowner) throws IllegalArgumentException {

		return this.iMapper.findInfoOf(profilowner);

	}

	@Override
	public Info findInfoOfEigenschaft(Eigenschaft eigenschaft) throws IllegalArgumentException {

		return this.iMapper.findEigenschaftsInfosOf(eigenschaft);

	}

	@Override
	public Freitext findFreitextOf(Info info) throws IllegalArgumentException {

		/**
		 * Gibt den Freitext einer Info über den Fremdschlüssel zurück
		 */

		return this.fMapper.findFreitextOfInfo(info);
	}

	@Override
	public Auswahl findAuswahlOf(Info info) throws IllegalArgumentException {

		/**
		 * Gibt eine Auswahl aus einer Info zurück
		 */

		return this.aMapper.findAuswahlOf(info);
	}

	@Override
	public void createBesuch(Profil source, Profil target) throws IllegalArgumentException {
		Besuch b = new Besuch();
		b.setEigenprofilID(source.getId());
		b.setFremdprofilID(target.getId());

		this.bMapper.insertBesuch(b);

	}

	@Override
	public void deleteBesuch(Besuch besuch) throws IllegalArgumentException {

		this.bMapper.deleteBesuch(besuch);

	}

	@Override
	public ArrayList<Besuch> findAllBesuche() throws IllegalArgumentException {

		return this.bMapper.findAll();
	}

	@Override
	public Besuch findBesuchByKey(int id) throws IllegalArgumentException {

		return this.bMapper.findByKey(id);
	}

	@Override
	public ArrayList<Besuch> findBesucheOf(Profil profilowner) throws IllegalArgumentException {

		return this.bMapper.findByEigenprofil(profilowner);
	}

	@Override
	public Auswahl findAuswahlByTitle(Auswahl auswahl) throws IllegalArgumentException {

		return this.aMapper.findAuswahlByTitle(auswahl);
	}

	@Override
	public ArrayList<Eigenschaft> getAllEigenschaftenOf(Profil profil) throws IllegalArgumentException {

		return this.eiMapper.getAllEigenschaftenOf(profil);
	}

	

}