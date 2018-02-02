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
// import de.hdm.partnerboerse.server.db.FreitextMapper;
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
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class PartnerboerseAdministrationImpl extends RemoteServiceServlet implements PartnerboerseAdministration {

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

	@Override
	public void init() throws IllegalArgumentException {

		this.aMapper = AuswahlMapper.auswahlMapper();
		this.eiMapper = EigenschaftMapper.eigenschaftMapper();
		// this.fMapper = FreitextMapper.freitextMapper();
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

		ArrayList<Info> infosOfSuchprofil = this.findInfoOf(suchprofil);

		if (infosOfSuchprofil != null) {
			for (Info infos : infosOfSuchprofil) {
				this.deleteInfo(infos);
			}
		}

		this.sMapper.deleteSuchprofil(suchprofil);
	}

	public ArrayList<Info> getInfoOfSuchprofil(int suchprofilid) {

		return this.iMapper.findInfoOfSuchprofil(suchprofilid);
	}

	public ArrayList<Info> findAllInfosOfProfil(Profil profil) {

		return this.iMapper.findInfoOf(profil);
	}

	public ArrayList<Profil> berechneAehnlichkeitsmass(Profil profil, ArrayList<Profil> ergebnisse) {

		Aehnlichkeitsmass aehnlichkeitsmass = new Aehnlichkeitsmass();
		aehnlichkeitsmass.setGewichtung1Infoobjekt(10);
		aehnlichkeitsmass.setGewichtungAlter(50);
		aehnlichkeitsmass.setGewichtungHaarfarbe(20);
		aehnlichkeitsmass.setGewichtungRaucher(40);
		aehnlichkeitsmass.setGewichtungReligion(30);

		ArrayList<Profil> comparedProfiles = new ArrayList<>();
		float o1 = aehnlichkeitsmass.getGewichtungHaarfarbe();
		float o2 = aehnlichkeitsmass.getGewichtungReligion();
		float o3 = aehnlichkeitsmass.getGewichtungAlter();
		float o4 = aehnlichkeitsmass.getGewichtungRaucher();
		float o5 = aehnlichkeitsmass.getGewichtung1Infoobjekt();

		float summe = o1 + o2 + o3 + o4 + o5;
		float x = (float) 100 / summe;

		for (Profil p : ergebnisse) {
			float p1 = 0;
			float p2 = 0;
			float p3 = 0;
			float p4 = 0;
			float p5 = 0;

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
			if (compareInfos(profil, p) >= 1) {
				o5 = o5 * compareInfos(profil, p);
				p5 = o5;

			}
			int aehnlichkeit = (int) (((p1 * x) + (p2 * x) + (p3 * x) + (p4 * x) + (p5 * x)) + 0.5);
			if (aehnlichkeit > 100) {
				aehnlichkeit = 100;
			}
			p.setÄhnlichkeit(aehnlichkeit);
			comparedProfiles.add(p);
		}
		Collections.sort(comparedProfiles, new Comparator<Profil>() {
			public int compare(Profil a, Profil b) {
				return Integer.valueOf(b.getÄhnlichkeit()).compareTo(a.getÄhnlichkeit());
			}
		});

		return comparedProfiles;
	}

	@Override
	public ArrayList<Profil> getSuchProfilErgebnisse(Suchprofil suchprofil) throws IllegalArgumentException {

		ArrayList<Profil> profile = this.getAllProfils();
		Profil suchprofilowner = this.getProfilByID(suchprofil.getEigenprofilID());
		ArrayList<Kontaktsperre> blockedSuchprofilowner = new ArrayList<>();
		ArrayList<Profil> profilsToRemove = new ArrayList<Profil>();

		// Wenn die Fremdprofil-ID einer Kontaktsperre (gesperrte Profil ID) mit
		// der eigenen Profil-ID des "Suchprofilbesitzers"
		// übereinstimmt, wird diese Kontaktsperre zu blockedSuchprofilowner
		// hinzugefügt.( Alle Kontaktsperren die das eigene Profil geblockt
		// haben)
		for (Kontaktsperre s : this.getAllKontaktsperreEintraege()) {
			if (s.getFremdprofilID() == suchprofilowner.getId()) {
				blockedSuchprofilowner.add(s);
			}
			// Besitzer dieser Kontaktsperren finden und einer Arraylist
			// (profilBlockedSuchprofilowner) hinzufügen
			for (Kontaktsperre t : blockedSuchprofilowner) {
				int epidofkkk = t.getEigenprofilID();
				Profil pp = getProfilByID(epidofkkk);
				profilsToRemove.add(pp);
			}
		}

		for (Profil p : profile) {

			for (Kontaktsperre k : this.findKontaktsperrenOf(suchprofilowner)) {

				if (k.getFremdprofilID() == p.getId()) {
					profilsToRemove.add(p);
				}
			}

			// Wenn die Arraylist mit Profilen,( Profilbesitzer die einen selbst
			// geblockt haben), ein Profil der Liste mit allen Profilen
			// enthält(Abgleich)
			// so wird dieses Profil aus der Liste aller Profile gelöscht. Somit
			// sind Profile aus der Liste, die das eigene Profil auf der
			// Kontaaktsperrliste hatten.

			if (p.getId() == suchprofilowner.getId()) {
				profilsToRemove.add(p);
			}

			if (compare(suchprofil, p) == false) {
				profilsToRemove.add(p);
			}

			if (compareSexuelleOrientierung(suchprofilowner, p) == false) {
				profilsToRemove.add(p);
			}
		}
		profile.removeAll(profilsToRemove);
		ArrayList<Profil> comparedProfiles = this.berechneAehnlichkeitsmass(suchprofilowner, profile);
		return comparedProfiles;
	}

	/**
	 * Gibt TRUE zurück, wenn ein Profil mit einem Suchprofil übereinstimmt
	 * 
	 * @param suchprofill
	 * @param profil
	 * @return
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

	public boolean compareProfilAuswahlInfosWith(Suchprofil suchprofil, Profil profil) {

		ArrayList<Info> infos = suchprofilInfoHasAuswahl(suchprofil);
		ArrayList<Info> inf = profilInfoHasAuswahl(profil);
		ArrayList<String> suchprofilinfotexte = new ArrayList<>();
		ArrayList<String> profilinfotexte = new ArrayList<>();
		if (infos.isEmpty()) {
			return true;
		}
		for (Info i : infos) {

			for (Info a : inf) {
				if (i.getText().equals(a.getText()))
					return true;
				// suchprofilinfotexte.add(i.getText());
				// profilinfotexte.add(a.getText());
			}
		}
		// if(profilinfotexte.containsAll(suchprofilinfotexte)){
		// return true;
		// }
		return false;
	}

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

	public boolean compareEigenprofil(Profil profil, Profil fremdprofil) {
		if ((profil.isRaucher() == fremdprofil.isRaucher())
				&& (getAge(profil.getGeburtsdatum()) == getAge(fremdprofil.getGeburtsdatum()))
				&& (profil.getReligion() == fremdprofil.getReligion())) {
			return true;
		} else
			return false;
	}

	public boolean compareSexuelleOrientierung(Profil profil, Profil fremdprofil) {

		if (((profil.getGeschlecht().equals("Mann")) && (profil.getSucheNach().equals("Frauen")))
				&& ((fremdprofil.getGeschlecht().equals("Frau")) && (fremdprofil.getSucheNach().equals("Männer")))
				|| ((profil.getGeschlecht().equals("Mann")) && (profil.getSucheNach().equals("Männer")))
						&& ((fremdprofil.getGeschlecht().equals("Mann"))
								&& (fremdprofil.getSucheNach().equals("Männer")))
				|| ((profil.getGeschlecht().equals("Frau")) && (profil.getSucheNach().equals("Männer")))
						&& ((fremdprofil.getGeschlecht().equals("Mann"))
								&& (fremdprofil.getSucheNach().equals("Frauen")))
				|| ((profil.getGeschlecht().equals("Frau")) && (profil.getSucheNach().equals("Frauen")))
						&& ((fremdprofil.getGeschlecht().equals("Frau"))
								&& (fremdprofil.getSucheNach().equals("Frauen")))) {
			return true;
		} else
			return false;
	}

	@Override
	public ArrayList<Profil> getNotSeenPartnervorschläge(Profil profil) throws IllegalArgumentException {

		ArrayList<Profil> profile = getAllProfils();

		ArrayList<Kontaktsperre> blockedProfilowner = new ArrayList<Kontaktsperre>();
		ArrayList<Profil> profilBlockedProfilowner = new ArrayList<Profil>();

		for (Kontaktsperre s : this.getAllKontaktsperreEintraege()) {
			if (s.getFremdprofilID() == profil.getId()) {
				blockedProfilowner.add(s);
			}
			// Besitzer dieser Kontaktsperren finden und einer Arraylist
			// (profilBlockedSuchprofilowner) hinzufügen
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

			// if ((compareInfos(profil, p) <1) &&
			// ((findAllInfosOfProfil(profil).isEmpty()== false)) {
			// profilesToRemove.add(p);
			// System.out.println(p.toString());

			// }

		}
		profile.remove(profil);
		profile.removeAll(profilesToRemove);
		ArrayList<Profil> comparedProfiles = this.berechneAehnlichkeitsmass(profil, profile);
		return comparedProfiles;
	}

	public ArrayList<String> findAllTexts(Profil profil) {
		ArrayList<String> textsOfInfos = new ArrayList<String>();
		for (Info i : findAllInfosOfProfil(profil)) {

			textsOfInfos.add(i.getText());
		}
		return textsOfInfos;
	}

	public int compareInfos(Profil profil, Profil fremdprofil) {

		int counter = 0;
		if ((findAllInfosOfProfil(profil).isEmpty()) && (findAllInfosOfProfil(fremdprofil).isEmpty())) {
			counter = 1;
		}

		for (Info i : findAllInfosOfProfil(profil)) {

			for (Info o : findAllInfosOfProfil(fremdprofil)) {

				if ((i.getEigenschaftId() == o.getEigenschaftId()) && (i.getText().equals(o.getText()))) {
					counter++;

				}

			}
		}
		return counter;
	}

	@Override
	public Info createInfo(Profil profil, String text, Eigenschaft eigenschaft) throws IllegalArgumentException {

		ArrayList<Info> infos = this.findInfoOf(profil);
		ArrayList<Info> del = new ArrayList<Info>();

		// Erstellt ein Info Objekt, welches in die Datenbank geschrieben wird
		Info info = new Info();
		info.setepId(profil.getId());
		info.setText(text);
		info.setEigenschaftId(eigenschaft.getId());

		// Prüft, ob für diese Eigenschaft bereits ein Info-Objekt für diesen
		// User angelegt wurde
		for (Info i : infos) {
			// Wenn bereits eine Info für diese Eigenschaft besteht, wird sie
			// geupdated.
			if (i.getEigenschaftId() == info.getEigenschaftId()) {
				info.setId(i.getId());
				this.updateInfo(info);
			} else {
				del.add(i);
			}
		}
		// Removed alle bereits vorhandenen objekte, wenn die Liste leer ist,
		// sprich noch kein Eintrag vorhanden,
		// wird eine neue Info angelegt
		infos.removeAll(del);
		if (infos.isEmpty()) {
			this.iMapper.insertInfo(info);
		}

		return info;

	}

	public Info createInfo(Suchprofil suchprofil, String text, Eigenschaft eigenschaft)
			throws IllegalArgumentException {

		ArrayList<Info> infos = getInfoOfSuchprofil(suchprofil.getId());
		ArrayList<Info> del = new ArrayList<Info>();

		// Erstellt ein Info Objekt, welches in die Datenbank geschrieben wird
		Info info = new Info();
		info.setSuchprofilId(suchprofil.getId());
		info.setText(text);
		info.setEigenschaftId(eigenschaft.getId());

		// Prüft, ob für diese Eigenschaft bereits ein Info-Objekt für diesen
		// User angelegt wurde
		for (Info i : infos) {
			// Wenn bereits eine Info für diese Eigenschaft besteht, wird sie
			// geupdated.
			if (i.getEigenschaftId() == info.getEigenschaftId()) {
				info.setId(i.getId());
				this.updateInfo(info);
			} else {
				del.add(i);
			}
		}
		// Removed alle bereits vorhandenen objekte, wenn die Liste leer ist,
		// sprich noch kein Eintrag vorhanden,
		// wird eine neue Info angelegt
		infos.removeAll(del);
		if (infos.isEmpty()) {
			this.iMapper.insertInfo(info);
		}

		return info;
	}

	@Override
	public ArrayList<Info> getAllInfos() throws IllegalArgumentException {
		return this.iMapper.findAll();

	}

	@Override
	public Info getInfoByID(int id) throws IllegalArgumentException {
		return this.iMapper.findByKey(id);
	}

	@Override
	public void updateInfo(Info info) throws IllegalArgumentException {

		this.iMapper.update(info);

	}

	@Override
	public void deleteInfo(Info info) throws IllegalArgumentException {

		this.iMapper.deleteInfo(info);

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
	public void deleteInfoOfEigenschaft(Eigenschaft eigenschaft, Profil p) throws IllegalArgumentException {

		ArrayList<Info> userInfos = this.findInfoOf(p);

		for (Info i : userInfos) {
			if (i.getEigenschaftId() == eigenschaft.getId()) {
				this.iMapper.deleteInfo(i);
			}
		}

	}

	/**
	 * Die Info zu einer Eigenschaft wird gelöscht zu dem passendem Suchprofil
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

	@Override
	public ArrayList<Auswahl> getAuswahl() throws IllegalArgumentException {

		return this.aMapper.findAll();
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
	public ArrayList<Info> findInfoOf(Suchprofil suchprofil) throws IllegalArgumentException {

		return this.iMapper.findInfoOfSuchprofil(suchprofil.getId());

	}

	@Override
	public Info findInfoOfEigenschaft(Eigenschaft eigenschaft) throws IllegalArgumentException {

		return this.iMapper.findEigenschaftsInfosOf(eigenschaft);

	}

	@Override
	public Auswahl findAuswahlOf(Info info) throws IllegalArgumentException {
		return null;

		/**
		 * Gibt eine Auswahl aus einer Info zurück
		 */

		// return this.aMapper.findAuswahlOf(info);
	}

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