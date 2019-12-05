package com.uplan.jdbc.extractor;

import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.function.BiConsumer;

public class ColumnGroupJoinExtractionContract<E,P> {

    private ResultSetExtractor<E> entityRowMapper;
    private BiConsumer<E,P> callSetterMethod;

    public ColumnGroupJoinExtractionContract(ResultSetExtractor<E> entityRowMapper, BiConsumer<E, P> callSetterMethod) {
        this.entityRowMapper = entityRowMapper;
        this.callSetterMethod = callSetterMethod;
    }

    public ResultSetExtractor<E> getEntityRowMapper() {
        return entityRowMapper;
    }

    public BiConsumer<E, P> getCallSetterMethod() {
        return callSetterMethod;
    }

}
