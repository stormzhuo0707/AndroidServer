package com.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUtil {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/androiddb1?useUnicode=true&characterEncoding=utf-8";
    private static final String USER = "root";
    private static final String PASSWORD = "jin1687062650";
    private static Connection ct;
    private static PreparedStatement ps;
    private static ResultSet rs;

    static {
        // 1.加载驱动,只需要加载一次,所以放到静态代码块中
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 描述:封装一个方法可以获得连接,目的可以在其他地方之接调用
     */
    public static Connection getConnection() {

        try {

            ct = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ct;
    }

    /**
     * 描述:封装一个方法可以完成查询操作
     *
     * @param sql 要查询的sql语句
     * @param obj 占位符的具体内容
     * @return ResultSet 将查询到的结果返回
     */
    public static ResultSet executeQuery(String sql, Object... obj) {
        // 1.得到连接
        ct = getConnection();
        // 2.创键发送对象
        try {
            ps = ct.prepareStatement(sql);
            // 处理占位符问题
            if (obj != null) {

                for (int i = 0; i < obj.length; i++) {
                    ps.setObject(i + 1, obj[i]);
                }
            }
            rs = ps.executeQuery();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 描述:封装一个方法可以完成DDL,DML操作
     *
     * @param sql 要操作的sql语句
     * @param obj 占位符
     * @return
     */
    public static int executeUpdate(String sql, Object... obj) {
        // 1.得到连接
        ct = getConnection();
        // 2.创键发送对象
        try {
            ps = ct.prepareStatement(sql);
            // 处理占位符问题
            if (obj != null) {
                for (int i = 0; i < obj.length; i++) {
                    ps.setObject(i + 1, obj[i]);
                }
            }
            int in = ps.executeUpdate();
            close(ct, ps, null);
            return in;
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 描述:封装一个关闭资源的方法
     *
     * @param ct 连接对象
     * @param ps 发送sql语句对象
     * @param rs 返回值对象
     */
    public static void close(Connection ct, PreparedStatement ps, ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (ct != null) {
            try {
                ct.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }

    // 给外部一个访问ct,和ps的方法
    public static Connection getCt() {
        return ct;
    }

    public static PreparedStatement getPs() {
        return ps;
    }

}
