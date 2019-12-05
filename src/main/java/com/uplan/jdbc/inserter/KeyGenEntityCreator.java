package com.uplan.jdbc.inserter;


import com.uplan.jdbc.SQLParamDeterminate;

public interface KeyGenEntityCreator {

    Long insert(String sqlStatement, SQLParamDeterminate paramSetter);

}
