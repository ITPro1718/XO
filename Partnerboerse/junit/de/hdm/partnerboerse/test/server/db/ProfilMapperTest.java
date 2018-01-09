package de.hdm.partnerboerse.test.server.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.hdm.partnerboerse.server.db.ProfilMapper;
import de.hdm.partnerboerse.shared.bo.Profil;

public class ProfilMapperTest {

	@Test
	public void testFindByKey() {
		assertTrue(ProfilMapper.profilMapper().findProfilByKey(1) != null);
	}

	@Test
	public void testFindAll() {
		ProfilMapper mapper = ProfilMapper.profilMapper();
		assertFalse(mapper.findAllProfiles().isEmpty());
	}

	@Test
	public void testInsertProfil() {
		ProfilMapper mapper = ProfilMapper.profilMapper();
		Profil profil = getTestProfilObjekt();
		mapper.insert(profil);
	}

	@Test
	public void testUpdateProfil() {
		ProfilMapper mapper = ProfilMapper.profilMapper();
		// Neues Testobjekt anlegen
		Profil testObjekt = getTestProfilObjekt();
		mapper.insert(testObjekt);

		// Das angelegte Objekt wieder aus der Datenbank auslesen
		List<Profil> all = mapper.findAllProfiles();
		Profil neuestesProfilObjektAusDB = all.get(all.size() - 1);
		int idDesObjektes = neuestesProfilObjektAusDB.getId();

		// Das Objekt Updaten
		String updateVorname = "UpdateTest";
		neuestesProfilObjektAusDB.setVorname(updateVorname);
		mapper.update(neuestesProfilObjektAusDB);

		// Das Objekt wieder auslesen um zu prüfen ob der neue Wert in der DB
		// steht
		Profil geupdatetesProfil = mapper.findProfilByKey(idDesObjektes);
		assertTrue(updateVorname.equals(geupdatetesProfil.getVorname()));

	}

	private Profil getTestProfilObjekt() {
		Profil profil = new Profil();

		profil.setId(1);
		profil.setEmail("hallo@www.de");
		profil.setPasswort("passwort123");
		profil.setNachname("Horst");
		profil.setVorname("Willie");
		profil.setHaarfarbe("gelb");
		profil.setKoerpergroesse(156);
		profil.setRaucher(false);
		profil.setReligion("katholisch");

		return profil;
	}

}
