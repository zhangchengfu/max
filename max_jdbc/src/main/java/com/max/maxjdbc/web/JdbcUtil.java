package com.max.maxjdbc.web;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcUtil {
    public static void main(String[] args) {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/mysql";
        Connection conn;
        PreparedStatement psmt;
        int i = 0;
        String sql = "insert into students (Name,Sex,Age) values(?,?,?)";
        try {
            Class.forName(driver);// 加载驱动
            conn = DriverManager.getConnection(url, "123", "123");
            psmt = conn.prepareStatement(sql);
            psmt.setString(1, "");
            i = psmt.executeUpdate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (psmt != null) {
                psmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

    }
}
