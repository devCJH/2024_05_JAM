package com.koreaIT.JAM.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class ArticleDao {

	private Connection connection;
	
	public ArticleDao(Connection connection) {
		this.connection = connection;
	}

	public int doWrite(String title, String body) {
		
		SecSql sql = new SecSql();
    	sql.append("INSERT INTO article");
    	sql.append("SET regDate = NOW()");
    	sql.append(", updateDate = NOW()");
    	sql.append(", title = ?", title);
    	sql.append(", `body` = ?", body);
		
		return DBUtil.insert(connection, sql);
	}

	public List<Map<String, Object>> showList() {
		
    	SecSql sql = new SecSql();
    	sql.append("SELECT * FROM article");
    	sql.append("ORDER BY id DESC");
    	
		return DBUtil.selectRows(connection, sql);
	}

	public Map<String, Object> showDetail(int id) {
		
		SecSql sql = new SecSql();
		sql.append("SELECT *");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		
		return DBUtil.selectRow(connection, sql);
	}

	public int getArticleCount(int id) {
		
		SecSql sql = new SecSql();
		sql.append("SELECT COUNT(id)");
		sql.append("FROM article");
		sql.append("WHERE id = ?", id);
		
		return DBUtil.selectRowIntValue(connection, sql);
	}

	public void doModify(int id, String title, String body) {
		
		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET updateDate = NOW()");
		sql.append(", title = ?", title);
		sql.append(", `body` = ?", body);
		sql.append("WHERE id = ?", id);
		
		DBUtil.update(connection, sql);
	}

	public void doDelete(int id) {
		SecSql sql = new SecSql();
		sql.append("DELETE FROM article");
		sql.append("WHERE id = ?", id);
		
		DBUtil.delete(connection, sql);
	}

}
