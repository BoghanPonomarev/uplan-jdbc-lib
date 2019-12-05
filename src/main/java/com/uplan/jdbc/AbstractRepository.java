package com.uplan.jdbc;

import org.springframework.dao.DataRetrievalFailureException;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

/**
 * Class used for simplification of code associated with getting data  by any combinations of they fields.Also process
 * logic of throwing {@code EmptyResultDataAccessException} in case no data info with entered credentials.
 */

public abstract class AbstractRepository {

  protected <T> T getObject(Supplier<T> queryFunction) {
    try {
      return queryFunction.get();
    } catch (DataRetrievalFailureException ignore) {
    }
    return null;
  }

  protected <T> List<T> getList(Supplier<List<T>> queryFunction) {
    try {
      return queryFunction.get();
    } catch (DataRetrievalFailureException ignore) {
    }
    return Collections.emptyList();
  }


}
