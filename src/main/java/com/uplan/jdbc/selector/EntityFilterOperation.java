package com.uplan.jdbc.selector;

import java.util.function.BiPredicate;

public class EntityFilterOperation<P,E> {

  private P fieldValue;
  private BiPredicate<P,E> isResultEntityMatch;

  public EntityFilterOperation(P fieldValue, BiPredicate<P,E> isResultEntityMatch) {
    this.fieldValue = fieldValue;
    this.isResultEntityMatch = isResultEntityMatch;
  }


  public P getFieldValue() {
    return fieldValue;
  }

  public BiPredicate<P,E> getIsResultEntityMatch() {
    return isResultEntityMatch;
  }

}
