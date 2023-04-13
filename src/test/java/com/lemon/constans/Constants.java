package com.lemon.constans;

import com.lemon.untils.Excelutils;
import java.util.HashMap;
import java.util.Map;

/**
 * @Project: java19_jmeter2
 * @Site:
 * @Forum:
 * @Copyright:
 * @Author: sam
 * @Create: 2023-03-29 09:24
 * @Desc：
 *
 * 常量类
 **/
public class Constants {
        //数据驱动excel路径
        //public  static final String ExcelPath= Excelutils.class.getClassLoader().getResource(".src/test/resources/cases_v3.xlsx").getPath();
        public  static final String ExcelPath="src/test/resources/cases_v3.xlsx";
        //默认请求头
        public  static  final Map<String,String> HEADERS=new HashMap<>();
        //excel 响应回写列
        public static final int RESPONSE_WRITE_BACK_CELL = 8;
        //excel 断言回写列
        public static final int ASSERT_WRITE_BACK_CELL = 10;
        //数据库链接URL                              jdbc:数据库名称://ip:port/数据库名称
        //                                         //jdbc:oracle:thin:@//127.0.0.1:1521/orcl
        public static final String  JDBC_URL="jdbc:mysql://api.lemonban.com:3306/futureloan?useUnicode=true&characterEncoding=utf-8";
        //数据库用户名
        public static final String  JDBC_USERNAME="future";
        //数据库密码
        public static final String  JDBC_PASSWORD="123456";
}
