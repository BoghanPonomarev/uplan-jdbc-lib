package com.uplan.jdbc.selector;

import com.uplan.jdbc.common.SqlStatementConstructor;
import org.apache.logging.log4j.util.Strings;

import java.util.List;
import java.util.Map;

public class SelectSqlStatementConstructorImpl implements SqlStatementConstructor<TemplateEntityComposite> {

    @Override
    public String construct(TemplateEntityComposite composite, String sqlBeforeStatement, String sqlAfterStatement) {
        StringBuilder resultStatement = new StringBuilder(sqlBeforeStatement).append(" WHERE ");

        for (Map.Entry<String, ?> singleParameterEntry : composite.getMainParameters().entrySet()) {
            mapSingleParameter(singleParameterEntry, resultStatement);
        }
        return resultStatement.replace(resultStatement.lastIndexOf(" AND "), resultStatement.length(), Strings.EMPTY)
                .append(sqlAfterStatement).toString();
    }

    private void mapSingleParameter(Map.Entry<String, ?> singleParameterEntry, StringBuilder resultStatement) {
        String parameterName = singleParameterEntry.getKey();

        resultStatement.append(parameterName);
        if (singleParameterEntry.getValue() instanceof List) {
            resultStatement.append(" IN (:").append(parameterName).append(")").append(" ");
        } else {
            resultStatement.append("=:").append(parameterName).append(" ");
        }
        resultStatement.append(" AND ");
    }

}
