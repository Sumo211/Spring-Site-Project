package com.leon.film;

import com.leon.infrastructure.jpa.AbstractEntity;
import com.leon.rating.Rating;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

@Entity
@Getter
@ToString(callSuper = true)
@NamedEntityGraph(name = "graph.Film.ratings", attributeNodes = @NamedAttributeNode("ratings"))
public class Film extends AbstractEntity<FilmId> {

    private String code;

    private String type;

    private OffsetDateTime releaseDate;

    private String description;

    @Lob
    @Type(type = "org.hibernate.type.BinaryType")
    @Setter
    private byte[] cover; // learning purpose only, bad practice in reality

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private Set<Rating> ratings;

    protected Film() {

    }

    public Film(FilmId id, String code, String type, OffsetDateTime releaseDate, String description) {
        super(id);
        this.code = code;
        this.type = type;
        this.releaseDate = releaseDate;
        this.description = description;
    }

}
