package com.bank.repository.dao;

import com.bank.repository.base.AbstractDAO;
import com.bank.repository.entity.LoanEntity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class LoanDAO extends AbstractDAO<LoanEntity, Long> {

    private EntityManager entityManager;

    public LoanEntity getLoanByPartyNumber(final Long partyNumber) {
        String hql = "SELECT l FROM LoanEntity l WHERE l.party.partyNumber = :partyNumber";
        return entityManager.createQuery(hql, LoanEntity.class)
                .setParameter("partyNumber", partyNumber).getSingleResult();

    }
}
