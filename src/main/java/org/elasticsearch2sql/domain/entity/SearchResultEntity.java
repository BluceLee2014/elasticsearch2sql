package org.elasticsearch2sql.domain.entity;

import lombok.Data;

/**
 * @author 306548063@qq.com
 * @description
 * @date 2020/5/28 19:25
 */
@Data
public class SearchResultEntity<T> {

    private int took;
    private boolean timedOut;
    private ShardsEntity shards;
    private HitsEntity<T> hits;
    

}
