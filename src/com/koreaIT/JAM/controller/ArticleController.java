package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class ArticleController {

	private Connection connection;
	private Scanner sc;
	
	public ArticleController(Connection connection, Scanner sc) {
		this.connection = connection;
		this.sc = sc;
	}

	public void doWrite() {
		System.out.println("== 게시물 작성 ==");
		
		System.out.printf("제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("내용 : ");
		String body = sc.nextLine().trim();
		
    	SecSql sql = new SecSql();
    	sql.append("INSERT INTO article");
    	sql.append("SET regDate = NOW()");
    	sql.append(", updateDate = NOW()");
    	sql.append(", title = ?", title);
    	sql.append(", `body` = ?", body);
    	
    	int id = DBUtil.insert(connection, sql);

		System.out.printf("%d번 게시물이 작성되었습니다\n", id);
	}

	public void showList() {
    	List<Article> articles = new ArrayList<>();
    	
    	SecSql sql = new SecSql();
    	sql.append("SELECT * FROM article");
    	sql.append("ORDER BY id DESC");
    	
    	List<Map<String, Object>> articleListMap = DBUtil.selectRows(connection, sql);
    	
    	for (Map<String, Object> articleMap : articleListMap) {
    		articles.add(new Article(articleMap));
    	}
		
		if (articles.size() == 0) {
			System.out.println("게시물이 존재하지 않습니다");
			return;
		}
		
    	System.out.println("== 게시물 목록 ==");
		System.out.println("번호	|	제목	|		작성일		");
		
		for (Article article : articles) {
			System.out.printf("%d	|	%s	|	%s	\n", article.id, article.title, article.regDate);
		}
	}

	public void showDetail(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);
		
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		
		Map<String, Object> articleMap = DBUtil.selectRow(connection, sql);

		if (articleMap.isEmpty()) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}
		
		Article article = new Article(articleMap);
		
		System.out.printf("== %d번 게시물 상세보기 ==\n", id);
		
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성일 : %s\n", article.regDate);
		System.out.printf("수정일 : %s\n", article.updateDate);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);
	}

	public void doModify(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);

		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(id)");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

		int articleCount = DBUtil.selectRowIntValue(connection, sql);
		
		if (articleCount == 0) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}
		
		System.out.println("== 게시물 수정 ==");
		
		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("수정할 내용 : ");
		String body = sc.nextLine().trim();
		
		sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append("WHERE id = ?", id);
		
		DBUtil.update(connection, sql);

        System.out.printf("%d번 게시물이 수정되었습니다\n", id);
	}

	public void doDelete(String cmd) {
		int id = Integer.parseInt(cmd.split(" ")[2]);

		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(id)");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);

		int articleCount = DBUtil.selectRowIntValue(connection, sql);
		
		if (articleCount == 0) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}
		
		System.out.println("== 게시물 삭제 ==");
		
		sql = new SecSql();
		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", id);
		
		DBUtil.delete(connection, sql);

        System.out.printf("%d번 게시물이 삭제되었습니다\n", id);
	}

}
