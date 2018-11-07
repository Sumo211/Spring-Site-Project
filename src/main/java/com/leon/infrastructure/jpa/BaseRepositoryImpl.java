package com.leon.infrastructure.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class BaseRepositoryImpl<T, ID extends EntityId> extends SimpleJpaRepository<T, EntityId> implements BaseRepository<T, ID> {

    private final EntityManager entityManager;

    @Autowired
    private UniqueIdGenerator<ID> uniqueIdGenerator;

    public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public List<T> findAll(Specification<T> spec, EntityGraph.EntityGraphType entityGraphType, String entityGraphName) {
        TypedQuery<T> query = getQuery(spec, Sort.unsorted());
        query.setHint(entityGraphType.getKey(), entityManager.getEntityGraph(entityGraphName));
        return query.getResultList();
    }

    ID getNext() {
        return uniqueIdGenerator.getNextUniqueId();
    }

}
