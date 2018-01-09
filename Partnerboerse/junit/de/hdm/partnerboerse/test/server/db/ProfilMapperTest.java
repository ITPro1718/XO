package de.hdm.partnerboerse.test.server.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
