package com.ideamoment.emars.importor;

import com.ideamoment.emars.model.Announcer;
import com.ideamoment.emars.model.Author;
import com.ideamoment.emars.model.enumeration.ProductionState;
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
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by Chinakite on 2018/11/25.
 */
public class ExcelImportor {

//    String path = "E:\\yksg_181126.xlsx";

    String path = "/Users/zhangzhonghua/yksg_20181126.xlsx";

    public void refreshMakeTotal() throws IOException, SQLException, ClassNotFoundException {
        Connection conn = getConnection();
        String sql = "select c_mc_id, sum(c_price) as totalPrice, sum(c_section) as totalSection from t_make_constract_product group by c_mc_id";
        PreparedStatement pstmt = conn.prepareStatement("update t_make_contract set c_total_price = ?, c_total_section = ? where c_id = ?");

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                Long mcId = rs.getLong("c_mc_id");
                Double totalPrice = rs.getDouble("totalPrice");
                Integer totalSection = rs.getInt("totalSection");

                pstmt.setDouble(1, totalPrice);
                pstmt.setInt(2, totalSection);
                pstmt.setLong(3, mcId);

                pstmt.executeUpdate();
            }
            commitConnection(conn);
            pstmt.close();
        }catch (Exception e) {
            e.printStackTrace();;
            rollbackConnection(conn);
            pstmt.close();
        }finally {
            closeConnection(conn);
        }

    }

    public void importMakeProducts() throws IOException, SQLException, ClassNotFoundException {
        File excelFile = new File(path); // 创建文件对象
        FileInputStream in = new FileInputStream(excelFile); // 文件流
        Workbook workbook = new XSSFWorkbook(in);

        Sheet sheet = workbook.getSheetAt(0);

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement("insert into t_make_contract_product (`c_make_contract_id`, `c_product_id`, `c_price`, `c_section`, `c_creator`, `c_createtime`)values(?,?,?,?,1,now())");

        HashMap<String, Long> makeContractsMap = mapMakeContracts(conn);
        HashMap<String, Long> productsMap = mapProducts(conn);

        try {
            int c = 0;
            for (Row row : sheet) {
                c++;
                System.out.println("========== 第 " + c + " 行 ==========");

                //如果当前行没有数据，跳出循环
                if (row.getCell(0).toString().equals("")) {
                    System.out.println("没数据了");
                    break;
                }

                Cell codeCell = row.getCell(42);
                if (codeCell == null) {
                    System.out.println("null");
                    continue;
                }
                Object codeObj = getValue(codeCell);
                if(codeObj == null) {
                    System.out.println("无制作合同");
                    continue;
                }

                String makeContractCode = (String) codeObj;
                makeContractCode = makeContractCode.trim();
                if(makeContractCode.indexOf("/") > 0) {
                    makeContractCode = makeContractCode.substring(0, makeContractCode.indexOf("/"));
                }
                String[] chunks = makeContractCode.split("-");
                if (chunks.length > 3) {
                    if(makeContractCode.contains("zx-z")) {
                        if(chunks.length > 4) {
                            makeContractCode = makeContractCode.substring(0, makeContractCode.lastIndexOf("-"));
                        }
                    }else{
                        makeContractCode = makeContractCode.substring(0, makeContractCode.lastIndexOf("-"));
                    }
                }
                makeContractCode = makeContractCode.trim();
                Long makeContractId = makeContractsMap.get(makeContractCode);
                if(makeContractId == null) {
                    continue;
                }
                pstmt.setLong(1, makeContractId);

                int end = row.getLastCellNum();
                for (int i = 0; i < end; i++) {
                    if (i == 0) {
                        continue;
                    }

                    Cell cell = row.getCell(i);
                    if (cell == null) {
                        System.out.print("null" + "\t");
                        continue;
                    }
                    Object obj = getValue(cell);

                    if (i == 1) {
                        String productNo = (String) obj;
                        Long productId = productsMap.get(productNo);

                        pstmt.setLong(2, productId);
                    }

                    if (i == 40) {
                        Double price = (Double) obj;
                        if (price == null) {
                            price = 0.0;
                        }
                        pstmt.setDouble(3, price);
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
                        if(section != null){
                            pstmt.setString(4, section.toString());
                        }else{
                            pstmt.setString(3, null);
                        }
                    }

                }
                pstmt.executeUpdate();
            }
            commitConnection(conn);
            pstmt.close();
        }catch (Exception e) {
            e.printStackTrace();;
            rollbackConnection(conn);
            pstmt.close();
        }finally {
            closeConnection(conn);
        }
    }

    public void importMakeContracts() throws IOException, SQLException, ClassNotFoundException {
        File excelFile = new File(path); // 创建文件对象
        FileInputStream in = new FileInputStream(excelFile); // 文件流
        Workbook workbook = new XSSFWorkbook(in);

        Sheet sheet = workbook.getSheetAt(0);

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement("insert into t_make_contract (`c_code`, `c_target_type`, `c_owner`, `c_maker_id`, `c_creator`, `c_createtime`)values(?,?,'北京悦库时光文化传媒有限公司',?,1,now())");

        HashMap<String, Long> makersMap = mapMakers(conn);

        HashSet<String> makeContractsSet = new HashSet<String>();

        try {
            int c = 0;
            for (Row row : sheet) {
                c++;
                System.out.println("========== 第 " + c + " 行 ==========");

                //如果当前行没有数据，跳出循环
                if (row.getCell(0).toString().equals("")) {
                    System.out.println("没数据了");
                    break;
                }

                Cell codeCell = row.getCell(42);
                if (codeCell == null) {
                    System.out.println("null");
                    continue;
                }
                Object codeObj = getValue(codeCell);
                if(codeObj == null) {
                    System.out.println("无制作合同");
                    continue;
                }

                String makeContractCode = (String) codeObj;
                makeContractCode = makeContractCode.trim();
                String targetType = null;
                String typeCode = null;
                if(makeContractCode.indexOf("/") > 0) {
                    makeContractCode = makeContractCode.substring(0, makeContractCode.indexOf("/"));
                }
                String[] chunks = makeContractCode.split("-");
                if (chunks.length > 3) {
                    if(makeContractCode.contains("zx-z")) {
                        typeCode = "zx-z";
                        if(chunks.length > 4) {
                            makeContractCode = makeContractCode.substring(0, makeContractCode.lastIndexOf("-"));
                        }
                    }else{
                        makeContractCode = makeContractCode.substring(0, makeContractCode.lastIndexOf("-"));
                        typeCode = chunks[1];
                    }
                }else{
                    typeCode = chunks[1];
                }

                if("zz".equals(typeCode)){
                    targetType = "1";
                }else if("zzlw".equals(typeCode)) {
                    targetType = "0";
                }else if("zx-z".equals(typeCode)) {
                    targetType = "2";
                }else{
                    System.out.println("无法识别合同类型！！");
                    continue;
                }

                if (makeContractsSet.contains(makeContractCode)) {
                    System.out.println("已处理过");
                    continue;
                }

                makeContractsSet.add(makeContractCode);

                pstmt.setString(1, makeContractCode);
                pstmt.setString(2, targetType);

                int end = row.getLastCellNum();
                for (int i = 0; i < end; i++) {
                    if (i == 0) {
                        continue;
                    }

                    Cell cell = row.getCell(i);
                    if (cell == null) {
                        System.out.print("null" + "\t");
                        continue;
                    }
                    Object obj = getValue(cell);

                    if(i == 41) {
                        String makerStr = (String)obj;
                        Long makerId = 0L;
                        if(makerStr != null && makerStr.trim().length() > 0) {
                            makerStr = makerStr.trim();
                            if(makerStr.indexOf("/") > 0) {
                                String[] makerChunks = makerStr.split("/");
                                for(String makerChunk : makerChunks) {
                                    makerChunk = makerChunk.trim();
                                    makerId = makersMap.get(makerChunk);
                                }
                            }else{
                                makerId = makersMap.get(makerStr);
                            }
                        }
                        pstmt.setLong(3, makerId);
                    }
                }
                pstmt.executeUpdate();
            }
            commitConnection(conn);
            pstmt.close();
        }catch (Exception e) {
            e.printStackTrace();;
            rollbackConnection(conn);
            pstmt.close();
        }finally {
            closeConnection(conn);
        }
    }

    public void importProductAnnouncers() throws IOException, SQLException, ClassNotFoundException {
        File excelFile = new File(path); // 创建文件对象
        FileInputStream in = new FileInputStream(excelFile); // 文件流
        Workbook workbook = new XSSFWorkbook(in);

        Sheet sheet = workbook.getSheetAt(0);

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement("insert into t_product_announcer (`c_product_id`, `c_announcer_id`, `c_mc_id`, `c_creator`, `c_createtime`)values(?,?,?,'1',now())");

        HashMap<String, Long> productsMap = mapProducts(conn);
        HashMap<String, Long> announcersMap = mapAnnouncers(conn);
        HashMap<String, Long> makeContractsMap = mapMakeContracts(conn);

        try {
            int c = 0;
            for (Row row : sheet) {
                c++;
                System.out.println("========== 第 " + c + " 行 ==========");

                //如果当前行没有数据，跳出循环
                if (row.getCell(0).toString().equals("")) {
                    System.out.println("没数据了");
                    break;
                }

                Cell codeCell = row.getCell(42);
                if (codeCell == null) {
                    System.out.println("null");
                    continue;
                }
                Object codeObj = getValue(codeCell);
                if(codeObj == null) {
                    System.out.println("无制作合同");
                    continue;
                }

                String makeContractCode = (String) codeObj;
                makeContractCode = makeContractCode.trim();
                if(makeContractCode.indexOf("/") > 0) {
                    makeContractCode = makeContractCode.substring(0, makeContractCode.indexOf("/"));
                }
                String[] chunks = makeContractCode.split("-");
                if (chunks.length > 3) {
                    if(makeContractCode.contains("zx-z")) {
                        if(chunks.length > 4) {
                            makeContractCode = makeContractCode.substring(0, makeContractCode.lastIndexOf("-"));
                        }
                    }else{
                        makeContractCode = makeContractCode.substring(0, makeContractCode.lastIndexOf("-"));
                    }
                }
                makeContractCode = makeContractCode.trim();
                Long makeContractId = makeContractsMap.get(makeContractCode);
                if(makeContractId == null) {
                    continue;
                }

                Cell productNoCell = row.getCell(1);
                if (productNoCell == null) {
                    System.out.print("productNo cell is null" + "\t");
                    continue;
                }

                Object productNoObj = getValue(productNoCell);
                if(productNoObj == null) {
                    continue;
                }

                String productNo = (String)productNoObj;
                Long productId = productsMap.get(productNo);

                Cell announcerCell = row.getCell(15);
                if (announcerCell == null) {
                    System.out.print("announcerCell cell is null" + "\t");
                    continue;
                }

                Object announcerObj = getValue(announcerCell);
                if(announcerObj == null) {
                    continue;
                }

                String announcerStr = (String)announcerObj;
                if(announcerStr.trim().length() > 0) {
                    announcerStr = announcerStr.trim();
                    if(announcerStr.indexOf("、") > 0) {
                        String[] annChunks = announcerStr.split("、");
                        for(String annChunk : annChunks) {
                            if(annChunk.indexOf("(") > 0) {
                                String announcerName = annChunk.substring(0, annChunk.indexOf("(")).trim();
                                String announcerPseudonym = annChunk.substring(annChunk.indexOf("(")+1, annChunk.length()-1).trim();
                                String announcerKey = announcerName+"__"+announcerPseudonym;
                                Long announcerId = announcersMap.get(announcerKey);
                                pstmt.setLong(1, productId);
                                pstmt.setLong(2, announcerId);
                                pstmt.setLong(3, makeContractId);
                                pstmt.executeUpdate();
                            }else if(annChunk.indexOf("（") > 0) {
                                String announcerName = annChunk.substring(0, annChunk.indexOf("（")).trim();
                                String announcerPseudonym = annChunk.substring(annChunk.indexOf("（")+1, annChunk.length()-1).trim();
                                String announcerKey = announcerName+"__"+announcerPseudonym;
                                Long authorId = announcersMap.get(announcerKey);
                                pstmt.setLong(1, productId);
                                pstmt.setLong(2, authorId);
                                pstmt.setLong(3,makeContractId);
                                pstmt.executeUpdate();
                            }else{
                                annChunk = annChunk.trim();
                                Long announcerId = announcersMap.get(annChunk);
                                pstmt.setLong(1, productId);
                                pstmt.setLong(2, announcerId);
                                pstmt.setLong(3,makeContractId);
                                pstmt.executeUpdate();
                            }
                        }
                    }else{
                        if(announcerStr.indexOf("(") > 0) {
                            String announcerName = announcerStr.substring(0, announcerStr.indexOf("(")).trim();
                            String announcerPseudonym = announcerStr.substring(announcerStr.indexOf("(")+1, announcerStr.length()-1).trim();
                            String announcerKey = announcerName+"__"+announcerPseudonym;
                            Long announcerId = announcersMap.get(announcerKey);
                            pstmt.setLong(1, productId);
                            pstmt.setLong(2, announcerId);
                            pstmt.setLong(3, makeContractId);
                            pstmt.executeUpdate();
                        }else if(announcerStr.indexOf("（") > 0) {
                            String announcerName = announcerStr.substring(0, announcerStr.indexOf("（")).trim();
                            String announcerPseudonym = announcerStr.substring(announcerStr.indexOf("（")+1, announcerStr.length()-1).trim();
                            String announcerKey = announcerName+"__"+announcerPseudonym;
                            Long announcerId = announcersMap.get(announcerKey);
                            pstmt.setLong(1, productId);
                            pstmt.setLong(2, announcerId);
                            pstmt.setLong(3,makeContractId);
                            pstmt.executeUpdate();
                        }else{
                            Long announcerId = announcersMap.get(announcerStr);
                            pstmt.setLong(1, productId);
                            pstmt.setLong(2, announcerId);
                            pstmt.setLong(3,makeContractId);
                            pstmt.executeUpdate();
                        }
                    }
                }else{
                    continue;
                }

            }
            commitConnection(conn);
            pstmt.close();
        }catch (Exception e) {
            e.printStackTrace();;
            rollbackConnection(conn);
            pstmt.close();
        }finally {
            closeConnection(conn);
        }
    }

    public void importCopyrightProducts() throws IOException, SQLException, ClassNotFoundException {
        File excelFile = new File(path); // 创建文件对象
        FileInputStream in = new FileInputStream(excelFile); // 文件流
        Workbook workbook = new XSSFWorkbook(in);

        Sheet sheet = workbook.getSheetAt(0);

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement("insert into t_copyright_product (`c_copyright_id`, `c_product_id`, `c_price`, `c_privileges`, `c_grant`, `c_copyright_type`, `c_settlement_type`, `c_begin`, `c_end`, `c_desc`, `c_creator`, `c_createtime`)values(?,?,?,?,?,?,'0',?,?,?,'1',now())");

        HashMap<String, Long> productsMap = mapProducts(conn);
        HashMap<String, Long> copyrightsMap = mapCopyrights(conn);

        try {
            int c = 0;
            for (Row row : sheet) {
                c++;
                System.out.println("========== 第 " + c + " 行 ==========");

                //如果当前行没有数据，跳出循环
                if (row.getCell(0).toString().equals("")) {
                    System.out.println("没数据了");
                    break;
                }

                int end = row.getLastCellNum();
                for (int i = 0; i < end; i++) {
                    if (i == 0) {
                        continue;
                    }

                    Cell cell = row.getCell(i);
                    if (cell == null) {
                        System.out.print("null" + "\t");
                        continue;
                    }
                    Object obj = getValue(cell);

                    if(i == 1) {
                        String productNo = (String)obj;
                        String[] chunks = productNo.split("-");
                        String copyrightCode = productNo;
                        if(chunks.length > 3) {
                            copyrightCode = productNo.substring(0, productNo.lastIndexOf("-"));
                        }

                        Long copyrightId = copyrightsMap.get(copyrightCode);
                        Long productId = productsMap.get(productNo);

                        pstmt.setLong(1, copyrightId);
                        pstmt.setLong(2, productId);
                    }

                    if(i == 40) {
                        Double price = (Double)obj;
                        if(price == null){
                            price = 0.0;
                        }
                        pstmt.setDouble(3, price);
                    }

                    if(i == 9) {
                        String privilegesStr = (String)obj;
                        StringBuffer privilegesBuf = new StringBuffer(7);
                        if(privilegesStr != null && privilegesStr.trim().length() > 0) {
                            privilegesStr = privilegesStr.trim();
                            if(privilegesStr.contains("音频改编权")) {
                                privilegesBuf.append("1");
                            }else{
                                privilegesBuf.append("0");
                            }

                            if(privilegesStr.contains("广播权")) {
                                privilegesBuf.append("1");
                            }else{
                                privilegesBuf.append("0");
                            }

                            if(privilegesStr.contains("信息网络传播权")) {
                                privilegesBuf.append("1");
                            }else{
                                privilegesBuf.append("0");
                            }

                            if(privilegesStr.contains("广播剧改编权")) {
                                privilegesBuf.append("1");
                            }else{
                                privilegesBuf.append("0");
                            }

                            if(privilegesStr.contains("著作权")) {
                                privilegesBuf.append("1");
                            }else{
                                privilegesBuf.append("0");
                            }

                            if(privilegesStr.contains("邻接权")) {
                                privilegesBuf.append("1");
                            }else{
                                privilegesBuf.append("0");
                            }

                            if(privilegesStr.contains("非广播剧音频改编权")) {
                                privilegesBuf.append("1");
                            }else{
                                privilegesBuf.append("0");
                            }
                            pstmt.setString(4, privilegesBuf.toString());
                        }else{
                            pstmt.setString(4, "0000000");
                        }
                    }

                    if(i == 10) {
                        String grantStr = (String)obj;
                        String grant = "0";
                        if(grantStr != null && grantStr.trim().length() > 0) {
                            grantStr = grantStr.trim();
                            if("有".equals(grantStr)) {
                                grant = "1";
                            }
                        }
                        pstmt.setString(5, grant);
                    }

                    if(i == 11) {
                        String copyrightTypeStr = (String)obj;
                        String copyrightType = "0";
                        if(copyrightTypeStr != null && copyrightTypeStr.trim().length() > 0) {
                            copyrightTypeStr = copyrightTypeStr.trim();
                            if("专有许可使用".equals(copyrightTypeStr)) {
                                copyrightType = "1";
                            }
                        }
                        pstmt.setString(6, copyrightType);
                    }

                    if(i == 3) {
                        String signDate = (String)obj;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                        Date date = sdf.parse(signDate);
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        pstmt.setDate(7, sqlDate);
                    }

                    String desc = null;
                    if(i == 12) {
                        String copyrightEnd = (String)obj;
                        Date copyrightEndDate = null;
                        java.sql.Date sqlDate = null;
                        if(copyrightEnd != null) {
                            if(copyrightEnd.indexOf("出版") > -1
                                    || copyrightEnd.indexOf("完结") > -1) {
                                desc = copyrightEnd;
                            }else{
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                                copyrightEndDate = sdf.parse(copyrightEnd);
                                sqlDate = new java.sql.Date(copyrightEndDate.getTime());
                            }
                        }
                        pstmt.setDate(8, sqlDate);
                    }

                    if(i == 46) {
                        String descStr = (String)obj;
                        if(descStr != null && descStr.trim().length() > 0) {
                            if(desc == null) {
                                desc = descStr.trim();
                            }else{
                                desc = desc + "/" + descStr.trim();
                            }
                        }
                        pstmt.setString(9, desc);
                    }

                }
                int r = pstmt.executeUpdate();
            }
            commitConnection(conn);
            pstmt.close();
        }catch (Exception e) {
            e.printStackTrace();;
            rollbackConnection(conn);
            pstmt.close();
        }finally {
            closeConnection(conn);
        }
    }

    public void importCopyrights() throws IOException, SQLException, ClassNotFoundException {
        File excelFile = new File(path); // 创建文件对象
        FileInputStream in = new FileInputStream(excelFile); // 文件流
        Workbook workbook = new XSSFWorkbook(in);

        Sheet sheet = workbook.getSheetAt(0);

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement("insert into t_copyright (`c_code`, `c_type`, `c_granter_id`, `c_grantee_id`, `c_signdate`, `c_operator`, `c_creator`, `c_createtime`)values(?,?,?,?,?,1,1,now())");

        HashMap<String, Long> grantersMap = mapGranters(conn);
        HashMap<String, Long> granteesMap = mapGrantees(conn);

        HashSet<String> copyrightsSet = new HashSet<String>();

        try {
            int c = 0;
            for (Row row : sheet) {
                c++;
                System.out.println("========== 第 " + c + " 行 ==========");

                //如果当前行没有数据，跳出循环
                if (row.getCell(0).toString().equals("")) {
                    System.out.println("没数据了");
                    break;
                }

                Cell codeCell = row.getCell(1);
                if (codeCell == null) {
                    System.out.print("null" + "\t");
                    continue;
                }
                Object codeObj = getValue(codeCell);
                String copyrightCode = (String)codeObj;
                String[] chunks = copyrightCode.split("-");
                if(chunks.length > 3) {
                    copyrightCode = copyrightCode.substring(0, copyrightCode.lastIndexOf("-"));
                }

                if(copyrightsSet.contains(copyrightCode)) {
                    System.out.println("已处理过");
                    continue;
                }

                copyrightsSet.add(copyrightCode);

                String type = chunks[1];
                pstmt.setString(1, copyrightCode);
                pstmt.setString(2, type);

                int end = row.getLastCellNum();
                for (int i = 0; i < end; i++) {
                    if (i == 0) {
                        continue;
                    }

                    Cell cell = row.getCell(i);
                    if (cell == null) {
                        System.out.print("null" + "\t");
                        continue;
                    }
                    Object obj = getValue(cell);

                    if(i == 6) {
                        String granterName = (String)obj;
                        Long granterId = null;
                        if(granterName != null && granterName.trim().length() > 0) {
                            granterName = granterName.trim();
                            granterId = grantersMap.get(granterName);
                        }
                        pstmt.setLong(3, granterId);
                    }

                    if(i == 38) {
                        String granteeName = (String)obj;
                        Long granteeId = null;
                        if(granteeName != null && granteeName.trim().length() > 0) {
                            granteeName = granteeName.trim();
                            granteeId = granteesMap.get(granteeName);
                        }
                        pstmt.setLong(4, granteeId);
                    }

                    if(i == 3) {
                        String signDate = (String)obj;
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
                        Date date = sdf.parse(signDate);
                        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
                        pstmt.setDate(5, sqlDate);
                    }
                }
                int r = pstmt.executeUpdate();
            }
            commitConnection(conn);
            pstmt.close();
        }catch (Exception e) {
            e.printStackTrace();;
            rollbackConnection(conn);
            pstmt.close();
        }finally {
            closeConnection(conn);
        }
    }

    public void importProductSubjects() throws IOException, SQLException, ClassNotFoundException {
        File excelFile = new File(path); // 创建文件对象
        FileInputStream in = new FileInputStream(excelFile); // 文件流
        Workbook workbook = new XSSFWorkbook(in);

        Sheet sheet = workbook.getSheetAt(0);

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement("update t_product_info set `c_subject_id` = ? where c_id = ?");

        HashMap<String, Long> productsMap = mapProducts(conn);
        HashMap<String, Long> subjectsMap = mapSubjects(conn);

        try {
            int c = 0;
            for (Row row : sheet) {
                c++;
                System.out.println("========== 第 " + c + " 行 ==========");

                //如果当前行没有数据，跳出循环
                if (row.getCell(0).toString().equals("")) {
                    System.out.println("没数据了");
                    break;
                }

                Cell productNoCell = row.getCell(1);
                if (productNoCell == null) {
                    System.out.print("productNo cell is null" + "\t");
                    continue;
                }

                Object productNoObj = getValue(productNoCell);
                if(productNoObj == null) {
                    continue;
                }

                String productNo = (String)productNoObj;
                Long productId = productsMap.get(productNo);

                Cell subjectCell = row.getCell(18);
                if (subjectCell == null) {
                    System.out.print("subjectCell cell is null" + "\t");
                    continue;
                }

                Object subjectObj = getValue(subjectCell);
                if(subjectObj == null) {
                    continue;
                }

                String subjectStr = (String)subjectObj;
                if(subjectStr != null) {
                    subjectStr = subjectStr.trim();

                    Long subjectId = subjectsMap.get(subjectStr);
                    pstmt.setLong(1, subjectId);
                    pstmt.setLong(2, productId);
                    pstmt.executeUpdate();
                }
            }
            commitConnection(conn);
            pstmt.close();
        }catch (Exception e) {
            e.printStackTrace();;
            rollbackConnection(conn);
            pstmt.close();
        }finally {
            closeConnection(conn);
        }

    }

    public void importProductAuthors() throws IOException, SQLException, ClassNotFoundException {
        File excelFile = new File(path); // 创建文件对象
        FileInputStream in = new FileInputStream(excelFile); // 文件流
        Workbook workbook = new XSSFWorkbook(in);

        Sheet sheet = workbook.getSheetAt(0);

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement("insert into t_product_author (`c_product_id`, `c_author_id`, `c_creator`, `c_createtime`)values(?,?,?,now())");

        HashMap<String, Long> productsMap = mapProducts(conn);
        HashMap<String, Long> authorsMap = mapAuthors(conn);

        try {
            int c = 0;
            for (Row row : sheet) {
                c++;
                System.out.println("========== 第 " + c + " 行 ==========");

                //如果当前行没有数据，跳出循环
                if (row.getCell(0).toString().equals("")) {
                    System.out.println("没数据了");
                    break;
                }

                Cell productNoCell = row.getCell(1);
                if (productNoCell == null) {
                    System.out.print("productNo cell is null" + "\t");
                    continue;
                }

                Object productNoObj = getValue(productNoCell);
                if(productNoObj == null) {
                    continue;
                }

                String productNo = (String)productNoObj;
                Long productId = productsMap.get(productNo);

                Cell authorCell = row.getCell(7);
                if (authorCell == null) {
                    System.out.print("authorCell cell is null" + "\t");
                    continue;
                }

                Object authorObj = getValue(authorCell);
                if(authorObj == null) {
                    continue;
                }

                String authorStr = (String)authorObj;
                if(authorStr != null) {
                    authorStr = authorStr.trim();
                    if(authorStr.indexOf("、") > 0) {
                        String[] chunks = authorStr.split("、");
                        for(String chunk : chunks) {
                            if(chunk.indexOf("(") > 0) {
                                String authorName = chunk.substring(0, chunk.indexOf("(")).trim();
                                String authorPseudonym = chunk.substring(chunk.indexOf("(")+1, chunk.length()-1).trim();
                                String authorKey = authorName+"__"+authorPseudonym;
                                Long authorId = authorsMap.get(authorKey);
                                pstmt.setLong(1, productId);
                                pstmt.setLong(2, authorId);
                                pstmt.setString(3,"1");
                                pstmt.executeUpdate();
                            }else if(chunk.indexOf("（") > 0) {
                                String authorName = chunk.substring(0, chunk.indexOf("（")).trim();
                                String authorPseudonym = chunk.substring(chunk.indexOf("（")+1, chunk.length()-1).trim();
                                String authorKey = authorName+"__"+authorPseudonym;
                                Long authorId = authorsMap.get(authorKey);
                                pstmt.setLong(1, productId);
                                pstmt.setLong(2, authorId);
                                pstmt.setString(3,"1");
                                pstmt.executeUpdate();
                            }else{
                                chunk = chunk.trim();
                                Long authorId = authorsMap.get(chunk);
                                pstmt.setLong(1, productId);
                                pstmt.setLong(2, authorId);
                                pstmt.setString(3,"1");
                                pstmt.executeUpdate();
                            }
                        }
                    }else{
                        if(authorStr.indexOf("(") > 0) {
                            String authorName = authorStr.substring(0, authorStr.indexOf("(")).trim();
                            String authorPseudonym = authorStr.substring(authorStr.indexOf("(")+1, authorStr.length()-1).trim();
                            String authorKey = authorName+"__"+authorPseudonym;
                            Long authorId = authorsMap.get(authorKey);
                            pstmt.setLong(1, productId);
                            pstmt.setLong(2, authorId);
                            pstmt.setString(3,"1");
                            pstmt.executeUpdate();
                        }else if(authorStr.indexOf("（") > 0) {
                            String authorName = authorStr.substring(0, authorStr.indexOf("（")).trim();
                            String authorPseudonym = authorStr.substring(authorStr.indexOf("（")+1, authorStr.length()-1).trim();
                            String authorKey = authorName+"__"+authorPseudonym;
                            Long authorId = authorsMap.get(authorKey);
                            pstmt.setLong(1, productId);
                            pstmt.setLong(2, authorId);
                            pstmt.setString(3,"1");
                            pstmt.executeUpdate();
                        }else{
                            Long authorId = authorsMap.get(authorStr);
                            pstmt.setLong(1, productId);
                            pstmt.setLong(2, authorId);
                            pstmt.setString(3,"1");
                            pstmt.executeUpdate();
                        }
                    }
                }

            }
            commitConnection(conn);
            pstmt.close();
        }catch (Exception e) {
            e.printStackTrace();;
            rollbackConnection(conn);
            pstmt.close();
        }finally {
            closeConnection(conn);
        }

    }

    public void importProducts() throws IOException, SQLException, ClassNotFoundException {
        File excelFile = new File(path); // 创建文件对象
        FileInputStream in = new FileInputStream(excelFile); // 文件流
        Workbook workbook = new XSSFWorkbook(in);

        Sheet sheet = workbook.getSheetAt(0);

        HashSet<String> productSet = new HashSet<String>();

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement("insert into t_product_info (`c_name`, `c_wordcount`, `c_section`, `c_isbn`, `c_publish_state`, `c_press`, `c_stockin`, `c_type`, `c_sort`, `c_production_state`, `c_desc`, `c_creator`, `c_createtime`,`c_code`)values(?,?,?,?,?,?,?,?,?,?,?,?,now(),?)");

        try {
            int c = 0;
            for (Row row : sheet) {
                c++;
                System.out.println("========== 第 " + c + " 行 ==========");

                //如果当前行没有数据，跳出循环
                if (row.getCell(0).toString().equals("")) {
                    System.out.println("没数据了");
                    break;
                }

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

                    if(i == 4) {
                        String productName = (String)obj;
                        System.out.println("ProductName = " + productName);
//                        if(!productSet.contains(productName)) {
                        productSet.add(productName);
                        pstmt.setString(1,productName);
//                        }else{
//                            System.out.println("重复了: " + productName);
//                        }
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
                        pstmt.setString(2,String.valueOf(wordCount));
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
                        if(section != null){
                            pstmt.setString(3, section.toString());
                        }else{
                            pstmt.setString(3, null);
                        }

                    }

                    if(i == 8) {
                        String isbnStr = (String)obj;
                        String isbn = null;
                        if(isbnStr != null && isbnStr.trim().length() > 0) {
                            isbn = isbnStr.trim();
                        }
                        pstmt.setString(4, isbn);
                    }

                    if(i == 19) {
                        String pressStr = (String)obj;
                        String press = null;
                        String publishState = "1";
                        if(pressStr != null && pressStr.trim().length() > 0) {
                            if("未出版".equals(pressStr.trim())) {
                                publishState = "0";
                            }else{
                                press = pressStr.trim();
                            }
                        }
                        pstmt.setString(5, publishState);
                        pstmt.setString(6, press);
                    }

                    if(i == 20) {
                        String stockInStr = (String)obj;
                        String stockIn = "1";
                        if(stockInStr != null && stockInStr.trim().length() > 0) {
                            if("未入库".equals(stockInStr.trim())) {
                                stockIn = "0";
                            }
                        }
                        pstmt.setString(7, stockIn);
                    }

                    if(i == 1) {
                        String productNo = (String)obj;
                        String[] chunks = productNo.split("-");
                        String copyrightProductSort = "1";
                        if(chunks.length > 3) {
                            copyrightProductSort = productNo.substring(productNo.lastIndexOf("-") + 1);
                        }
                        String type = chunks[1];
                        pstmt.setString(8, type);
                        pstmt.setInt(9,Integer.parseInt(copyrightProductSort));
                        pstmt.setString(13, productNo);
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
                        String stateCode = null;
                        if(state != null) {
                            state = state.trim();
                            if("老节目".equals(state)) {
                                stateCode = ProductionState.THE_OLD_PROGRAM;
                            }else if("自制".equals(state)){
                                stateCode = ProductionState.THE_OWN;
                            }else if("待制作".equals(state)) {
                                stateCode = ProductionState.TO_BE_MADE;
                            }else if("预定".equals(state)) {
                                stateCode = ProductionState.RESERVATION;
                            }else if("已制作".equals(state)) {
                                stateCode = ProductionState.HAS_BEEN_PRODUCED;
                            }
                        }
                        pstmt.setString(10, stateCode);
                    }

                    if(i == 46) {
                        String descStr = (String)obj;
                        String desc = null;
                        if(descStr != null && descStr.trim().length() > 0) {
                            desc = descStr.trim();
                        }
                        pstmt.setString(11, desc);
                    }
                }
                pstmt.setString(12, "1");
                int r = pstmt.executeUpdate();
                System.out.println("End of " + c + ", insert " + r);
            }

            commitConnection(conn);
            System.out.println("Commited");
            pstmt.close();
        }catch (Exception e) {
            e.printStackTrace();
            rollbackConnection(conn);
            pstmt.close();
        }finally {
            closeConnection(conn);
        }
    }


    public void importGrantees() throws IOException, SQLException, ClassNotFoundException {
        File excelFile = new File(path); // 创建文件对象
        FileInputStream in = new FileInputStream(excelFile); // 文件流
        Workbook workbook = new XSSFWorkbook(in);

        Sheet sheet = workbook.getSheetAt(0);

        HashSet<String> granteeSet = new HashSet<String>();

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement("insert into t_grantee (`c_name`, `c_creator`, `c_createtime`)values(?,?,now())");
        try {
            int c = 0;
            int s = 0;
            for(Row row : sheet) {
                c++;
                System.out.println("========== 第 " + c + " 行 ==========");
                Cell cell = row.getCell(38);
                if (cell == null) {
                    System.out.print("cell is null" + "\t");
                    continue;
                }

                Object obj = getValue(cell);
                if(obj == null) {
                    continue;
                }

                String granteeName = (String)obj;
                if(granteeName != null) {
                    granteeName = granteeName.trim();
                    if(granteeName.length() > 0) {
                        if(!granteeSet.contains(granteeName)) {
                            granteeSet.add(granteeName);
                            pstmt.setString(1, granteeName);
                            pstmt.setString(2,"1");
                            pstmt.executeUpdate();
                        }
                    }
                }
            }
            commitConnection(conn);
            pstmt.close();
        }catch (Exception e) {
            rollbackConnection(conn);
            pstmt.close();
            pstmt = null;
        }finally {
            pstmt = null;
            closeConnection(conn);
        }

    }

    public void importAnnouncers() throws IOException, SQLException, ClassNotFoundException {
        File excelFile = new File(path); // 创建文件对象
        FileInputStream in = new FileInputStream(excelFile); // 文件流
        Workbook workbook = new XSSFWorkbook(in);

        Sheet sheet = workbook.getSheetAt(0);

        HashSet<String> announcerSet = new HashSet<String>();

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement("insert into t_announcer (`c_name`, `c_pseudonym`, `c_creator`, `c_createtime`)values(?,?,?,now())");
        try {
            int c = 0;
            int s = 0;
            for(Row row : sheet) {
                c++;
                System.out.println("========== 第 " + c + " 行 ==========");
                Cell cell = row.getCell(15);
                if (cell == null) {
                    System.out.print("cell is null" + "\t");
                    continue;
                }

                Object obj = getValue(cell);
                if(obj == null) {
                    continue;
                }

                String announcerStr = (String)obj;
                if(announcerStr != null) {
                    announcerStr = announcerStr.trim();
                    if(announcerStr.indexOf("、") > 0) {
                        String[] chunks = announcerStr.split("、");
                        for(String chunk : chunks) {
                            if(chunk.indexOf("(") > 0) {
                                String announcerName = chunk.substring(0, chunk.indexOf("(")).trim();
                                String announcerPseudonym = chunk.substring(chunk.indexOf("(")+1, chunk.length()-1).trim();
                                if(!announcerSet.contains(announcerName+"__"+announcerPseudonym)) {
                                    announcerSet.add(announcerName+"__"+announcerPseudonym);
                                    pstmt.setString(1, announcerName);
                                    pstmt.setString(2, announcerPseudonym);
                                    pstmt.setString(3,"1");
                                    pstmt.executeUpdate();
                                }
                            }else if(chunk.indexOf("（") > 0) {
                                String announcerName = chunk.substring(0, chunk.indexOf("（")).trim();
                                String announcerPseudonym = chunk.substring(chunk.indexOf("（")+1, chunk.length()-1).trim();
                                if(!announcerSet.contains(announcerName+"__"+announcerPseudonym)) {
                                    announcerSet.add(announcerName+"__"+announcerPseudonym);
                                    pstmt.setString(1, announcerName);
                                    pstmt.setString(2, announcerPseudonym);
                                    pstmt.setString(3,"1");
                                    pstmt.executeUpdate();
                                }
                            }else{
                                chunk = chunk.trim();
                                if(!announcerSet.contains(chunk)) {
                                    announcerSet.add(chunk);
                                    pstmt.setString(1, chunk);
                                    pstmt.setString(2, null);
                                    pstmt.setString(3,"1");
                                    pstmt.executeUpdate();
                                }
                            }
                        }
                    }else{
                        if(announcerStr.indexOf("(") > 0) {
                            String announcerName = announcerStr.substring(0, announcerStr.indexOf("(")).trim();
                            String announcerPseudonym = announcerStr.substring(announcerStr.indexOf("(")+1, announcerStr.length()-1).trim();
                            if(!announcerSet.contains(announcerName+"__"+announcerPseudonym)) {
                                announcerSet.add(announcerName+"__"+announcerPseudonym);
                                pstmt.setString(1, announcerName);
                                pstmt.setString(2, announcerPseudonym);
                                pstmt.setString(3,"1");
                                pstmt.executeUpdate();
                            }
                        }else if(announcerStr.indexOf("（") > 0) {
                            String announcerName = announcerStr.substring(0, announcerStr.indexOf("（")).trim();
                            String announcerPseudonym = announcerStr.substring(announcerStr.indexOf("（")+1, announcerStr.length()-1).trim();
                            if(!announcerSet.contains(announcerName+"__"+announcerPseudonym)) {
                                announcerSet.add(announcerName+"__"+announcerPseudonym);
                                pstmt.setString(1, announcerName);
                                pstmt.setString(2, announcerPseudonym);
                                pstmt.setString(3,"1");
                                pstmt.executeUpdate();
                            }
                        }else{
                            if(!announcerSet.contains(announcerStr)) {
                                announcerSet.add(announcerStr);
                                pstmt.setString(1, announcerStr);
                                pstmt.setString(2, null);
                                pstmt.setString(3,"1");
                                pstmt.executeUpdate();
                            }
                        }
                    }
                }
            }
            commitConnection(conn);
            pstmt.close();
        }catch (Exception e) {
            rollbackConnection(conn);
            pstmt.close();
            pstmt = null;
        }finally {
            pstmt = null;
            closeConnection(conn);
        }

    }

    public void importMaker() throws IOException, SQLException, ClassNotFoundException {
        File excelFile = new File(path); // 创建文件对象
        FileInputStream in = new FileInputStream(excelFile); // 文件流
        Workbook workbook = new XSSFWorkbook(in);

        Sheet sheet = workbook.getSheetAt(0);

        HashSet<String> makerSet = new HashSet<String>();

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement("insert into t_maker (`c_name`, `c_creator`, `c_createtime`)values(?,?,now())");
        try {
            int c = 0;
            int s = 0;
            for(Row row : sheet) {
                c++;
                System.out.println("========== 第 " + c + " 行 ==========");
                Cell cell = row.getCell(41);
                if (cell == null) {
                    System.out.print("cell is null" + "\t");
                    continue;
                }

                Object obj = getValue(cell);
                if(obj == null) {
                    continue;
                }

                String makerStr = (String)obj;
                String makerName = null;
                if(makerStr != null && makerStr.trim().length() > 0) {
                    makerStr = makerStr.trim();
                    if(makerStr.indexOf("/") > 0) {
                        String[] chunks = makerStr.split("/");
                        for(String chunk : chunks) {
                            chunk = chunk.trim();
                            if(!makerSet.contains(chunk)) {
                                makerSet.add(chunk);
                                pstmt.setString(1, chunk);
                                pstmt.setString(2,"1");
                                pstmt.executeUpdate();
                            }
                        }
                    }else{
                        if(!makerSet.contains(makerStr)) {
                            makerSet.add(makerStr);
                            pstmt.setString(1, makerStr);
                            pstmt.setString(2,"1");
                            pstmt.executeUpdate();
                        }
                    }
                }
            }
            commitConnection(conn);
            pstmt.close();
        }catch (Exception e) {
            rollbackConnection(conn);
            pstmt.close();
            pstmt = null;
        }finally {
            pstmt = null;
            closeConnection(conn);
        }
    }

    public void importAuthors() throws IOException, SQLException, ClassNotFoundException {
        File excelFile = new File(path); // 创建文件对象
        FileInputStream in = new FileInputStream(excelFile); // 文件流
        Workbook workbook = new XSSFWorkbook(in);

        Sheet sheet = workbook.getSheetAt(0);

        HashSet<String> authorSet = new HashSet<String>();

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement("insert into t_author (`c_name`, `c_pseudonym`, `c_creator`, `c_createtime`)values(?,?,?,now())");
        try {
            int c = 0;
            int s = 0;
            for(Row row : sheet) {
                c++;
                System.out.println("========== 第 " + c + " 行 ==========");
                Cell cell = row.getCell(7);
                if (cell == null) {
                    System.out.print("cell is null" + "\t");
                    continue;
                }

                Object obj = getValue(cell);
                if(obj == null) {
                    continue;
                }

                String authorStr = (String)obj;
                if(authorStr != null) {
                    authorStr = authorStr.trim();
                    if(authorStr.indexOf("、") > 0) {
                        String[] chunks = authorStr.split("、");
                        for(String chunk : chunks) {
                            if(chunk.indexOf("(") > 0) {
                                String authorName = chunk.substring(0, chunk.indexOf("(")).trim();
                                String authorPseudonym = chunk.substring(chunk.indexOf("(")+1, chunk.length()-1).trim();
                                if(!authorSet.contains(authorName+"__"+authorPseudonym)) {
                                    authorSet.add(authorName+"__"+authorPseudonym);
                                    pstmt.setString(1, authorName);
                                    pstmt.setString(2, authorPseudonym);
                                    pstmt.setString(3,"1");
                                    pstmt.executeUpdate();
                                }
                            }else if(chunk.indexOf("（") > 0) {
                                String authorName = chunk.substring(0, chunk.indexOf("（")).trim();
                                String authorPseudonym = chunk.substring(chunk.indexOf("（")+1, chunk.length()-1).trim();
                                if(!authorSet.contains(authorName+"__"+authorPseudonym)) {
                                    authorSet.add(authorName+"__"+authorPseudonym);
                                    pstmt.setString(1, authorName);
                                    pstmt.setString(2, authorPseudonym);
                                    pstmt.setString(3,"1");
                                    pstmt.executeUpdate();
                                }
                            }else{
                                chunk = chunk.trim();
                                if(!authorSet.contains(chunk)) {
                                    authorSet.add(chunk);
                                    pstmt.setString(1, chunk);
                                    pstmt.setString(2, null);
                                    pstmt.setString(3,"1");
                                    pstmt.executeUpdate();
                                }
                            }
                        }
                    }else{
                        if(authorStr.indexOf("(") > 0) {
                            String authorName = authorStr.substring(0, authorStr.indexOf("(")).trim();
                            String authorPseudonym = authorStr.substring(authorStr.indexOf("(")+1, authorStr.length()-1).trim();
                            if(!authorSet.contains(authorName+"__"+authorPseudonym)) {
                                authorSet.add(authorName+"__"+authorPseudonym);
                                pstmt.setString(1, authorName);
                                pstmt.setString(2, authorPseudonym);
                                pstmt.setString(3,"1");
                                pstmt.executeUpdate();
                            }
                        }else if(authorStr.indexOf("（") > 0) {
                            String authorName = authorStr.substring(0, authorStr.indexOf("（")).trim();
                            String authorPseudonym = authorStr.substring(authorStr.indexOf("（")+1, authorStr.length()-1).trim();
                            if(!authorSet.contains(authorName+"__"+authorPseudonym)) {
                                authorSet.add(authorName+"__"+authorPseudonym);
                                pstmt.setString(1, authorName);
                                pstmt.setString(2, authorPseudonym);
                                pstmt.setString(3,"1");
                                pstmt.executeUpdate();
                            }
                        }else{
                            if(!authorSet.contains(authorStr)) {
                                authorSet.add(authorStr);
                                pstmt.setString(1, authorStr);
                                pstmt.setString(2, null);
                                pstmt.setString(3,"1");
                                pstmt.executeUpdate();
                            }
                        }
                    }
                }
            }
            commitConnection(conn);
            pstmt.close();
        }catch (Exception e) {
            rollbackConnection(conn);
            pstmt.close();
            pstmt = null;
        }finally {
            pstmt = null;
            closeConnection(conn);
        }

    }

    public void importGranters() throws IOException, SQLException, ClassNotFoundException {
        File excelFile = new File(path); // 创建文件对象
        FileInputStream in = new FileInputStream(excelFile); // 文件流
        Workbook workbook = new XSSFWorkbook(in);

        Sheet sheet = workbook.getSheetAt(0);

        HashSet<String> granterSet = new HashSet<String>();

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement("insert into t_granter (`c_name`, `c_creator`, `c_createtime`)values(?,?,now())");
        try {
            int c = 0;
            int s = 0;
            for(Row row : sheet) {
                c++;
                System.out.println("========== 第 " + c + " 行 ==========");
                Cell cell = row.getCell(6);
                if (cell == null) {
                    System.out.print("cell is null" + "\t");
                    continue;
                }

                Object obj = getValue(cell);
                if(obj == null) {
                    continue;
                }

                String granterName = (String)obj;
                if(granterName != null) {
                    granterName = granterName.trim();
                    if(granterName.length() > 0) {
                        if(!granterSet.contains(granterName)) {
                            granterSet.add(granterName);
                            pstmt.setString(1, granterName);
                            pstmt.setString(2,"1");
                            pstmt.executeUpdate();
                        }
                    }
                }
            }
            commitConnection(conn);
            pstmt.close();
        }catch (Exception e) {
            rollbackConnection(conn);
            pstmt.close();
            pstmt = null;
        }finally {
            pstmt = null;
            closeConnection(conn);
        }

    }

    public void importSubjects() throws IOException, SQLException, ClassNotFoundException {
        File excelFile = new File(path); // 创建文件对象
        FileInputStream in = new FileInputStream(excelFile); // 文件流
        Workbook workbook = new XSSFWorkbook(in);

        Sheet sheet = workbook.getSheetAt(0);

        HashSet<String> subjectSet = new HashSet<String>();

        Connection conn = getConnection();
        PreparedStatement pstmt = conn.prepareStatement("insert into t_subject (`c_name`, `c_order`, `c_creator`, `c_createtime`)values(?,?,?,now())");
        try {
            int c = 0;
            int s = 0;
            for(Row row : sheet) {
                c++;
                System.out.println("========== 第 " + c + " 行 ==========");
                Cell cell = row.getCell(18);
                if (cell == null) {
                    System.out.print("cell is null" + "\t");
                    continue;
                }

                Object obj = getValue(cell);
                if(obj == null) {
                    continue;
                }

                String subjectName = (String)obj;
                if(subjectName != null) {
                    subjectName = subjectName.trim();
                    if(!subjectSet.contains(subjectName)) {
                        subjectSet.add(subjectName);
                        pstmt.setString(1, subjectName);
                        pstmt.setInt(2, ++s);
                        pstmt.setString(3,"1");
                        pstmt.executeUpdate();
                    }
                }
            }
            commitConnection(conn);
            pstmt.close();
        }catch (Exception e) {
            rollbackConnection(conn);
            pstmt.close();
            pstmt = null;
        }finally {
            pstmt = null;
            closeConnection(conn);
        }

    }

    public void importExcelData() throws IOException, ParseException {
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

                if(i == 17) {

                }

                if(i == 18) {
                    String subject = (String)obj;
                    System.out.println("Subject = " + subject);
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

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        final String DB_URL = "jdbc:mysql://127.0.0.1:3306/emars2?characterEncoding=utf8&connectTimeout=10000&autoReconnect=true";
        final String USER = "root";
        final String PASS = "root";

        // 注册 JDBC 驱动
        Class.forName("com.mysql.jdbc.Driver");

        // 打开链接
        System.out.println("连接数据库...");
        Connection conn = DriverManager.getConnection(DB_URL,USER,PASS);
        conn.setAutoCommit(false);

        return conn;
    }

    private void rollbackConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
                closeConnection(conn);
            }
        }
    }

    private void commitConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
                closeConnection(conn);
            }
        }
    }

    private void closeConnection(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
                conn = null;
            }
        }
    }

    private HashMap<String, Long> mapAuthors(Connection conn) {
        HashMap<String, Long> result = new HashMap<String, Long>();
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select c_id, c_name, c_pseudonym from t_author");
            while(rs.next()) {
                Long id = rs.getLong("c_id");
                String name = rs.getString("c_name");
                String pseudonym = rs.getString("c_pseudonym");
                if(pseudonym != null && pseudonym.trim().length() > 0) {
                    result.put(name + "__" + pseudonym, id);
                }else{
                    result.put(name, id);
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    private HashMap<String, Long> mapAnnouncers(Connection conn) {
        HashMap<String, Long> result = new HashMap<String, Long>();
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select c_id, c_name, c_pseudonym from t_announcer");
            while(rs.next()) {
                Long id = rs.getLong("c_id");
                String name = rs.getString("c_name");
                String pseudonym = rs.getString("c_pseudonym");
                if(pseudonym != null && pseudonym.trim().length() > 0) {
                    result.put(name + "__" + pseudonym, id);
                }else{
                    result.put(name, id);
                }
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    private HashMap<String, Long> mapProducts(Connection conn) {
        HashMap<String, Long> result = new HashMap<String, Long>();
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select c_id, c_code from t_product_info");
            while(rs.next()) {
                Long id = rs.getLong("c_id");
                String code = rs.getString("c_code");
                result.put(code, id);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    private HashMap<String, Long> mapSubjects(Connection conn) {
        HashMap<String, Long> result = new HashMap<String, Long>();
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select c_id, c_name from t_subject");
            while(rs.next()) {
                Long id = rs.getLong("c_id");
                String name = rs.getString("c_name");
                result.put(name, id);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    private HashMap<String, Long> mapGranters(Connection conn) {
        HashMap<String, Long> result = new HashMap<String, Long>();
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select c_id, c_name from t_granter");
            while(rs.next()) {
                Long id = rs.getLong("c_id");
                String name = rs.getString("c_name");
                result.put(name, id);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    private HashMap<String, Long> mapGrantees(Connection conn) {
        HashMap<String, Long> result = new HashMap<String, Long>();
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select c_id, c_name from t_grantee");
            while(rs.next()) {
                Long id = rs.getLong("c_id");
                String name = rs.getString("c_name");
                result.put(name, id);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    private HashMap<String, Long> mapCopyrights(Connection conn) {
        HashMap<String, Long> result = new HashMap<String, Long>();
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select c_id, c_code from t_copyright");
            while(rs.next()) {
                Long id = rs.getLong("c_id");
                String code = rs.getString("c_code");
                result.put(code, id);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    private HashMap<String, Long> mapMakeContracts(Connection conn) {
        HashMap<String, Long> result = new HashMap<String, Long>();
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select c_id, c_code from t_make_contract");
            while(rs.next()) {
                Long id = rs.getLong("c_id");
                String code = rs.getString("c_code");
                result.put(code, id);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    private HashMap<String, Long> mapMakers(Connection conn) {
        HashMap<String, Long> result = new HashMap<String, Long>();
        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select c_id, c_name from t_maker");
            while(rs.next()) {
                Long id = rs.getLong("c_id");
                String name = rs.getString("c_name");
                result.put(name, id);
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    public static void main(String[] args) throws Exception {
        ExcelImportor importor = new ExcelImportor();
//        importor.importExcelData();
//        importor.importSubjects();
//        importor.importGranters();
//        importor.importAuthors();
//        importor.importMaker();
//        importor.importAnnouncers();
//        importor.importGrantees();
//        importor.importProducts();
//        importor.importProductAuthors();
//        importor.importProductSubjects();
//        importor.importCopyrights();
//        importor.importCopyrightProducts();
//        importor.importMakeContracts();
//        importor.importMakeProducts();
//        importor.importProductAnnouncers();
        importor.refreshMakeTotal();
    }

}
