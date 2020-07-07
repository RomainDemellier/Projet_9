package com.dummy.myerp.business.impl.manager;

import java.math.BigDecimal;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;

import static org.assertj.core.api.Assertions.*;

public class ComptabiliteManagerImplTest {

	private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

	@Test
	public void checkEcritureComptableUnit() throws Exception {
		EcritureComptable vEcritureComptable;
		vEcritureComptable = new EcritureComptable();
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new Date());
		vEcritureComptable.setLibelle("Libelle");
		vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
				null, new BigDecimal(123),
				null));
		vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
				null, null,
				new BigDecimal(123)));
		manager.checkEcritureComptableUnit(vEcritureComptable);
	}

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableUnitViolation() throws Exception {
		EcritureComptable vEcritureComptable;
		vEcritureComptable = new EcritureComptable();
		manager.checkEcritureComptableUnit(vEcritureComptable);
	}

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableUnitRG2() throws Exception {
		EcritureComptable vEcritureComptable;
		vEcritureComptable = new EcritureComptable();
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new Date());
		vEcritureComptable.setLibelle("Libelle");
		vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
				null, new BigDecimal(123),
				null));
		vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
				null, null,
				new BigDecimal(1234)));
		manager.checkEcritureComptableUnit(vEcritureComptable);
	}

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableUnitRG3() throws Exception {
		EcritureComptable vEcritureComptable;
		vEcritureComptable = new EcritureComptable();
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new Date());
		vEcritureComptable.setLibelle("Libelle");
		vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
				null, new BigDecimal(123),
				null));
		vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
				null, new BigDecimal(123),
				null));
		manager.checkEcritureComptableUnit(vEcritureComptable);
	}

	@Test
	public void checkEcritureComptableUnitRG5Format() throws Exception {
		EcritureComptable vEcritureComptable;
		vEcritureComptable = new EcritureComptable();
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new Date());
		vEcritureComptable.setLibelle("libelle");
		vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
				null, new BigDecimal(123),
				null));
		vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
				null, null,
				new BigDecimal(123)));
		vEcritureComptable.setReference("AC-2020/00001");

		manager.checkEcritureComptableUnit(vEcritureComptable);
	}

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableUnitRG5Code() throws Exception {
		EcritureComptable vEcritureComptable;
		vEcritureComptable = new EcritureComptable();
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new Date());
		vEcritureComptable.setLibelle("libelle");
		vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
				null, new BigDecimal(123),
				null));
		vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
				null, null,
				new BigDecimal(123)));
		vEcritureComptable.setReference("AB-2020/00001");

		manager.checkEcritureComptableUnit(vEcritureComptable);
	}

	@Test(expected = FunctionalException.class)
	public void checkEcritureComptableUnitRG5Annee() throws Exception {
		EcritureComptable vEcritureComptable;
		vEcritureComptable = new EcritureComptable();
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new Date());
		vEcritureComptable.setLibelle("libelle");
		vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
				null, new BigDecimal(123),
				null));
		vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
				null, null,
				new BigDecimal(123)));
		vEcritureComptable.setReference("AC-2021/00001");

		manager.checkEcritureComptableUnit(vEcritureComptable);
	}

}
