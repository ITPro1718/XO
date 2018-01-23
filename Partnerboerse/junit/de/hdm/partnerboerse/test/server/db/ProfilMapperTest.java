package de.hdm.partnerboerse.test.server.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
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

	@Test
	public void testDeleteProfil() {
		ProfilMapper mapper = ProfilMapper.profilMapper();
		Profil profil = getTestProfilObjekt();

		List<Profil> allProfil = mapper.findAllProfiles();
		if (!allProfil.isEmpty()) {
			int zuloeschendeId = allProfil.get(0).getId();
			mapper.delete(profil);
			assertNull(mapper.findProfilByKey(zuloeschendeId));

			mapper.insert(profil);
		}
	}

	private Profil getTestProfilObjekt() {
		Profil profil = new Profil();

		// SimpleDateFormat date = new SimpleDateFormat("yyyy-mm-dd");

		profil.setId(1);
		profil.setEmail("hello@horst.de");
		// profil.setPasswort("passwort123");
		profil.setNachname("Horst");
		profil.setVorname("Willie");
		profil.setHaarfarbe("gelb");
		// profil.setGeburtsdatum(2000-06-11);
		profil.setKoerpergroesse(156);
		profil.setRaucher(false);
		profil.setReligion("katholisch");

		return profil;
	}

}
