package com.koreaIT.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class App {
	
	private final String URL = "jdbc:mysql://localhost:3306/jdbc_article_manager?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull";
    private final String USER = "root";
    private final String PASSWORD = "";
	
	public void run() {
		
		System.out.println("== 프로그램 시작 ==");
		
		Scanner sc = new Scanner(System.in);
		
		Connection connection = null;
		
		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			
			while (true) {
				
				System.out.printf("명령어) ");
				String cmd = sc.nextLine().trim();
				
				if (cmd.equals("exit")) {
					break;
				}
				
				if (cmd.equals("article write")) {
					System.out.println("== 게시물 작성 ==");
					
					System.out.printf("제목 : ");
					String title = sc.nextLine();
					System.out.printf("내용 : ");
					String body = sc.nextLine();
					
		        	SecSql sql = new SecSql();
		        	sql.append("INSERT INTO article");
		        	sql.append("SET regDate = NOW()");
		        	sql.append(", updateDate = NOW()");
		        	sql.append(", title = ?", title);
		        	sql.append(", `body` = ?", body);
		        	
		        	int id = DBUtil.insert(connection, sql);

					System.out.printf("%d번 게시물이 작성되었습니다\n", id);
					
				} else if (cmd.equals("article list")) {
					System.out.println("== 게시물 목록 ==");
					
			    	ResultSet rs = null;
			        
			    	List<Article> articles = new ArrayList<>();
			    	
			        try {
			            String sql = "SELECT * FROM article";
			            sql += " ORDER BY id DESC;";
			            
			            pstmt = connection.prepareStatement(sql);
			            rs = pstmt.executeQuery();
			            
			            while (rs.next()) {
			            	int id = rs.getInt("id");
			            	String regDate = rs.getString("regDate");
			            	String updateDate = rs.getString("updateDate");
			            	String title = rs.getString("title");
			            	String body = rs.getString("body");
			            	
			            	Article article = new Article(id, regDate, updateDate, title, body);
			            	articles.add(article);
			            }
			            
			        } catch (SQLException e) {
			            e.printStackTrace();
			        } finally {
			        	if (rs != null) {
			        		try {
			        			rs.close();
			        		} catch (SQLException e) {
			        			e.printStackTrace();
			        		}
			        	}
			        }
					
					if (articles.size() == 0) {
						System.out.println("게시물이 존재하지 않습니다");
						continue;
					}
					
					System.out.println("번호	|	제목");
					for (Article article : articles) {
						System.out.printf("%d	|	%s\n", article.id, article.title);
					}
					
				} else if (cmd.startsWith("article modify ")) {
					System.out.println("== 게시물 수정 ==");
					
					int id = Integer.parseInt(cmd.split(" ")[2]);
					
					System.out.printf("수정할 제목 : ");
					String title = sc.nextLine();
					System.out.printf("수정할 내용 : ");
					String body = sc.nextLine();
					
			        try {
			            String sql = "UPDATE article";
			            sql += " SET updateDate = NOW()";
			            sql += ", title = '" + title + "'";
			            sql += ", `body` = '" + body + "'";
			            sql += " WHERE id = " + id + ";";
			            
			            pstmt = connection.prepareStatement(sql);
			            pstmt.executeUpdate();

			        } catch (SQLException e) {
			            e.printStackTrace();
			        }
			        
			        System.out.printf("%d번 게시물이 수정되었습니다\n", id);
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
		sc.close();
		
		System.out.println("== 프로그램 끝 ==");
	}
}
