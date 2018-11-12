package com.leon.film;

import com.leon.infrastructure.jpa.UniqueIdGenerator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.Map;

@Repository
public class FilmRepositoryImpl implements FilmRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    private final UniqueIdGenerator<Long> generator;

    public FilmRepositoryImpl(@Qualifier("snowFlakeGenerator") UniqueIdGenerator<Long> generator) {
        this.generator = generator;
    }

    @Override
    public FilmId getNextId() {
        return new FilmId(generator.getNextUniqueId());
    }

    @Override
    public Film getFilmAndItsRatings(FilmId id) {
        // Using dynamic entity graph
        /*EntityGraph graph = entityManager.createEntityGraph(Film.class);
        Subgraph ratingGraph = graph.addSubgraph("ratings");

        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.loadgraph", graph);*/

        // Using named entity graph
        EntityGraph graph = entityManager.getEntityGraph("graph.Film.ratings");

        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", graph);

        return entityManager.find(Film.class, id, hints);
    }

}
