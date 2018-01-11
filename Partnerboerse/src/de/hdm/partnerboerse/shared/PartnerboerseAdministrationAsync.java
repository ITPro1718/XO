package de.hdm.partnerboerse.shared;

import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

import de.hdm.partnerboerse.shared.bo.Auswahl;
import de.hdm.partnerboerse.shared.bo.Besuch;
import de.hdm.partnerboerse.shared.bo.Eigenschaft;
import de.hdm.partnerboerse.shared.bo.Freitext;
import de.hdm.partnerboerse.shared.bo.Info;
import de.hdm.partnerboerse.shared.bo.Kontaktsperre;
import de.hdm.partnerboerse.shared.bo.Merkzettel;
import de.hdm.partnerboerse.shared.bo.Profil;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

public interface PartnerboerseAdministrationAsync {

	void berechneAehnlichkeitsmass(Profil source, Suchprofil suchprofil, AsyncCallback<ArrayList<Profil>> callback);

	void createAuswahl(String title, AsyncCallback<Auswahl> callback);

	void createBesuch(Profil source, Profil target, AsyncCallback<Void> callback);

	void createEigenschaft(String erlaueterung, Profil profil, AsyncCallback<Void> callback);

	void createFreitext(String text, AsyncCallback<Freitext> callback);

	void createInfo(String bezeichnung, String is_a, String string, AsyncCallback<Void> callback);

	void createKontaksperreEintrag(Profil source, Profil target, AsyncCallback<Void> callback);

	void createMerkzettelEintrag(Profil source, Profil target, AsyncCallback<Void> callback);

	void createProfil(int id, String vname, String nname, String haarfarbe, float kgr, boolean raucher, String religion,
			Date geb, String pw, String email, AsyncCallback<Void> callback);

	void createSuchprofil(Profil source, String titel, String haarfarbe, float kgr, boolean raucher, String religion,
			int alter, AsyncCallback<Void> callback);

	void deleteAuswahl(Auswahl auswahl, AsyncCallback<Void> callback);

	void deleteBesuch(Besuch besuch, AsyncCallback<Void> callback);

	void deleteEigenschaft(Eigenschaft eigenschaft, AsyncCallback<Void> callback);

	void deleteFreitext(Freitext freitext, AsyncCallback<Void> callback);

	void deleteInfo(Info info, AsyncCallback<Void> callback);

	void deleteKontaktsperreEintraege(Kontaktsperre kontaktsperre, AsyncCallback<Void> callback);

	void deleteMerkzettelEintrag(Merkzettel merkzettel, AsyncCallback<Void> callback);

	void deleteProfil(Profil p, AsyncCallback<Void> callback);

	void deleteSuchprofil(Suchprofil suchprofil, AsyncCallback<Void> callback);

	void findAllBesuche(AsyncCallback<ArrayList<Besuch>> callback);

	void findAuswahlOf(Info info, AsyncCallback<Auswahl> callback);

	void findBesuchByKey(int id, AsyncCallback<Besuch> callback);

	void findBesucheOf(Profil profilowner, AsyncCallback<ArrayList<Besuch>> callback);

	void findEigenschaftsInfosOf(Eigenschaft eigenschaft, AsyncCallback<ArrayList<Info>> callback);

	void findFreitextOf(Info info, AsyncCallback<Freitext> callback);

	void findInfoOf(Profil profilowner, AsyncCallback<ArrayList<Info>> callback);

	void findKontaktsperrenOf(Profil profilowner, AsyncCallback<ArrayList<Kontaktsperre>> callback);

	void findMerkzettelnOf(Profil profilowner, AsyncCallback<ArrayList<Merkzettel>> callback);

	void findSuchprofileOf(Profil profilowner, AsyncCallback<ArrayList<Suchprofil>> callback);

	void getAllEigenschaften(AsyncCallback<ArrayList<Eigenschaft>> callback);

	void getAllInfos(AsyncCallback<ArrayList<Info>> callback);

	void getAllKontaktsperreEintraege(AsyncCallback<ArrayList<Kontaktsperre>> callback);

	void getAllMerkzettelEintraege(AsyncCallback<ArrayList<Merkzettel>> callback);

	void getAllProfils(AsyncCallback<ArrayList<Profil>> callback);

	void getAllSuchprofile(AsyncCallback<ArrayList<Suchprofil>> callback);

	void getAuswahl(AsyncCallback<ArrayList<Auswahl>> callback);

	void getEigenschaftByID(int id, AsyncCallback<Eigenschaft> callback);

	void getFreitext(Eigenschaft eigenschaft, AsyncCallback<Freitext> callback);

	void getInfoByID(int id, AsyncCallback<Info> callback);

	void getKontaktsperreEintragByID(int id, AsyncCallback<Kontaktsperre> callback);

	void getMerkzettelEintraegeByID(int ID, AsyncCallback<Merkzettel> callback);

	void getNotSeenSuchProfilErgebnisse(Suchprofil suchprofil, AsyncCallback<ArrayList<Profil>> callback);

	void getProfilByID(int id, AsyncCallback<Profil> callback);

	void getProfilByName(String name, AsyncCallback<ArrayList<Profil>> callback);

	void getSuchProfilErgebnisse(Suchprofil suchprofil, AsyncCallback<ArrayList<Profil>> callback);

	void getSuchprofilByID(int id, AsyncCallback<Suchprofil> callback);

	void getSuchprofilByTitle(String title, AsyncCallback<Suchprofil> callback);

	void init(AsyncCallback<Void> callback);

	void updateAuswahl(Auswahl auswahl, AsyncCallback<Void> callback);

	void updateEigenschaft(Eigenschaft eigenschaft, AsyncCallback<Void> callback);

	void updateFreitext(Freitext freitext, AsyncCallback<Void> callback);

	void updateInfo(Info info, AsyncCallback<Void> callback);

	void updateProfil(Profil p, AsyncCallback<Void> callback);

	void updateSuchprofil(Suchprofil suchprofil, AsyncCallback<Void> callback);

}
