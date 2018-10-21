package com.leon.infrastructure.jpa;

public interface UniqueIdGenerator<T> {

    T getNextUniqueId();

}
