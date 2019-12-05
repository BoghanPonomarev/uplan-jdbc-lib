package com.uplan.jdbc.extractor;

public class SingleEntityExtractResult<T> {

    private T entity;
    private boolean isResultSetContinue;

    public SingleEntityExtractResult(T entity, boolean isResultSetContinue) {
        this.entity = entity;
        this.isResultSetContinue = isResultSetContinue;
    }

    public T getEntity() {
        return entity;
    }

    public boolean isResultSetContinue() {
        return isResultSetContinue;
    }
}
