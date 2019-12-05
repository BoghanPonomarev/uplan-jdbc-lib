package com.uplan.jdbc;

import com.uplan.jdbc.extractor.BaseJoinQueryResultSetExtractor;
import com.uplan.jdbc.extractor.ListJoinQueryResultSetExtractor;
import com.uplan.jdbc.extractor.SingleJoinQueryResultSetExtractor;
import com.uplan.jdbc.inserter.KeyGenEntityCreator;
import com.uplan.jdbc.inserter.KeyGenEntityCreatorImpl;
import com.uplan.jdbc.selector.*;
import com.uplan.jdbc.updater.EntityUpdater;
import com.uplan.jdbc.updater.EntityUpdaterImpl;
import com.uplan.jdbc.updater.UpdateEntityComposite;
import com.uplan.jdbc.updater.executor.JoinUpdateOperation;
import com.uplan.jdbc.updater.executor.JoinUpdateOperationsExecutor;
import com.uplan.jdbc.updater.executor.JoinUpdateOperationsExecutorImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.function.Function;

public class EntityJdbcTemplateImpl<E, ID> implements EntityJdbcTemplate<E, ID> {

  private JdbcTemplate jdbcTemplate;
  private String entityTableName;

  private KeyGenEntityCreator keyGenEntityCreator;

  private TemplateEntitySelector<E> templateEntitySelector;
  private EntityResultFilter<E> entityResultFilter;

  private ResultSetExtractor<E> singleEntityJoinQueryUserResultSetExtractor;
  private ResultSetExtractor<List<E>> listJoinQueryUserResultSetExtractor;

  private EntityUpdater entityUpdater;
  private JoinUpdateOperationsExecutor<ID> joinUpdateOperationsExecutor;

  public EntityJdbcTemplateImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate, String entityTableName,
                                BaseJoinQueryResultSetExtractor<E, ID> baseJoinQueryResultSetExtractor, Function<E, ID> getMainEntityIdValueFunction) {
    this.jdbcTemplate = jdbcTemplate;
    this.entityTableName = entityTableName;

    this.keyGenEntityCreator = new KeyGenEntityCreatorImpl(jdbcTemplate);
    this.templateEntitySelector = new TemplateEntitySelectorImpl<>(namedParameterJdbcTemplate);
    this.entityResultFilter = new EntityResultFilterImpl<>();
    this.singleEntityJoinQueryUserResultSetExtractor = new SingleJoinQueryResultSetExtractor<>(baseJoinQueryResultSetExtractor);
    this.listJoinQueryUserResultSetExtractor = new ListJoinQueryResultSetExtractor<>(baseJoinQueryResultSetExtractor, getMainEntityIdValueFunction);
    this.entityUpdater = new EntityUpdaterImpl(namedParameterJdbcTemplate);
    this.joinUpdateOperationsExecutor = new JoinUpdateOperationsExecutorImpl<>();
  }

  public EntityJdbcTemplateImpl(JdbcTemplate jdbcTemplate, KeyGenEntityCreator keyGenEntityCreator,
                                TemplateEntitySelector<E> templateEntitySelector,
                                EntityResultFilter<E> entityResultFilter,
                                ResultSetExtractor<E> singleEntityJoinQueryUserResultSetExtractor,
                                ResultSetExtractor<List<E>> listJoinQueryUserResultSetExtractor,
                                EntityUpdater entityUpdater, JoinUpdateOperationsExecutor<ID> joinUpdateOperationsExecutor) {
    this.jdbcTemplate = jdbcTemplate;
    this.keyGenEntityCreator = keyGenEntityCreator;
    this.templateEntitySelector = templateEntitySelector;
    this.entityResultFilter = entityResultFilter;
    this.singleEntityJoinQueryUserResultSetExtractor = singleEntityJoinQueryUserResultSetExtractor;
    this.listJoinQueryUserResultSetExtractor = listJoinQueryUserResultSetExtractor;
    this.entityUpdater = entityUpdater;
    this.joinUpdateOperationsExecutor = joinUpdateOperationsExecutor;
  }

  @Override
  public Long insertWithKeyGeneration(String sqlStatement, SQLParamDeterminate paramSetter) {
    return keyGenEntityCreator.insert(sqlStatement, paramSetter);
  }

  @Override
  public E selectById(String sqlStatement, ID entityId) {
    return jdbcTemplate.query(sqlStatement, singleEntityJoinQueryUserResultSetExtractor, entityId);
  }

  @Override
  public E selectByParameters(String sqlStatement, Object... params) {
    return jdbcTemplate.query(sqlStatement, singleEntityJoinQueryUserResultSetExtractor, params);
  }

  @Override
  public List<E> selectByTemplate(TemplateEntityComposite templateEntityComposite,
                                  String beforeWhereSqlStatement, String afterWhereSqlStatement,
                                  List<EntityFilterOperation<?,E>> entityFilters) {

    List<E> resultEntities = templateEntitySelector.selectByTemplate(templateEntityComposite,
            beforeWhereSqlStatement, afterWhereSqlStatement, listJoinQueryUserResultSetExtractor);
    return entityResultFilter.filterResultEntities(resultEntities, entityFilters);
  }

  @Override
  public boolean updateEntity(UpdateEntityComposite entityUpdateComposite,
                              List<JoinUpdateOperation<?, ID>> joinUpdateOperations, ID entityId) {
    return entityUpdater.updateEntity(entityUpdateComposite, entityTableName)
            && joinUpdateOperationsExecutor.execute(joinUpdateOperations, entityId);
  }
}
