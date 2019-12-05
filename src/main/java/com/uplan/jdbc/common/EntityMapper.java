package com.uplan.jdbc.common;

public interface EntityMapper<E,C extends EntityComposite> {

    C mapEntity(E entity);

}
