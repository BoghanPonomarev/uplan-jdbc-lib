package com.uplan.jdbc.updater;

import com.uplan.jdbc.common.EntityParametersMapper;
import com.uplan.jdbc.common.SqlStatementConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Map;

public class EntityUpdaterImpl implements EntityUpdater {

    private final EntityParametersMapper<UpdateEntityComposite> updateEntityParameterMapper;
    private final SqlStatementConstructor<UpdateEntityComposite> updateSqlStatementConstructor;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public EntityUpdaterImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.updateEntityParameterMapper = new UpdateEntityParametersMapperImpl();
        this.updateSqlStatementConstructor = new UpdateSqlStatementConstructImpl();
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public boolean updateEntity(UpdateEntityComposite entityUpdateComposite, String tableName) {
        Map<String,Object> mappedParameters = updateEntityParameterMapper.mapParameters(entityUpdateComposite);

        if(mappedParameters.size() == 1) {
            return true;
        }

        entityUpdateComposite.setMainParameters(mappedParameters);

        String baseSqlStatement = "UPDATE " + tableName + " SET ";
        String sqlStatement = updateSqlStatementConstructor.construct(entityUpdateComposite,baseSqlStatement, Strings.EMPTY);

        return namedParameterJdbcTemplate.update(sqlStatement, mappedParameters) != 0;
    }

}
