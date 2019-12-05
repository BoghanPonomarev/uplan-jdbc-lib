package com.uplan.jdbc.inserter;

import com.uplan.jdbc.SQLParamDeterminate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.PreparedStatement;
import java.sql.Statement;

public class KeyGenEntityCreatorImpl implements KeyGenEntityCreator {

    private JdbcTemplate jdbcTemplate;

    public KeyGenEntityCreatorImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long insert(String sqlStatement, SQLParamDeterminate paramSetter) {
        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection
                    .prepareStatement(sqlStatement
                            , Statement.RETURN_GENERATED_KEYS);
            paramSetter.determine(preparedStatement);
            return preparedStatement;
        }, generatedKeyHolder);

        Number newKey = generatedKeyHolder.getKey();
        return newKey != null ? newKey.longValue() : null;
    }

}
