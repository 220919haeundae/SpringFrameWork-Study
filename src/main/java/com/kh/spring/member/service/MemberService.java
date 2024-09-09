package com.kh.spring.member.service;

import com.kh.spring.member.model.vo.Member;

public interface MemberService {
	/* 로그인 서비스 */
	Member loginMember(Member m);
	
	/* 회원가입 서비스 */
	int insertMember(Member m);
		
	
	/* 아이디 중복체크 기능 */
	int idCheck(String id);
	
	/* 회원 정보 수정 */
	Member updateMember(Member m);
	
	/* 회원 탈퇴 */
	int deleteMember(Member m);
	
}
