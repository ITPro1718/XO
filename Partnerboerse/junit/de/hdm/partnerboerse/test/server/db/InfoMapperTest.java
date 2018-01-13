package de.hdm.partnerboerse.test.server.db;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import de.hdm.partnerboerse.server.db.InfoMapper;
import de.hdm.partnerboerse.shared.bo.Info;

public class InfoMapperTest {

	@Test
	public void testFindByKey() {
		assertTrue(InfoMapper.infoMapper().findByKey(1) != null);
	}

	@Test
	public void testFindAll() {
		InfoMapper mapper = InfoMapper.infoMapper();
		assertFalse(mapper.findAll().isEmpty());
	}

	@Test
	public void testDeleteInfo() {
		InfoMapper mapper = InfoMapper.infoMapper();
		Info info = getTestInfoObjekt();

		List<Info> allInfos = mapper.findAll();
		if (!allInfos.isEmpty()) {
			int zuloeschendeId = allInfos.get(0).getId();
			info.setId(zuloeschendeId);
			mapper.deleteInfo(info);
			assertNull(mapper.findByKey(zuloeschendeId));

			mapper.insertInfo(info);
		}
	}

	@Test
	public void testInsertInfo() {
		InfoMapper mapper = InfoMapper.infoMapper();
		Info info = getTestInfoObjekt();
		mapper.insertInfo(info);
	}

	private Info getTestInfoObjekt() {
		Info info = new Info();
		info.setId(1);
		info.setText("Testinfo");
		// info.setEigenprofilID(1);
		// info.setEigenschaftsID(10);

		return info;

	}

}
