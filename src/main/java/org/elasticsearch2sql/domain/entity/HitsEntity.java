package org.elasticsearch2sql.domain.entity;

import lombok.Data;

import java.util.List;

/**
 * @author 306548063@qq.com
 * @description
 * @date 2020/5/28 19:25
 */
@Data
public class HitsEntity<T> {
    
    private int total;
    private double maxScore;
    private List<HitEntity<T>> hits;
    
}
