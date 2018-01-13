package de.hdm.partnerboerse.shared;

import java.util.ArrayList;
import java.util.Date;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.hdm.partnerboerse.shared.bo.Auswahl;
import de.hdm.partnerboerse.shared.bo.Besuch;
import de.hdm.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.partnerboerse.shared.bo.Freitext;
import de.hdm.partnerboerse.shared.bo.Info;
import de.hdm.partnerboerse.shared.bo.Kontaktsperre;
import de.hdm.partnerboerse.shared.bo.Merkzettel;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

@RemoteServiceRelativePath("partnerboerseadministration")
public interface PartnerboerseAdministration extends RemoteService {

	/**
	 * Initialisierung des Objekts. Diese Methode ist vor dem Hintergrund von
	 * GWT RPC zusaetzlich zum No Argument Constructor der implementierenden
	 * Klasse {@link BankVerwaltungImpl} notwendig. Bitte diese Methode direkt
	 * nach der Instantiierung aufrufen.
	 * 
	 * @throws IllegalArgumentException
	 */
	public void init() throws IllegalArgumentException;

	/**
	 * Profil Methoden Ein Profil anlegen.
	 * 
	 * @param id
	 *            Prim�rschl�ssel
	 * @param vname
	 *            Vorname
	 * @param nname
	 *            Nachname
	 * @param haarfarbe
	 *            Haarfarbe
	 * @param kgr
	 *            Koerpergroesse
	 * @param raucher
	 *            Raucher
	 * @param religion
	 *            Religion
	 * @param geb
	 *            Geburtsdatum
	 * @param pw
	 *            Passwort
	 * @param email
	 *            Email
	 * @throws IllegalArgumentException
	 */
	public void createProfil(int id, String vname, String nname, String haarfarbe, float kgr, boolean raucher,
			String religion, Date geb, String pw, String email) throws IllegalArgumentException;

	/**
	 * Profil aendern. (evtl. Was wird veraendert, Attribute?)
	 * 
	 * @param p
	 *            Eigenes Profil
	 * @throws IllegalArgumentException
	 */
	public void updateProfil(Profil p) throws IllegalArgumentException;

	/**
	 * 
	 * Profil loeschen.
	 * 
	 * @param p
	 *            Eigenes Profil
	 * @throws IllegalArgumentException
	 */
	public void deleteProfil(Profil p) throws IllegalArgumentException;

	/**
	 * Profil nach Name anzeigen.
	 * Interne Methode zum Aufrufen des Profils aus der DB.
	 * 
	 * @param name
	 *            Profilname
	 * @return Profil-Objekt
	 * @throws IllegalArgumentException
	 */

	public ArrayList<Profil> getProfilByName(String name) throws IllegalArgumentException;

	/**
	 * Profil nach ID anzeigen.
	 * Interne Methode zum Aufrufen des Profils aus der DB.
	 * 
	 * @param id
	 *            Profil-ID
	 * @return Profil-Objekt
	 * @throws IllegalArgumentException
	 */

	public Profil getProfilByID(int id) throws IllegalArgumentException;

	/**
	 * Alle Profile anzeigen.
	 * Interne Methode zum Aufrufen der Profile aus der DB.
	 * 
	 * @return alle Profil-Objekte
	 * @throws IllegalArgumentException
	 */
	
	public Profil getProfilByEmail(String email)throws IllegalArgumentException;
	
	public ArrayList<Profil> getAllProfils() throws IllegalArgumentException;

	/**
	 * 
	 * Merkzettel Methoden
	 * 
	 * Merkzetteleintrag erstellen, Profil merken.
	 * 
	 * @param source
	 *            eigenes Profil
	 * @param target
	 *            fremdes Ziel-Profil
	 * @throws IllegalArgumentException
	 */
	public void createMerkzettelEintrag(Profil source, Profil target) throws IllegalArgumentException;

	/**
	 * Merkzettel mit allen Eintraegen anzeigen.
	 * 
	 * @return alle gemerkten Profile, alle Merkzetteleintraege
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Merkzettel> getAllMerkzettelEintraege() throws IllegalArgumentException;

	/**
	 * Merkzetteleintrag (gemerkte Profile) mit bestimmter ID anzeigen.
	 * Interne Methode zum Aufrufen des gemerkten Profils aus der DB.
	 * 
	 * @param ID id von Merkzetteleintrag bzw. gemerktem Profil
	 * @return Merkzetteleintrag mit bestimmter ID
	 * @throws IllegalArgumentException
	 */
	public Merkzettel getMerkzettelEintraegeByID(int ID) throws IllegalArgumentException;

	/**
	 * Merkzetteleintrag persistent loeschen, gemerktes Profil von Merkzettel loeschen.
	 * 
	 * @param fremdprofil gemerktes Profil
	 * @throws IllegalArgumentException
	 */
	public void deleteMerkzettelEintrag(Merkzettel merkzettel) throws IllegalArgumentException;

	/**
	 * Kontaktsperre Methoden 
	 * 
	 * Kontaktsperreneintrag erstellen.
	 * 
	 * @param source eigenes Profil
	 * @param target fremdes Ziel-Profil
	 * @throws IllegalArgumentException
	 */
	public void createKontaksperreEintrag(Profil source, Profil target) throws IllegalArgumentException;

	/**
	 * Alle Kontaktsperreneintraege bzw. gesperrten Profile anzeigen.
	 * 
	 * @param p gesperrte Profile bzw. Kontaktsperreneintraege
	 * @return alle gesperrten Profile
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Kontaktsperre> getAllKontaktsperreEintraege() throws IllegalArgumentException;

	/**
	 * Kontaktsperreneintraege bzw. gesperrtes Profil mit bestimmter ID anzeigen
	 * Interne Methode zum Aufrufen des Profils aus der DB.
	 * 
	 * @param id ID vom gesperrten Profil
	 * @return gesperrtes Profil mit bestimmer ID
	 * @throws IllegalArgumentException
	 */
	public Kontaktsperre getKontaktsperreEintragByID(int id) throws IllegalArgumentException;

	/**
	 * Kontaktsperreneintrag von Kontaktsperre persistent loeschen bzw. Profil entsperren.
	 * 
	 * @param fremdprofil gesperrtes Profil
	 * @throws IllegalArgumentException
	 */
	public void deleteKontaktsperreEintraege(Kontaktsperre kontaktsperre) throws IllegalArgumentException;

	/**
	 * Suchprofil Methoden
	 * neues Suchprofil erstellen.
	 *  
	 * @param source eigenes Profil
	 * @param haarfarbe 
	 * @param kgr Koerpergroesse
	 * @param raucher
	 * @param religion
	 * @param alter
	 * @throws IllegalArgumentException
	 */
	public void createSuchprofil(Profil source, String titel, String haarfarbe, float kgr, boolean raucher, String religion,
			int alter) throws IllegalArgumentException;
	
	/**
	 * Alle Suchprofile eines Profils anzeigen.
	 * 
	 * @param p eigenes Profil
	 * @return alle vorhandenen Suchprofil-Objekte
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Suchprofil> getAllSuchprofile() throws IllegalArgumentException;
	
	/**
	 * Suchprofil mit bestimmter ID anzeigen.
	 * Interne Methode zum Aufrufen des Suchprofils aus der DB.
	 * 
	 * @param id id des Suchprofils
	 * @return Suchprofil-Objekt mit bestimmter ID
	 * @throws IllegalArgumentException
	 */
	public Suchprofil getSuchprofilByID(int id) throws IllegalArgumentException;

	/**
	 * Suchprofil mit bestimmtem Namen anzeigen.
	 * Interne Methode zum Aufrufen des Suchprofils aus der DB.
	 * 
	 * @param title Titel des Suchprofils
	 * @return Suchprofil-Objekt mit bestimmtem Namen
	 * @throws IllegalArgumentException
	 */
	public Suchprofil getSuchprofilByTitle(String title) throws IllegalArgumentException;

	/**
	 * Suchprofil bearbeiten. (Attribute, Info-Objekte...?)
	 * 
	 * @param suchprofil das zu bearbeitende Suchprofil
	 * @throws IllegalArgumentException
	 */
	public void updateSuchprofil(Suchprofil suchprofil) throws IllegalArgumentException;

	/**
	 * Suchprofil persistent loeschen.
	 * 
	 * @param suchprofil das zu loeschende Suchprofil
	 * @throws IllegalArgumentException
	 */
	public void deleteSuchprofil(Suchprofil suchprofil) throws IllegalArgumentException;

	/**
	 * Aehnlichkeitsmass eines gefundenen Profils mit dem eigenen Profil berechnen
	 * 
	 * @param source eigenes Profil
	 * @param suchprofil gesuchtes/gefundenes Profil
	 * @return Aehnlichkeitsmass-Objekt
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Profil> berechneAehnlichkeitsmass(Profil source, Suchprofil suchprofil)
			throws IllegalArgumentException;

	/**
	 * Ergebnisse der Suchprofile. (gefundene Profile aufgrund des Suchprofils)
	 * 
	 * @param suchprofil das angewendete Suchprofil
	 * @return alle Ergebnisse, nach Aehnichkeitsmass geordnet, vom angewendeten Suchprofil
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Profil> getSuchProfilErgebnisse(Suchprofil suchprofil) throws IllegalArgumentException;

	/**
	 * Noch nicht gesehene Profile bezgl. dem Suchprofil zurückgeben
	 * 
	 * @param suchprofil das angewendete Suchprofil
	 * @return alle nicht-gesehenden Ergebnisse des Suchprofils
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Profil> getNotSeenSuchProfilErgebnisse(Suchprofil suchprofil) throws IllegalArgumentException;

	/**
	 * Info-Objekt Methoden
	 * Neues Info-Objekt erstellen.
	 * @param info TODO
	 * @param freitext TODO
	 * @return TODO
	 * @throws IllegalArgumentException
	 */
	public Info createInfoForFreitext(Info info, Freitext freitext) throws IllegalArgumentException;

	/**
	 * Alle Info-Objekte anzeigen.
	 * 
	 * @return alle Info-Objekte 
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Info> getAllInfos() throws IllegalArgumentException;

	/**
	 * Info-Objekt mit besimmter ID anzeigen.
	 * 
	 * @param id ID des Info-Objekts
	 * @return Info-Objekt mit der bestimmten Id
	 * @throws IllegalArgumentException
	 */
	public Info getInfoByID(int id) throws IllegalArgumentException;

	/**
	 * Info-Objekte bearbeiten.
	 * 
	 * @param info das zu bearbeitende Info-Objekt
	 * @throws IllegalArgumentException
	 */
	public void updateInfo(Info info) throws IllegalArgumentException;

	/**
	 * Info-Objekt loeschen.
	 * 
	 * @param info das zu loeschende Info-Objekt
	 * @throws IllegalArgumentException
	 */
	public void deleteInfo(Info info) throws IllegalArgumentException;

	/**
	 * Eigenschaft Methoden
	 * Eigenschaft fuer das bestimmte Info-Objekt erstellen.
	 * @param p TODO
	 * @param i TODO
	 * @param a TODO
	 * @throws IllegalArgumentException
	 */

	public void createEigenschaftForAuswahl(Profil p, Info i, Auswahl a) throws IllegalArgumentException;
	
	public void createEigenschaftForFreitext(Profil p, Info i, Freitext f) throws IllegalArgumentException;

	/**
	 * Alle Eigenschafts-Objekte anzeigen.
	 * 
	 * @return alle Eigenschafts-Objekte
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Eigenschaft> getAllEigenschaften() throws IllegalArgumentException;

	/**
	 * Eigenschafts-Objekt mit der bestimmten ID anzeigen.
	 * 
	 * @param id die ID der Eigenschaft 
	 * @return Eigenschafts-Objekt mit der bestimmten ID
	 * @throws IllegalArgumentException
	 */
	public Eigenschaft getEigenschaftByID(int id) throws IllegalArgumentException;

	/**
	 * Eigenschaft bearbeiten.
	 * 
	 * @param eigenschaft die zu bearbeitende Eigenschaft
	 * @throws IllegalArgumentException
	 */
	public void updateEigenschaft(Eigenschaft eigenschaft) throws IllegalArgumentException;

	/**
	 * Eigenschaft loeschen.
	 * 
	 * @param eigenschaft zu loeschendes Eigenschafts-Objekt
	 * @throws IllegalArgumentException
	 */
	public void deleteEigenschaft(Eigenschaft eigenschaft) throws IllegalArgumentException;

	/**
	 * Freitext Methoden
	 * Neues Freitext-Objekt, bezogen auf ein bestimmtes Eigenschafts-Objekt, erstellen.
	 * @param freitext TODO
	 * @return TODO
	 * @throws IllegalArgumentException
	 */
	public Freitext createFreitext(Freitext freitext) throws IllegalArgumentException;

	/**
	 * Freitext anzeigen.
	 * @param eigenschaft TODO
	 * 
	 * @return Freitext-Objekt
	 * @throws IllegalArgumentException
	 */
	public Freitext getFreitext(Eigenschaft eigenschaft) throws IllegalArgumentException;

	/**
	 * Freitext bearbeiten
	 * 
	 * @param freitext zu bearbeitendes Freitext-Objekt
	 * @throws IllegalArgumentException
	 */
	public void updateFreitext(Freitext freitext) throws IllegalArgumentException;

	/**
	 * Freitext-Objekt loeschen.
	 * 
	 * @param freitext zu loeschendes Freitext-Objekt
	 * @throws IllegalArgumentException
	 */
	public void deleteFreitext(Freitext freitext) throws IllegalArgumentException;

	/**
	 * Auswahl Methoden
	 * Neues Auswahl-Objekt, bezogen auf ein bestimmtes Eigenschafts-Objekt, erstellen.
	 * @param auswahl TODO
	 * @return TODO
	 * 
	 * @throws IllegalArgumentException
	 */
	public Auswahl createAuswahl(Auswahl auswahl) throws IllegalArgumentException;

	/**
	 * Auswahl-Objekte anzeigen.
	 * 
	 * @return Auswahl-Objekt
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Auswahl> getAuswahl() throws IllegalArgumentException;

	/**
	 * Auswahl-Objekt bearbeiten.
	 * 
	 * @param auswahl das zu bearbeitende Auswahl-Objekt
	 * @throws IllegalArgumentException
	 */
	public void updateAuswahl(Auswahl auswahl) throws IllegalArgumentException;

	/**
	 * Auswahl-Objekt loeschen.
	 * 
	 * @param auswahl zu loeschendes Auswahl-Objekt
	 * @throws IllegalArgumentException
	 */
	public void deleteAuswahl(Auswahl auswahl) throws IllegalArgumentException;

	public ArrayList<Kontaktsperre> findKontaktsperrenOf(Profil profilowner) throws IllegalArgumentException;
	
	public ArrayList<Merkzettel> findMerkzettelnOf(Profil profilowner) throws IllegalArgumentException;
	
	public ArrayList<Suchprofil> findSuchprofileOf(Profil profilowner) throws IllegalArgumentException;
	
	public ArrayList<Info> findInfoOf(Profil profilowner) throws IllegalArgumentException;
	
	public ArrayList<Info> findEigenschaftsInfosOf(Eigenschaft eigenschaft) throws IllegalArgumentException;
	
	public Freitext findFreitextOf(Info info) throws IllegalArgumentException;
	
	public Auswahl findAuswahlOf(Info info) throws IllegalArgumentException;
	
	public void createBesuch(Profil source, Profil target) throws IllegalArgumentException;
	
	public void deleteBesuch(Besuch besuch) throws IllegalArgumentException;
	
	public ArrayList<Besuch> findAllBesuche() throws IllegalArgumentException;
	
	public Besuch findBesuchByKey(int id) throws IllegalArgumentException;
	
	public ArrayList<Besuch> findBesucheOf(Profil profilowner) throws IllegalArgumentException;

	public ArrayList<Profil> getProfileForMerkzettel(Profil eigenProfil) throws IllegalArgumentException;
	
	public ArrayList<Profil> getProfileForKontaktsperre(Profil eigenProfil) throws IllegalArgumentException;
	
}
