package de.hdm.partnerboerse.server.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.hdm.partnerboerse.shared.bo.Auswahl;

public class AuswahlMapperTest {

	public AuswahlMapperTest() {
		DBConnection.getConnection();
	}

	@Test
	public void testFindAll() {
		AuswahlMapper mapper = AuswahlMapper.auswahlMapper();
		assertFalse(mapper.findAll().isEmpty());
	}

	@Test
	public void testInsertAuswahl() {
		AuswahlMapper mapper = AuswahlMapper.auswahlMapper();
		Auswahl auswahl = getTestAuswahlObjekt();
		mapper.insertAuswahl(auswahl);
	}

	@Test
	public void testDeleteAuswahl() {
		AuswahlMapper mapper = AuswahlMapper.auswahlMapper();
		Auswahl auswahl = getTestAuswahlObjekt();

		List<Auswahl> allAuswahlen = mapper.findAll();
		if (!allAuswahlen.isEmpty()) {
			int zuloeschendeId = allAuswahlen.get(0).getId();
			auswahl.setId(zuloeschendeId);
			mapper.deleteAuswahl(auswahl);
			assertNull(mapper.findByKey(zuloeschendeId));

			mapper.insertAuswahl(auswahl);
		}
	}

	@Test
	public void testUpdateAuswahl() {
		AuswahlMapper mapper = AuswahlMapper.auswahlMapper();
		// Neues Testobjekt anlegen
		Auswahl testObjekt = getTestAuswahlObjekt();
		mapper.insertAuswahl(testObjekt);

		// Das angelegte Objekt wieder aus der Datenbank auslesen
		List<Auswahl> all = mapper.findAll();
		Auswahl neuestesAuswahlObjektAusDb = all.get(all.size() - 1);
		int idDesObjektes = neuestesAuswahlObjektAusDb.getId();

		// Das Objekt Updaten
		String updateTitel = "UpdateTest";
		neuestesAuswahlObjektAusDb.setTitel(updateTitel);
		mapper.updateAuswahl(neuestesAuswahlObjektAusDb);

		// Das Objekt wieder auselsen um zu prüfen ob der neue Wert in der DB
		// steht.
		Auswahl geupdateteAuswahl = mapper.findByKey(idDesObjektes);
		assertTrue(updateTitel.equals(geupdateteAuswahl.getTitel()));

	}

	private Auswahl getTestAuswahlObjekt() {
		Auswahl auswahl = new Auswahl();
		auswahl.setId(0);
		auswahl.setTitel("Testauswahl");
		return auswahl;
	}
}
