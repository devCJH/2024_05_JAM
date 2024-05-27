package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

import com.koreaIT.JAM.dto.Article;
import com.koreaIT.JAM.service.ArticleService;
import com.koreaIT.JAM.session.Session;
import com.koreaIT.JAM.util.Util;

public class ArticleController {

	private ArticleService articleService;
	private Scanner sc;
	
	public ArticleController(Connection connection, Scanner sc) {
		this.articleService = new ArticleService(connection);
		this.sc = sc;
	}

	public void doWrite() {
		if (Session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		
		System.out.println("== 게시물 작성 ==");
		
		System.out.printf("제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("내용 : ");
		String body = sc.nextLine().trim();
		
		int id = articleService.doWrite(Session.getLoginedMemberId(), title, body);
		
		System.out.printf("%d번 게시물이 작성되었습니다\n", id);
	}

	public void showList() {
    	List<Article> articles = articleService.showList();
		
		if (articles.size() == 0) {
			System.out.println("게시물이 존재하지 않습니다");
			return;
		}
		
    	System.out.println("== 게시물 목록 ==");
		System.out.println("번호	|	제목	|	작성자	|		작성일		");
		
		for (Article article : articles) {
			System.out.printf("%d	|	%s	|	%s	|	%s	\n", article.id, article.title, article.writerName, Util.datetimeFormat(article.regDate));
		}
	}

	public void showDetail(String cmd) {
		int id = articleService.getCmdNum(cmd);
		
		if (id == -1) {
			System.out.println("게시물 번호를 잘못 입력하셨습니다");
			return;
		}
		
		Article article = articleService.showDetail(id);
		
		if (article == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}
		
		System.out.printf("== %d번 게시물 상세보기 ==\n", id);
		
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성일 : %s\n", Util.datetimeFormat(article.regDate));
		System.out.printf("수정일 : %s\n", Util.datetimeFormat(article.updateDate));
		System.out.printf("작성자 : %s\n", article.writerName);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);
	}

	public void doModify(String cmd) {
		if (Session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		
		int id = articleService.getCmdNum(cmd);
		
		if (id == -1) {
			System.out.println("게시물 번호를 잘못 입력하셨습니다");
			return;
		}

		Article article = articleService.getArticleById(id);
		
		if (article == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}
		
		if (article.memberId != Session.getLoginedMemberId()) {
			System.out.println("해당 게시물에 대한 권한이 없습니다");
			return;
		}
		
		System.out.println("== 게시물 수정 ==");
		
		System.out.printf("수정할 제목 : ");
		String title = sc.nextLine().trim();
		System.out.printf("수정할 내용 : ");
		String body = sc.nextLine().trim();
		
		articleService.doModify(id, title, body);

        System.out.printf("%d번 게시물이 수정되었습니다\n", id);
	}

	public void doDelete(String cmd) {
		if (Session.isLogined() == false) {
			System.out.println("로그인 후 이용해주세요");
			return;
		}
		
		int id = articleService.getCmdNum(cmd);
		
		if (id == -1) {
			System.out.println("게시물 번호를 잘못 입력하셨습니다");
			return;
		}

		Article article = articleService.getArticleById(id);
		
		if (article == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}
		
		if (article.memberId != Session.getLoginedMemberId()) {
			System.out.println("해당 게시물에 대한 권한이 없습니다");
			return;
		}
		
		System.out.println("== 게시물 삭제 ==");
		
		articleService.doDelete(id);

        System.out.printf("%d번 게시물이 삭제되었습니다\n", id);
	}

}
