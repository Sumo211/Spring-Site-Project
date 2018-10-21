package com.leon.infrastructure.jpa;

import javax.persistence.MappedSuperclass;
import java.util.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;

@MappedSuperclass
public abstract class AbstractEntityId<T> implements EntityId<T> {

    private T id;

    protected AbstractEntityId() {

    }

    public AbstractEntityId(T id) {
        this.id = Objects.requireNonNull(id);
    }

    @Override
    public T getId() {
        return this.id;
    }

    @Override
    // @JsonValue: a compact alternative for EntityIdJsonSerializer class
    public String asString() {
        return id.toString();
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;

        if (this == obj) {
            result = true;
        } else if (obj instanceof AbstractEntityId) {
            AbstractEntityId other = (AbstractEntityId) obj;
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
