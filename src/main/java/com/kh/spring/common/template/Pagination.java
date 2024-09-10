package com.kh.spring.common.template;

import com.kh.spring.common.model.vo.PageInfo;

public class Pagination {
	
	// ----------- 페이징 처리 ------------------
	public static PageInfo getPageInfo(int listCount
										, int currentPage
										, int pageLimit
										, int boardLimit) {
		
		int maxPage = (int)Math.ceil((double)listCount / boardLimit);  // 페이징바의 가장 마지막 페이지
		int startPage = ((currentPage-1)/pageLimit)*pageLimit+1;
		int endPage = startPage+pageLimit-1;
		endPage = endPage > maxPage ? maxPage : endPage;
		
		return new PageInfo(listCount
							, currentPage
							, pageLimit
							, boardLimit
							, maxPage
							, startPage
							, endPage);
	}

}
