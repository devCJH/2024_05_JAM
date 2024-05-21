package com.koreaIT.JAM.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JDBCInsertTest {
    private static final String URL = "jdbc:mysql://localhost:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static void main(String[] args) {
    	Connection connection = null;
    	PreparedStatement pstmt = null;
        
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            String sql = "INSERT INTO article";
            sql += " SET regDate = NOW()";
            sql += ", updateDate = NOW()";
            sql += ", title = '제목1'";
            sql += ", `body` = '내용1';";
            
            pstmt = connection.prepareStatement(sql);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                	pstmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
            	try {
            		connection.close();
            	} catch (SQLException e) {
            		e.printStackTrace();
            	}
            }
        }
    }
}
