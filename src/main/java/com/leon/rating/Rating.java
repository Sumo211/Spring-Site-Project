package com.leon.rating;

import com.leon.film.Film;
import com.leon.infrastructure.jpa.AbstractEntity;
import com.leon.user.User;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@ToString(exclude = {"user", "film"})
public class Rating extends AbstractEntity<RatingId> {

    private int value;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "film_id")
    private Film film;

    protected Rating() {

    }

}
