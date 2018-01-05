package de.hdm.partnerboerse.test.server.db;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.hdm.partnerboerse.server.db.EigenschaftMapper;
import de.hdm.partnerboerse.shared.bo.Eigenschaft;

public class EigenschaftMapperTest {

	@Test
	public void testInsertEigenschaft() {
		EigenschaftMapper mapper = EigenschaftMapper.eigenschaftMapper();
		Eigenschaft eigenschaft = getTestEigenschaftObjekt();
		mapper.insertEigenschaft(eigenschaft);

	}

	@Test
	public void testUpdateEigenschaft() {
		EigenschaftMapper mapper = EigenschaftMapper.eigenschaftMapper();
		// Neues Testobjekt anlegen
		Eigenschaft testObjekt = getTestEigenschaftObjekt();
		mapper.insertEigenschaft(testObjekt);

		// Das angelegte Objekt wieder aus der Datenbank auslesen
		List<Eigenschaft> all = mapper.findAll();
		Eigenschaft neuestesEigenschaftObjektAusDb = all.get(all.size() - 1);
		int idDesObjektes = neuestesEigenschaftObjektAusDb.getId();

		// Das Objekt Updaten
		String updateErlaeuterung = "UpdateTest";
		neuestesEigenschaftObjektAusDb.setErlaeuterung(updateErlaeuterung);
		mapper.updateEigenschaft(neuestesEigenschaftObjektAusDb);

		// Das Objekt wieder auselsen um zu prüfen ob der neue Wert in der DB
		// steht.
		Eigenschaft geupdateteEigenschaft = mapper.findByKey(idDesObjektes);
		assertTrue(updateErlaeuterung.equals(geupdateteEigenschaft.getErlaeuterung()));

	}

	@Test
	public void testDeleteEigenschaft() {
		EigenschaftMapper mapper = EigenschaftMapper.eigenschaftMapper();
		Eigenschaft eigenschaft = getTestEigenschaftObjekt();

		List<Eigenschaft> allEigenschaften = mapper.findAll();
		if (!allEigenschaften.isEmpty()) {
			int zuloeschendeId = allEigenschaften.get(0).getId();
			eigenschaft.setId(zuloeschendeId);
			mapper.deleteEigenschaft(eigenschaft);
			assertNull(mapper.findByKey(zuloeschendeId));

			mapper.insertEigenschaft(eigenschaft);
		}
	}

	@Test
	public void testFindByKey() {
		assertTrue(EigenschaftMapper.eigenschaftMapper().findByKey(3) != null);
	}

	private Eigenschaft getTestEigenschaftObjekt() {
		Eigenschaft eigenschaft = new Eigenschaft();
		eigenschaft.setId(1);
		eigenschaft.setErlaeuterung("Testeigenschaft");
		eigenschaft.setIs_a("Auswahl");
		eigenschaft.setAuswahlID(10);
		// TODO Eigenschaft sollte nicht zu beiden Fremdschlüsseln eine
		// Beziehung haben MÜSSEN
		eigenschaft.setFreitextID(1);

		return eigenschaft;

	}

}
