package org.elasticsearch2sql.http.request.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch2sql.api.ao.QueryAO;
import org.elasticsearch2sql.domain.entity.SearchResultEntity;
import org.elasticsearch2sql.http.RequestElasticSearch;
import org.elasticsearch2sql.http.request.RequestQuery;
import org.elasticsearch2sql.util.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.io.IOException;

@Slf4j
@Component
public class RequestQueryImpl implements RequestQuery {

    @Autowired
    RequestElasticSearch requestElasticSearch;

    @Override
    public <T> SearchResultEntity<T> query(String requestBody, String uri, Class<T> clazz) throws IOException {
        String resultVal = requestElasticSearch.doRequestResultStr(RequestElasticSearch.METHOD.POST, requestBody, uri);
        if(log.isDebugEnabled()){
            log.debug("query [ uri = {} ] result val = {}", uri, resultVal);
        }
        return JSONObject.parseObject(resultVal, new TypeReference<SearchResultEntity<T>>(clazz) {});
    }

    @Override
    public <T> SearchResultEntity<T> query(QueryAO<T> queryAO) throws IOException {
        return (SearchResultEntity<T>) this.query(queryAO.getRequestBody(), this.getSearchURI(queryAO), queryAO.getT().getClass());
    }

    private String getSearchURI(QueryAO queryAO) {
        String index = queryAO.getIndex();
        String type = queryAO.getType();
        StringBuffer uriStr = new StringBuffer(2);
        if(CommonUtils.isNotEmpty(index)){
            uriStr.append(index).append(CommonUtils.BACKSLASH);
        }
        if(CommonUtils.isNotEmpty(type)){
            uriStr.append(type).append(CommonUtils.BACKSLASH);
        }
        uriStr.append(SEARCH_KEYWORD);
        return uriStr.toString();
    }
}
