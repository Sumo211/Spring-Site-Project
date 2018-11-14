package com.leon.film;

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
