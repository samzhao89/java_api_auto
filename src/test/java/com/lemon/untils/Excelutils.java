package com.lemon.untils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.lemon.constans.Constants;
import com.lemon.pojo.CaseInfo;
import com.lemon.pojo.WriteBackData;
import org.apache.poi.ss.usermodel.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @Project: java19_jmeter2
 * @Site:
 * @Forum:
 * @Copyright:
 * @Author: sam
 * @Create: 2023-03-23 15:49
 * @Desc：
 **/
//忽略 unchecked 警告信息
@SuppressWarnings("unchecked")
public class Excelutils {

    //批量回写集合
    public  static  List<WriteBackData> wbdList=new ArrayList<>();


    public static void main(String[] args) throws Exception{
         read(0, 1, CaseInfo.class);
    }

    /**
     * 获取testng测试方法 数据驱动
     * @param sheetIndex    sheet开始位置
     * @param sheetNum      sheet个数
     * @param clazz         映射关系字节码
     * @return
     */
    public static Object[] getdatas(int sheetIndex, int sheetNum,Class clazz){
        try {
            List<CaseInfo> list = Excelutils.read(sheetIndex, sheetNum, clazz);
            Object[] datas = list.toArray();
            return  datas;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 读取excel并返回映射关系集合
     * @param sheetIndex        sheet开始位置
     * @param sheetNum          sheet个数
     * @param clazz             映射关系字节码
     * @return
     * @throws Exception
     */
    public static  List read(int sheetIndex, int sheetNum,Class clazz ) throws Exception {
        //String path=Excelutils.class.getClassLoader().getResource("./cases_v1.xls").getPath();
        FileInputStream fis = new FileInputStream(Constants.ExcelPath);
        //easypoi
        ImportParams ipms = new ImportParams();
        //从第一个sheet读取
        ipms.setStartSheetIndex(sheetIndex);
        //每次读取一个sheet
        ipms.setSheetNum(sheetNum);
        //importExcel(Excel文件流，映射关系字节码对象，导入参数)
        List caseinfolist = ExcelImportUtil.importExcel(fis, clazz, ipms);
        return caseinfolist;
    }

    /**
     * 批量回写
     */
    public  static  void  batchWrite(){
        FileInputStream fis = null;
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(Constants.ExcelPath);
            //1.2、解析数据必须用poi提供对象
            Workbook excel = WorkbookFactory.create(fis);
            //循环 批量回写集合 wbdList
            for (WriteBackData writeBackData : wbdList) {
                //取出sheetIndex
                int sheetIndex = writeBackData.getSheetIndex();
                //取出行号
                int rownum = writeBackData.getRownum();
                //取出列号
                int cellnum = writeBackData.getCellnum();
                //取出回写内容
                String content = writeBackData.getContent();
                //2、选择sheet
                Sheet sheet = excel.getSheetAt(sheetIndex);
                //3、读取每一行
                Row row = sheet.getRow(rownum);
                //4、读取每一个单元格 MissingCellPolicy 当cell为null时，返回一个空的cell对象
                Cell cell = row.getCell(cellnum, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                //5、修改
                cell.setCellValue(content);
            }
            fos = new FileOutputStream(Constants.ExcelPath);
            //6、写回去
            excel.write(fos);
        }catch (Exception e ){
            e.printStackTrace();
        }finally {
            //一定会执行（无论是否发生异常都会执行）
            //释放资源
            //6、关流
            try {
                if(fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
