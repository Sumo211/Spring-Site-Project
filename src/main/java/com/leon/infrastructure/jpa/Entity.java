package com.leon.infrastructure.jpa;

public interface Entity<T extends EntityId> {

    T getId();

}
