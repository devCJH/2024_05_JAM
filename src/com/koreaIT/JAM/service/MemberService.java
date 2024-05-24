package com.koreaIT.JAM.service;

import java.sql.Connection;
import java.util.Map;

import com.koreaIT.JAM.dao.MemberDao;
import com.koreaIT.JAM.dto.Member;

public class MemberService {

	private MemberDao memberDao;
	
	public MemberService(Connection connection) {
		this.memberDao = new MemberDao(connection);
	}

	public boolean isLoginIdDup(String loginId) {
		return memberDao.isLoginIdDup(loginId);
	}

	public void doJoin(String loginId, String loginPw, String name) {
		memberDao.doJoin(loginId, loginPw, name);
	}

	public Member getMemberByLoginId(String loginId) {
		Map<String, Object> memberMap = memberDao.getMemberByLoginId(loginId);
		
		if (memberMap.isEmpty()) {
			return null;
		}
		return new Member(memberMap);
	}

}
