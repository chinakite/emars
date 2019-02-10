package com.ideamoment.emars.importor;

import com.ideamoment.emars.model.ProductInfo;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Chinakite on 2019/1/1.
 */
public class OssFileImportor {

    private static HashSet<Long> uniqueCopyrightFileIds = new HashSet<Long>();

    public void preHandleDatas() throws IOException, SQLException, ClassNotFoundException {
        Connection conn = getConnection();
        String sql = "select id, c_local_path, c_file_name, c_oss_path from oss_files";
        Statement stmt = conn.createStatement();
        try {
            int c = 0;
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                c++;
                Long id = rs.getLong("id");
                String localPath = rs.getString("c_local_path");
                String fileName = rs.getString("c_file_name");
                String ossPath = rs.getString("c_oss_path");

                if(excludeFileName(fileName)) {
                    continue;
                }

                localPath = localPath.replaceAll("\\\\", "/");
                localPath = localPath.substring(15);
                System.out.println(c + " >>>");
                System.out.println(localPath);

                String[] arr = localPath.split("/");

                String rootFolder = arr[0];
                String kind = "";
                if(rootFolder.startsWith("存档")) {
                    kind = "存档";
                }else if(rootFolder.startsWith("营销")) {
                    kind = "营销";
                }

                if(kind.equals("存档")) {
//                    updateCopyrightCodeAndType(conn, arr, id);
                    importCopyrightFiles(conn, arr, fileName, ossPath);
                }else{

                }
            }
            commitConnection(conn);
            stmt.close();
        }catch (Exception e) {
            e.printStackTrace();;
            rollbackConnection(conn);
            stmt.close();
        }finally {
            closeConnection(conn);
        }
    }

    private void updateCopyrightCodeAndType(Connection conn, String[] arr, Long ossFileId) throws SQLException {
        String copyrightNoFolder = arr[2];
        String copyrightNo = "";
        if(copyrightNoFolder.startsWith("dz-")) {
            copyrightNo = copyrightNoFolder.substring(3);
        }else{
            copyrightNo = copyrightNoFolder;
        }

        String type = "";
        String maskFileName = "";
        int arrLength = arr.length;
        for(int i=3; i<arrLength; i++) {
            maskFileName = maskFileName + arr[i];
        }
        if(maskFileName.indexOf("身份证") > -1) {
            type = "7";
        }else if(maskFileName.indexOf("版权页") > -1) {
            type = "7";
        }else if(maskFileName.indexOf("出版合同") > -1) {
            type = "7";
        }else if(maskFileName.indexOf("外版合同") > -1) {
            type = "7";
        }else if(maskFileName.indexOf("授权") > -1) {
            type = "3";
        }else{
            type = "1";
        }

        String sql = "update oss_files set c_copyright_code = ?, c_type = ? where id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, copyrightNo);
            pstmt.setString(2, type);
            pstmt.setLong(3, ossFileId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    private boolean excludeFileName(String fileName) {
        if(fileName.equals("Thumbs.db")
                || fileName.equals(".DS_Store")) {
            return true;
        }else{
            return false;
        }
    }

//    private void importCopyrightFiles(Connection conn, String[] arr, String fileName, String ossPath) {
//        String yearFolder = arr[1];
//        String year = "";
//        if(yearFolder.startsWith("2015")) {
//            year = "2015";
//        }else if(yearFolder.startsWith("2016")) {
//            year = "2016";
//        }else if(yearFolder.startsWith("2017")) {
//            year = "2017";
//        }else if(yearFolder.startsWith("2018")) {
//            year = "2018";
//        }
//
//        String copyrightNoFolder = arr[2];
//        String copyrightNo = "";
//        if(copyrightNoFolder.startsWith("dz-")) {
//            copyrightNo = copyrightNoFolder.substring(3);
//        }else{
//            copyrightNo = copyrightNoFolder;
//        }
//
//        String type = "";
//        String maskFileName = "";
//        int arrLength = arr.length;
//        for(int i=3; i<arrLength; i++) {
//            maskFileName = maskFileName + arr[i];
//        }
//        if(maskFileName.indexOf("身份证") > -1) {
//            type = "身份证";
//        }else if(maskFileName.indexOf("版权页") > -1) {
//            type = "版权页";
//        }else if(maskFileName.indexOf("出版合同") > -1) {
//            type = "出版合同";
//        }else if(maskFileName.indexOf("外版合同") > -1) {
//            type = "出版合同";
//        }else if(maskFileName.indexOf("授权") > -1) {
//            type = "授权书";
//        }else{
//            type = "合同";
//        }
//
//        System.out.println(year + " | " + copyrightNo + " | " + type);
//
//        List<ProductInfo> products = loadProductsByCopyrightNo(conn, copyrightNo);
//        if(products != null && products.size() > 0) {
//            for(ProductInfo prod : products) {
//                String prodName = prod.getName();
//                Long prodId = prod.getId();
//            }
//        }
//    }

    private void importCopyrightFiles(Connection conn, String[] arr, String fileName, String ossPath) {
        
    }

    private List<ProductInfo> loadProductsByCopyrightNo(Connection conn, String copyrightNo) {
        String sql = "SELECT p.c_id, p.c_name " +
                "FROM t_copyright c, t_copyright_product cp, t_product_info p " +
                "WHERE c.`c_code` = '" + copyrightNo + "' " +
                "AND c.c_id = cp.`c_copyright_id` " +
                "AND cp.`c_product_id` = p.`c_id`";

        List<ProductInfo> products = new ArrayList<ProductInfo>();

        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                ProductInfo product = new ProductInfo();

                Long id = rs.getLong("c_id");
                product.setId(id);

                String name = rs.getString("c_name");
                product.setName(name);

                products.add(product);
            }

            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        OssFileImportor importor = new OssFileImportor();
        importor.preHandleDatas();
    }

    private Connection getConnection() throws ClassNotFoundException, SQLException {
        final String DB_URL = "jdbc:mysql://127.0.0.1:3306/emars3?characterEncoding=utf8&connectTimeout=10000&autoReconnect=true";
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
}
