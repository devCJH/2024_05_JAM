package com.koreaIT.JAM;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import com.koreaIT.JAM.controller.ArticleController;
import com.koreaIT.JAM.controller.MemberController;

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
			
			ArticleController articleController = new ArticleController(connection, sc);
			MemberController memberController = new MemberController(connection, sc);
			
			while (true) {
				
				System.out.printf("명령어) ");
				String cmd = sc.nextLine().trim();
				
				if (cmd.equals("exit")) {
					break;
				}
				
				if (cmd.length() == 0) {
					System.out.println("명령어를 입력해주세요");
					continue;
				}
				
				if (cmd.equals("member join")) {
					memberController.doJoin();
				} else if (cmd.equals("member login")) {
					memberController.doLogin();
				} else if (cmd.equals("member logout")) {
					memberController.doLogout();
				} else if (cmd.equals("article write")) {
					articleController.doWrite();
				} else if (cmd.equals("article list")) {
					articleController.showList();
				} else if (cmd.startsWith("article detail ")) {
					articleController.showDetail(cmd);
				} else if (cmd.startsWith("article modify ")) {
					articleController.doModify(cmd);
				} else if (cmd.startsWith("article delete ")) {
					articleController.doDelete(cmd);
				} else {
					System.out.println("존재하지 않는 명령어 입니다");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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
