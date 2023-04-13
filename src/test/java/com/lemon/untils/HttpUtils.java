package com.lemon.untils;

import com.alibaba.fastjson.JSONObject;
import com.lemon.pojo.CaseInfo;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xwpf.usermodel.IBody;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

/**
 * @Project: java19_jmeter2
 * @Site:
 * @Forum:
 * @Copyright:
 * @Author: sam
 * @Create: 2023-03-22 18:42
 * @Desc：
 **/
@SuppressWarnings("unchecked")
public class HttpUtils {

    private static Logger logger = Logger.getLogger(HttpUtils.class);
    public static  HttpResponse call(String method,String contentType,String url,String params,Map<String,String> headers)  {

        //如果请求方式是post
        try {
            if ("post".equalsIgnoreCase(method)) {
                //如果参数方式是json
                if ("json".equalsIgnoreCase(contentType)) {
                    return HttpUtils.jsonPost(url,params,headers);
                    //如果参数方式是form
                } else if ("form".equalsIgnoreCase(contentType)) {
                    params = json2KeyValue(params);
                    return HttpUtils.formPost(url,params,headers);
                } else {
                    System.out.println("method = " + method + ", contentType = " + contentType + ", url = " + url + ", params = " + params);
                }
                //如果请求方式是get
            } else if ("get".equalsIgnoreCase(method)) {
                //处理url url/xxx/yyy/2/zzz
                return HttpUtils.get(url,headers);
                //如果请求方式是patch
            } else if ("patch".equalsIgnoreCase(method)) {
                return HttpUtils.patch(url,params,headers);
            } else {
                System.out.println("method = " + method + ", contentType = " + contentType + ", url = " + url + ", params = " + params);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * JSON转成form参数
     * @param jsonStr Json参数
     * @return
     */
    public static  String json2KeyValue(String jsonStr){
        //把json转成map
        Map<String,String> map = JSONObject.parseObject(jsonStr, Map.class);
        //获取所有key
        Set<String> keySet = map.keySet();
        String result="";
        //遍历
        for (String key : keySet) {
            //通过key获取值
            String value=map.get(key);
            //拼接key=value
            result+=key+"="+value+"&";
        }
        //去掉最后一个多余的&
        result = result.substring(0, result.length() - 1);
        return result;
    }
    public static HttpResponse get(String url,Map<String,String> headers) throws Exception {
        //1、创建get请求并写入url
        HttpGet get = new HttpGet(url);
        addHeaders(headers, get);
        //2、添加请求头
        //get.addHeader("X-Lemonban-Media-Type","lemonban.v2");
        //3、创建一个客户端  XXXs  XXXUtils 工具类
        HttpClient client = HttpClients.createDefault();
        //4、客户端发送请求,并且返回响应对象（响应头、响应体、响应状态码）
        HttpResponse respose = client.execute(get);
        //6、获取响应头、响应体、响应状态码
        //6.1、获取响应头
        //printResponse(respose);
        return respose;
    }  /**
     * 发送http post请求。
     * @param url               接口请求地址
     * @param headers           请求头
     * @param params            json格式的参数
     * @throws Exception
     */
    public static HttpResponse jsonPost(String url,String params,Map<String,String> headers) throws Exception {
        HttpPost post = new HttpPost(url);
        //2、在POST请求上添加请求头
        addHeaders(headers, post);
        //post.addHeader("X-Lemonban-Media-Type","lemonban.v2");
        //post.addHeader("Content-Type","application/json");
        //3、请求参数 加在请求体里面
        StringEntity stringEntityentity=new StringEntity(params,"UTF-8");
        post.setEntity(stringEntityentity);
        //4、创建一个客户端  XXXs  XXXUtils 工具类
        HttpClient client = HttpClients.createDefault();
        //5、客户端发送请求,并且返回响应对象（响应头、响应体、响应状态码）
        HttpResponse respose = client.execute(post);
        //6、获取响应头、响应体、响应状态码
        //6.1、获取响应头
        //printResponse(respose);
        return respose;
    }


    /**
     * 发送http post请求。
     * @param url               接口请求地址
     * @param headers           请求头
     * @param params            key=value格式的参数
     * @throws Exception
     */
    public static HttpResponse formPost(String url,String params,Map<String,String> headers) throws Exception {
        HttpPost post = new HttpPost(url);
        addHeaders(headers, post);
        //2、在POST请求上添加请求头
        //post.addHeader("Content-Type","application/x-www-form-urlencoded");
        //3、请求参数 加在请求体里面
        StringEntity stringEntity=new StringEntity(params,"UTF-8");
        post.setEntity(stringEntity);
        //4、创建一个客户端  XXXs  XXXUtils 工具类
        HttpClient client = HttpClients.createDefault();
        //5、客户端发送请求,并且返回响应对象（响应头、响应体、响应状态码）
        HttpResponse respose = client.execute(post);
        //6、获取响应头、响应体、响应状态码
        //6.1、获取响应头
        //printResponse(respose);
        return respose;
    }
    /**
     * 发送http patch请求。
     * @param url               接口请求地址
     * @param headers           请求头
     * @param params            json格式的参数
     * @throws Exception
     */
    public static HttpResponse patch(String url,String params,Map<String,String> headers) throws Exception {
        HttpPatch patch = new HttpPatch(url);
        addHeaders(headers, patch);
        //2、在patch请求上添加请求头
//        patch.addHeader("X-Lemonban-Media-Type","lemonban.v2");
//        patch.addHeader("Content-Type","application/json");
        //3、请求参数 加在请求体里面
        StringEntity stringEntityentity=new StringEntity(params,"UTF-8");
        patch.setEntity(stringEntityentity);
        //4、创建一个客户端  XXXs  XXXUtils 工具类
        HttpClient client = HttpClients.createDefault();
        //5、客户端发送请求,并且返回响应对象（响应头、响应体、响应状态码）
        HttpResponse respose = client.execute(patch);
        //6、获取响应头、响应体、响应状态码
        //6.1、获取响应头
        //printResponse(respose);
        return respose;
    }
    public static String printResponse(HttpResponse respose){
        try {
                Header[] allHeaders = respose.getAllHeaders();
               logger.info(Arrays.toString(allHeaders));
                //6.2、获取响应体
                //entity的工具类EntityUtils
                String body = null;
                body = EntityUtils.toString(respose.getEntity());
                logger.info(body);
                //6.3、响应状态码
                //链式编程 调用方法之后继续调用方法
                int statusCode = respose.getStatusLine().getStatusCode();
                logger.info(statusCode);
                return body;
            } catch (Exception e) {
                e.printStackTrace();
                }
        return "";
    }

    /**
     * 添加请求头
     * @param headers  请求头map
     * @param request   请求对象
     */
    public static void addHeaders(Map<String, String> headers, HttpRequest request) {
        Set<String> keySet = headers.keySet();
        for (String name : keySet) {
            String value = headers.get(name);
            request.addHeader(name,value);
        }
    }
}
