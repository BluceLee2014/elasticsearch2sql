package org.elasticsearch2sql.http;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class RequestElasticSearch implements Serializable {
    private static final long serialVersionUID = -529312788708024761L;

    public enum METHOD{
        GET, POST, PUT, DELETE
    }

    @Autowired
    RestClient restClient;


    public Response doRequest(METHOD method, String body, String uri, Map<String, String> params) throws IOException {
        return restClient.performRequest(method.name(), uri, params, this.createHttpEntity(body));
    }

    public String doRequestResultStr(METHOD method, String body, String uri, Map<String, String> params) throws IOException {
        Response response = this.doRequest(method,  body, uri, params);
        return this.parseResponse(response);
    }

    public String doRequestResultStr(METHOD method, String body, String uri) throws IOException {
        Response response = this.doRequest(method,  body, uri, new HashMap<>(1));
        return this.parseResponse(response);
    }

    public Response doRequest(METHOD method, String body, String uri) throws IOException {
        return this.doRequest(method,  body, uri, new HashMap<>(1));
    }


    private HttpEntity createHttpEntity(String body){
        return this.createHttpEntity(body, ContentType.APPLICATION_JSON);
    }

    private HttpEntity createHttpEntity(String body, ContentType contentType){
        return new NStringEntity(body, contentType);
    }

    private String parseResponse(Response response) throws IOException {
        String resultVal = null;
        /** 请求发送成功，并得到响应 **/
        if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            /** 读取服务器返回过来的json字符串数据 **/
            resultVal = EntityUtils.toString(response.getEntity());
            log.debug(resultVal);
        }
        return resultVal;
    }
}
