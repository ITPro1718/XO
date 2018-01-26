package de.hdm.partnerboerse.shared;

import java.util.ArrayList;

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

	void createAuswahl(Auswahl auswahl, AsyncCallback<Auswahl> callback);

	void createBesuch(Profil source, Profil target, AsyncCallback<Void> callback);

	void createEigenschaftForAuswahl(Profil p, Info i, Auswahl a, AsyncCallback<Void> callback);

	void createEigenschaftForFreitext(Profil p, Info i, Freitext f, AsyncCallback<Void> callback);

	void createFreitext(Freitext freitext, AsyncCallback<Freitext> callback);

	void createInfo(Profil profil, String text, Eigenschaft eigenschaft, AsyncCallback<Info> callback);

	void createInfo(Suchprofil suchprofil, String text, Eigenschaft eigenschaft, AsyncCallback<Info> callback);

	void createInfoForAuswahl(Info info, Auswahl auswahl, AsyncCallback<Info> callback);

	void createKontaksperreEintrag(Profil source, Profil target, AsyncCallback<Void> callback);

	void createMerkzettelEintrag(Profil source, Profil target, AsyncCallback<Void> callback);

	void createProfil(Profil p, AsyncCallback<Profil> callback);

	void createSuchprofil(Profil source, String titel, String haarfarbe, float kgr, boolean raucher, String religion,
			int alter, AsyncCallback<Suchprofil> callback);

	void deleteAuswahl(Auswahl auswahl, AsyncCallback<Void> callback);

	void deleteBesuch(Besuch besuch, AsyncCallback<Void> callback);

	void deleteFreitext(Freitext freitext, AsyncCallback<Void> callback);

	void deleteInfo(Info info, AsyncCallback<Void> callback);

	void deleteInfoOfEigenschaft(Eigenschaft eigenschaft, Profil p, AsyncCallback<Void> callback);

	void deleteInfoOfEigenschaft(Eigenschaft eigenschaft, Suchprofil sp, AsyncCallback<Void> callback);

	void deleteKontaktsperreEintraege(Kontaktsperre kontaktsperre, AsyncCallback<Void> callback);

	void deleteMerkzettelEintrag(Merkzettel merkzettel, AsyncCallback<Void> callback);

	void deleteProfil(Profil p, AsyncCallback<Void> callback);

	void deleteSuchprofil(Suchprofil suchprofil, AsyncCallback<Void> callback);

	void findAllBesuche(AsyncCallback<ArrayList<Besuch>> callback);

	void findAuswahlByTitle(Auswahl auswahl, AsyncCallback<Auswahl> callback);

	void findAuswahlOf(Info info, AsyncCallback<Auswahl> callback);

	void findBesuchByKey(int id, AsyncCallback<Besuch> callback);

	void findBesucheOf(Profil profilowner, AsyncCallback<ArrayList<Besuch>> callback);

	void findFreitextOf(Info info, AsyncCallback<Freitext> callback);

	void findInfoOf(Profil profilowner, AsyncCallback<ArrayList<Info>> callback);

	void findInfoOfEigenschaft(Eigenschaft eigenschaft, AsyncCallback<Info> callback);

	void findKontaktsperrenOf(Profil profilowner, AsyncCallback<ArrayList<Kontaktsperre>> callback);

	void findMerkzettelnOf(Profil profilowner, AsyncCallback<ArrayList<Merkzettel>> callback);

	void findSuchprofileOf(Profil profilowner, AsyncCallback<ArrayList<Suchprofil>> callback);

	void getAllEigenschaften(AsyncCallback<ArrayList<Eigenschaft>> callback);

	void getAllEigenschaftenOf(Profil profil, AsyncCallback<ArrayList<Eigenschaft>> callback);

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

	void getNotSeenPartnervorschläge(Profil profil, AsyncCallback<ArrayList<Profil>> callback);

	void getProfilByEmail(String email, AsyncCallback<Profil> callback);

	void getProfilByID(int id, AsyncCallback<Profil> callback);

	void getProfilByName(String name, AsyncCallback<ArrayList<Profil>> callback);

	void getProfileForKontaktsperre(Profil eigenProfil, AsyncCallback<ArrayList<Profil>> callback);

	void getProfileForMerkzettel(Profil eigenProfil, AsyncCallback<ArrayList<Profil>> callback);

	void getSuchProfilErgebnisse(Suchprofil suchprofil, AsyncCallback<ArrayList<Profil>> callback);

	void getSuchprofilByID(int id, AsyncCallback<Suchprofil> callback);

	void getSuchprofilByTitle(String title, AsyncCallback<Suchprofil> callback);

	void init(AsyncCallback<Void> callback);

	void updateAuswahl(Profil profil, Info info, AsyncCallback<Void> callback);

	void updateEigenschaft(Eigenschaft eigenschaft, AsyncCallback<Void> callback);

	void updateFreitext(Profil profil, Info info, AsyncCallback<Void> callback);

	void updateInfo(Info info, AsyncCallback<Void> callback);

	void updateProfil(Profil p, AsyncCallback<Void> callback);

	void updateSuchprofil(Suchprofil suchprofil, AsyncCallback<Void> callback);

    void findInfoOf(Suchprofil suchprofil, AsyncCallback<ArrayList<Info>> callback);

}
