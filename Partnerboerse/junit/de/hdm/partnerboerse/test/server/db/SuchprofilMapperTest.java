package de.hdm.partnerboerse.test.server.db;

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
