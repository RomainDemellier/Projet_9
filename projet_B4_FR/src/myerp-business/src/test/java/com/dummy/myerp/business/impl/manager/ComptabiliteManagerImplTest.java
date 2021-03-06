package com.dummy.myerp.business.impl.manager;

//import static org.junit.Assert.assertEquals;


import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
//import org.junit.rules.ExpectedException;
//import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dummy.myerp.business.BusinessTestCase;
import com.dummy.myerp.business.SpringRegistry;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
//import com.sun.org.apache.xml.internal.security.Init;
import com.dummy.myerp.technical.exception.NotFoundException;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ComptabiliteManagerImplTest {
	
    private ComptabiliteManagerImpl manager = new ComptabiliteManagerImpl();

    public ComptabiliteManagerImplTest() {
		// TODO Auto-generated constructor stub
    	super();
    	SpringRegistry.init();
	}
    
    
//    @BeforeAll
//    public void init() {
//    	SpringRegistry.init();
//    }
    
    @Test  
    public void checkEcritureComptableUnit() throws Exception {
    	//SpringRegistry.init();
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
        vEcritureComptable.setReference("AC-2020/00001");
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
        vEcritureComptable.setReference("AC-2020/00001");
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
                                                                                 null, null,
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, null, 
                                                                                 null));
        vEcritureComptable.setReference("AC-2020/00001");
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableUnitRG3_() throws Exception { 
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.setReference("AC-2020/00001");
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    @Test(expected = FunctionalException.class)
    public void chekcEcrutiureComptableUnitRG5Format() throws Exception {
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
        vEcritureComptable.setReference("AC-2020/000010");
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    @Test(expected = FunctionalException.class)
    public void chekcEcrutiureComptableUnitRG5Code() throws Exception {
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
        vEcritureComptable.setReference("AQ-2020/00001");
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    @Test(expected = FunctionalException.class)
    public void chekcEcrutiureComptableUnitRG5Annee() throws Exception {
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
        vEcritureComptable.setReference("AC-2019/00001");
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    @Test(expected = FunctionalException.class)
    public void chekcEcrutiureComptableUnitRG5RefVide() throws Exception {
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
        vEcritureComptable.setReference("");
        manager.checkEcritureComptableUnit(vEcritureComptable);
    }
    
    @Test(expected = FunctionalException.class)
    public void checkEcritureComptableContextRG6() throws Exception {
    	EcritureComptable vEcritureComptable = new EcritureComptable();
    	vEcritureComptable.setReference("BQ-2016/00005");
    	
    	manager.checkEcritureComptableContext(vEcritureComptable);
    }
    
    @Test
    //cas où pas de séquence avec ce code cette année en base
    public void checkAddReference1() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                                                                                 null, null,
                                                                                 new BigDecimal(123)));
        String code = vEcritureComptable.getJournal().getCode();
        int annee = vEcritureComptable.getYear();
        
        SequenceEcritureComptable sequenceEcritureComptable;
               
        manager.addReference(vEcritureComptable);
        
        sequenceEcritureComptable = manager.getSequenceEcritureComptableByCodeAndByAnnee(code,annee);
        int valeurIncremente = sequenceEcritureComptable.getDerniereValeur();
        
        assertThat(valeurIncremente).isEqualTo(1);
    }
    
    @Test
    //cas où séquence en base et pas écriture id null
    public void checkAddReference2() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.YEAR, 2016);
        vEcritureComptable.setDate(c.getTime());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                                                                                 null, null,
                                                                                 new BigDecimal(123)));
        String code = vEcritureComptable.getJournal().getCode();
        int annee = vEcritureComptable.getYear();
        
        SequenceEcritureComptable sequenceEcritureComptable;
        
        int derniereValeur = 0;
        
        try {
			sequenceEcritureComptable = manager.getSequenceEcritureComptableByCodeAndByAnnee(code, annee);
			derniereValeur = sequenceEcritureComptable.getDerniereValeur();
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        manager.addReference(vEcritureComptable);
        
        sequenceEcritureComptable = manager.getSequenceEcritureComptableByCodeAndByAnnee(code,annee);
        int valeurIncremente = sequenceEcritureComptable.getDerniereValeur();
      
        int n = derniereValeur + 1;
        
        assertThat(valeurIncremente).isEqualTo(n);
    }
    
    @Test
    //cas où séquence en base mais pas écriture avec id écriture non null
    public void checkAddReference3() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setId(6);
        vEcritureComptable.setJournal(new JournalComptable("AC", "Achat"));
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.YEAR, 2016);
        vEcritureComptable.setDate(c.getTime());
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                                                                                 null, null,
                                                                                 new BigDecimal(123)));
        String code = vEcritureComptable.getJournal().getCode();
        int annee = vEcritureComptable.getYear();
        
        SequenceEcritureComptable sequenceEcritureComptable;
        
        int derniereValeur = 0;
        
        try {
			sequenceEcritureComptable = manager.getSequenceEcritureComptableByCodeAndByAnnee(code, annee);
			derniereValeur = sequenceEcritureComptable.getDerniereValeur();
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        manager.addReference(vEcritureComptable);
        
        sequenceEcritureComptable = manager.getSequenceEcritureComptableByCodeAndByAnnee(code,annee);
        int valeurIncremente = sequenceEcritureComptable.getDerniereValeur();
      
        int n = derniereValeur + 1;
         
        assertThat(valeurIncremente).isEqualTo(n);
    }
    
    @Test
    //séquence et écriture en base
    public void checkAddReference4() throws Exception {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = manager.getEcritureComptableById(-1);

        String code = vEcritureComptable.getJournal().getCode();
        int annee = vEcritureComptable.getYear();
        
        SequenceEcritureComptable sequenceEcritureComptable;
        
        int derniereValeur = 0;
        
        try {
			sequenceEcritureComptable = manager.getSequenceEcritureComptableByCodeAndByAnnee(code, annee);
			derniereValeur = sequenceEcritureComptable.getDerniereValeur();
		} catch (Exception e) {
			// TODO: handle exception
		}
        
        manager.addReference(vEcritureComptable);
        
        sequenceEcritureComptable = manager.getSequenceEcritureComptableByCodeAndByAnnee(code,annee);
        int valeurIncremente = sequenceEcritureComptable.getDerniereValeur();
      
        int n = derniereValeur + 1;
        
        assertThat(valeurIncremente).isEqualTo(n);
    }
    
    @Test(expected = FunctionalException.class)
    public void checkAddReferenceIfNull() throws Exception {
    	EcritureComptable vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(401),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(411),
                                                                                 null, null,
                                                                                 new BigDecimal(123)));
        manager.addReference(vEcritureComptable);
    }
    
    @Test
    //check unit et context
    public void checkEcritureComptable() throws FunctionalException {
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
        vEcritureComptable.setReference("AC-2020/00002");
        manager.checkEcritureComptable(vEcritureComptable);
    }
    
    @Test(expected = FunctionalException.class)
    //check unit et context avec une mauvaise référence
    public void checkEcritureComptableBadRef() throws FunctionalException {
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
        vEcritureComptable.setReference("AC-2019/00001");
        manager.checkEcritureComptable(vEcritureComptable);
    }
    
    @Test(expected = FunctionalException.class)
    //check unit et context avec une référence qui existe déjà en base
    public void checkEcritureComptableBadContext() throws FunctionalException, ParseException {
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("BQ", "Achat"));
        vEcritureComptable.setDate(new SimpleDateFormat("yyyy-MM-dd").parse("2016-12-29"));
        vEcritureComptable.setLibelle("Libelle");
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
                                                                                 null, new BigDecimal(123),
                                                                                 null));
        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
                                                                                 null, null,
                                                                                 new BigDecimal(123)));
        vEcritureComptable.setReference("BQ-2016/00005");
        manager.checkEcritureComptable(vEcritureComptable);
    }

}
