package com.kh.spring.common.model.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class PageInfo {

	private int listCount;		// 총 게시글 수
	private int currentPage;	// 현재 페이지 수
	private int pageLimit;		// 페이지 하단에 보여질 페이징바의 최대 갯수
	private int boardLimit;		// 한 페이지에 보여줄 게시글 수
	
	// 위의 4개 데이터를 기준으로 아래 데이터를 계산할 예정
	private int maxPage;		// 가장 마지막 페이지 수 (총 페이지 수)
	private int startPage;		// 페이징바의 시작 수
	private int endPage;		// 페이징바의 끝 수
	
}
