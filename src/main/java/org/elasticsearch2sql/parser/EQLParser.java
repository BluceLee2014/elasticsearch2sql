package org.elasticsearch2sql.parser;

import com.alibaba.druid.sql.ast.expr.SQLQueryExpr;
import com.alibaba.druid.sql.ast.statement.SQLExprTableSource;
import com.alibaba.druid.sql.ast.statement.SQLJoinTableSource;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLTableSource;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import org.elasticsearch2sql.exception.SqlParseException;
import org.elasticsearch2sql.parser.domain.Field;
import org.elasticsearch2sql.parser.domain.From;
import org.elasticsearch2sql.parser.domain.Select;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 306548063@qq.com
 * @Desc
 * @date 2020/5/31 16:23
 */
public class EQLParser {

    public EQLParser() {

    }


    public Select parseSelect(SQLQueryExpr mySqlExpr) throws SqlParseException {

        MySqlSelectQueryBlock query = (MySqlSelectQueryBlock)mySqlExpr.getSubQuery().getQuery();
        System.out.println(query);
        Select select = parseSelect(query);
        return select;
    }

    private void findSelect(MySqlSelectQueryBlock query, Select select, String tableAlias) throws SqlParseException {
        List<SQLSelectItem> selectList = query.getSelectList();
        for (SQLSelectItem sqlSelectItem : selectList) {
            Field field = FieldMaker.makeField(sqlSelectItem.getExpr(), sqlSelectItem.getAlias(), tableAlias);
            select.addField(field);
        }
    }

    public Select parseSelect(MySqlSelectQueryBlock query) throws SqlParseException {

        Select select = new Select();
        WhereParser whereParser = new WhereParser(this, query);


        findSelect(query, select, query.getFrom().getAlias());

        select.setFrom(findFrom(query.getFrom()).get(0));

//        select.addField();
        select.setWhere(whereParser.findWhere());
//
//        select.fillSubQueries();
//
//        select.getHints().addAll(parseHints(query.getHints()));
//
//        findLimit(query.getLimit(), select);
//
//        findOrderBy(query, select);
//
//        findGroupBy(query, select);
        return select;
    }

    private List<From> findFrom(SQLTableSource from) {
        boolean isSqlExprTable = from.getClass().isAssignableFrom(SQLExprTableSource.class);

        ArrayList<From> fromList = new ArrayList<>();
        if (isSqlExprTable) {
            SQLExprTableSource fromExpr = (SQLExprTableSource) from;
            String[] split = fromExpr.getExpr().toString().split(",");

            for (String source : split) {
                fromList.add(new From(source.trim(), fromExpr.getAlias()));
            }
        }
        return fromList;
    }

}
