package org.elasticsearch2sql.domain.entity;

import lombok.Data;

/**
 * @author 306548063@qq.com
 * @description
 * @date 2020/5/28 19:25
 */
@Data
public class HitEntity<T> {
    
    private String index;
    private String type;
    private String id;
    private double score;
    private long version;
    private T source;
    
}
