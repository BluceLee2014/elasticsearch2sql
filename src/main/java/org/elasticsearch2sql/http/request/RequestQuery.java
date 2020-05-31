package org.elasticsearch2sql.http.request;


import org.elasticsearch2sql.api.ao.QueryAO;
import org.elasticsearch2sql.domain.entity.SearchResultEntity;

import java.io.IOException;

public interface RequestQuery {

    static final String SEARCH_KEYWORD = "_search";

    <T> SearchResultEntity<T> query(String requestBody, String uri, Class<T> clazz) throws IOException;

    <T> SearchResultEntity<T> query(QueryAO<T> queryAO) throws IOException;
}
