package com.uplan.jdbc.extractor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class ListJoinQueryResultSetExtractor<T,ID> implements ResultSetExtractor<List<T>> {

  private BaseJoinQueryResultSetExtractor<T,ID> baseJoinQueryResultSetExtractor;

  private Function<T, ID> getMainEntityIdValueFunction;

  public ListJoinQueryResultSetExtractor(BaseJoinQueryResultSetExtractor<T, ID> baseJoinQueryResultSetExtractor,
                                         Function<T, ID> getMainEntityIdValueFunction) {
    this.baseJoinQueryResultSetExtractor = baseJoinQueryResultSetExtractor;
    this.getMainEntityIdValueFunction = getMainEntityIdValueFunction;
  }

  @Override
  public List<T> extractData(@Nullable ResultSet resultSet) throws SQLException, DataAccessException {
    Map<ID,T> mainEntitiesMap =new HashMap<>();
    SingleEntityExtractResult<T> entityExtractResult = baseJoinQueryResultSetExtractor
            .extractEntities(resultSet, mainEntitiesMap, true);
    do {
      T entity;
      if (entityExtractResult != null && (entity = entityExtractResult.getEntity()) != null) {
        mainEntitiesMap.putIfAbsent(getMainEntityIdValueFunction.apply(entity), entity);
      } else {
        break;
      }
    } while (entityExtractResult.isResultSetContinue()
            && (entityExtractResult = baseJoinQueryResultSetExtractor.extractEntities(resultSet, mainEntitiesMap, false)) != null);

    return new ArrayList<>(mainEntitiesMap.values());
  }

}
