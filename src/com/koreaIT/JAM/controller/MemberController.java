package com.koreaIT.JAM.controller;

import java.sql.Connection;
import java.util.Scanner;

import com.koreaIT.JAM.util.DBUtil;
import com.koreaIT.JAM.util.SecSql;

public class MemberController {

	private Connection connection;
	private Scanner sc;
	
	public MemberController(Connection connection, Scanner sc) {
		this.connection = connection;
		this.sc = sc;
	}

	public void doJoin() {
		String loginId = null;
		String loginPw = null;
		String loginPwChk = null;
		String name = null;
		
		System.out.println("== 회원 가입 ==");
		
		while (true) {
			System.out.printf("아이디 : ");
			loginId = sc.nextLine().trim();
			
			if (loginId.length() == 0) {
				System.out.println("아이디는 필수 입력 정보입니다");
				continue;
			}
			
			SecSql sql = new SecSql();
			sql.append("SELECT COUNT(id) > 0");
			sql.append("FROM `member`");
			sql.append("WHERE loginId = ?", loginId);
			
			boolean isLoginIdDup = DBUtil.selectRowBooleanValue(connection, sql);
			
			if (isLoginIdDup) {
				System.out.printf("[ %s ] 은(는) 이미 사용중인 아이디입니다\n", loginId);
				continue;
			}
			
			System.out.printf("[ %s ] 은(는) 사용가능한 아이디입니다\n", loginId);
			break;
		}
		
		while (true) {
			System.out.printf("비밀번호 : ");
			loginPw = sc.nextLine().trim();
			
			if (loginPw.length() == 0) {
				System.out.println("비밀번호는 필수 입력 정보입니다");
				continue;
			}
			
			System.out.printf("비밀번호 확인 : ");
			loginPwChk = sc.nextLine().trim();
			
			if (loginPw.equals(loginPwChk) == false) {
				System.out.println("비밀번호가 일치하지 않습니다");
				continue;
			}
			break;
		}
		
		while (true) {
			System.out.printf("이름 : ");
			name = sc.nextLine().trim();
			
			if (name.length() == 0) {
				System.out.println("이름은 필수 입력 정보입니다");
				continue;
			}
			break;
		}
		
    	SecSql sql = new SecSql();
    	sql.append("INSERT INTO `member`");
    	sql.append("SET regDate = NOW()");
    	sql.append(", updateDate = NOW()");
    	sql.append(", loginId = ?", loginId);
    	sql.append(", loginPw = ?", loginPw);
    	sql.append(", `name` = ?", name);
    	
    	DBUtil.insert(connection, sql);

		System.out.printf("[ %s ] 님의 가입을 환영합니다~\n", loginId);
		
	}

}
