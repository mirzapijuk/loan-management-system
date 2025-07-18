package com.bank.repository.dao;

import com.bank.repository.base.AbstractDAO;
import com.bank.repository.entity.PartyEntity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class PartyDAO extends AbstractDAO<PartyEntity, Long> {

    private EntityManager entityManager;

    public boolean existsParty(final Long partyNumber) {
        String hql = "SELECT COUNT(e) FROM PartyEntity e WHERE e.partyNumber = :partyNumber";
        Long count = entityManager.createQuery(hql, Long.class)
                .setParameter("partyNumber", partyNumber).getSingleResult();
        return count > 0;
    }
}
