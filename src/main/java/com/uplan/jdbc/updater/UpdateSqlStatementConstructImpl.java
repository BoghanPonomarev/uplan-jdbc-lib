package com.uplan.jdbc.updater;

import com.uplan.jdbc.common.SqlStatementConstructor;
import org.apache.logging.log4j.util.Strings;

import java.util.Map;

public class UpdateSqlStatementConstructImpl implements SqlStatementConstructor<UpdateEntityComposite> {

    @Override
    public String construct(UpdateEntityComposite composite, String sqlBeforeStatement, String sqlAfterStatement) {
        StringBuilder resultStatement = new StringBuilder(sqlBeforeStatement);

        String keyName = composite.getKeyName();
        for (Map.Entry<String, Object> parameterEntry : composite.getMainParameters().entrySet()) {
            if(!parameterEntry.getKey().equals(keyName)) {
                mapSingleParameter(parameterEntry, resultStatement);
            }
        }

        return resultStatement.replace(resultStatement.lastIndexOf(" , "), resultStatement.length(), Strings.EMPTY)
                .append(" WHERE ").append(keyName).append("=:").append(keyName)
                .append(sqlAfterStatement).toString();
    }

    private void mapSingleParameter(Map.Entry<String, ?> singleParameterEntry, StringBuilder resultStatement) {
        String parameterName = singleParameterEntry.getKey();

        resultStatement.append(" ").append(parameterName);
        resultStatement.append("=:").append(parameterName).append(" , ");
    }

}
