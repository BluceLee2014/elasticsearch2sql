package org.elasticsearch2sql.parser;

import com.alibaba.fastjson.JSON;
import org.elasticsearch2sql.parser.domain.Condition;
import org.elasticsearch2sql.parser.domain.Field;
import org.elasticsearch2sql.parser.domain.Select;
import org.elasticsearch2sql.parser.domain.Where;

import java.util.*;

/**
 * @author 306548063@qq.com
 * @Desc
 * @date 2020/5/31 19:29
 */
public class ConditionParser {

    private static final String ROOT_SOURCE = "_source";

    public enum OPEAR {
        TERM, MATCH
    }

    /**
     * 创建返回字段
     * @param select
     * @param select
     * @return
     */
    public static Map<String, Object> createField(Select select) {
        List<Field> fields = select.getFields();
        Map<String, Object> sourceMap = new HashMap<String, Object>(1);
        List<String> sourceList = new ArrayList<>(fields.size());
        for (Field field : fields) {
            sourceList.add(field.getAlias() == null ? field.getName() : field.getAlias());
        }
        sourceMap.put(ROOT_SOURCE, sourceList);
        return sourceMap;
    }

    public static Map<String, Object> createQuery(Select select){
        System.out.println(select);
        Where where = select.getWhere();
        LinkedList<Where> wheres = where.getWheres();
        List<Object> mustList = new ArrayList<>();
        Map<String, Object> param = null;
        for (Where w :wheres){
            Condition condition = (Condition) w;
            param = createParam(condition, mustList);
        }
        System.out.println(param);
        System.out.println(JSON.toJSONString(param));
        return param;
    }

    private static Map<String, Object> createParam(Condition condition, List<Object> mustList){
        Map<String, Object> rootMap = new HashMap<>();
        if(Where.CONN.AND == condition.getConn()){
            List<Object> objects = doMust(condition, mustList);
            rootMap.put("must", objects);
        }

        return rootMap;
    }

    private static List<Object> doMust(Condition condition, List<Object> mustList){
        switch (condition.getOpear()){
            case EQ:
                mustList.add(doTerm(condition));
                break;
            case LIKE:
                mustList.add(doMatch(condition));
                break;
            default:
                break;
        }

        return mustList;
    }

    private static Map<String, Map<String, Map<String, Object>>> doTerm(Condition condition){
        Map<String, Map<String, Map<String, Object>>> rootMap = new HashMap<>(1);
        Map<String, Map<String, Object>> childMap = new HashMap<>(1);
        Map<String, Object> valueMap = new HashMap<>(1);
        valueMap.put("value", condition.getValue());
        childMap.put(condition.getName(), valueMap);
        rootMap.put("term",childMap);
        return rootMap;
    }

    private static Map<String, Map<String, Object>> doMatch(Condition condition){
        Map<String, Map<String, Object>> rootMap = new HashMap<>(1);
        Map<String, Object> childMap = new HashMap<>(1);
        childMap.put(condition.getName(), condition.getValue());
        rootMap.put("match",childMap);
        return rootMap;
    }

    public static Map<String, Object> creatRootNode(Map<String, Object> rootMap, Map<String, Object> map){
        Map<String, Object> boolMap = new HashMap<>();
        boolMap.put("bool", map);
        rootMap.put("query", boolMap);
        return rootMap;
    }
}
