package com.leon.infrastructure.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.lang.reflect.Constructor;
import java.util.List;

public class BaseRepositoryImpl<T extends Entity, ID extends EntityId> extends SimpleJpaRepository<T, ID> implements BaseRepository<T, ID> {

    private static final Logger LOG = LoggerFactory.getLogger(BaseRepositoryImpl.class);

    private static final String ID_POSTFIX = "Id";

    private static final UniqueIdGenerator<Long> uniqueIdGenerator = new SnowFlakeGenerator();

    private final EntityManager entityManager;

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

    @Override
    @SuppressWarnings("unchecked")
    public ID getNextId() {
        try {
            Constructor constructor = Class.forName(getDomainClass().getName() + ID_POSTFIX).getConstructor(Long.class);
            return (ID) constructor.newInstance(uniqueIdGenerator.getNextUniqueId());
        } catch (IllegalArgumentException | ReflectiveOperationException ex) {
            LOG.error("Error when generating id for instance of {} class: {}", getDomainClass(), ex);
            throw new RuntimeException("Generating id error.");
        }
    }

}
