package com.ideamoment.emars.importor;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by Chinakite on 2018/11/25.
 */
public class ExcelImportor {
    public void importExcelData() throws IOException {

        String path = "E:\\yksg_181126.xlsx";
        File excelFile = new File(path); // 创建文件对象
        FileInputStream in = new FileInputStream(excelFile); // 文件流
        Workbook workbook = new XSSFWorkbook(in);

        Sheet sheet = workbook.getSheetAt(0);

        for(Row row : sheet) {
            //如果当前行没有数据，跳出循环
            if (row.getCell(0).toString().equals("")) {
                return;
            }

            //获取总列数(空格的不计算)
            int columnTotalNum = row.getPhysicalNumberOfCells();
            System.out.println("总列数：" + columnTotalNum);
            System.out.println("最大列数：" + row.getLastCellNum());

            int end = row.getLastCellNum();
            for (int i = 0; i < end; i++) {
                Cell cell = row.getCell(i);
                if (cell == null) {
                    System.out.print("null" + "\t");
                    continue;
                }

                Object obj = getValue(cell);
                System.out.print(obj + "\t");
            }
        }
        in.close();
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
        ExcelImportor importor = new ExcelImportor();
        importor.importExcelData();
    }

}
