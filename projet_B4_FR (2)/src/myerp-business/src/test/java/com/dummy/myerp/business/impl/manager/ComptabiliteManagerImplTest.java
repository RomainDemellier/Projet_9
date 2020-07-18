package com.dummy.myerp.business.impl.manager;

import static org.junit.Assert.assertEquals;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.dummy.myerp.business.SpringRegistry;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
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
                                                                                 null, new BigDecimal(123),
                                                                                 null));
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
    
//    @Test
//    public void nbreEcritures() {
//    	SpringRegistry.init();
//    	EcritureComptable ecritureComptable = new EcritureComptable();
//    	manager.addReference(ecritureComptable);
//    	List<EcritureComptable> e = manager.getListEcritureComptable();
//    	assertEquals(5,e.size());
//    }
    
    @Test
    public void derniereValeur() {
    	SpringRegistry.init();
    	SequenceEcritureComptable s = manager.getSequenceEcritureComptableByCodeAndByAnnee("AC", 2016); 
    	int n = s.getDerniereValeur();
    	//assertThat(n).isEqualTo(40);
    }
    
    @Test
    public void checkIncrementationDerniereValeurSequenceEcritureComptable() {
    	SpringRegistry.init();
//        EcritureComptable vEcritureComptable;
//        vEcritureComptable = new EcritureComptable();
//        vEcritureComptable = manager.getEcritureComptableById(-1);
        EcritureComptable vEcritureComptable;
        vEcritureComptable = new EcritureComptable();
        vEcritureComptable.setJournal(new JournalComptable("AD", "Achat"));
        vEcritureComptable.setDate(new Date());
        vEcritureComptable.setLibelle("Libelle");
//        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(1),
//                                                                                 null, new BigDecimal(123),
//                                                                                 null));
//        vEcritureComptable.getListLigneEcriture().add(new LigneEcritureComptable(new CompteComptable(2),
//                                                                                 null, null,
//                                                                                 new BigDecimal(123)));
        String code = vEcritureComptable.getJournal().getCode();
        int annee = vEcritureComptable.getYear();
        
        SequenceEcritureComptable sequenceEcritureComptable;
        
//        SequenceEcritureComptable sequenceEcritureComptable = manager.getSequenceEcritureComptableByCodeAndByAnnee(code, annee);
//        int derniereValeur = sequenceEcritureComptable.getDerniereValeur();
        
        manager.addReference(vEcritureComptable);
        
        sequenceEcritureComptable = manager.getSequenceEcritureComptableByCodeAndByAnnee(code,annee);
        int valeurIncremente = sequenceEcritureComptable.getDerniereValeur();
//        
//        int n = derniereValeur + 1;
        
        assertThat(valeurIncremente).isEqualTo(1);
    }

}
