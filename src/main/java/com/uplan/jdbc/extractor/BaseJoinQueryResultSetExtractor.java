package com.uplan.jdbc.extractor;

import org.springframework.dao.DataAccessException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public interface BaseJoinQueryResultSetExtractor<T,ID> {

    SingleEntityExtractResult<T> extractEntities(ResultSet resultSet, Map<ID, T> mainEntitiesMap,
                                                 boolean isFirstIteration) throws SQLException, DataAccessException;

}
