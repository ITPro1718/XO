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
		
		ArrayList<Merkzettel> merkzettel = this.findMerkzettelnOf(p);
		ArrayList<Kontaktsperre> kontaktsperren = this.findKontaktsperrenOf(p);
		ArrayList<Besuch> besuche = this.findBesucheOfe(p);
		ArrayList<Suchprofil> suchprofile = this.findSuchprofileOf(p);
		ArrayList<Info> infos = this.findInfoOf(p);
		
		
		if (merkzettel != null){
			for (Merkzettel m : merkzettel){
				this.deleteMerkzettelEintrag(m);
			}
		}
		
		if (kontaktsperren != null){
			for (Kontaktsperre k : kontaktsperren){
				this.deleteKontaktsperreEintraege(k);
			}
		}
		
		if (besuche != null){
			for (Besuch b : besuche){
				this.deleteBesuch(b);
			}
		}
		
		if (suchprofile != null){
			for (Suchprofil s : suchprofile){
				this.deleteSuchprofil(s);
			}
		}
		
		if (infos != null){
			for (Info i : infos){
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
		 * keine Abhängigkeiten, deswegen können wir es einfach löschen
		 */
	  this.kMapper.deleteKontaktsperreEintrag(kontaktsperre);
		
	}

	@Override
	public void createSuchprofil(Profil source, String titel, String haarfarbe, float kgr, boolean raucher, String religion,
			int alter) throws IllegalArgumentException {
		Suchprofil s = new Suchprofil();
		s.setEigenprofilID(source.getId());
		s.setHaarFarbe(haarfarbe);
		s.setKoerpergroesse(kgr);
		s.setTitle(titel);
		// !!! Für Religion fehlt das Attribut im relationalen Modell
		s.setReligion(religion);
		s.setRaucher(raucher);
		s.setAlter(alter);
		

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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Profil> getNotSeenProfilErgebnisse(Suchprofil suchprofil) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createInfo(Profil p, String bezeichnung) throws IllegalArgumentException {
		
		Info i = new Info();
		i.setText(bezeichnung);
		i.setEigenprofilID(p.getId());
		
		//this.iMapper.insertInfo(i); insert-Methode fehlt in Mapper klasse, Update-Methode auch (falls Eigenschaften gelöscht werden ändert sich das Infoobjekt etc.)
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
		//  Methode fehlt in Mapperklasse 
		
	}

	@Override
	public void deleteInfo(Info info) throws IllegalArgumentException {
		this.iMapper.deleteInfo(info);
		
	}

	@Override
	public void createEigenschaft(Info info) throws IllegalArgumentException {
		// brauchen wir hier nicht ain Auswahl auswahl bzw. Freitext freitext Übergabewert, um die AuswahlID/FreitextID dem Eigenschaftsobjekt hinzuzufügen nicht die InfoID.
		
	}

	@Override
	public ArrayList<Eigenschaft> getAllEigenschaften() throws IllegalArgumentException {
		
		return null;
	}

	@Override
	public Eigenschaft getEigenschaftByID(int id) throws IllegalArgumentException {
		
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
	  return this.kMapper.findKontaktsperrenOf(profilowner);
  }

  @Override
  public ArrayList<Merkzettel> findMerkzettelnOf(Profil profilowner)
      throws IllegalArgumentException {
	  return this.mMapper.findMerkzettelnOf(profilowner);
    
  }

  @Override
  public ArrayList<Besuch> findBesucheOfe(Profil profilowner) throws IllegalArgumentException {
 
    return this.bMapper.findByEigenprofil(profilowner);
  }

  @Override
  public ArrayList<Suchprofil> findSuchprofileOf(Profil profilowner)
      throws IllegalArgumentException {
	
    return this.sMapper.findSuchprofileOf(profilowner);
  }

  @Override
  public ArrayList<Info> findInfoOf(Profil profilowner) throws IllegalArgumentException {
	return this.iMapper.findInfoOf(profilowner);
  }

  @Override
  public ArrayList<Info> findEigenschaftsInfosOf(Eigenschaft eigenschaft)
      throws IllegalArgumentException {
	// Methode in Mapperklasse implementieren
    return null;
  }

  @Override
  public ArrayList<Freitext> findFreitextOf(Eigenschaft eigenschaft)
      throws IllegalArgumentException {
	// hat ein Eigenschaftsobjekt nicht maximal 1 Freitext oder 1 Auswahl(nach unserem relationalen Datenmodell) ? dann wäre der Rückgabewert keine Liste sondern nur "Freitext".
    return this.fMapper.findFreitextOf(eigenschaft);
  }
  @Override
  public ArrayList<Eigenschaft> findAuswahlOf(Eigenschaft eigenschaft)
      throws IllegalArgumentException {
	//Rückgabewertfehler: wir wollen Auswahlen eines Eigenschaftsobjekts finden und geben eine Liste mit Eigenschaften zurück? 
	// Ein Eigenschaftsobjekt hat entweder keine oder maximal eine Auswahl, somit wird die Rückgabe keine Liste von Auswahlen ergeben sondern nur ein einziges Auswahlobjekt, falls vorhanden.
	 // public Auswahl findAuswahlOf(Eigenschaft eigenschaft) ODER
	// public Eigenschaft findEigenschafteOf(Info info) ? Ein Infoobjket kann nur eine Eigenschaft haben oder mehrere?
    return null;
  }

  @Override
  public ArrayList<Auswahl> findElementeOf(Auswahl auswahl) throws IllegalArgumentException {
	// Rückgabebewert ArrayList<Element>  wenn wir Elemente von einem Auswahlobjekt suchen? 
	  
    return null;
  }

@Override
public void createBesuch(Profil source, Profil target) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	
}

@Override
public void deleteBesuch(Besuch besuch) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	
}

@Override
public ArrayList<Besuch> findAllBesuche() throws IllegalArgumentException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public Besuch findBesuchByKey(int id) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ArrayList<Besuch> findBesucheOf(Profil profilowner) throws IllegalArgumentException {
	// TODO Auto-generated method stub
	return null;
}


}