package com.bank.repository.dao;

import com.bank.model.MyUser;
import com.bank.repository.base.AbstractDAO;
import com.bank.repository.entity.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class UserDAO extends AbstractDAO<UserEntity, Long> {

    public MyUser getUserByUsername(final String username) {
        String hql = "SELECT new com.bank.model.MyUser(e.username, e.password, r.name) " +
                "FROM UserEntity e " +
                "INNER JOIN e.role r " +
                "WHERE e.username = :username";
        return entityManager.createQuery(hql, MyUser.class).setParameter("username", username)
                .getSingleResult();
    }
}

