package de.hdm.partnerboerse.server.db;

import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class ElementMapperTest {

	@Test
	public void testFindAll() {
		ElementMapper mapper = ElementMapper.elementMapper();
		assertFalse(mapper.findAll().isEmpty());
	}

}
