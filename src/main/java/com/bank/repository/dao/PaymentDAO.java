package com.bank.repository.dao;

import com.bank.repository.base.AbstractDAO;
import com.bank.repository.entity.PaymentEntity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class PaymentDAO extends AbstractDAO<PaymentEntity, Long> {

    private EntityManager entityManager;

    public List<PaymentEntity> getPaymentsByParty(final Long partyNumber) {
        String hql = "SELECT e FROM PaymentEntity e WHERE e.loan.party.partyNumber = :partyNumber";
        return entityManager.createQuery(hql, PaymentEntity.class).setParameter("partyNumber", partyNumber).getResultList();
    }
}
