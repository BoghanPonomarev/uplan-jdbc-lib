package com.uplan.jdbc.extractor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SingleJoinQueryResultSetExtractor<T, ID> implements ResultSetExtractor<T> {

    private BaseJoinQueryResultSetExtractor<T, ID> baseJoinQueryResultSetExtractor;

    public SingleJoinQueryResultSetExtractor(BaseJoinQueryResultSetExtractor<T, ID> baseJoinQueryResultSetExtractor) {
        this.baseJoinQueryResultSetExtractor = baseJoinQueryResultSetExtractor;
    }

    @Override
    public T extractData(@Nullable ResultSet resultSet) throws SQLException, DataAccessException {
        Map<ID, T> mainEntitiesMap =new HashMap<>();
        SingleEntityExtractResult<T> entityExtractResult = baseJoinQueryResultSetExtractor
                .extractEntities(resultSet, mainEntitiesMap, true);

        return entityExtractResult != null ? entityExtractResult.getEntity() : null;
    }

}
