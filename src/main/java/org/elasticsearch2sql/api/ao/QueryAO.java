package org.elasticsearch2sql.api.ao;

import lombok.Data;

@Data
public class QueryAO<T> {

    private String index;
    private String type;
    private String sql;
    private String requestBody;
    private T t;

}
