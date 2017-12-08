package de.hdm.partnerboerse.server.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.hdm.partnerboerse.shared.bo.Freitext;

public class FreitextMapperTest {

	@Test
	public void testFindAll() {
		assertFalse(FreitextMapper.freitextMapper().findAll().isEmpty());
	}

	@Test
	public void testFindByKey() {
		assertTrue(FreitextMapper.freitextMapper().findByKey(1) != null);
	}

	@Test
	public void testInsert() {
		FreitextMapper freitextMapper = FreitextMapper.freitextMapper();
		Freitext freitext = getFreitextTestObjekt();
		freitextMapper.insertFreitext(freitext);
	}

	@Test
	public void testDelete() {
		FreitextMapper freitextMapper = FreitextMapper.freitextMapper();
		Freitext freitext = getFreitextTestObjekt();
		freitextMapper.insertFreitext(freitext);

		List<Freitext> alleFreitexte = freitextMapper.findAll();
		int anzahlVorher = alleFreitexte.size();
		if (!alleFreitexte.isEmpty()) {
			freitextMapper.deleteFreitext(alleFreitexte.get(alleFreitexte.size() - 1));
		}
		int anzahlNacher = freitextMapper.findAll().size();
		assertTrue(anzahlVorher > anzahlNacher);
	}

	@Test
	public void testUpdate() {
		FreitextMapper mapper = FreitextMapper.freitextMapper();
		// Neues Testobjekt anlegen
		Freitext testObjekt = getFreitextTestObjekt();
		mapper.insertFreitext(testObjekt);

		// Das angelegte Objekt wieder aus der Datenbank auslesen
		List<Freitext> all = mapper.findAll();
		Freitext neuestesAuswahlObjektAusDb = all.get(all.size() - 1);
		int idDesObjektes = neuestesAuswahlObjektAusDb.getId();

		// Das Objekt Updaten
		String updateTitel = "UpdateTest";
		neuestesAuswahlObjektAusDb.setBeschreibung(updateTitel);
		mapper.updateFreitext(neuestesAuswahlObjektAusDb);

		// Das Objekt wieder auselsen um zu prüfen ob der neue Wert in der DB
		// steht.
		Freitext geupdateteAuswahl = mapper.findByKey(idDesObjektes);
		assertTrue(updateTitel.equals(geupdateteAuswahl.getBeschreibung()));
	}

	private Freitext getFreitextTestObjekt() {
		Freitext freitext = new Freitext();
		freitext.setId(1);
		freitext.setBeschreibung("Testbeschreibung");
		return freitext;
	}
}
