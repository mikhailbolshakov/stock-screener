package com.mikhailb.stockscreener.account.repository.impl;

import com.mikhailb.stockscreener.account.repository.UserAccountRepository;
import com.mikhailb.stockscreener.account.repository.entity.*;
import com.mikhailb.stockscreener.common.types.pagination.PaginationRequest;
import com.mikhailb.stockscreener.common.types.pagination.PaginationResponse;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class UserAccountRepositoryImpl implements UserAccountRepository {

    private final LocalDateTime MAX_VALID = LocalDateTime.of(2050, 1, 1, 0, 0);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public UserAccountEntity createAccount(UserAccountEntity entity) {

        entity.getPhones().forEach(p -> {
            p.setValidFrom(LocalDateTime.now());
            p.setValidTo(MAX_VALID);
            p.setUserAccount(entity);
        });

        entity.getEmails().forEach(p -> {
            p.setValidFrom(LocalDateTime.now());
            p.setValidTo(MAX_VALID);
            p.setUserAccount(entity);
        });

        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional(value = Transactional.TxType.REQUIRES_NEW)
    public void history(UUID userAccountId, String operation) {

        UserAccountHistoryEntity hst = new UserAccountHistoryEntity();
        hst.setAccountId(userAccountId);
        hst.setOperation(operation);
        hst.setSysTimestamp(LocalDateTime.now());
        entityManager.persist(hst);

    }

    @Override
    public Optional<UserAccountEntity> findById(UUID accountId) {
        return Optional.ofNullable(entityManager.find(UserAccountEntity.class, accountId));
    }

    @Override
    public List<UserAccountEntity> findByPhone(String phone) {
        return entityManager
                .createQuery(
                        "SELECT uc " +
                                "FROM UserAccountEntity uc " +
                                "   LEFT JOIN uc.phones p " +
                                "   LEFT JOIN uc.emails e " +
                                "WHERE p.phoneNumber = :phone " +
                                " AND :now BETWEEN p.validFrom AND p.validTo " +
                                " AND :now BETWEEN e.validFrom AND e.validTo", UserAccountEntity.class)
                .setParameter("phone", phone)
                .setParameter("now", LocalDateTime.now())
                .getResultList();
    }

    private CriteriaQuery<UserAccountEntity> buildCriteriaQuery(UserAccountCriteria criteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<UserAccountEntity> query = builder.createQuery(UserAccountEntity.class);

        Root<UserAccountEntity> userAccount = query.from(UserAccountEntity.class);

        Join<UserAccountEntity, UserPhoneEntity> phoneJoin = userAccount.join("phones");
        Join<UserAccountEntity, UserEmailEntity> emailJoin = userAccount.join("emails");

        query.select(userAccount);

        List<Predicate> predicates = new ArrayList<>();

        String lastNameMask = criteria.getLastNameMask();
        if (!StringUtils.isEmpty(lastNameMask))
            predicates.add(builder.like(userAccount.get("lastName"), "%" + lastNameMask + "%"));

        String phone = criteria.getPhoneNumber();
        if (!StringUtils.isEmpty(phone))
            predicates.add(builder.like(phoneJoin.get("phoneNumber"), "%" + phone + "%"));

        String email = criteria.getEmail();
        if (!StringUtils.isEmpty(email))
            predicates.add(builder.like(emailJoin.get("email"), "%" + email + "%"));

        query.where(predicates.toArray(new Predicate[0]));

        return query;
    }

    private CriteriaQuery<Long> buildCountQuery(UserAccountCriteria criteria) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> query = builder.createQuery(Long.class);

        Root<UserAccountEntity> userAccount = query.from(UserAccountEntity.class);

        Join<UserAccountEntity, UserPhoneEntity> phoneJoin = userAccount.join("phones");
        Join<UserAccountEntity, UserEmailEntity> emailJoin = userAccount.join("emails");

        query.select(builder.count(query.from(UserAccountEntity.class)));

        List<Predicate> predicates = new ArrayList<>();

        String lastNameMask = criteria.getLastNameMask();
        if (!StringUtils.isEmpty(lastNameMask))
            predicates.add(builder.like(userAccount.get("lastName"), "%" + lastNameMask + "%"));

        String phone = criteria.getPhoneNumber();
        if (!StringUtils.isEmpty(phone))
            predicates.add(builder.like(phoneJoin.get("phoneNumber"), "%" + phone + "%"));

        String email = criteria.getEmail();
        if (!StringUtils.isEmpty(email))
            predicates.add(builder.like(emailJoin.get("email"), "%" + email + "%"));

        query.where(predicates.toArray(new Predicate[0]));

        return query;
    }

    @Override
    public PaginationResponse<UserAccountEntity> findByCriteria(PaginationRequest<UserAccountCriteria> criteria) {

        CriteriaQuery<UserAccountEntity> criteriaQ = buildCriteriaQuery(criteria.getCriteria());

        TypedQuery<UserAccountEntity> typedQ = entityManager.createQuery(criteriaQ);
        typedQ.setFirstResult(criteria.getPageIndex() - 1);
        typedQ.setMaxResults(criteria.getItemsOnPage());

        PaginationResponse<UserAccountEntity> rs = new PaginationResponse<>();
        rs.setItems(typedQ.getResultList());

        if (criteria.isNumberOfPages()) {
            CriteriaQuery<Long> countQ = buildCountQuery(criteria.getCriteria());
            TypedQuery<Long> typedCountQ = entityManager.createQuery(countQ);
            rs.setPageCount(typedCountQ.getSingleResult().intValue());
        }

        return rs;

    }
}
