package de.hdm.partnerboerse.server.db;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.hdm.partnerboerse.shared.bo.Eigenschaft;

public class EigenschaftMapperTest {

	@Test
	public void testInsertEigenschaft() {
		EigenschaftMapper mapper = EigenschaftMapper.eigenschaftMapper();
		Eigenschaft eigenschaft = getTestEigenschaftObjekt();
		mapper.insertEigenschaft(eigenschaft);

	}

	@Test
	public void testUpdateAuswahl() {
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

	private Eigenschaft getTestEigenschaftObjekt() {
		Eigenschaft eigenschaft = new Eigenschaft();
		eigenschaft.setId(1);
		eigenschaft.setErlaeuterung("Testeigenschaft");

		return eigenschaft;

	}

}