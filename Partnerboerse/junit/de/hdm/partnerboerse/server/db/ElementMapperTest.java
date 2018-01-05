package de.hdm.partnerboerse.server.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ElementMapperTest {

	@Test
	public void testFindAll() {
		ElementMapper mapper = ElementMapper.elementMapper();
		assertFalse(mapper.findAll().isEmpty());
	}

	@Test
	public void testFindByKey() {
		assertTrue(ElementMapper.elementMapper().findByKey(1) != null);
	}

}
