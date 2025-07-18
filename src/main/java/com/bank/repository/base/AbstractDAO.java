package com.bank.repository.base;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Repository
public abstract class AbstractDAO<T, ID> {

    @PersistenceContext
    protected EntityManager entityManager;

    private final Class<T> entityClass;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType paramType = (ParameterizedType) type;
        this.entityClass = (Class<T>) paramType.getActualTypeArguments()[0];
    }

    @Transactional
    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional
    public List<T> saveAll(List<T> entities) {
        List<T> savedEntities = new ArrayList<>();
        for (T entity : entities) {
            entityManager.persist(entity);
            savedEntities.add(entity);
        }
        return savedEntities;
    }

    @Transactional
    public T merge(T entity) {
        entityManager.merge(entity);
        return entity;
    }

    public T findById(ID id) {
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll() {
        return entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }

    @Transactional
    public void deleteById(ID id) {
        entityManager.createQuery("DELETE FROM " + entityClass.getSimpleName() + " WHERE ID = :id")
                .setParameter("id", id).executeUpdate();
    }

    @Transactional
    public void delete(T entity) {
        entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
    }

    public long count() {
        return entityManager.createQuery("SELECT COUNT(e) FROM " + entityClass.getSimpleName() + " e", Long.class)
                .getSingleResult();
    }

    public boolean existsById(ID id) {
        return findById(id) != null;
    }

    public List<T> findAll(int page, int size) {
        TypedQuery<T> query = entityManager.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    public List<T> searchByField(String fieldName, String operator, String value, int page, int size) {
        String queryString = "SELECT e FROM " + entityClass.getSimpleName() + " e WHERE e." + fieldName + " "
                + setOperator(operator, value);
        TypedQuery<T> query = entityManager.createQuery(queryString, entityClass);
        query.setFirstResult(page * size);
        query.setMaxResults(size);
        return query.getResultList();
    }

    private String setOperator(String operator, String value) {
        StringBuilder query = new StringBuilder();
        switch (operator) {
            case "eq": query.append("= ").append(value);
                break;
            case "neq": query.append("!= ").append(value);
                break;
            case "gt": query.append("> ").append(value);
                break;
            case "lw": query.append("< ").append(value);
                break;
            case "gte": query.append(">= ").append(value);
                break;
            case "lwe": query.append("<= ").append(value);
                break;
            case "lk": query.append("LIKE").append(" '%").append(value).append("%'");
                break;
            case "nlk": query.append(" NOT LIKE").append(" '%").append(value).append("%'");
                break;
        }
        return query.toString();
    }
}
