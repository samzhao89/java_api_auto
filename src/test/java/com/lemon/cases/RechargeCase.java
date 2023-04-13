package com.lemon.cases;

import com.alibaba.fastjson.JSONPath;
import com.lemon.constans.Constants;
import com.lemon.pojo.CaseInfo;
import com.lemon.untils.Authentication;
import com.lemon.untils.Excelutils;
import com.lemon.untils.HttpUtils;
import com.lemon.untils.SQLUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @Project: java19_jmeter2
 * @Site:
 * @Forum:
 * @Copyright:
 * @Author: sam
 * @Create: 2023-03-29 09:48
 * @Desc：充值用力
 **/
public class RechargeCase extends BaseCase {
    private static Logger logger = Logger.getLogger(LoginCase.class);

    //读取testng.xml sheetIndex参数
    public int sheetIndex;
    @BeforeClass
    @Parameters("sheetIndex")
    public void BeforeClass(int sheetIndex){
        this.sheetIndex=sheetIndex;
    }

    @Test(dataProvider = "data")
    public void test(CaseInfo caseInfo) throws Exception {

        //1、参数化替换
        paramsReplace(caseInfo);
        //2、数据库前置查询结果（数据断言必须在接口执行前后查询）
        Object  beforeSqlResult = SQLUtils.getSingleResult(caseInfo.getSql());
        //  2.1、获取带token的请求头
        Map<String, String> headers = Authentication.getTokenHeader();
        HttpResponse response = HttpUtils.call(caseInfo.getMethod(),caseInfo.getContentType(),
                caseInfo.getUrl(),caseInfo.getParams(),headers);
        //打印响应
        String body = HttpUtils.printResponse(response);
        //4、断言响应结果
        //String expectResult = caseInfo.getExpectResult();
        boolean assertResponseFlag = assertResponse(body,caseInfo.getExpectResult());
        //5、添加接口响应回写内容
        //响应回写
        addWriteBackData(caseInfo.getId(), Constants.RESPONSE_WRITE_BACK_CELL,body);
        //6、数据库后置查询结果
        Object  afterSqlResult = SQLUtils.getSingleResult(caseInfo.getSql());
        //7、数据库断言
        boolean assertSqlFlag = sqlAssert(caseInfo, beforeSqlResult, afterSqlResult);
        //8、添加断言回写内容
        String assertResult=assertResponseFlag && assertSqlFlag ? "passed" : "failed";
        addWriteBackData(caseInfo.getId(), Constants.ASSERT_WRITE_BACK_CELL,assertResult);
        //9、添加日志
        //10、报表断言
        Assert.assertEquals(assertResult,"passed");
    }



    /**
     *
     * @param caseInfo              caseInfo对象
     * @param beforeSqlResult       sql前置查询结果
     * @param afterSqlResult        sql后置查询结果
     * @return
     */
    public boolean sqlAssert(CaseInfo caseInfo, Object beforeSqlResult, Object afterSqlResult) {
        boolean flag= false;
        if (StringUtils.isNotBlank(caseInfo.getSql())) {
            if (beforeSqlResult == null || afterSqlResult == null) {
                System.out.println("数据库断言失败");
            } else {
                BigDecimal b1 = (BigDecimal) beforeSqlResult;
                BigDecimal b2 = (BigDecimal) afterSqlResult;
                //充值后-充值前得到的结果
                BigDecimal result1 = b1.subtract(b2);
                //jsonpath获取参数
                Object obj = JSONPath.read(caseInfo.getParams(), "$.amount");
                if (obj==null){
                    System.out.println("断言失败");
                }else {
                    //参数amount
                    BigDecimal result2=new BigDecimal(obj.toString());
                    //结果==参数amount
                    if (result1.compareTo(result2) == 0){
                        System.out.println("数据库断言成功");
                    }else {
                        System.out.println("数据库断言失败");
                    }
                }
            }
            }else {
                    System.out.println("sql为空，不需要数据库断言");
        }
        return flag;
    }

    @DataProvider
    public Object[] data()  {
        Object[] datas = Excelutils.getdatas(sheetIndex, 1, CaseInfo.class);
        return datas;
    }
}
