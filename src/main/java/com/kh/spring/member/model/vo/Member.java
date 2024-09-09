package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/*
 * * Lombok(롬복) => 라이브러리! pom.xml에 dependency 추가
 * 
 * 1) 라이브러리 다운 후 적용(Maven -> pom.xml)
 * 2) 다운로드된 jar파일을 찾아 설치 작업 진행 (작업할 IDE 선택 - 등록)
 * 3) IDE 재시작
 * 
 */

@NoArgsConstructor			// 기본생성자
@AllArgsConstructor			// 모든 필드를 매개변수로 가지는 생성자
@Getter
@Setter
@ToString
public class Member {
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String gender;
	private String age;
	private String phone;
	private String address;
	private Date enrollDate;
	private Date modifyDate;
	private String status;
}
