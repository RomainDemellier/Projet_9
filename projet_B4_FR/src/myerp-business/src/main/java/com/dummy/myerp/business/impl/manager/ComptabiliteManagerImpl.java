package com.dummy.myerp.business.impl.manager;

import java.awt.image.RescaleOp;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.TransactionStatus;
import com.dummy.myerp.business.contrat.manager.ComptabiliteManager;
import com.dummy.myerp.business.impl.AbstractBusinessManager;
import com.dummy.myerp.business.impl.BusinessProxyImpl;
import com.dummy.myerp.consumer.dao.impl.DaoProxyImpl;
import com.dummy.myerp.model.bean.comptabilite.CompteComptable;
import com.dummy.myerp.model.bean.comptabilite.EcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.JournalComptable;
import com.dummy.myerp.model.bean.comptabilite.LigneEcritureComptable;
import com.dummy.myerp.model.bean.comptabilite.SequenceEcritureComptable;
import com.dummy.myerp.technical.exception.FunctionalException;
import com.dummy.myerp.technical.exception.NotFoundException;

/**
 * Comptabilite manager implementation.
 */
public class ComptabiliteManagerImpl extends AbstractBusinessManager implements ComptabiliteManager {

	// ==================== Attributs ====================

	// ==================== Constructeurs ====================
	/**
	 * Instantiates a new Comptabilite manager.
	 */
	public ComptabiliteManagerImpl() {
		
	}

	// ==================== Getters/Setters ====================
	@Override
	public List<CompteComptable> getListCompteComptable() {
		return getDaoProxy().getComptabiliteDao().getListCompteComptable();
	}

	@Override
	public List<JournalComptable> getListJournalComptable() {
		return getDaoProxy().getComptabiliteDao().getListJournalComptable();
	}

	@Override
	public List<EcritureComptable> getListEcritureComptable() {
		// TODO Auto-generated method stub
		return getDaoProxy().getComptabiliteDao().getListEcritureComptable();
	}

	@Override
	public EcritureComptable getEcritureComptableById(int id) {
		try {
			return getDaoProxy().getComptabiliteDao().getEcritureComptable(id);
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<SequenceEcritureComptable> getListSequenceEcritureComptable() {
		// TODO Auto-generated method stub
		return getDaoProxy().getComptabiliteDao().getListSequenceEcritureComptable();
	}

	@Override
	public SequenceEcritureComptable getSequenceEcritureComptableByCodeAndByAnnee(String code, int annee) throws NotFoundException {
		try {
			return getDaoProxy().getComptabiliteDao().getSequenceEcritureComptableByCodeAndByAnnee(code, annee);
		} catch (NotFoundException e) {
			// TODO: handle exception
			throw new NotFoundException("La séquence n'a pas été trouvée.");
			//return null;
		}
	}

	public String constructReference(String code, int annee, int derniereValeur) {
		String str = String.valueOf(derniereValeur);
		int nbreZeros = 5 - str.length();
		for (int i = 0; i < nbreZeros; i++) {
			str = '0' + str;
		}
		String ref = code + "-" + annee + "/" + str;
		return ref;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws FunctionalException
	 */
	// TODO à tester
	@Override
	public synchronized void addReference(EcritureComptable pEcritureComptable) throws FunctionalException {
		// TODO à implémenter
		// Bien se réferer à la JavaDoc de cette méthode !
		/*
		 * Le principe : 1. Remonter depuis la persitance la dernière valeur de la
		 * séquence du journal pour l'année de l'écriture (table
		 * sequence_ecriture_comptable) 2. * S'il n'y a aucun enregistrement pour le
		 * journal pour l'année concernée : 1. Utiliser le numéro 1. Sinon : 1. Utiliser
		 * la dernière valeur + 1 3. Mettre à jour la référence de l'écriture avec la
		 * référence calculée (RG_Compta_5) 4. Enregistrer (insert/update) la valeur de
		 * la séquence en persitance (table sequence_ecriture_comptable)
		 */
		String code = "";
		int annee = 0;
		int derniereValeur;
		String ref;
		SequenceEcritureComptable pSequenceEcritureComptable = new SequenceEcritureComptable();
		if (pEcritureComptable.getJournal() != null && pEcritureComptable.getJournal().getCode() != null && !pEcritureComptable.getJournal().getCode().equals("") && pEcritureComptable.getDate() != null) {
			code = pEcritureComptable.getJournal().getCode();
			annee = pEcritureComptable.getYear();
			
			try {
				pSequenceEcritureComptable = this.getSequenceEcritureComptableByCodeAndByAnnee(code, annee);
				//pSequenceEcritureComptable = getDaoProxy().getComptabiliteDao().getSequenceEcritureComptableByCodeAndByAnnee(code, annee);
				derniereValeur = pSequenceEcritureComptable.getDerniereValeur();
				derniereValeur = derniereValeur + 1;
				pSequenceEcritureComptable.setDerniereValeur(derniereValeur);
			} catch (NotFoundException e) {
				// TODO: handle exception
				derniereValeur = 1;
				pSequenceEcritureComptable.setJournalCode(code);
				pSequenceEcritureComptable.setAnnee(annee);
				pSequenceEcritureComptable.setDerniereValeur(derniereValeur);
			} 

			ref = this.constructReference(code, annee, derniereValeur);
			pEcritureComptable.setReference(ref);
			if (pEcritureComptable.getId() == null) {
				try {
					this.insertEcritureComptable(pEcritureComptable);
					this.insertOrUpdateSequenceEcritureComptable(pSequenceEcritureComptable, derniereValeur);
				} catch (FunctionalException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				int id = pEcritureComptable.getId();
				try {
					EcritureComptable ecritureComptable = getDaoProxy().getComptabiliteDao().getEcritureComptable(id);
					try {
						this.insertOrUpdateSequenceEcritureComptable(pSequenceEcritureComptable, derniereValeur);
						this.updateEcritureComptable(pEcritureComptable);
					} catch (FunctionalException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (NotFoundException e) {
					// TODO: handle exception
					try {
						this.insertEcritureComptable(pEcritureComptable);
						this.insertOrUpdateSequenceEcritureComptable(pSequenceEcritureComptable, derniereValeur);
					} catch (FunctionalException exception) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		} else {
			throw new FunctionalException("Code ou date null.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	// TODO à tester
	@Override
	public void checkEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
		this.checkEcritureComptableUnit(pEcritureComptable);
		this.checkEcritureComptableContext(pEcritureComptable);
	}

	/**
	 * Vérifie que l'Ecriture comptable respecte les règles de gestion unitaires,
	 * c'est à dire indépendemment du contexte (unicité de la référence, exercie
	 * comptable non cloturé...)
	 *
	 * @param pEcritureComptable -
	 * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les
	 *                             règles de gestion
	 */
	// TODO tests à compléter
	protected void checkEcritureComptableUnit(EcritureComptable pEcritureComptable) throws FunctionalException {
		// ===== Vérification des contraintes unitaires sur les attributs de l'écriture
		Set<ConstraintViolation<EcritureComptable>> vViolations = getConstraintValidator().validate(pEcritureComptable);
		if (!vViolations.isEmpty()) {
			throw new FunctionalException(
					"L'écriture comptable ne respecte pas les règles de gestion. " + pEcritureComptable.getReference(),
					new ConstraintViolationException(
							"L'écriture comptable ne respecte pas les contraintes de validation "
									+ vViolations.toString(),
							vViolations));
		}

		// ===== RG_Compta_2 : Pour qu'une écriture comptable soit valide, elle doit
		// être équilibrée
		if (!pEcritureComptable.isEquilibree()) {
			throw new FunctionalException("L'écriture comptable n'est pas équilibrée. " +  pEcritureComptable.toString());
		}

		// ===== RG_Compta_3 : une écriture comptable doit avoir au moins 2 lignes
		// d'écriture (1 au débit, 1 au crédit)
		int vNbrCredit = 0;
		int vNbrDebit = 0;
		for (LigneEcritureComptable vLigneEcritureComptable : pEcritureComptable.getListLigneEcriture()) {
			if (BigDecimal.ZERO
					.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getCredit(), BigDecimal.ZERO)) != 0) {
				vNbrCredit++;
			} 
			if (BigDecimal.ZERO
					.compareTo(ObjectUtils.defaultIfNull(vLigneEcritureComptable.getDebit(), BigDecimal.ZERO)) != 0) {
				vNbrDebit++;
			}
		}
		// On test le nombre de lignes car si l'écriture à une seule ligne
		// avec un montant au débit et un montant au crédit ce n'est pas valable
		if (pEcritureComptable.getListLigneEcriture().size() < 2 || vNbrCredit < 1 || vNbrDebit < 1) {
			throw new FunctionalException(
					"L'écriture comptable doit avoir au moins deux lignes : une ligne au débit et une ligne au crédit. " + pEcritureComptable.getListLigneEcriture());
		}

		// TODO ===== RG_Compta_5 : Format et contenu de la référence
		// vérifier que l'année dans la référence correspond bien à la date de
		// l'écriture, idem pour le code journal...
//		String ref = pEcritureComptable.getReference();
//		if (ref.isEmpty()) {
//			throw new FunctionalException("vide");
//		}
//		String p = "^[A-Z]{1,5}-\\d{4}/\\d{5}";
//		Pattern pattern = Pattern.compile(p);
//		Matcher matcher = pattern.matcher(ref);
//		if (!matcher.matches()) {
//			throw new FunctionalException("Le format n'est pas respecté.");
//		}

		if (pEcritureComptable.getReference() != null) {
			String code = pEcritureComptable.getReference().substring(0, 2);
			if (!code.equals(pEcritureComptable.getJournal().getCode())) {
				throw new FunctionalException(
						"Le code du journal dans la référence ne correspond pas au code du journal");
			}

			String anneeRef = pEcritureComptable.getReference().substring(3, 7);

			SimpleDateFormat formater = new SimpleDateFormat("yyyy");
			String annee = formater.format(pEcritureComptable.getDate());
			if (!anneeRef.equals(annee)) {
				throw new FunctionalException("L'année ne correspond pas");
			}
		}
	}

	/**
	 * Vérifie que l'Ecriture comptable respecte les règles de gestion liées au
	 * contexte (unicité de la référence, année comptable non cloturé...)
	 *
	 * @param pEcritureComptable -
	 * @throws FunctionalException Si l'Ecriture comptable ne respecte pas les
	 *                             règles de gestion
	 */
	protected void checkEcritureComptableContext(EcritureComptable pEcritureComptable) throws FunctionalException {
		// ===== RG_Compta_6 : La référence d'une écriture comptable doit être unique
		if (StringUtils.isNoneEmpty(pEcritureComptable.getReference())) {
			try {
				// Recherche d'une écriture ayant la même référence
				EcritureComptable vECRef = getDaoProxy().getComptabiliteDao()
						.getEcritureComptableByRef(pEcritureComptable.getReference());

				// Si l'écriture à vérifier est une nouvelle écriture (id == null),
				// ou si elle ne correspond pas à l'écriture trouvée (id != idECRef),
				// c'est qu'il y a déjà une autre écriture avec la même référence
				if (pEcritureComptable.getId() == null || !pEcritureComptable.getId().equals(vECRef.getId())) {
					throw new FunctionalException("Une autre écriture comptable existe déjà avec la même référence.");
				}
			} catch (NotFoundException vEx) {
				// Dans ce cas, c'est bon, ça veut dire qu'on n'a aucune autre écriture avec la
				// même référence.
			} 
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
		this.checkEcritureComptable(pEcritureComptable);
		TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
		try {
			getDaoProxy().getComptabiliteDao().insertEcritureComptable(pEcritureComptable);
			getTransactionManager().commitMyERP(vTS);
			vTS = null;
		} finally {
			getTransactionManager().rollbackMyERP(vTS);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateEcritureComptable(EcritureComptable pEcritureComptable) throws FunctionalException {
		this.checkEcritureComptable(pEcritureComptable);
		TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
		try {
			getDaoProxy().getComptabiliteDao().updateEcritureComptable(pEcritureComptable);
			getTransactionManager().commitMyERP(vTS);
			vTS = null;
		} finally {
			getTransactionManager().rollbackMyERP(vTS);
		}   
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void deleteEcritureComptable(Integer pId) {
		TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
		try {
			getDaoProxy().getComptabiliteDao().deleteEcritureComptable(pId);
			getTransactionManager().commitMyERP(vTS);
			vTS = null;
		} finally {
			getTransactionManager().rollbackMyERP(vTS);
		}
	}

	@Override
	public void insertSequenceEcritureComptable(SequenceEcritureComptable pSequenceEcritureComptable)
			throws FunctionalException {
		TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
		try {
			getDaoProxy().getComptabiliteDao().insertSequenceEcritureComptable(pSequenceEcritureComptable);
			getTransactionManager().commitMyERP(vTS);
			vTS = null;
		} finally {
			getTransactionManager().rollbackMyERP(vTS);
		}
	}

	@Override
	public void updateSequenceEcritureComptable(SequenceEcritureComptable pSequenceEcritureComptable)
			throws FunctionalException {
		TransactionStatus vTS = getTransactionManager().beginTransactionMyERP();
		try {
			getDaoProxy().getComptabiliteDao().updateSequenceEcritureComptable(pSequenceEcritureComptable);
			getTransactionManager().commitMyERP(vTS);
			vTS = null;
		} finally {
			getTransactionManager().rollbackMyERP(vTS);
		}
	}

	public void insertOrUpdateSequenceEcritureComptable(SequenceEcritureComptable pSequenceEcritureComptable,
			int derniereValeur) {
		if (derniereValeur == 1) {
			try {
				this.insertSequenceEcritureComptable(pSequenceEcritureComptable);
			} catch (FunctionalException e) {
				// TODO: handle exception
			}
		} else {
			try {
				this.updateSequenceEcritureComptable(pSequenceEcritureComptable);
			} catch (FunctionalException e) {
				// TODO: handle exception
			}
		}
	}

}
