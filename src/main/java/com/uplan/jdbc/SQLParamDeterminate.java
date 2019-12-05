package com.uplan.jdbc;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SQLParamDeterminate {

  void determine(PreparedStatement preparedStatement) throws SQLException;

}
