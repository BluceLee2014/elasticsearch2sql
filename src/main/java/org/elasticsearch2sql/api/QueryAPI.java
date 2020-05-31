package org.elasticsearch2sql.api;

import org.elasticsearch2sql.api.ao.QueryAO;
import org.elasticsearch2sql.domain.entity.SearchResultEntity;
import org.elasticsearch2sql.exception.SqlParseException;

import java.io.IOException;

public interface QueryAPI {

    <T> SearchResultEntity<T> query(QueryAO<T> queryAO) throws IOException, SqlParseException, ClassNotFoundException;

}
