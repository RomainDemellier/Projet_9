package com.dummy.myerp.consumer.db;

import java.util.List;

import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import com.dummy.myerp.consumer.config.SpringRegistry;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;

import static org.assertj.core.api.Assertions.*;

public class ComptabiliteDaoTest {

	private ComptabiliteDao comptabiliteDao;
	
	@Test
	public void checkGetListCompteComptable() {
		
		SpringRegistry.init();
		
		comptabiliteDao = ComptabiliteDaoImpl.getInstance();
		
		List<CompteComptable> cList = comptabiliteDao.getListCompteComptable();
		
		int n = cList.size();
		
		assertThat(n).isEqualTo(7);
	}
}
 