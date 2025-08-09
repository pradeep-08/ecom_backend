package com.webbious.ecommerce.services.impl;

import com.webbious.ecommerce.entities.Product;
import com.webbious.ecommerce.filters.ProductFilter;
import com.webbious.ecommerce.services.interfaces.ProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @Autowired
    private EntityManager em;

    @Override
    public List<Product> getByFilter(ProductFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Product> cq = cb.createQuery(Product.class);
        Root<Product> root = cq.from(Product.class);

        List<Predicate> predicates = buildPredicates(filter, cb, root);
        cq.where(predicates.toArray(new Predicate[0]));
        cq.orderBy(buildOrderBy(filter, cb, root));

        return em.createQuery(cq)
                .setFirstResult(filter.getOffSet())
                .setMaxResults(filter.getPageSize())
                .getResultList();
    }

    @Override
    public Long count(ProductFilter filter) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Product> root = countQuery.from(Product.class);

        List<Predicate> predicates = buildPredicates(filter, cb, root);
        countQuery.select(cb.count(root));
        countQuery.where(predicates.toArray(new Predicate[0]));

        return em.createQuery(countQuery).getSingleResult();
    }

    private List<Predicate> buildPredicates(ProductFilter filter, CriteriaBuilder cb, Root<Product> root) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.isTrue(root.get("active"))); // soft delete

        if (filter.getName() != null && !filter.getName().isBlank()) {
            predicates.add(cb.like(cb.lower(root.get("name")), "%" + filter.getName().toLowerCase() + "%"));
        }

        if (filter.getCategory() != null && !filter.getCategory().isBlank()) {
            predicates.add(cb.equal(cb.lower(root.get("category")), filter.getCategory().toLowerCase()));
        }

        if (filter.getSku() != null && !filter.getSku().isBlank()) {
            predicates.add(cb.equal(cb.lower(root.get("sku")), filter.getSku().toLowerCase()));
        }

        return predicates;
    }

    private List<Order> buildOrderBy(ProductFilter filter, CriteriaBuilder cb, Root<Product> root) {
        List<Order> orderList = new ArrayList<>();

        if (filter.getProperty() != null && !filter.getProperty().isBlank()) {
            if ("asc".equalsIgnoreCase(filter.getSortBy())) {
                orderList.add(cb.asc(root.get(filter.getProperty())));
            } else {
                orderList.add(cb.desc(root.get(filter.getProperty())));
            }
        } else {
            orderList.add(cb.desc(root.get("dateCreated")));
        }

        return orderList;
    }
}
