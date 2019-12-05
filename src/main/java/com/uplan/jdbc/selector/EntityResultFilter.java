package com.uplan.jdbc.selector;

import java.util.List;

public interface EntityResultFilter<E> {

  List<E> filterResultEntities(List<E> entities, List<EntityFilterOperation<?, E>> entityFilters);

}
