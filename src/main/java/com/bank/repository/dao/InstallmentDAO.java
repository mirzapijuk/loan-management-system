package com.bank.repository.dao;

import com.bank.model.CriticalCustomer;
import com.bank.repository.base.AbstractDAO;
import com.bank.repository.entity.InstallmentEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class InstallmentDAO extends AbstractDAO<InstallmentEntity, Long> {

    private EntityManager entityManager;

    public boolean existsInstallment(final Long loanId) {
        String hql = "SELECT COUNT(e) FROM InstallmentEntity e WHERE e.loan.id = :loanId";
        Long count = entityManager.createQuery(hql, Long.class).setParameter("loanId", loanId).getSingleResult();
        return count > 0;
    }

    public List<InstallmentEntity> getInstallmentsByLoanId(final Long loanId) {
        String hql = "SELECT e FROM InstallmentEntity e WHERE e.loan.id = :loanId";
        return entityManager.createQuery(hql, InstallmentEntity.class).setParameter("loanId", loanId).getResultList();
    }

    public InstallmentEntity getCurrentInstallmentsByLoanId(final Long loanId) {
        InstallmentEntity installmentEntity = null;
        String hql = "SELECT e FROM InstallmentEntity e WHERE e.loan.id = :loanId " +
                "AND MONTH(e.dueDate) = MONTH(CURRENT_DATE) " +
                "AND YEAR(e.dueDate) = YEAR(CURRENT_DATE)";
        try {
            installmentEntity =entityManager.createQuery(hql, InstallmentEntity.class).setParameter("loanId", loanId).getSingleResult();
        } catch (NoResultException ignored) {
        }
        return installmentEntity;
    }

    public List<CriticalCustomer> getCurrentUnpaidInstallments() {
       String sql = "select par.party_number , c.registration_number, c.full_name , c.email, c.phone " +
               "from bank.installment i " +
               "left join (" +
               "select loan_id " +
               "from bank.payments " +
               "where payment_date >= current_date - 5) p on p.loan_id  = i.loan_id " +
               "inner join bank.loans l on l.id = i.loan_id " +
               "inner join bank.customers c on c.id = l.customer_id " +
               "inner join bank.party par on par.id = l.party " +
               "where i.due_date = current_date and p.loan_id is null";
        List<Object[]> rows = entityManager.createNativeQuery(sql).getResultList();

        List<CriticalCustomer> result = new ArrayList<>();
        for (Object[] row : rows) {
            result.add(new CriticalCustomer(
                    ((Number) row[0]).longValue(),
                    (String) row[1],
                    (String) row[2],
                    (String) row[3],
                    (String) row[4]
            ));
        }
        return result;
    }
}
