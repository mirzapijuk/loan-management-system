package com.bank.repository.dao;

import com.bank.repository.base.AbstractDAO;
import com.bank.repository.entity.CustomerEntity;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class CustomerDAO extends AbstractDAO<CustomerEntity, Long> {

    private EntityManager entityManager;

    public CustomerEntity findCustomerByRegistrationNo(final String registrationNo) {
        String hql = "SELECT e FROM CustomerEntity e WHERE e.registrationNumber = :registrationNo";
        return entityManager.createQuery(hql, CustomerEntity.class)
                .setParameter("registrationNo", registrationNo).getSingleResult();
    }

    public boolean existsByRegistrationNo(final String registrationNo) {
        String hql = "SELECT COUNT(e) FROM CustomerEntity e WHERE e.registrationNumber = :registrationNo";
        Long count = entityManager.createQuery(hql, Long.class)
                .setParameter("registrationNo", registrationNo).getSingleResult();
        return count > 0;
    }
}
