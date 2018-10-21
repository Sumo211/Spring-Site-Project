package com.leon.infrastructure.jpa;

import java.io.Serializable;

public interface EntityId<T> extends Serializable {

    T getId();

    String asString();

}
