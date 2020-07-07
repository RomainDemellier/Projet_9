package com.dummy.myerp.model.bean.comptabilite;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class JournalComptableTest {

	@Test
	public void retourneNullSiNeTrouvePasNumero() {
		List<JournalComptable> jList = new ArrayList<>();
		
		JournalComptable j1 = new JournalComptable("1", "journal 1"); 
		JournalComptable j2 = new JournalComptable("2", "journal 2"); 
		JournalComptable j3 = new JournalComptable("3", "journal 3"); 
		
		jList.add(j1);
		jList.add(j2);
		jList.add(j3);
		
		JournalComptable c = JournalComptable.getByCode(jList, "4");
		
		assertThat(c).isEqualTo(null);
	}
	
	@Test
	public void retournejournalSiTrouveNumero() {
		List<JournalComptable> jList = new ArrayList<>();
		
		JournalComptable j1 = new JournalComptable("1", "journal 1"); 
		JournalComptable j2 = new JournalComptable("2", "journal 2"); 
		JournalComptable j3 = new JournalComptable("3", "journal 3"); 
		
		jList.add(j1);
		jList.add(j2);
		jList.add(j3);
		
		JournalComptable j = JournalComptable.getByCode(jList, "3");
		
		assertThat(j).isEqualTo(j3);
	}
}
