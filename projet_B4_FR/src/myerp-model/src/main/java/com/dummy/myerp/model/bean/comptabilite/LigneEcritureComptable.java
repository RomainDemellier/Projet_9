package com.dummy.myerp.model.bean.comptabilite;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.dummy.myerp.model.validation.constraint.MontantComptable;


/**
 * Bean représentant une Ligne d'écriture comptable.
 */
public class LigneEcritureComptable {

    // ==================== Attributs ====================
    /** Compte Comptable */
    @NotNull
    private CompteComptable compteComptable;

    /** The Libelle. */
    @Size(max = 200)
    private String libelle;

    /** The Debit. */
    @MontantComptable
    private BigDecimal debit;

    /** The Credit. */
    @MontantComptable
    private BigDecimal credit;


    // ==================== Constructeurs ====================
    /**
     * Instantiates a new Ligne ecriture comptable.
     */
    public LigneEcritureComptable() {
    }

    /**
     * Instantiates a new Ligne ecriture comptable.
     *
     * @param pCompteComptable the Compte Comptable
     * @param pLibelle the libelle
     * @param pDebit the debit
     * @param pCredit the credit
     */
    public LigneEcritureComptable(CompteComptable pCompteComptable, String pLibelle,
                                  BigDecimal pDebit, BigDecimal pCredit) {
    	
    	if(pDebit != null) {
    		pDebit = pDebit.setScale(2, RoundingMode.DOWN);
    	}
    	
    	if(pCredit != null) {
    		pCredit = pCredit.setScale(2, RoundingMode.DOWN);
    	}
    	
        compteComptable = pCompteComptable;
        libelle = pLibelle;
        debit = pDebit;
        credit = pCredit;
    }


    // ==================== Getters/Setters ====================
    public CompteComptable getCompteComptable() {
        return compteComptable;
    }
    public void setCompteComptable(CompteComptable pCompteComptable) {
        compteComptable = pCompteComptable;
    }
    public String getLibelle() {
        return libelle;
    }
    public void setLibelle(String pLibelle) {
        libelle = pLibelle;
    }
    public BigDecimal getDebit() {
//    	if(debit != null) {
//    		return debit.setScale(2, RoundingMode.DOWN);
//    	}
        return debit;
    }
    public void setDebit(BigDecimal pDebit) {
    	if(pDebit != null) {
    		pDebit = pDebit.setScale(2, RoundingMode.DOWN);
    	}
        debit = pDebit;
    }
    public BigDecimal getCredit() {
//    	if(credit != null) {
//    		return credit.setScale(2, RoundingMode.DOWN);
//    	}
        return credit;
    }
    public void setCredit(BigDecimal pCredit) {
    	if(pCredit != null) {
    		pCredit = pCredit.setScale(2, RoundingMode.DOWN);
    	}
        credit = pCredit;
    }


    // ==================== Méthodes ====================
    @Override
    public String toString() {
        final StringBuilder vStB = new StringBuilder(this.getClass().getSimpleName());
        final String vSEP = ", ";
        vStB.append("{")
            .append("compteComptable=").append(compteComptable)
            .append(vSEP).append("libelle='").append(libelle).append('\'')
            .append(vSEP).append("debit=").append(debit)
            .append(vSEP).append("credit=").append(credit)
            .append("}");
        return vStB.toString();
    }
}
