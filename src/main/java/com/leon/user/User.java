package com.leon.user;

import com.leon.infrastructure.jpa.AbstractEntity;
import com.leon.rating.Rating;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "xxx_user")
@Getter
public class User extends AbstractEntity<UserId> {

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private Set<Rating> ratings;

    protected User() {

    }

    public User(UserId id, String email, String password, UserRole role) {
        super(id);
        this.email = email;
        this.password = password;
        this.role = role;
    }

    static User createUser(UserId userId, String username, String password) {
        return new User(userId, username, password, UserRole.USER);
    }

    static User createAdmin(UserId userId, String username, String password) {
        return new User(userId, username, password, UserRole.ADMIN);
    }

}
