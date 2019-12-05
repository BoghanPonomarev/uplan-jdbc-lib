package com.uplan.jdbc.selector;

import java.util.List;

public class EntityResultFilterImpl<E> implements EntityResultFilter<E> {

  @Override
  public List<E> filterResultEntities(List<E> entities, List<EntityFilterOperation<?,E>> entityFilterOperations) {
    for(EntityFilterOperation<?,E> filterOperation: entityFilterOperations) {
      if(filterOperation.getFieldValue() != null) {
        loopSingleFilterOperation(entities, filterOperation);
      }
    }
    return entities;
  }

  private <P> void loopSingleFilterOperation(List<E> entities, EntityFilterOperation<P,E> filterOperation) {
    for(int i=0;i<entities.size();i++) {
      if(!filterOperation.getIsResultEntityMatch().test(filterOperation.getFieldValue(), entities.get(i))) {
        entities.remove(i--);
      }
    }
  }

}
