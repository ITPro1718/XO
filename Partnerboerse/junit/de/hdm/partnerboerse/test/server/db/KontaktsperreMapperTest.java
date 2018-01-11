package de.hdm.partnerboerse.test.server.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.hdm.partnerboerse.server.db.KontaktsperreMapper;
import de.hdm.partnerboerse.shared.bo.Kontaktsperre;

public class KontaktsperreMapperTest {

	@Test
	public void testInsertKontaktsperre() {
		KontaktsperreMapper mapper = KontaktsperreMapper.kontaktsperreMapper();
		Kontaktsperre kontaktsperre = getTestKontaktsperreObjekt();

	}

	@Test
	public void testDeleteKontaktsperre() {
		KontaktsperreMapper mapper = KontaktsperreMapper.kontaktsperreMapper();
		Kontaktsperre kontaktsperre = getTestKontaktsperreObjekt();

		List<Kontaktsperre> allKontaktsperren = mapper.findAll();
		if (!allKontaktsperren.isEmpty()) {
			int zuloeschendeId = allKontaktsperren.get(0).getId();
			kontaktsperre.setId(zuloeschendeId);
			mapper.deleteKontaktsperreEintrag(kontaktsperre);
			assertNull(mapper.findByKey(zuloeschendeId));

			mapper.insertKontaktsperreEintrag(kontaktsperre);
		}
	}

	@Test
	public void testFindByKey() {
		assertTrue(KontaktsperreMapper.kontaktsperreMapper().findByKey(1) != null);
	}

	@Test
	public void testFindAll() {
		KontaktsperreMapper mapper = KontaktsperreMapper.kontaktsperreMapper();
		assertFalse(mapper.findAll().isEmpty());

	}

	private Kontaktsperre getTestKontaktsperreObjekt() {
		Kontaktsperre kontaktsperre = new Kontaktsperre();
		kontaktsperre.setId(1);
		kontaktsperre.setEigenprofilID(1);
		kontaktsperre.setFremdprofilID(2);

		return kontaktsperre;
	}

}
