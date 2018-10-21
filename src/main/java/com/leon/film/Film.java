package com.leon.film;

import com.leon.infrastructure.jpa.AbstractEntity;
import com.leon.rating.Rating;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
public class Film extends AbstractEntity<FilmId> {

    private String code;

    private String type;

    private String description;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private Set<Rating> ratings;

    protected Film() {

    }

    public Film(FilmId id, String code, String type, String description) {
        super(id);
        this.code = code;
        this.type = type;
        this.description = description;
    }

}
