package com.ideamoment.emars.importor;

import com.ideamoment.emars.model.Announcer;
import com.ideamoment.emars.model.Author;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Chinakite on 2018/11/25.
 */
public class ExcelImportor {
    public void importExcelData() throws IOException, ParseException {

//        String path = "E:\\yksg_181126.xlsx";
        String path = "/Users/zhangzhonghua/yksg_20181126.xlsx";
        File excelFile = new File(path); // 创建文件对象
        FileInputStream in = new FileInputStream(excelFile); // 文件流
        Workbook workbook = new XSSFWorkbook(in);

        Sheet sheet = workbook.getSheetAt(0);

        int c = 0;
        for(Row row : sheet) {
            c++;
            System.out.println("第 " + c + "行");

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
                if(i == 0) {
                    continue;
                }

                Cell cell = row.getCell(i);
                if (cell == null) {
                    System.out.print("null" + "\t");
                    continue;
                }
                Object obj = getValue(cell);
//                System.out.print(obj + "\t");

                if(i == 1) {
                    String productNo = (String)obj;
                    String[] chunks = productNo.split("-");
                    String copyrightContractCode = null;
                    String copyrightProductSort = "1";
                    if(chunks.length > 3) {
                        copyrightContractCode = productNo.substring(0, productNo.lastIndexOf("-"));
                        copyrightProductSort = productNo.substring(productNo.lastIndexOf("-") + 1);
                    }else{
                        copyrightContractCode = productNo;
                    }
                    System.out.println("CopyrightContractCode = " + copyrightContractCode);
                    System.out.println("ProductCode = " + copyrightContractCode + "-" + copyrightProductSort);
                }

                if(i == 3) {
                    String signDate = (String)obj;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                    Date date = sdf.parse(signDate);
                    System.out.println("SignDate = " + signDate + ", Date = " + date);
                }

                if(i == 4) {
                    String productName = (String)obj;
                    System.out.println("ProductName = " + productName);
                }

                if(i == 5) {
                    String wordCount = null;
                    if(obj != null) {
                        wordCount = (String)obj;
                        if(wordCount.length() > 0) {
                            if(wordCount.indexOf("万") > 0) {
                                wordCount = wordCount.substring(0, wordCount.indexOf("万"));
                            }else if(wordCount.indexOf("字") > 0) {
                                wordCount = wordCount.substring(0, wordCount.indexOf("字"));
                                BigDecimal wordCountDecimal = new BigDecimal(wordCount);
                                wordCountDecimal.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP);
                            }
                        }
                    }
                    System.out.println("WordCount = " + wordCount + "万字");
                }

                if(i == 6) {
                    String granterName = (String)obj;
                    if(granterName == null) {
                        granterName = "未知";
                    }
                    System.out.println("granterName = " + granterName);
                }

                if(i == 7) {
                    String authorStr = (String)obj;
                    List<Author> authors = new ArrayList<Author>();
                    if(authorStr != null) {
                        if(authorStr.indexOf("、") > 0) {
                            String[] chunks = authorStr.split("、");
                            for(String chunk : chunks) {
                                Author author = new Author();
                                if(chunk.indexOf("(") > 0) {
                                    String authorName = chunk.substring(0, chunk.indexOf("("));
                                    String authorPseudonym = chunk.substring(chunk.indexOf("(")+1, chunk.length()-1);
                                    author.setName(authorName);
                                    author.setPseudonym(authorPseudonym);
                                }else if(chunk.indexOf("（") > 0) {
                                    String authorName = chunk.substring(0, chunk.indexOf("（"));
                                    String authorPseudonym = chunk.substring(chunk.indexOf("（")+1, chunk.length()-1);
                                    author.setName(authorName);
                                    author.setPseudonym(authorPseudonym);
                                }else{
                                    author.setName(chunk);
                                }
                                authors.add(author);
                            }
                        }else{
                            Author author = new Author();
                            if(authorStr.indexOf("(") > 0) {
                                String authorName = authorStr.substring(0, authorStr.indexOf("("));
                                String authorPseudonym = authorStr.substring(authorStr.indexOf("(")+1, authorStr.length()-1);
                                author.setName(authorName);
                                author.setPseudonym(authorPseudonym);
                            }else if(authorStr.indexOf("（") > 0) {
                                String authorName = authorStr.substring(0, authorStr.indexOf("（"));
                                String authorPseudonym = authorStr.substring(authorStr.indexOf("（")+1, authorStr.length()-1);
                                author.setName(authorName);
                                author.setPseudonym(authorPseudonym);
                            }else{
                                author.setName(authorStr);
                            }
                            authors.add(author);
                        }
                    }
                    System.out.println("Authors size = " + authors.size());
                }

                if(i == 8) {
                    String isbn = (String)obj;
                    System.out.println("ISBN = " + isbn);
                }

                if(i == 9) {
                    String rights = (String)obj;
                }

                if(i == 10) {
                    String grant = (String)obj;
                    if(grant != null) {
                        if(grant.trim().equals("有")) {
                            grant = "1";
                        }else{
                            grant = "0";
                        }
                    }
                    System.out.println("Grant = " + grant);
                }

                if(i == 11) {
                    String copyrightType = (String)obj;
                    if(copyrightType != null) {
                        if(copyrightType.trim().equals("专有许可使用")) {
                            copyrightType = "1";
                        }else{
                            copyrightType = "0";
                        }
                    }
                    System.out.println("CopyrightType = " + copyrightType);
                }

                if(i == 12) {
                    String copyrightEnd = (String)obj;
                    Date copyrightEndDate = null;
                    if(copyrightEnd != null) {
                        if(copyrightEnd.indexOf("出版") > -1
                                || copyrightEnd.indexOf("完结") > -1) {

                        }else{
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                            copyrightEndDate = sdf.parse(copyrightEnd);
                        }
                    }
                    System.out.println("copyrightEndDate = " + copyrightEnd + ", Date = " + copyrightEndDate);
                }

                if(i == 13) {
                    Integer section = null;
                    if(obj != null) {
                        if(obj instanceof Double) {
                            section = ((Double)obj).intValue();
                        }else if(obj instanceof String) {
                            String sectionStr = (String)obj;
                            if(sectionStr.indexOf("/") > -1) {
                                section = null;
                            }else{
                                section = Integer.parseInt((String)obj);
                            }
                        }
                    }
                    System.out.println("section = " + section);
                }

                if(i == 14) {
                    String stateStr = (String)obj;
                    String state = null;
                    String makerName = null;
                    if(stateStr != null) {
                        if(stateStr.indexOf("/") > 0) {
                            String[] chunks = stateStr.split("/");
                            String chunk = chunks[0];
                            //TODO 这里要记个日志
                            if(chunk.indexOf("(") > 0) {
                                state = chunk.substring(0, chunk.indexOf("("));
                                makerName = chunk.substring(chunk.indexOf("(")+1, chunk.length()-1);
                            }else if(chunk.indexOf("（") > 0) {
                                state = chunk.substring(0, chunk.indexOf("（"));
                                makerName = chunk.substring(chunk.indexOf("（")+1, chunk.length()-1);
                            }else{
                                state = chunk;
                            }
                        }else{
                            if(stateStr.indexOf("(") > 0) {
                                state = stateStr.substring(0, stateStr.indexOf("("));
                                makerName = stateStr.substring(stateStr.indexOf("(")+1, stateStr.length()-1);
                            }else if(stateStr.indexOf("（") > 0) {
                                state = stateStr.substring(0, stateStr.indexOf("（"));
                                makerName = stateStr.substring(stateStr.indexOf("（")+1, stateStr.length()-1);
                            }else{
                                state = stateStr;
                            }
                        }
                    }
                    System.out.println("State = " + state + ", MakerName = " + makerName);
                }

                if(i == 15) {
                    String announcerStr = (String)obj;
                    List<Announcer> announcers = new ArrayList<Announcer>();
                    if(announcerStr != null) {
                        if(announcerStr.indexOf("、") > 0) {
                            String[] chunks = announcerStr.split("、");
                            for(String chunk : chunks) {
                                Announcer announcer = new Announcer();
                                if(chunk.indexOf("(") > 0) {
                                    String announcerName = chunk.substring(0, chunk.indexOf("("));
                                    String announcerPseudonym = chunk.substring(chunk.indexOf("(")+1, chunk.length()-1);
                                    announcer.setName(announcerName);
                                    announcer.setPseudonym(announcerPseudonym);
                                }else if(chunk.indexOf("（") > 0) {
                                    String announcerName = chunk.substring(0, chunk.indexOf("（"));
                                    String announcerPseudonym = chunk.substring(chunk.indexOf("（")+1, chunk.length()-1);
                                    announcer.setName(announcerName);
                                    announcer.setPseudonym(announcerPseudonym);
                                }else{
                                    announcer.setName(chunk);
                                }
                                announcers.add(announcer);
                            }
                        }else{
                            Announcer announcer = new Announcer();
                            if(announcerStr.indexOf("(") > 0) {
                                String announcerName = announcerStr.substring(0, announcerStr.indexOf("("));
                                String announcerPseudonym = announcerStr.substring(announcerStr.indexOf("(")+1, announcerStr.length()-1);
                                announcer.setName(announcerName);
                                announcer.setPseudonym(announcerPseudonym);
                            }else if(announcerStr.indexOf("（") > 0) {
                                String announcerName = announcerStr.substring(0, announcerStr.indexOf("（"));
                                String announcerPseudonym = announcerStr.substring(announcerStr.indexOf("（")+1, announcerStr.length()-1);
                                announcer.setName(announcerName);
                                announcer.setPseudonym(announcerPseudonym);
                            }else{
                                announcer.setName(announcerStr);
                            }
                            announcers.add(announcer);
                        }
                    }
                    System.out.println("Announcers size = " + announcers.size());
                }

                if(i == 16) {
                    String platform = (String)obj;
                    System.out.println("Platform = " + platform);
                }


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
