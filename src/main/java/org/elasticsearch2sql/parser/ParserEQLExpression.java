package org.elasticsearch2sql.parser;

import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.expr.SQLQueryExpr;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.dialect.mysql.ast.statement.MySqlSelectQueryBlock;
import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.sql.parser.SQLExprParser;
import com.alibaba.druid.sql.parser.SQLParser;
import com.alibaba.druid.sql.parser.Token;
import com.alibaba.fastjson.JSON;
import org.elasticsearch2sql.parser.domain.Select;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch2sql.exception.SqlParseException;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ParserEQLExpression {

    public enum A{
        SELECT, INSERT, DELETE
    }

    public String doParserEQL(String sql) throws SqlParseException {
        sql = sql.replaceAll("\n"," ");
        String firstWord = sql.substring(0, sql.indexOf(' '));
        String resultJson = null;
        switch (A.valueOf(firstWord.toUpperCase())) {
            case SELECT:
                return this.creatJSON(sql);
//                SQLQueryExpr sqlExpr = (SQLQueryExpr) toSqlExpr(sql);
//                SQLExprParser sqlParser =  new EQLExpression(sql);
//                SQLExpr expr = sqlParser.expr();
//                SQLQueryExpr sqlQueryExpr = (SQLQueryExpr) expr;
//                if (sqlParser.getLexer().token() != Token.EOF) {
//                    throw new ParserException("illegal sql expr : " + sql);
//                }
//                SQLSelectQuery query1 = sqlQueryExpr.getSubQuery().getQuery();
//                MySqlSelectQueryBlock query = (MySqlSelectQueryBlock)query1;
//                System.out.println(query);
            case INSERT:
                break;
            case DELETE:
                break;
            default:
                break;
        }
        return null;
    }

    private String creatJSON(String sql) throws SqlParseException {
        SQLExprParser parser = new EQLExpression(sql);
        SQLExpr expr = parser.expr();

        if (parser.getLexer().token() != Token.EOF) {
            throw new ParserException("illegal sql expr : " + sql);
        }
        SQLQueryExpr sqlQueryExpr = (SQLQueryExpr) expr;
        Select select = new EQLParser().parseSelect(sqlQueryExpr);
        System.out.println(select);
        return this.createESJSON(select);
    }

    private String createESJSON(Select select){
        Map<String, Object> sourceMap = ConditionParser.createField(select);
        Map<String, Object> query = ConditionParser.createQuery(select);
        Map<String, Object> rootNode = ConditionParser.creatRootNode(sourceMap, query);
        return JSON.toJSONString(rootNode);
    }
}
