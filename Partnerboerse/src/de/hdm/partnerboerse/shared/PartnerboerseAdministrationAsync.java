package de.hdm.partnerboerse.shared;

import java.util.ArrayList;
import java.util.Date;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.hdm.partnerboerse.shared.bo.Auswahl;
import de.hdm.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.partnerboerse.shared.bo.Element;
import de.hdm.partnerboerse.shared.bo.Freitext;
import de.hdm.partnerboerse.shared.bo.Info;
import de.hdm.partnerboerse.shared.bo.Kontaktsperre;
import de.hdm.partnerboerse.shared.bo.Merkzettel;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

public interface PartnerboerseAdministrationAsync {

	void init(AsyncCallback<Void> callback);

	/**
	 * Profil Methoden Asynchron
	 * 
	 * @param id ID
	 * @param vname Vorname
	 * @param nname Nachname
	 * @param haarfarbe
	 * @param kgr Koerpergroesse
	 * @param raucher
	 * @param religion
	 * @param geb Geburtsdatum
	 * @param pw Passwort
	 * @param email
	 * @param callback
	 */
	void createProfil(int id, String vname, String nname, String haarfarbe, float kgr, boolean raucher, String religion,
			Date geb, String pw, String email, AsyncCallback<Profil> callback);

	/**
	 * 
	 * @param p
	 * @param callback
	 */
	void updateProfil(Profil p, AsyncCallback<Profil> callback);

	/**
	 * 
	 * @param p
	 * @param callback
	 */
	void deleteProfil(Profil p, AsyncCallback<Profil> callback);

	/**
	 * 
	 * @param name
	 * @param callback
	 */
	void getProfilByName(String name, AsyncCallback<ArrayList<Profil>> callback);

	/**
	 * 
	 * @param id
	 * @param callback
	 */
	void getProfilByID(int id, AsyncCallback<Profil> callback);

	/**
	 * 
	 * @param callback
	 */
	void getAllProfils(AsyncCallback<ArrayList<Profil>> callback);

	/**
	 * Merkzettel Methoden Asynchron
	 * 
	 * @param source
	 * @param target
	 * @param callback
	 */
	void createMerkzettelEintrag(Profil source, Profil target, AsyncCallback<Merkzettel> callback);

	/**
	 * 
	 * @param p
	 * @param callback
	 */
	void getAllMerkzettelEintraege(Profil p, AsyncCallback<ArrayList<Merkzettel>> callback);

	/**
	 * 
	 * @param ID
	 * @param callback
	 */
	void getMerkzettelEintraegeByID(int ID, AsyncCallback<Merkzettel> callback);

	/**
	 * 
	 * @param fremdprofil
	 * @param callback
	 */
	void deleteMerkzettelEintrag(Profil fremdprofil, AsyncCallback<Merkzettel> callback);

	/**
	 * Kontaktsperre Methoden Asynchron
	 * 
	 * @param source
	 * @param target
	 * @param callback
	 */
	void createKontaksperreEintrag(Profil source, Profil target, AsyncCallback<Kontaktsperre> callback);

	/**
	 * 
	 * @param p
	 * @param callback
	 */
	void getAllKontaktsperreEintraege(Profil p, AsyncCallback<ArrayList<Kontaktsperre>> callback);

	/**
	 * 
	 * @param id
	 * @param callback
	 */
	void getKontaktsperreEintragByID(int id, AsyncCallback<Kontaktsperre> callback);

	/**
	 * 
	 * @param fremdprofil
	 * @param callback
	 */
	void deleteKontaktsperreEintraege(Profil fremdprofil, AsyncCallback<Kontaktsperre> callback);

	/**
	 * Suchprofil Methoden Asynchron
	 * 
	 * @param source
	 * @param haarfarbe
	 * @param kgr
	 * @param raucher
	 * @param religion
	 * @param alter
	 * @param callback
	 */
	void createSuchprofil(Profil source, String haarfarbe, float kgr, boolean raucher, String religion, int alter,
			AsyncCallback<Suchprofil> callback);

	/**
	 * 
	 * @param p
	 * @param callback
	 */
	void getAllSuchprofile(Profil p, AsyncCallback<ArrayList<Suchprofil>> callback);

	/**
	 * 
	 * @param id
	 * @param callback
	 */
	void getSuchprofilByID(int id, AsyncCallback<Suchprofil> callback);

	/**
	 * 
	 * @param title
	 * @param callback
	 */
	void getSuchprofilByTitle(String title, AsyncCallback<Suchprofil> callback);

	/**
	 * 
	 * @param sp
	 * @param callback
	 */
	void updateSuchprofil(Suchprofil sp, AsyncCallback<Suchprofil> callback);

	/**
	 * 
	 * @param suchprofil
	 * @param callback
	 */
	void deleteSuchprofil(Suchprofil suchprofil, AsyncCallback<Suchprofil> callback);

	/**
	 * 
	 * @param source
	 * @param suchprofil
	 * @param callback
	 */
	void berechneAehnlichkeitsmass(Profil source, Suchprofil suchprofil, AsyncCallback<ArrayList<Profil>> callback);

	/**
	 * 
	 * @param suchprofil
	 * @param callback
	 */
	void getSuchProfilErgebnisse(Suchprofil suchprofil, AsyncCallback<ArrayList<Profil>> callback);

	/**
	 * 
	 * @param suchprofil
	 * @param callback
	 */
	void getNotSeenProfilErgebnisse(Suchprofil suchprofil, AsyncCallback<ArrayList<Profil>> callback);

	/**
	 * Info-Objekt Methoden Asynchron
	 * 
	 * @param p
	 * @param callback
	 */
	void createInfo(Profil p, AsyncCallback<Info> callback);

	/**
	 * 
	 * @param callback
	 */
	void getAllInfos(AsyncCallback<ArrayList<Info>> callback);

	/**
	 * 
	 * @param id
	 * @param callback
	 */
	void getInfoByID(int id, AsyncCallback<Info> callback);

	/**
	 * 
	 * @param info
	 * @param callback
	 */
	void updateInfo(Info info, AsyncCallback<Info> callback);

	/**
	 * 
	 * @param info
	 * @param callback
	 */
	void deleteInfo(Info info, AsyncCallback<Info> callback);

	/**
	 * Eigenschaft Methoden Asynchron
	 * 
	 * @param info
	 * @param callback
	 */
	void createEigenschaft(Info info, AsyncCallback<Eigenschaft> callback);

	/**
	 * 
	 * @param callback
	 */
	void getAllEigenschaften(AsyncCallback<ArrayList<Eigenschaft>> callback);

	/**
	 * 
	 * @param id
	 * @param callback
	 */
	void getEigenschaftByID(int id, AsyncCallback<Eigenschaft> callback);

	/**
	 * 
	 * @param eigenschaft
	 * @param callback
	 */
	void updateEigenschaft(Eigenschaft eigenschaft, AsyncCallback<Eigenschaft> callback);

	/**
	 * 
	 * @param eigenschaft
	 * @param callback
	 */
	void deleteEigenschaft(Eigenschaft eigenschaft, AsyncCallback<Eigenschaft> callback);

	/**
	 * Freitext Methoden Asynchron
	 * 
	 * @param eigenschaft
	 * @param text
	 * @param callback
	 */
	void createFreitext(Eigenschaft eigenschaft, String text, AsyncCallback<Freitext> callback);

	/**
	 * 
	 * @param callback
	 */
	void getFreitext(AsyncCallback<Freitext> callback);

	/**
	 * 
	 * @param freitext
	 * @param callback
	 */
	void updateFreitext(Freitext freitext, AsyncCallback<Freitext> callback);

	/**
	 * 
	 * @param freitext
	 * @param callback
	 */
	void deleteFreitext(Freitext freitext, AsyncCallback<Freitext> callback);

	/**
	 * Auswahl Methoden Asynchron
	 * 
	 * @param eigenschaft
	 * @param title
	 * @param callback
	 */
	void createAuswahl(Eigenschaft eigenschaft, String title, AsyncCallback<Auswahl> callback);

	/**
	 * 
	 * @param callback
	 */
	void getAuswahl(AsyncCallback<ArrayList<Auswahl>> callback);

	/**
	 * 
	 * @param auswahl
	 * @param callback
	 */
	void updateAuswahl(Auswahl auswahl, AsyncCallback<Auswahl> callback);

	/**
	 * 
	 * @param auswahl
	 * @param callback
	 */
	void deleteAuswahl(Auswahl auswahl, AsyncCallback<Auswahl> callback);

	/**
	 * Element Methoden Asynchron
	 * 
	 * @param callback
	 */
	void getAllElements(AsyncCallback<ArrayList<Element>> callback);

	/**
	 * 
	 * @param callback
	 */
	void getElement(AsyncCallback<Element> callback);

}
