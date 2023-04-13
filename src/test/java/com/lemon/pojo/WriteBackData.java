package com.lemon.pojo;

/**
 * @Project: java19_jmeter2
 * @Site:
 * @Forum:
 * @Copyright:
 * @Author: sam
 * @Create: 2023-03-31 10:48
 * @Desc：
 **/
public class WriteBackData {
    private int sheetIndex;
    private int rownum;
    private int cellnum;
    private String content;

    public WriteBackData(int sheetIndex, int rownum, int cellnum, String content) {
        this.sheetIndex = sheetIndex;
        this.rownum = rownum;
        this.cellnum = cellnum;
        this.content = content;
    }

    public WriteBackData() {
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public int getRownum() {
        return rownum;
    }

    public void setRownum(int rownum) {
        this.rownum = rownum;
    }

    public int getCellnum() {
        return cellnum;
    }

    public void setCellnum(int cellnum) {
        this.cellnum = cellnum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "WriteBackData{" +
                "sheetIndex=" + sheetIndex +
                ", rownum=" + rownum +
                ", cellnum=" + cellnum +
                ", content='" + content + '\'' +
                '}';
    }
}
