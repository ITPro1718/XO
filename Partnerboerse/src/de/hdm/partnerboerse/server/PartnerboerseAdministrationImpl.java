package de.hdm.partnerboerse.server;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.partnerboerse.server.db.AuswahlMapper;
import de.hdm.partnerboerse.server.db.BesuchMapper;
import de.hdm.partnerboerse.server.db.EigenschaftMapper;
import de.hdm.partnerboerse.server.db.ElementMapper;
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
import de.hdm.partnerboerse.shared.bo.Element;
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

	private ElementMapper elMapper = null;

	private FreitextMapper fMapper = null;

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
		this.elMapper = ElementMapper.elementMapper();
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
	public void createProfil(int id, String vname, String nname, String haarfarbe, float kgr, boolean raucher,
			String religion, Date geb, String pw, String email) throws IllegalArgumentException {

		Profil p = new Profil();
		p.setVorname(vname);
		p.setNachname(nname);
		p.setHaarfarbe(haarfarbe);
		p.setKoerpergroesse(kgr);
		p.setRaucher(raucher);
		p.setGeburtsdatum(geb);
		p.setPasswort(pw);
		p.setEmail(email);
		p.setId(id);
		p.setReligion(religion);

		this.pMapper.insert(p);
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

		return this.pMapper.findProfilesByName(name);

	}

	@Override
	public Profil getProfilByID(int id) throws IllegalArgumentException {

		return this.pMapper.findProfilByKey(id);

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

		this.mMapper.deleteMerkzettelEintrag(merkzettel);

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

		this.kMapper.deleteKontaktsperreEintrag(kontaktsperre);

	}

	@Override
	public void createSuchprofil(Profil source, String titel, String haarfarbe, float kgr, boolean raucher,
			String religion, int alter) throws IllegalArgumentException {
		Suchprofil s = new Suchprofil();
		s.setEigenprofilID(source.getId());
		s.setHaarFarbe(haarfarbe);
		s.setKoerpergroesse(kgr);
		s.setTitle(titel);
		// !!! Für Religion fehlt das Attribut im relationalen Modell
		// TODO: Datenbank updaten!!
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

	@Override
	public ArrayList<Profil> berechneAehnlichkeitsmass(Profil source, Suchprofil suchprofil)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Profil> getSuchProfilErgebnisse(Suchprofil suchprofil) throws IllegalArgumentException {

		ArrayList<Profil> profile = getAllProfils();
		Profil suchprofilowner = getProfilByID(suchprofil.getEigenprofilID());
		ArrayList<Kontaktsperre> kontaktsperrenofsuchprofilowner = findKontaktsperrenOf(suchprofilowner);
		ArrayList<Integer> fpids = new ArrayList<>();

		for (Kontaktsperre k : kontaktsperrenofsuchprofilowner) {
			int fpid = k.getFremdprofilID();
			fpids.add(fpid);
		}

		for (Profil p : profile) {
			int id = p.getId();

			if (fpids.contains(id)) {
				profile.remove(p);
			} else if (id == suchprofilowner.getId()) {
				profile.remove(p);
			} else if (compare(suchprofil, p) == false) {
				profile.remove(p);
			}
		}
		return profile;
	}

	public boolean compare(Suchprofil suchprofill, Profil profil) {

		if (suchprofill.getHaarFarbe() == profil.getHaarfarbe() &&

				(suchprofill.getKoerpergroesse() == profil.getKoerpergroesse()) &&

				(suchprofill.isRaucher() == profil.isRaucher()) &&

				(suchprofill.getAlter() == getAge(profil.getGeburtsdatum())) &&

				(suchprofill.getReligion() == profil.getReligion())) {
			return true;
		} else
			return false;

	}

	@Override
	public ArrayList<Profil> getNotSeenSuchProfilErgebnisse(Suchprofil suchprofil) throws IllegalArgumentException {

		ArrayList<Profil> suchProfilErgebnisse = getSuchProfilErgebnisse(suchprofil);
		Profil suchprofilowner = getProfilByID(suchprofil.getEigenprofilID());
		ArrayList<Besuch> visitsOfSuchProfilowner = findBesucheOf(suchprofilowner);
		ArrayList<Integer> visitedProfilids = new ArrayList<>();

		for (Besuch b : visitsOfSuchProfilowner) {
			int visitid = b.getFremdprofilID();
			visitedProfilids.add(visitid);
		}
		for (Profil p : suchProfilErgebnisse) {
			int id = p.getId();

			if (visitedProfilids.contains(id)) {
				suchProfilErgebnisse.remove(p);
			}
		}
		return suchProfilErgebnisse;
	}

	@Override
	public void createInfo(Profil p, String bezeichnung) throws IllegalArgumentException {

		Info i = new Info();
		i.setText(bezeichnung);
		i.setEigenprofilID(p.getId());

		// this.iMapper.insertInfo(i); insert-Methode fehlt in Mapper klasse,
		// Update-Methode auch (falls Eigenschaften gelöscht werden ändert sich
		// das Infoobjekt etc.)
		// TODO: Methode einfügen
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
		// Methode fehlt in Mapperklasse

	}

	@Override
	public void deleteInfo(Info info) throws IllegalArgumentException {
		// this.iMapper.deleteInfo(info); Abhängigkeiten?

	}

	@Override
	public void createEigenschaft(Info info) throws IllegalArgumentException {
		// brauchen wir hier nicht ain Auswahl auswahl bzw. Freitext freitext
		// Übergabewert, um die AuswahlID/FreitextID dem Eigenschaftsobjekt
		// hinzuzufügen nicht die InfoID.

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
		// Abhängigkeiten Freitext,Auswahl und Element löschen bevor Eigenschaft
		// gelöscht werden kann

	}

	@Override
	public void createFreitext(Eigenschaft eigenschaft, String text) throws IllegalArgumentException {

		Freitext f = new Freitext();

		f.setBeschreibung(text);
		// Freitext ID von Eigenschaftsobjekt setzen, wie? ID von
		// Freitext(welche der Freitext ID vom
		// Eigenschaftsobjekt entspricht) wird erst im Mapper gesetzt.

		// this.fMapper.insertFreitext(f);

	}

	@Override
	public Freitext getFreitext() throws IllegalArgumentException {
		// Alle Freitexte (Arraylist) oder ein Freitext von einem
		// Eigenschaftsobjekt? (dann brauchen wir den Übergabewert Eigenschaft
		// eigenschaft)
		return null;
	}

	@Override
	public void updateFreitext(Freitext freitext) throws IllegalArgumentException {
		this.fMapper.updateFreitext(freitext);

	}

	@Override
	public void deleteFreitext(Freitext freitext) throws IllegalArgumentException {
		this.fMapper.deleteFreitext(freitext);

	}

	@Override
	public void createAuswahl(Eigenschaft eigenschaft, String title) throws IllegalArgumentException {
		// siehe createFreitext Kommentar. Gleiches Problem.

	}

	@Override
	public ArrayList<Auswahl> getAuswahl() throws IllegalArgumentException {

		return this.aMapper.findAll();
	}

	@Override
	public void updateAuswahl(Auswahl auswahl) throws IllegalArgumentException {
		this.aMapper.updateAuswahl(auswahl);

	}

	@Override
	public void deleteAuswahl(Auswahl auswahl) throws IllegalArgumentException {
		/**
		 * Wie löschen wir die Abhängigkeiten bezüglich Element (auswahlID)?
		 * 
		 * Elemente kann man nicht löschen, wir löschen die Auswahl-FremdID aus
		 * dem Element und dann erst die Auswahl
		 * 
		 */

		ArrayList<Element> el = this.findElementeOf(auswahl);

		if (el != null) {
			for (Element e : el) {
				// this.deleteElementAuswahl();
			}
		}

	}

	@Override
	public ArrayList<Element> getAllElements() throws IllegalArgumentException {
		return this.elMapper.findAll();
	}

	@Override
	public Element getElementByID(int id) throws IllegalArgumentException {
		return this.elMapper.findByKey(id);
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
	public ArrayList<Info> findEigenschaftsInfosOf(Eigenschaft eigenschaft) throws IllegalArgumentException {

		/**
		 * Was macht diese Methode? TODO: Methode im Info Mapper implementieren
		 */

		return this.iMapper.findEigenschaftsInfosOf(eigenschaft);

	}

	@Override
	public Freitext findFreitextOf(Eigenschaft eigenschaft) throws IllegalArgumentException {

		/**
		 * Gibt den Freitext einer Eigenschaft über den Fremdschlüssel zurück
		 */

		return this.fMapper.findFreitextOf(eigenschaft);
	}

	@Override
	public Auswahl findAuswahlOf(Eigenschaft eigenschaft) throws IllegalArgumentException {

		/**
		 * Gibt eine Auswahl aus einer Eigenschaft zurück
		 */

		return this.aMapper.findAuswahlOf(eigenschaft);
	}

	@Override
	public ArrayList<Element> findElementeOf(Auswahl auswahl) throws IllegalArgumentException {

		/**
		 * Gibt alle Elemente einer Auswahl zurück. z.B. Auswahl = Sportart
		 * Elemente = Fußball, Handball
		 */

		return this.elMapper.findElementOf(auswahl);
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
	public void deleteElementAuswahl(Auswahl auswahl) throws IllegalArgumentException {

		this.elMapper.deleteAuswahlIDs();

	}

}