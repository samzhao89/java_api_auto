package com.lemon.cases;

import com.lemon.constans.Constants;
import com.lemon.pojo.CaseInfo;
import com.lemon.untils.Authentication;
import com.lemon.untils.Excelutils;
import com.lemon.untils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;


/**
 * @Project: java19_jmeter2
 * @Site:
 * @Forum:
 * @Copyright:
 * @Author: sam
 * @Create: 2023-03-29 09:28
 * @Desc：
 **/
public class LoginCase  extends BaseCase {
    private static Logger logger = Logger.getLogger(LoginCase.class);

    @Test(dataProvider = "data")
    public void test(CaseInfo caseInfo){
        //1、参数化替换
        paramsReplace(caseInfo);
        //2、数据库前置查询结果（数据断言必须在接口执行前后查询
        //3、调用接口
        HttpResponse response = HttpUtils.call(caseInfo.getMethod(),caseInfo.getContentType(),
                caseInfo.getUrl(),caseInfo.getParams(), Constants.HEADERS);
        String body = HttpUtils.printResponse(response);
        //  3.1、从响应体里面获取token
        //2、使用jsonpath获取$.data.token_info.token
        //fastjson
        Authentication.json2Vars(body,"$.data.token_info.token","${token}");
        //  3.2、从响应体里面获取member_id
        Authentication.json2Vars(body,"$.data.id","${member_id}");
        //4、断言响应结果
        //String expectResult = caseInfo.getExpectResult();
        boolean assertResponseFlag = assertResponse(body, caseInfo.getExpectResult());
        //5、添加接口响应回写内容
        //响应回写
        addWriteBackData(caseInfo.getId(), Constants.RESPONSE_WRITE_BACK_CELL,body);
        //6、数据库后置查询结果
        //7、数据库断言
        //8、添加断言回写内容
        String assertResult=assertResponseFlag  ? "passed" : "failed";
        addWriteBackData(caseInfo.getId(), Constants.ASSERT_WRITE_BACK_CELL,assertResult);
        //9、添加日志
        //10、报表断言
        Assert.assertEquals(assertResult,"passed");
    }



    @DataProvider
    public Object[] data()  {
        Object[] datas = Excelutils.getdatas(sheetIndex, 1, CaseInfo.class);
        return datas;
    }
}
