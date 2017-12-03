package de.hdm.partnerboerse.shared;

import java.util.ArrayList;
import java.util.Date;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import de.hdm.partnerboerse.shared.bo.Auswahl;
import de.hdm.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.partnerboerse.shared.bo.Element;
import de.hdm.partnerboerse.shared.bo.Freitext;
import de.hdm.partnerboerse.shared.bo.Info;
import de.hdm.partnerboerse.shared.bo.Kontaktsperre;
import de.hdm.partnerboerse.shared.bo.Merkzettel;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

@RemoteServiceRelativePath("partnerboerseadmin")

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
	 *            Primärschlüssel
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
	 * @return Ein fertiges Profil-Objekt.
	 * @throws IllegalArgumentException
	 */
	public Profil createProfil(int id, String vname, String nname, String haarfarbe, float kgr, boolean raucher,
			String religion, Date geb, String pw, String email) throws IllegalArgumentException;

	/**
	 * 
	 * @param p
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Profil updateProfil(Profil p) throws IllegalArgumentException;

	/**
	 * 
	 * @param p
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Profil deleteProfil(Profil p) throws IllegalArgumentException;

	/**
	 * 
	 * @param name
	 * @return
	 * @throws IllegalArgumentException
	 */

	public ArrayList<Profil> getProfilByName(String name) throws IllegalArgumentException;

	/**
	 * 
	 * @param id
	 * @return
	 * @throws IllegalArgumentException
	 */

	public Profil getProfilByID(int id) throws IllegalArgumentException;

	/**
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 */

	public ArrayList<Profil> getAllProfils() throws IllegalArgumentException;

	/**
	 * 
	 * Merkzettel Methoden
	 * 
	 * @param source
	 * @param target
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Merkzettel createMerkzettelEintrag(Profil source, Profil target) throws IllegalArgumentException;

	public ArrayList<Merkzettel> getAllMerkzettelEintraege(Profil p) throws IllegalArgumentException;

	public Merkzettel getMerkzettelEintraegeByID(int ID) throws IllegalArgumentException;

	public Merkzettel deleteMerkzettelEintrag(Profil fremdprofil) throws IllegalArgumentException;

	/**
	 * Kontaktsperre Methoden
	 */

	public Kontaktsperre createKontaksperreEintrag(Profil source, Profil target) throws IllegalArgumentException;

	public ArrayList<Kontaktsperre> getAllKontaktsperreEintraege(Profil p) throws IllegalArgumentException;

	public Kontaktsperre getKontaktsperreEintragByID(int id) throws IllegalArgumentException;

	public Kontaktsperre deleteKontaktsperreEintraege(Profil fremdprofil) throws IllegalArgumentException;

	/**
	 * Suchprofil Methoden
	 * 
	 * @param source
	 * @param haarfarbe
	 * @param kgr
	 * @param raucher
	 * @param religion
	 * @param alter
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Suchprofil createSuchprofil(Profil source, String haarfarbe, float kgr, boolean raucher, String religion,
			int alter) throws IllegalArgumentException;

	public ArrayList<Suchprofil> getAllSuchprofile(Profil p) throws IllegalArgumentException;

	public Suchprofil getSuchprofilByID(int id) throws IllegalArgumentException;

	public Suchprofil getSuchprofilByTitle(String title) throws IllegalArgumentException;

	public Suchprofil updateSuchprofil(Suchprofil suchprofil) throws IllegalArgumentException;

	public Suchprofil deleteSuchprofil(Suchprofil suchprofil) throws IllegalArgumentException;

	public ArrayList<Profil> berechneAehnlichkeitsmass(Profil source, Suchprofil suchprofil)
			throws IllegalArgumentException;

	public ArrayList<Profil> getSuchProfilErgebnisse(Suchprofil suchprofil) throws IllegalArgumentException;

	public ArrayList<Profil> getNotSeenProfilErgebnisse(Suchprofil suchprofil) throws IllegalArgumentException;

	/**
	 * Info-Objekt Methoden
	 * 
	 * @param p
	 *            Profil
	 * @return Objekt von Info-Objekt
	 * @throws IllegalArgumentException
	 */
	public Info createInfo(Profil p) throws IllegalArgumentException;

	public ArrayList<Info> getAllInfos() throws IllegalArgumentException;

	public Info getInfoByID(int id) throws IllegalArgumentException;

	public Info updateInfo(Info info) throws IllegalArgumentException;

	public Info deleteInfo(Info info) throws IllegalArgumentException;

	/**
	 * Eigenschaft Methoden
	 * 
	 * @param info
	 * @return
	 * @throws IllegalArgumentException
	 */

	public Eigenschaft createEigenschaft(Info info) throws IllegalArgumentException;

	public ArrayList<Eigenschaft> getAllEigenschaften() throws IllegalArgumentException;

	public Eigenschaft getEigenschaftByID(int id) throws IllegalArgumentException;

	public Eigenschaft updateEigenschaft(Eigenschaft eigenschaft) throws IllegalArgumentException;

	public Eigenschaft deleteEigenschaft(Eigenschaft eigenschaft) throws IllegalArgumentException;

	/**
	 * Freitext Methoden
	 * 
	 * @param eigenschaft
	 * @param text
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Freitext createFreitext(Eigenschaft eigenschaft, String text) throws IllegalArgumentException;

	public Freitext getFreitext() throws IllegalArgumentException;

	public Freitext updateFreitext(Freitext freitext) throws IllegalArgumentException;

	public Freitext deleteFreitext(Freitext freitext) throws IllegalArgumentException;

	/**
	 * Auswahl Methoden
	 * 
	 * @param eigenschaft
	 * @param title
	 * @return
	 * @throws IllegalArgumentException
	 */
	public Auswahl createAuswahl(Eigenschaft eigenschaft, String title) throws IllegalArgumentException;

	public ArrayList<Auswahl> getAuswahl() throws IllegalArgumentException;

	public Auswahl updateAuswahl(Auswahl auswahl) throws IllegalArgumentException;

	public Auswahl deleteAuswahl(Auswahl auswahl) throws IllegalArgumentException;

	/**
	 * Element Methoden
	 * 
	 * @return
	 * @throws IllegalArgumentException
	 */
	public ArrayList<Element> getAllElements() throws IllegalArgumentException;

	public Element getElement() throws IllegalArgumentException;

}
