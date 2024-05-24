package com.koreaIT.JAM.service;

import java.sql.Connection;

import com.koreaIT.JAM.dao.MemberDao;

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

}
