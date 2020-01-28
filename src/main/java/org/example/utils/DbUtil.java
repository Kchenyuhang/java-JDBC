package org.example.utils;

import java.io.IOException;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;

/**
 * @Author yhChen
 * @Description 工具类
 * @Date 2020/1/28
 */
public class DbUtil {
    static Properties pros;

    //只需要加载一次，所以写成静态代码块，执行本类时加载
    static {
        pros = new Properties();
        try {
            pros.load(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResourceAsStream("db.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn() {
        Connection conn = null;
        try {
            Class.forName(pros.getProperty("mysqlDriver"));
            conn = DriverManager.getConnection(pros.getProperty("mysqlUrl"), pros.getProperty("mysqlUser"), pros.getProperty("mysqlPassword"));
        } catch (ClassNotFoundException | SQLException e) {
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

    public static void close(Statement stmt) {
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(PreparedStatement pst) {
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void close(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void commit(Connection conn) {
        if (conn != null) {
            try {
                conn.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void rollback(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setAutoCommit(Connection conn, boolean autoCommit) {
        if (conn != null) {
            try {
                conn.setAutoCommit(autoCommit);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        if (DbUtil.getConn() != null) {
            System.out.println("连接成功！");
        }
    }
}
