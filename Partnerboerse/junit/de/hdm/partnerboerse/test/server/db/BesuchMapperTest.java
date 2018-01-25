package de.hdm.partnerboerse.test.server.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.hdm.partnerboerse.server.db.BesuchMapper;
import de.hdm.partnerboerse.shared.bo.Besuch;

public class BesuchMapperTest {

	@Test
	public void testFindByKey() {
		assertTrue(BesuchMapper.besuchMapper().findByKey(4) != null);
	}

	@Test
	public void testFindAll() {
		BesuchMapper mapper = BesuchMapper.besuchMapper();
		assertFalse(mapper.findAll().isEmpty());
	}

	@Test
	public void testInsertBesuch() {
		BesuchMapper mapper = BesuchMapper.besuchMapper();
		Besuch besuch = getTestBesuchObjekt();
		mapper.insertBesuch(besuch);
	}

	@Test
	public void testDeleteBesuch() {
		BesuchMapper mapper = BesuchMapper.besuchMapper();
		Besuch besuch = getTestBesuchObjekt();

		List<Besuch> allBesuche = mapper.findAll();
		if (!allBesuche.isEmpty()) {
			int zuloeschendeId = allBesuche.get(allBesuche.size() - 1).getId();
			besuch.setId(zuloeschendeId);
			mapper.deleteBesuch(besuch);
			assertNull(mapper.findByKey(zuloeschendeId));

			mapper.insertBesuch(besuch);
		}
	}

	private Besuch getTestBesuchObjekt() {
		Besuch besuch = new Besuch();
		besuch.setId(1);
		besuch.setEigenprofilID(1);
		besuch.setFremdprofilID(2);

		return besuch;
	}

}
