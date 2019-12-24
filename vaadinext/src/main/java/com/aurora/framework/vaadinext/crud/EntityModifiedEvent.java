package com.aurora.framework.vaadinext.crud;

import com.aurora.framework.data.BaseEntity;

/**
 * Created by max on 23/08/17.
 */
public class EntityModifiedEvent<E extends BaseEntity> {

    private final E entity;

    public EntityModifiedEvent(E e) {
        this.entity = e;
    }

    public E getEntity() {
        return entity;
    }
}
