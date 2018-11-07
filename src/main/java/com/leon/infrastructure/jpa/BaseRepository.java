package com.leon.infrastructure.jpa;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T, ID extends EntityId> extends JpaRepository<T, EntityId>, JpaSpecificationExecutor<T> {

    List<T> findAll(Specification<T> spec, EntityGraph.EntityGraphType entityGraphType, String entityGraphName);

}
