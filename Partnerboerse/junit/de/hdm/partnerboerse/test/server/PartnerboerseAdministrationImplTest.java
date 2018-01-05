package de.hdm.partnerboerse.test.server;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import de.hdm.partnerboerse.server.PartnerboerseAdministrationImpl;

public class PartnerboerseAdministrationImplTest {

	@Test
	public void testGetAge() {
		PartnerboerseAdministrationImpl adminImpl = new PartnerboerseAdministrationImpl();

		Calendar cal = Calendar.getInstance();
		cal.set(2000, 1, 1);
		Date date = cal.getTime();

		int age = adminImpl.getAge(date);
		assertTrue(age == 18);

		assertFalse(age == 20);

	}
}
