package de.hdm.partnerboerse.test.server.db;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.hdm.partnerboerse.server.db.BesuchMapper;
import de.hdm.partnerboerse.shared.bo.Besuch;

public class BesuchMapperTest {

	@Test
	public void testFindByKey() {
		assertTrue(BesuchMapper.besuchMapper().findByKey(1) != null);
	}

	private Besuch getTestBesuchObjekt() {
		Besuch besuch = new Besuch();
		besuch.setId(1);
		besuch.setEigenprofilID(1);
		besuch.setFremdprofilID(2);

		return besuch;
	}

}
