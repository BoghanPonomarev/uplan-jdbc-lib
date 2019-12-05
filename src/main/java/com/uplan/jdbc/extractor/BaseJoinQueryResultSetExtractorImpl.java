package com.uplan.jdbc.extractor;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class BaseJoinQueryResultSetExtractorImpl<T,ID> implements BaseJoinQueryResultSetExtractor<T,ID> {

    private ResultSetExtractor<T> mainEntityResultSetExtractor;

    private List<ColumnGroupJoinExtractionContract<?,T>> elementJoinExtractionContracts;

    private Function<T, ID> getMainEntityIdValueFunction;

    private SqlExtractFunction<ID> extractMainEntityIdValueFunction;

    public BaseJoinQueryResultSetExtractorImpl(ResultSetExtractor<T> mainEntityResultSetExtractor,
                                               List<ColumnGroupJoinExtractionContract<?, T>> elementJoinExtractionContracts,
                                               Function<T, ID> getMainEntityIdValueFunction,
                                               SqlExtractFunction<ID> extractMainEntityIdValueFunction) {
        this.mainEntityResultSetExtractor = mainEntityResultSetExtractor;
        this.elementJoinExtractionContracts = elementJoinExtractionContracts;
        this.getMainEntityIdValueFunction = getMainEntityIdValueFunction;
        this.extractMainEntityIdValueFunction = extractMainEntityIdValueFunction;
    }

    @Override
    public SingleEntityExtractResult<T> extractEntities(ResultSet resultSet,
                              Map<ID,T> mainEntitiesMap, boolean isFirstIteration) throws SQLException, DataAccessException {
        if (isFirstIteration) {
            if (!resultSet.next()) {
                return null;
            }
        }

        T extractedEntity = mainEntityResultSetExtractor.extractData(resultSet);
        boolean isResultSetContinue = extractSubEntities(resultSet, determinateOriginalEntity(extractedEntity, mainEntitiesMap));

        return new SingleEntityExtractResult<>(extractedEntity, isResultSetContinue);
    }

    private boolean extractSubEntities(ResultSet resultSet, T mainEntity) throws SQLException {
        boolean isResultSetContinue;

        do {
            for(ColumnGroupJoinExtractionContract<?,T> columnGroupJoinExtractionContract : elementJoinExtractionContracts){
                extractSingleSubEntity(columnGroupJoinExtractionContract, resultSet, mainEntity);
            }
            isResultSetContinue = resultSet.next();
        } while (isResultSetContinue && Objects.equals(extractMainEntityIdValueFunction.apply(resultSet), getMainEntityIdValueFunction.apply(mainEntity)));

        return isResultSetContinue;
    }

    private <P> void extractSingleSubEntity(ColumnGroupJoinExtractionContract<P,T> elementJoinExtractionContract,
                                            ResultSet resultSet, T mainEntity) throws SQLException {
        P extractedJoinEntity = elementJoinExtractionContract.getEntityRowMapper().extractData(resultSet);
        if (extractedJoinEntity != null) {
            elementJoinExtractionContract.getCallSetterMethod().accept(extractedJoinEntity, mainEntity);
        }

    }

    private T determinateOriginalEntity(T entity, Map<ID,T> originalEntities) {
        return originalEntities.getOrDefault(getMainEntityIdValueFunction.apply(entity), entity);
    }

}
