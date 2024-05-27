package com.koreaIT.JAM.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class Article {
	public int id;
	public LocalDateTime regDate;
	public LocalDateTime updateDate;
	public int memberId;
	public String writerName;
	public String title;
	public String body;
	
	public Article(Map<String, Object> articleMap) {
		this.id = (int) articleMap.get("id");
		this.regDate = (LocalDateTime) articleMap.get("regDate");
		this.updateDate = (LocalDateTime) articleMap.get("updateDate");
		this.memberId = (int) articleMap.get("memberId");
		this.writerName = (String) articleMap.get("writerName");
		this.title = (String) articleMap.get("title");
		this.body = (String) articleMap.get("body");
	}

}
