package com.uplan.jdbc.common;

public interface SqlStatementConstructor<T> {

    String construct(T composite, String sqlBeforeStatement, String sqlAfterStatement);

}
