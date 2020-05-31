package org.elasticsearch2sql.api.impl;

import org.elasticsearch2sql.api.QueryAPI;
import org.elasticsearch2sql.api.ao.QueryAO;
import org.elasticsearch2sql.domain.entity.SearchResultEntity;
import org.elasticsearch2sql.exception.SqlParseException;
import org.elasticsearch2sql.http.request.RequestQuery;
import org.elasticsearch2sql.parser.ParserEQLExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class QueryAPIImpl implements QueryAPI {

    @Autowired
    RequestQuery requestQuery;

    @Autowired
    ParserEQLExpression parserEQLExpression;

    @Override
    public <T> SearchResultEntity<T> query(QueryAO<T> queryAO) throws IOException, SqlParseException, ClassNotFoundException {
        String sql = queryAO.getSql();
        // TODO 解析SQL，生成JSON字符串
        queryAO.setRequestBody(parserEQLExpression.doParserEQL(sql));
        return (SearchResultEntity<T>) requestQuery.query(queryAO);
    }

}
