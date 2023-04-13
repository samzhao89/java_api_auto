package com.lemon.untils;

import com.alibaba.fastjson.JSONPath;
import com.lemon.constans.Constants;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Project: java19_jmeter2
 * @Site:
 * @Forum:
 * @Copyright:
 * @Author: sam
 * @Create: 2023-03-30 15:54
 * @Desc：接口鉴权类
 **/
@SuppressWarnings("unchecked")
public class Authentication {
    //鉴权类 类似就meter中的用户变量 vars
    public  static Map<String,Object> vars=new HashMap<String, Object>();


    /**
     * 使用jsonpath获取内容存储到vars变量，给其他接口使用
     * @param json          json字符串
     * @param expression    jsonpath表达式
     * @param key           存储到vars的key
     */
    public static void json2Vars(String json,String expression,String key){
        //如果json不是空，则继续操作
        if (StringUtils.isNotBlank(json)){
            //使用jsonpath获取内容，给其他接口使用
            Object  obj = JSONPath.read(json, expression);
            System.out.println(key+":"+obj);
            //如果获取内容不是，存入VARS变量，给其他接口使用。
            if(obj!=null){
                Authentication.vars.put(key,obj);
            }
        }
    }

    /**
     * 获取带token的请求头Map集合
     * @return
     */

    public static Map<String, String> getTokenHeader() {
        //3、调用接口
        //3.1、从vars中获取token
        Object token = Authentication.vars.get("${token}");
        System.out.println("Recharge token:"+token);
        //3.2、添加到请求头
        //3.3、改造call支持传递请求头
        Map<String,String> headers=new HashMap<>();
        headers.put("Authorization","Bearer " + token);
        headers.putAll(Constants.HEADERS);
        return headers;
    }
}
