package com.uplan.jdbc.extractor;

import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BaseJoinQueryResultSetExtractorBuilder<T, ID> {

    private ResultSetExtractor<T> mainEntityResultSetExtractor;

    private List<ColumnGroupJoinExtractionContract<?,T>> elementJoinExtractionContracts;

    private Function<T, ID> getMainEntityIdValueFunction;

    private SqlExtractFunction<ID> extractMainEntityIdValueFunction;

    private BaseJoinQueryResultSetExtractorBuilder(){
        elementJoinExtractionContracts = new ArrayList<>();
    }

    public static <T, ID> BaseJoinQueryResultSetExtractorBuilder<T, ID> newInstance(Class<T> tClass, Class<ID> idClass) {
        return new BaseJoinQueryResultSetExtractorBuilder<T, ID>();
    }

    public BaseJoinQueryResultSetExtractorBuilder<T, ID> mainEntityResultSetExtractor(ResultSetExtractor<T> mainEntityResultSetExtractor) {
        this.mainEntityResultSetExtractor = mainEntityResultSetExtractor;
        return this;
    }

    public BaseJoinQueryResultSetExtractorBuilder<T, ID> getMainEntityIdValueFunction(Function<T, ID> getMainEntityIdValueFunction){
        this.getMainEntityIdValueFunction = getMainEntityIdValueFunction;
        return this;
    }

    public BaseJoinQueryResultSetExtractorBuilder<T, ID> extractMainEntityIdValueFunction(SqlExtractFunction<ID> extractMainEntityIdValueFunction){
        this.extractMainEntityIdValueFunction = extractMainEntityIdValueFunction;
        return this;
    }

    public BaseJoinQueryResultSetExtractorBuilder<T, ID> elementJoinExtractionContract(ColumnGroupJoinExtractionContract<?,T> elementJoinExtractionContract) {
        elementJoinExtractionContracts.add(elementJoinExtractionContract);
        return this;
    }

    public BaseJoinQueryResultSetExtractor<T,ID> build(){
        return new BaseJoinQueryResultSetExtractorImpl<>(this.mainEntityResultSetExtractor, this.elementJoinExtractionContracts,
                        this.getMainEntityIdValueFunction, this.extractMainEntityIdValueFunction);

    }

}
