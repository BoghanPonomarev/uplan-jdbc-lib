package com.uplan.jdbc.selector;

import org.springframework.jdbc.core.ResultSetExtractor;

import java.util.List;

public interface TemplateEntitySelector<T> {

  List<T> selectByTemplate(TemplateEntityComposite templateEntityComposite, String beforeWhereSqlStatement,
                           String afterWhereSqlStatement, ResultSetExtractor<List<T>> resultSetExtractor);

}
