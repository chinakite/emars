package com.ideamoment.emars.importor;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class YddExcel {
    String path = "/Users/zhangzhonghua/ydd.xlsx";
    String name = "敏成";
    String fileName = "wuming";

    public void handleExcel() throws Exception{
        File excelFile = new File(path); // 创建文件对象
        FileInputStream in = new FileInputStream(excelFile); // 文件流
        Workbook workbook = new XSSFWorkbook(in);

        Sheet sheet = workbook.getSheetAt(0);

        int c = 0;
        int r = 0;
        int t = 0;

        int a1=0,a2=0,a3=0,a4=0,a5=0,a6=0,a7=0,a8=0,a9=0,a10=0,a11=0,a12 = 0;
        List<String[]> dataList = new ArrayList<String[]>();
        for (Row row : sheet) {
            c++;

            System.out.println("第 "+ c + "行");
            //如果当前行没有数据，跳出循环
            if (row == null || row.getCell(0) == null || row.getCell(0).toString().equals("")) {
                System.out.println("没数据了");
                break;
            }

            Cell cell1 = row.getCell(0);
            String value1 = cell1.getStringCellValue();

            Cell cell2 = row.getCell(1);
            Object obj2 = getValue(cell2);
            String value2 = null;
            if(obj2 instanceof String) {
                value2 = cell2.getStringCellValue();
            }else if(obj2 instanceof Double) {
                value2 = String.valueOf(cell2.getNumericCellValue());
            }

            Cell cell3 = row.getCell(2);
            Object obj3 = getValue(cell3);
            String value3 = null;
            if(obj3 instanceof String) {
                value3 = cell3.getStringCellValue();
            }else if(obj3 instanceof Double) {
                value3 = String.valueOf(cell3.getNumericCellValue());
            }

            Cell cell4 = row.getCell(3);
            Object obj4 = getValue(cell4);
            String value4 = null;
            if(obj4 instanceof String) {
                value4 = cell4.getStringCellValue();
            }else if(obj4 instanceof Double) {
                value4 = String.valueOf(cell4.getNumericCellValue());
            }

            Cell cell5 = row.getCell(4);
            Object obj5 = getValue(cell5);
            String value5 = null;
            if(obj5 instanceof String) {
                value5 = cell5.getStringCellValue();
            }else if(obj5 instanceof Double) {
                value5 = String.valueOf(cell5.getNumericCellValue());
            }

            Cell cell6 = row.getCell(5);
            Object obj6 = getValue(cell6);
            String value6 = null;
            if(obj6 instanceof String) {
                value6 = cell6.getStringCellValue();
            }else if(obj6 instanceof Double) {
                value6 = String.valueOf(cell6.getNumericCellValue());
            }

            Cell cell7 = row.getCell(6);
            Object obj7 = getValue(cell7);
            String value7 = null;
            if(obj7 instanceof String) {
                value7 = cell7.getStringCellValue();
            }else if(obj7 instanceof Double) {
                value7 = String.valueOf(cell6.getNumericCellValue());
            }

//            if(value2.contains(name) || value3.contains(name)) {
//                r++;
//                dataList.add(new String[]{value1, value2, value3, value4, value5, value6});
//            }
//
//            if(value2.contains(name) && value3.contains(name)) {
//                t++;
//            }

            if(value2.contains("忠帅") || value3.contains("忠帅")) {
                a1++;
            }

            if(value2.contains("一辰") || value3.contains("一辰")) {
                a2++;
            }

            if(value2.contains("文雄") || value3.contains("文雄")) {
                a3++;
            }

            if(value2.contains("马俊松") || value3.contains("马俊松")) {
                a4++;
            }

            if(value2.contains("汪琦") || value3.contains("汪琦")) {
                a5++;
            }

            if(value2.contains("小燚") || value3.contains("小燚")) {
                a6++;
            }

            if(value2.contains("春佳") || value3.contains("春佳")) {
                a7++;
            }

            if(value2.contains("恩宇") || value3.contains("恩宇")) {
                a8++;
            }

            if(value2.contains("李响") || value3.contains("李响")) {
                a9++;
            }

            if(value2.contains("朱辉") || value3.contains("朱辉")) {
                a10++;
            }

            if(value2.contains("敏成") || value3.contains("敏成")) {
                a11++;
            }

            if(!value2.contains("忠帅") && !value3.contains("忠帅")
                    && !value2.contains("一辰") && !value3.contains("一辰")
                    && !value2.contains("文雄") && !value3.contains("文雄")
                    && !value2.contains("马俊松") && !value3.contains("马俊松")
                    && !value2.contains("汪琦") && !value3.contains("汪琦")
                    && !value2.contains("小燚") && !value3.contains("小燚")
                    && !value2.contains("春佳") && !value3.contains("春佳")
                    && !value2.contains("恩宇") && !value3.contains("恩宇")
                    && !value2.contains("李响") && !value3.contains("李响")
                    && !value2.contains("朱辉") && !value3.contains("朱辉")
                    && !value2.contains("敏成") && !value3.contains("敏成")
            ){
                r++;
                a12++;
                dataList.add(new String[]{value1, value2, value3, value4, value5, value6, value7});
            }

            System.out.println(value2 + " : " + value3);
        }

        in.close();

//        export(dataList, fileName);


//        System.out.println(name+"共有 " + r + " 条");
        System.out.println(a1 + ":" + a2 + ":" + a3 + ":" + a4 + ":" + a5
                + ":" + a6 + ":" + a7 + ":" + a8 + ":" + a9 + ":" + a10 + ":" + a11 + ":" + a12);
        System.out.println(a1+a2+a3+a4+a5+a6+a7+a8+a9+a10+a11+a12);
        System.out.println("无名共有 " + r + " 条");
        System.out.println("重复 " + t + " 条");

    }

    public void export(List<String[]> dataList, String fileName) throws Exception {
        // 声明一个工作薄
        XSSFWorkbook workBook = null;
        workBook = new XSSFWorkbook();
        // 生成一个表格
        XSSFSheet sheet = workBook.createSheet();
        workBook.setSheetName(0,"Sheet0");
        //插入需导出的数据
        for(int i=0;i<dataList.size();i++){
            XSSFRow row = sheet.createRow(i+1);
            row.createCell(0).setCellValue(dataList.get(i)[0]);
            row.createCell(1).setCellValue(dataList.get(i)[1]);
            row.createCell(2).setCellValue(dataList.get(i)[2]);
            row.createCell(3).setCellValue(dataList.get(i)[3]);
            row.createCell(4).setCellValue(dataList.get(i)[4]);
            row.createCell(5).setCellValue(dataList.get(i)[5]);
            row.createCell(6).setCellValue(dataList.get(i)[6]);
        }

        File  file = new File("/Users/zhangzhonghua/" + fileName + ".xlsx");
        //文件输出流
        FileOutputStream outStream = new FileOutputStream(file);
        workBook.write(outStream);
        outStream.flush();
        outStream.close();
        System.out.println("导出2007文件成功！");
    }

    private static Object getValue(Cell cell) {
        Object obj = null;
        int type = cell.getCellType();
        switch (type) {
            case HSSFCell.CELL_TYPE_BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case HSSFCell.CELL_TYPE_ERROR:
                obj = cell.getErrorCellValue();
                break;
            case HSSFCell.CELL_TYPE_NUMERIC:
                obj = cell.getNumericCellValue();
                break;
            case HSSFCell.CELL_TYPE_STRING:
                obj = cell.getStringCellValue();
                break;
            default:
                break;
        }
        return obj;
    }

    public static void main(String[] args) throws Exception {
        YddExcel YddExcel = new YddExcel();
        YddExcel.handleExcel();
    }
}
