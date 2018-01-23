package de.hdm.partnerboerse.test.server.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.hdm.partnerboerse.server.db.MerkzettelMapper;
import de.hdm.partnerboerse.shared.bo.Merkzettel;

public class MerkzettelMapperTest {

	@Test
	public void testInsertMerkzettel() {

		MerkzettelMapper mapper = MerkzettelMapper.merkzettelMapper();
		Merkzettel merkzettel = getTestMerkzettelObjekt();
		mapper.insertMerkzettelEintrag(merkzettel);

	}

	@Test
	public void testDeleteMerkzettel() {
		MerkzettelMapper mapper = MerkzettelMapper.merkzettelMapper();
		Merkzettel merkzettel = getTestMerkzettelObjekt();

		List<Merkzettel> allMerkzettel = mapper.findAll();
		if (!allMerkzettel.isEmpty()) {
			int zuloeschendeId = allMerkzettel.get(allMerkzettel.size() - 1).getId();
			merkzettel.setId(zuloeschendeId);
			mapper.deleteMerkzettelEintrag(merkzettel);
			assertNull(mapper.findByKey(zuloeschendeId));

			mapper.insertMerkzettelEintrag(merkzettel);
		}
	}

	@Test
	public void testFindByKey() {
		assertTrue(MerkzettelMapper.merkzettelMapper().findByKey(3) != null);

	}

	@Test
	public void testFindAll() {
		MerkzettelMapper mapper = MerkzettelMapper.merkzettelMapper();
		assertFalse(mapper.findAll().isEmpty());

	}

	private Merkzettel getTestMerkzettelObjekt() {
		Merkzettel merkzettel = new Merkzettel();
		merkzettel.setId(1);
		merkzettel.setEigenprofilID(1);
		merkzettel.setFremdprofilID(2);

		return merkzettel;

	}

}
