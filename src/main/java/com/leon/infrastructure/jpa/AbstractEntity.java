package com.leon.infrastructure.jpa;

import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;

@MappedSuperclass
//TODO JPA Auditing (@CreatedDate & @LastModifiedDate)
public abstract class AbstractEntity<T extends EntityId> implements Entity<T> {

    @EmbeddedId
    private T id;

    protected AbstractEntity() {

    }

    public AbstractEntity(T id) {
        this.id = Objects.requireNonNull(id);
    }

    @Override
    public T getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;

        if (this == obj) {
            result = true;
        } else if (obj instanceof AbstractEntity) {
            AbstractEntity other = (AbstractEntity) obj;
            result = Objects.equals(id, other.id);
        }

        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return toStringHelper(this).add("id", id).toString();
    }

}
