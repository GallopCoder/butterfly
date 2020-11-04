package com.ali.retail.prism;

import com.ali.retail.config.PrismConfig;
import org.apache.commons.lang3.StringUtils;

import java.sql.*;
import java.util.*;

public class MysqlUtil {

    private static final String QUERY_ID_MAPPING = "SELECT * FROM ps_mapping sub ORDER BY sub.id DESC";
    private static StringBuilder INSERT_SUB_PRISM_MAPPING = new StringBuilder("INSERT INTO ps_mapping (id_prism,id_subject) values ");

    /**
     * 用作程序启动时,获取前端专题关键词和专题id，注意配置mysql链接和表信息; 转向功能，不可复用
     * @return
     */
    public static Map<String, Set<String>> getV_SubjectByType(Connection conn, int type) {
        Map<String, Set<String>> prismWordSubjectIds = new HashMap<>();
        ResultSet rs = getQueryResult(conn, "SELECT * FROM vt_subject sub where type = " + type + "  ORDER BY sub.id DESC");
        try {
            while (rs.next()) {
                String keywords = rs.getString("keywords");
                if (StringUtils.isBlank(keywords)) continue;
                String id = rs.getString("id");
                Set<String> subjectIds = prismWordSubjectIds.computeIfAbsent(keywords, k -> new HashSet<>());
                subjectIds.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn);
        }
        return prismWordSubjectIds;
    }

    @Deprecated
    public static Map<String, Set<String>> getNecessaryResult(Connection conn, String sql) {
        Map<String, Set<String>> map = new HashMap<>();
        ResultSet rs = getQueryResult(conn, sql);
        try {
            while (rs.next()) {// TODO 由库结构决定具体处理细节
                String keywords = rs.getString("keywords");// 对于监测单元和预警，为关键词，对于专题来说是：文章关键词
                if (StringUtils.isBlank(keywords)) continue;
                String id = rs.getString("id");
                Set<String> subjectIds = map.computeIfAbsent(keywords, k -> new HashSet<>());
                subjectIds.add(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close(conn);
        }
        return map;
    }

    //获取查询结果
    private static ResultSet getQueryResult(Connection conn, String sql) {
        ResultSet rs = null;
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);
            rs = statement.executeQuery(sql);//query operation
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    //获取更新结果
    private static Integer getUpdateResult(Connection conn, String sql) {
        int rs = 0;
        PreparedStatement statement;
        try {
            statement = conn.prepareStatement(sql);
            rs = statement.executeUpdate(sql);//update operation
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
            String url = PrismConfig.JDBC_LOCAL_URL;
            String username = PrismConfig.JDBC_LOCAL_USER;
            String password = PrismConfig.JDBC_LOCAL_PASSWORD;
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

//    PrismConfig.JDBC_LOCAL_URL;
//    JDBC_LOCAL_USER;
//    String password = PrismConfig.JDBC_LOCAL_PASSWORD;
    public static Connection getConnection(String url, String username, String password) {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
//            url = PrismConfig.JDBC_LOCAL_URL;
//            username = PrismConfig.JDBC_LOCAL_USER;
//            password = PrismConfig.JDBC_LOCAL_PASSWORD;
            // 一个Connection代表一个数据库连接
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
