package com.lemon.untils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * @Project: java19_jmeter2
 * @Site:
 * @Forum:
 * @Copyright:
 * @Author: sam
 * @Create: 2023-04-03 14:54
 * @Desc：
 **/
@SuppressWarnings("unchecked")
public class SQLUtils {

    /**
     * 查询数据库单行单列结果集
     * @param sql   sql语句
     * @return      查询结果
     */
    public static Object getSingleResult(String sql) {
        if (StringUtils.isBlank(sql)){
            System.out.println("sql为空");
            return null;
        }
        Object result = null;
        QueryRunner runner=new QueryRunner();
        //获取数据库连接
        Connection conn = JDBCUtils.getConnection();
        try {
            //String sql="select *  from member a where a.moble_phone='15670890431'";
            //创建处理结果集对象
            ScalarHandler  handler=new ScalarHandler();
            //执行查询语句
            result = runner.query(conn, sql, handler);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtils.close(conn);
        }
        return  result;
    }

    public static void mapHanddler() {
        //DBUtils
        QueryRunner runner=new QueryRunner();
        Connection conn = JDBCUtils.getConnection();
        try {
            String sql="SELECT * FROM member a where a.mobile_phone = '15670890431'";
            MapHandler handler=new MapHandler();
            Map<String, Object> map = runner.query(conn, sql, handler);
            System.out.println(map);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }finally {
            JDBCUtils.close(conn);
        }
    }
}
