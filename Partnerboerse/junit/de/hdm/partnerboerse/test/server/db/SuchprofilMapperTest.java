package de.hdm.partnerboerse.test.server.db;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.hdm.partnerboerse.server.db.SuchprofilMapper;
import de.hdm.partnerboerse.shared.bo.Suchprofil;

public class SuchprofilMapperTest {

	@Test
	public void testInsertSuchprofil() {
		SuchprofilMapper mapper = SuchprofilMapper.suchprofilMapper();
		Suchprofil suchprofil = getTestSuchprofilObjekt();
		mapper.insertSuchprofil(suchprofil);
	}

	@Test
	public void testUpdateSuchprofil() {
		SuchprofilMapper mapper = SuchprofilMapper.suchprofilMapper();

		Suchprofil testObjekt = getTestSuchprofilObjekt();
		mapper.insertSuchprofil(testObjekt);

		List<Suchprofil> allSuchprofile = mapper.findAll();
		Suchprofil neuestesSuchprofilObjektAusDerDb = allSuchprofile.get(allSuchprofile.size() - 1);
		int idDesObjektes = neuestesSuchprofilObjektAusDerDb.getId();

		String updateTitel = "TestObjekt";
		neuestesSuchprofilObjektAusDerDb.setTitle(updateTitel);
		mapper.updateSuchprofil(neuestesSuchprofilObjektAusDerDb);

		Suchprofil geupdatetesSuchprofil = mapper.findByKey(idDesObjektes);
		assertTrue(updateTitel.equals(geupdatetesSuchprofil.getTitel()));

	}

	private Suchprofil getTestSuchprofilObjekt() {
		Suchprofil suchprofil = new Suchprofil();
		suchprofil.setId(1);
		suchprofil.setHaarFarbe("blond");
		suchprofil.setEigenprofilID(1);
		suchprofil.setAlter(20);
		suchprofil.setKoerpergroesse(123);
		suchprofil.setRaucher(false);
		suchprofil.setReligion("evangelisch");
		suchprofil.setTitle("TestSuchprofil");

		return suchprofil;
	}

}
