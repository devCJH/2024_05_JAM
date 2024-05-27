package com.koreaIT.JAM.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.koreaIT.JAM.dao.ArticleDao;
import com.koreaIT.JAM.dto.Article;

public class ArticleService {

	private ArticleDao articleDao;
	
	public ArticleService(Connection connection) {
		this.articleDao = new ArticleDao(connection);
	}

	public int doWrite(int loginedMemberId, String title, String body) {
		return articleDao.doWrite(loginedMemberId, title, body);
	}

	public List<Article> showList() {
		List<Map<String, Object>> articleListMap = articleDao.showList();
		
		List<Article> articles = new ArrayList<>();
		
		for (Map<String, Object> articleMap : articleListMap) {
    		articles.add(new Article(articleMap));
    	}
		return articles;
	}
	
	public Article showDetail(int id) {
		Map<String, Object> articleMap = articleDao.showDetail(id);
		
		if (articleMap.isEmpty()) {
			return null;
		}
		
		return new Article(articleMap);
	}
	
	public int getArticleCount(int id) {
		return articleDao.getArticleCount(id);
	}

	public void doModify(int id, String title, String body) {
		articleDao.doModify(id, title, body);
	}

	public void doDelete(int id) {
		articleDao.doDelete(id);
	}

	public int getCmdNum(String cmd) {
		int id = 0;
		
		try {
			id = Integer.parseInt(cmd.split(" ")[2]);
		} catch (Exception e) {
			return -1;
		}
		
		return id;
	}

	public Article getArticleById(int id) {
		
		Map<String, Object> articleMap = articleDao.getArticleById(id);
		
		if (articleMap.isEmpty()) {
			return null;
		}
		
		return new Article(articleMap);
	}

	public int increaseVCnt(int id) {
		return articleDao.increaseVCnt(id);
	}
}
