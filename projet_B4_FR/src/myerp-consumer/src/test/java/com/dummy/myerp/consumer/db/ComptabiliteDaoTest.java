package com.dummy.myerp.consumer.db;

//import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertTrue;

//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.equalTo;

//import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hamcrest.CoreMatchers.*;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

import com.dummy.myerp.consumer.config.SpringRegistry;
import com.dummy.myerp.consumer.dao.contrat.ComptabiliteDao;
import com.dummy.myerp.consumer.dao.impl.db.dao.ComptabiliteDaoImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
//@ContextConfiguration(locations = {"classpath:/com/dummy/myerp/consumer/applicationContextTest.xml"})
public class ComptabiliteDaoTest {

	private ComptabiliteDao comptabiliteDao;

	public ComptabiliteDaoTest() {
		// TODO Auto-generated constructor stub
		SpringRegistry.init();
		comptabiliteDao = ComptabiliteDaoImpl.getInstance();
	}
	
	@Test
	public void checkGetListCompteComptableSize() {
		List<CompteComptable> cList = comptabiliteDao.getListCompteComptable();
		int size = cList.size();
		
		assertThat(size).isEqualTo(7);
	}
	
	@Test
	public void checkGetListJournalComptableSize() {
		List<JournalComptable> jList = comptabiliteDao.getListJournalComptable();
		int size = jList.size();
		
		assertThat(size).isEqualTo(4);
	}
	
	@Test
	public void checkGetEcritureComptableById() throws ParseException, NotFoundException {
		int id = -4;
		EcritureComptable ecritureComptable = new EcritureComptable();
		ecritureComptable.setId(-4);
		ecritureComptable.setJournal(new JournalComptable("VE","Vente"));
		ecritureComptable.setLibelle("TMA Appli Yyy");
		ecritureComptable.setReference("VE-2016/00004");
		ecritureComptable.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2016-12-28"));
		
		LigneEcritureComptable ligne1 = new LigneEcritureComptable(new CompteComptable(411,"Clients"),"Facture C110004",new BigDecimal(5700),null);
		LigneEcritureComptable ligne2 = new LigneEcritureComptable(new CompteComptable(706,"Prestations de services"),"TMA Appli Xxx",null,new BigDecimal(4750));
		LigneEcritureComptable ligne3 = new LigneEcritureComptable(new CompteComptable(4457,"Taxes sur le chiffre d'affaires collectées par l'entreprise"),"TVA 20%",null,new BigDecimal(950));

		ecritureComptable.getListLigneEcriture().add(ligne1);
		ecritureComptable.getListLigneEcriture().add(ligne2);
		ecritureComptable.getListLigneEcriture().add(ligne3);
		
		EcritureComptable eComptableTest = comptabiliteDao.getEcritureComptable(-4);
		
		assertThat(eComptableTest).usingRecursiveComparison().isEqualTo(ecritureComptable);
	}
	
	@Test(expected = NotFoundException.class)
	public void checkGetEcritureComptableByIdNotFound() throws NotFoundException {
		comptabiliteDao.getEcritureComptable(28);
	}
	
	@Test
	public void checkGetEcritureComptableByRef() throws ParseException, NotFoundException {
		String ref = "BQ-2016/00005";
		
		EcritureComptable ecritureComptable = new EcritureComptable();
		ecritureComptable.setId(-5);
		ecritureComptable.setJournal(new JournalComptable("BQ","Banque"));
		ecritureComptable.setLibelle("Paiement Facture C110002");
		ecritureComptable.setReference("BQ-2016/00005");
		ecritureComptable.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2016-12-27"));
		
		LigneEcritureComptable ligne1 = new LigneEcritureComptable(new CompteComptable(512,"Banque"),null,new BigDecimal(3000),null);
		LigneEcritureComptable ligne2 = new LigneEcritureComptable(new CompteComptable(411,"Clients"),null,null,new BigDecimal(3000));

		ecritureComptable.getListLigneEcriture().add(ligne1);
		ecritureComptable.getListLigneEcriture().add(ligne2);
		
		EcritureComptable eComptableTest = comptabiliteDao.getEcritureComptableByRef(ref);
		
		assertThat(eComptableTest).usingRecursiveComparison().isEqualTo(ecritureComptable);
	}
	
	@Test(expected = NotFoundException.class)
	public void checkGetEcritureComptableByRefNotFound() throws NotFoundException {
		comptabiliteDao.getEcritureComptableByRef("AD-2018/00041");
	}
	
	@Test
	public void checkGetSequenceEcritureComptableByCodeAndAnnee() throws NotFoundException {
		String code = "OD";
		int annee = 2016;
		
		SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();
		sequenceEcritureComptable.setAnnee(2016);
		sequenceEcritureComptable.setJournalCode(code);
		sequenceEcritureComptable.setDerniereValeur(88);
		
		SequenceEcritureComptable sComptableTest = comptabiliteDao.getSequenceEcritureComptableByCodeAndByAnnee(code, annee);
		
		assertThat(sComptableTest).usingRecursiveComparison().isEqualTo(sequenceEcritureComptable);
	}
	
	@Test(expected = NotFoundException.class)
	public void checkGetSequenceEcritureComptableByCodeAndAnneeNotFound() throws NotFoundException {
		String code = "JS";
		int annee = 2014;
		
		comptabiliteDao.getSequenceEcritureComptableByCodeAndByAnnee(code, annee);
	} 

	@Test
	public void checkInsertSequenceEcritureComptable() throws NotFoundException {

		List<SequenceEcritureComptable> sList = new ArrayList<SequenceEcritureComptable>();
		sList = comptabiliteDao.getListSequenceEcritureComptable();
		int sizeBefore = sList.size();

		SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable();
		sequenceEcritureComptable.setJournalCode("VE");
		sequenceEcritureComptable.setAnnee(2020);
		sequenceEcritureComptable.setDerniereValeur(1);
		comptabiliteDao.insertSequenceEcritureComptable(sequenceEcritureComptable);

		sList = comptabiliteDao.getListSequenceEcritureComptable();

		int sizeExpected = sizeBefore + 1;

		assertThat(sList).hasSize(sizeExpected);

		try {
			SequenceEcritureComptable sequenceEcritureComptable2 = comptabiliteDao
					.getSequenceEcritureComptableByCodeAndByAnnee("VE", 2020);
			assertThat(sequenceEcritureComptable2).usingRecursiveComparison().isEqualTo(sequenceEcritureComptable);
			//assertThat(sequenceEcritureComptable2).isEqualTo(sequenceEcritureComptable);
		} catch (NotFoundException e) {
			// TODO: handle exception
			throw new NotFoundException("Cette séquence n'existe pas.");
		}

//		assertThat(sizeAfter).isEqualTo(sizeExpected);
 
	}

	@Test
	public void checkUpdateSequenceEcritureComptable() throws NotFoundException {
		SequenceEcritureComptable sequenceEcritureComptable = new SequenceEcritureComptable("BQ", 2016, 2);
		comptabiliteDao.updateSequenceEcritureComptable(sequenceEcritureComptable);
		try {
			sequenceEcritureComptable = comptabiliteDao.getSequenceEcritureComptableByCodeAndByAnnee("BQ", 2016);
			assertThat(sequenceEcritureComptable).extracting("derniereValeur").isEqualTo(2);
		} catch (Exception e) {
			// TODO: handle exception
			throw new NotFoundException("La séquence n'a pas été trouvée.");
		}
	}

	@Test
	@Transactional
	@Rollback
	public void checkInsertEcritureComptable() throws NotFoundException, ParseException {

		List<EcritureComptable> eList = new ArrayList<EcritureComptable>();
		eList = comptabiliteDao.getListEcritureComptable();
		int sizeBefore = eList.size();

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		calendar.set(2020, 6, 29);

		EcritureComptable vEcritureComptable;
		vEcritureComptable = new EcritureComptable();
		vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
		vEcritureComptable.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2019-12-31"));
		vEcritureComptable.setLibelle("Achat");
		
		LigneEcritureComptable ligneEcritureComptable1 = new LigneEcritureComptable(new CompteComptable(401, "Fournisseurs"), null, new BigDecimal(123), null);
		LigneEcritureComptable ligneEcritureComptable2 = new LigneEcritureComptable(new CompteComptable(411, "Clients"), null, null, new BigDecimal(123));
		
		vEcritureComptable.getListLigneEcriture().add(ligneEcritureComptable1);
		vEcritureComptable.getListLigneEcriture().add(ligneEcritureComptable2);
		vEcritureComptable.setReference("AC-2019/00001");
		
		

		comptabiliteDao.insertEcritureComptable(vEcritureComptable);

		eList = comptabiliteDao.getListEcritureComptable();

		int sizeExpected = sizeBefore + 1;

		//assertThat(eList).hasSize(sizeExpected);

		try {
			EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptableByRef("AC-2019/00001");
			assertThat(ecritureComptable).usingRecursiveComparison().isEqualTo(vEcritureComptable);
			//assertThat(ecritureComptable.getListLigneEcriture()).contains(ligneEcritureComptable1,ligneEcritureComptable2);
			assertThat(ecritureComptable.getListLigneEcriture().get(0)).usingRecursiveComparison().isEqualTo(ligneEcritureComptable1);
		} catch (NotFoundException e) {
			// TODO: handle exception
			//throw new NotFoundException("L'écriture n'a pas été trouvée");
			//e.printStackTrace();
		}
	}
	
	@Test
	public void checkUpdateEcritureComptable() throws ParseException, NotFoundException {
		EcritureComptable ecritureComptable = new EcritureComptable();
		ecritureComptable.setId(-3);
		ecritureComptable.setJournal(new JournalComptable("BQ","Banque"));
		ecritureComptable.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2019-12-29"));
		ecritureComptable.setLibelle("Paiement Facture F110001 changement");
		ecritureComptable.setReference("BQ-2016/00006");
		
		LigneEcritureComptable ligneEcritureComptable1 = new LigneEcritureComptable(new CompteComptable(401, "Fournisseurs"), null, new BigDecimal(52.74), null);
		LigneEcritureComptable ligneEcritureComptable2 = new LigneEcritureComptable(new CompteComptable(512, "Banque"), null, null, new BigDecimal(52.74));
		
		ecritureComptable.getListLigneEcriture().add(ligneEcritureComptable1);
		ecritureComptable.getListLigneEcriture().add(ligneEcritureComptable2);
		
		comptabiliteDao.updateEcritureComptable(ecritureComptable);
		
		EcritureComptable eComptableTest =comptabiliteDao.getEcritureComptableByRef("BQ-2016/00006");
		
		assertThat(eComptableTest).usingRecursiveComparison().isEqualTo(ecritureComptable);
	}
	
	@Test 
	@Transactional
	@Rollback
	public void checkDeleteEcritureComptable() throws NotFoundException {
		
		int id = -2;
		 
		EcritureComptable ecritureComptable = comptabiliteDao.getEcritureComptable(id);
		
		List<EcritureComptable> eList = comptabiliteDao.getListEcritureComptable();
		int sizeEListBefore = eList.size();
		
		//comptabiliteDao.loadListLigneEcriture(ecritureComptable);
		
		//int sizeListLigneBefore = ecritureComptable.getListLigneEcriture().size();
		
		comptabiliteDao.deleteEcritureComptable(id);
		
		eList = comptabiliteDao.getListEcritureComptable();
		int sizeExpected = sizeEListBefore - 1;
		
		assertThat(eList.size()).isEqualTo(sizeExpected);
		assertThat(eList).doesNotContain(ecritureComptable);
	}
}
