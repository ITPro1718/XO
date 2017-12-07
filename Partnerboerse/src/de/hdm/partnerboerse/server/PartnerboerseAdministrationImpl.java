package de.hdm.partnerboerse.server;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.hdm.partnerboerse.server.db.*;
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
public class PartnerboerseAdministrationImpl extends RemoteServiceServlet 
	implements PartnerboerseAdministration {

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
		
		this.pMapper.insert(p);
	}

	@Override
	public void updateProfil(Profil p) throws IllegalArgumentException {
		pMapper.update(p);
	}

	@Override
	public void deleteProfil(Profil p) throws IllegalArgumentException {
		
		/**
		 * Hier muss man die ganzen Abhngigkeiten abchecken, bevor man ein Profil l�scht.
		 * z.B. muss man erst alle Merkzettel Eintr�ge l�schen, in denen das Profil vorkommt.
		 * Erst dann kann man ein Profil l�schen
		 * 
		 * Abh�ngigkeiten von Profil:
		 * 
		 * Merkzettel
		 * Kontaktsperre
		 * Visit
		 * Suchprofil
		 * Info
		 * 
		 */
	  
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
	public ArrayList<Merkzettel> getAllMerkzettelEintraege(Profil p) throws IllegalArgumentException {
		
		/**
		 * bisher ist es so, dass wir in der methode alle von einem owner haben wollen,
		 * aber beim Mapper w�rde man alle zur�ckgeben
		 * 
		 */
		
		// TODO: weitere Methoden einf�gen und Konflikt kl�ren (getAll, getAllFromOwner)
		
		return this.mMapper.findAll();
	}

	@Override
	public Merkzettel getMerkzettelEintraegeByID(int ID) throws IllegalArgumentException {

		return this.mMapper.findByKey(ID);
		
	}

	@Override
	public void deleteMerkzettelEintrag(Merkzettel merkzettel) throws IllegalArgumentException {

		/**
		 * Da es keine unterabh�ngigkeiten mehr von Merkzettel gibt, kann man Eintr�ge so 
		 * l�schen
		 */
		
		
	}

	@Override
	public void createKontaksperreEintrag(Profil source, Profil target) throws IllegalArgumentException {

		Kontaktsperre k = new Kontaktsperre();
		k.setEigenprofilID(source.getId());
		k.setFremdprofilID(target.getId());
		
		this.kMapper.insertKontaktsperreEintrag(k);
		
		
	}

	@Override
	public ArrayList<Kontaktsperre> getAllKontaktsperreEintraege(Profil p) throws IllegalArgumentException {
		
		/**
		 * gleiches Problem wie bei Merkzettel
		 * 
		 * TODO: findByOwner und findAll 
		 */
		
		return this.kMapper.findAll();
	}

	@Override
	public Kontaktsperre getKontaktsperreEintragByID(int id) throws IllegalArgumentException {


		return this.kMapper.findByKey(id);
		
	}

	@Override
	public void deleteKontaktsperreEintraege(Kontaktsperre kontaktsperre) throws IllegalArgumentException {
		/**
		 * keine Abh�ngigkeiten, deswegen k�nnen wir es einfach l�schen
		 */
		
		
	}

	@Override
	public void createSuchprofil(Profil source, String haarfarbe, float kgr, boolean raucher, String religion,
			int alter) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Suchprofil> getAllSuchprofile(Profil p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Suchprofil getSuchprofilByID(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Suchprofil getSuchprofilByTitle(String title) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateSuchprofil(Suchprofil suchprofil) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteSuchprofil(Suchprofil suchprofil) throws IllegalArgumentException {
		// TODO Auto-generated method stub
	
	}

	@Override
	public ArrayList<Profil> berechneAehnlichkeitsmass(Profil source, Suchprofil suchprofil)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Profil> getSuchProfilErgebnisse(Suchprofil suchprofil) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Profil> getNotSeenProfilErgebnisse(Suchprofil suchprofil) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createInfo(Profil p) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Info> getAllInfos() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Info getInfoByID(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateInfo(Info info) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteInfo(Info info) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createEigenschaft(Info info) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Eigenschaft> getAllEigenschaften() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Eigenschaft getEigenschaftByID(int id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateEigenschaft(Eigenschaft eigenschaft) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteEigenschaft(Eigenschaft eigenschaft) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createFreitext(Eigenschaft eigenschaft, String text) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Freitext getFreitext() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateFreitext(Freitext freitext) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFreitext(Freitext freitext) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createAuswahl(Eigenschaft eigenschaft, String title) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Auswahl> getAuswahl() throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateAuswahl(Auswahl auswahl) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAuswahl(Auswahl auswahl) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
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
  public ArrayList<Kontaktsperre> findKontaktsperrenOf(Profil profilowner)
      throws IllegalArgumentException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArrayList<Merkzettel> findMerkzettelnOf(Profil profilowner)
      throws IllegalArgumentException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArrayList<Besuch> findBesucheOfe(Profil profilowner) throws IllegalArgumentException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArrayList<Suchprofil> findSuchprofileOf(Profil profilowner)
      throws IllegalArgumentException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArrayList<Info> findInfoOf(Profil profilowner) throws IllegalArgumentException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArrayList<Info> findEigenschaftsInfosOf(Eigenschaft eigenschaft)
      throws IllegalArgumentException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArrayList<Freitext> findFreitextOf(Eigenschaft eigenschaft)
      throws IllegalArgumentException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArrayList<Eigenschaft> findAuswahlOf(Eigenschaft eigenschaft)
      throws IllegalArgumentException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public ArrayList<Auswahl> findElementeOf(Auswahl auswahl) throws IllegalArgumentException {
    // TODO Auto-generated method stub
    return null;
  }


}