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

	public int doWrite(int loginedMemberId, String title, String body) {
		
		SecSql sql = new SecSql();
    	sql.append("INSERT INTO article");
    	sql.append("SET regDate = NOW()");
    	sql.append(", updateDate = NOW()");
    	sql.append(", memberId = ?", loginedMemberId);
    	sql.append(", title = ?", title);
    	sql.append(", `body` = ?", body);
		
		return DBUtil.insert(connection, sql);
	}

	public List<Map<String, Object>> showList(String searchKeyword) {
		
    	SecSql sql = new SecSql();
    	sql.append("SELECT a.*, m.loginId AS `writerName`");
    	sql.append("FROM article AS a");
    	sql.append("INNER JOIN `member` AS m");
    	sql.append("ON a.memberId = m.id");
    	if (searchKeyword.length() > 0) {
    		sql.append("WHERE a.title LIKE CONCAT('%', ?, '%')", searchKeyword);
    	}
    	sql.append("ORDER BY a.id DESC");
    	
		return DBUtil.selectRows(connection, sql);
	}
	
	public Map<String, Object> showDetail(int id) {
		
		SecSql sql = new SecSql();
		sql.append("SELECT a.*, m.loginId AS `writerName`");
    	sql.append("FROM article AS a");
    	sql.append("INNER JOIN `member` AS m");
    	sql.append("ON a.memberId = m.id");
		sql.append("WHERE a.id = ?", id);
		
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

	public Map<String, Object> getArticleById(int id) {
		
		SecSql sql = new SecSql();
		sql.append("SELECT * FROM article");
		sql.append("WHERE id = ?", id);
		
		return DBUtil.selectRow(connection, sql);
	}

	public int increaseVCnt(int id) {
		
		SecSql sql = new SecSql();
		sql.append("UPDATE article");
		sql.append("SET vCnt = vCnt + 1");
		sql.append("WHERE id = ?", id);
		
		return DBUtil.update(connection, sql);
	}
}





