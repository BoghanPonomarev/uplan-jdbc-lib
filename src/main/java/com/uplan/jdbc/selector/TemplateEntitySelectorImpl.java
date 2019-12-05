package com.uplan.jdbc.selector;

import com.uplan.jdbc.common.EntityParametersMapper;
import com.uplan.jdbc.common.SqlStatementConstructor;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;

public class TemplateEntitySelectorImpl<T> implements TemplateEntitySelector<T> {

    private final EntityParametersMapper<TemplateEntityComposite> sqlTemplateParametersMapper;
    private final SqlStatementConstructor<TemplateEntityComposite> sqlSelectStatementConstructor;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public TemplateEntitySelectorImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.sqlTemplateParametersMapper = new TemplateEntityParametersMapperImpl();
        this.sqlSelectStatementConstructor = new SelectSqlStatementConstructorImpl();
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<T> selectByTemplate(TemplateEntityComposite templateEntityComposite, String beforeWhereSqlStatement,
                                    String afterWhereSqlStatement, ResultSetExtractor<List<T>> resultSetExtractor) {
        Map<String, Object> mappedParameters = sqlTemplateParametersMapper.mapParameters(templateEntityComposite);
        templateEntityComposite.setMainParameters(mappedParameters);

        String sqlStatement = sqlSelectStatementConstructor.construct(templateEntityComposite,
                beforeWhereSqlStatement, afterWhereSqlStatement);

        return namedParameterJdbcTemplate.query(sqlStatement, mappedParameters, resultSetExtractor);
    }

}
