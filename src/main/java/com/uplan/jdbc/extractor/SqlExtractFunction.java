package com.uplan.jdbc.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface SqlExtractFunction<R> {

    R apply(ResultSet resultSet) throws SQLException;

}
