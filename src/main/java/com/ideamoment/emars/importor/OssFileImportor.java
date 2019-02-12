package com.ideamoment.emars.importor;

import com.ideamoment.emars.model.CopyrightFile;
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

    public void preHandleDatas() throws SQLException, ClassNotFoundException {
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
                    updateArchiveCopyrightCodeAndType(conn, arr, id);
                }else{
                    updateSaleCopyrightCodeAndType(conn, arr, id);
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

    private void updateArchiveCopyrightCodeAndType(Connection conn, String[] arr, Long ossFileId) throws SQLException {
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

    private void updateSaleCopyrightCodeAndType(Connection conn, String[] arr, Long ossFileId) throws SQLException {
        String yearFolder = arr[1];
        String year = "";
        if(yearFolder.startsWith("2015")) {
            year = "2015";
        }else if(yearFolder.startsWith("2016")) {
            year = "2016";
        }else if(yearFolder.startsWith("2017")) {
            year = "2017";
        }else if(yearFolder.startsWith("2018")) {
            year = "2018";
        }

        String copyrightCodeField = null;
        if(year.equals("2015")
                || year.equals("2016")) {
            copyrightCodeField = arr[3];
        }else{
            copyrightCodeField = arr[2];
        }

        String[] fieldArrs = copyrightCodeField.split("-");
        String copyrightNo = "";
        if(!fieldArrs[0].startsWith("201")) {
            copyrightNo += year + "-";
            copyrightNo += fieldArrs[0] + "-" + fieldArrs[1];
        }else{
            copyrightNo = fieldArrs[0] + "-" + fieldArrs[1] + "-" + fieldArrs[2];
        }

        String sql = "update oss_files set c_copyright_code = ?, c_type = ? where id = ?";
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, copyrightNo);
            pstmt.setString(2, "6");
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

    private void importCopyrightFiles() throws SQLException, ClassNotFoundException {
        Connection conn = getConnection();
        String sql = "insert into t_copyright_file (`c_name`, `c_type`, `c_product_id`, `c_path`, `c_creator`, `c_createtime`)values(?,?,?,?,'1',now())";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        try {
            List<ProductInfo> products = loadProducts(conn);
            int c = 0;
            for(ProductInfo product : products) {
//                if(c > 9) {
//                    break;
//                }
                c++;
                System.out.println("第 " + c + " 部作品正在处理...");
                String copyrightCode = product.getCopyrightCode();
                String productName = product.getName();
                List<OssFile> files = loadOssFiles(conn, copyrightCode);
                for(OssFile file : files) {
                    if(belongToSiblings(conn, file, copyrightCode, product.getId())) {
                        continue;
                    }

                    if(belongToSelf(file, productName)){
                        uniqueCopyrightFileIds.add(file.getId());
                    }

                    pstmt.setString(1, file.getFileName());
                    pstmt.setString(2, file.getType());
                    pstmt.setLong(3, product.getId());
                    pstmt.setString(4, file.getOssPath());

                    pstmt.executeUpdate();
                }
            }
            commitConnection(conn);
            System.out.println("共 " + c + " 部作品，完成！");
        }catch (Exception e) {
            e.printStackTrace();;
            rollbackConnection(conn);
        }finally {
            closeConnection(conn);
        }

    }

    private boolean belongToSiblings(Connection conn, OssFile file, String copyrightCode, long id) {
        if(uniqueCopyrightFileIds.contains(file.getId())) {
            return true;
        }

        String localPath = file.getLocalPath();
        localPath = localPath.replaceAll("\\\\", "/");
        localPath = localPath.substring(15);
        String[] arr = localPath.split("/");

        String maskFileName = "";
        int arrLength = arr.length;
        for(int i=3; i<arrLength; i++) {
            maskFileName = maskFileName + arr[i];
        }

        List<ProductInfo> products = loadProductsByCopyrightCode(conn, copyrightCode);
        for(ProductInfo product : products) {
            if(product.getId() == id) {
                continue;
            }

            String productName = product.getName();
            if(maskFileName.indexOf(productName) > -1) {
                return true;
            }
        }

        return false;
    }

    private boolean belongToSelf(OssFile file, String productName) {
        String localPath = file.getLocalPath();
        localPath = localPath.replaceAll("\\\\", "/");
        localPath = localPath.substring(15);
        String[] arr = localPath.split("/");

        String maskFileName = "";
        int arrLength = arr.length;
        for(int i=3; i<arrLength; i++) {
            maskFileName = maskFileName + arr[i];
        }

        if(maskFileName.indexOf(productName) > -1) {
            return true;
        }

        return false;
    }

    private List<OssFile> loadOssFiles(Connection conn, String copyrightCode) {
        String sql = "select id, c_local_path, c_file_name, c_oss_path, c_type from oss_files where c_copyright_code = '" + copyrightCode + "'";
        List<OssFile> files = new ArrayList<OssFile>();

        try{
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()) {
                OssFile file = new OssFile();

                Long id = rs.getLong("id");
                file.setId(id);

                String localPath = rs.getString("c_local_path");
                file.setLocalPath(localPath);

                file.setFileName(rs.getString("c_file_name"));
                file.setOssPath(rs.getString("c_oss_path"));
                file.setType(rs.getString("c_type"));

                files.add(file);
            }

            stmt.close();
            return files;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<ProductInfo> loadProducts(Connection conn) {
        String sql = "SELECT p.c_id, p.c_name, c.c_code " +
                "FROM t_copyright c, t_copyright_product cp, t_product_info p " +
                "WHERE c.c_id = cp.`c_copyright_id` " +
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

                product.setCopyrightCode(rs.getString("c_code"));

                products.add(product);
            }

            stmt.close();
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private List<ProductInfo> loadProductsByCopyrightCode(Connection conn, String copyrightCode) {
        String sql = "SELECT p.c_id, p.c_name " +
                "FROM t_copyright c, t_copyright_product cp, t_product_info p " +
                "WHERE c.c_code = '" + copyrightCode + "' " +
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

            stmt.close();
            return products;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
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

    public static void main(String[] args) throws Exception {
        OssFileImportor importor = new OssFileImportor();
//        importor.preHandleDatas();
        importor.importCopyrightFiles();
    }

    private class OssFile {
        private Long id;
        private String ossPath;
        private String type;
        private String fileName;
        private String localPath;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getOssPath() {
            return ossPath;
        }

        public void setOssPath(String ossPath) {
            this.ossPath = ossPath;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getLocalPath() {
            return localPath;
        }

        public void setLocalPath(String localPath) {
            this.localPath = localPath;
        }
    }
}
