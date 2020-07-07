package com.dummy.myerp.model.bean.comptabilite;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

import org.junit.Test;

public class CompteComptableTest {

	@Test
	public void retourneNullSiNeTrouvePasNumero() {
		List<CompteComptable> cList = new ArrayList<>();
		
		CompteComptable c1 = new CompteComptable(1, "compte 1"); 
		CompteComptable c2 = new CompteComptable(2, "compte 2"); 
		CompteComptable c3 = new CompteComptable(3, "compte 3"); 
		
		cList.add(c1);
		cList.add(c2);
		cList.add(c3);
		
		CompteComptable c = CompteComptable.getByNumero(cList, 4);
		
		assertThat(c).isEqualTo(null);
	}
	
	@Test
	public void retourneCompteSiTrouveNumero() {
		List<CompteComptable> cList = new ArrayList<>();
		
		CompteComptable c1 = new CompteComptable(1, "compte 1"); 
		CompteComptable c2 = new CompteComptable(2, "compte 2"); 
		CompteComptable c3 = new CompteComptable(3, "compte 3"); 
		
		cList.add(c1);
		cList.add(c2);
		cList.add(c3);
		
		CompteComptable c = CompteComptable.getByNumero(cList, 2);
		
		assertThat(c).isEqualTo(c2);
	}
}
