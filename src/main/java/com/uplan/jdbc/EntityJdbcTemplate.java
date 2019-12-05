package com.uplan.jdbc;

import com.uplan.jdbc.selector.EntityFilterOperation;
import com.uplan.jdbc.selector.TemplateEntityComposite;
import com.uplan.jdbc.updater.UpdateEntityComposite;
import com.uplan.jdbc.updater.executor.JoinUpdateOperation;

import java.util.List;

public interface EntityJdbcTemplate<E,ID> {

    Long insertWithKeyGeneration(String sqlStatement, SQLParamDeterminate paramSetter);

    E selectById(String sqlStatement, ID entityId);

    E selectByParameters(String sqlStatement, Object... params);

    List<E> selectByTemplate(TemplateEntityComposite templateEntityComposite, String beforeWhereSqlStatement,
                             String afterWhereSqlStatement, List<EntityFilterOperation<?, E>> entityFilters);

    boolean updateEntity(UpdateEntityComposite entityUpdateComposite,
                         List<JoinUpdateOperation<?, ID>> joinUpdateOperations, ID entityId);

}
