package org.elasticsearch2sql.http.request;


import org.elasticsearch2sql.domain.entity.SearchResultEntity;

import java.io.IOException;

public interface RequestQuery {

    <T> SearchResultEntity<T> query(String requestBody, String uri, Class<T> clazz) throws IOException;

}
