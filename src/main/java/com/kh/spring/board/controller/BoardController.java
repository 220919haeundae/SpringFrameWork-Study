package com.kh.spring.board.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.service.BoardService;
import com.kh.spring.common.model.vo.PageInfo;
import com.kh.spring.common.template.Pagination;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	private final BoardService bService;
	
	@Autowired
	public BoardController(BoardService bService) {
		this.bService = bService;
	}
	
	// BoardService 객체를 주입

	/*
	 * 		/board/list 요청이 들어오는 경우
	 * 		=> 메뉴바에서 자유게시판 메뉴 클릭 /board/list 요청 (기본적으로 첫페이지 표시)
	 * 		=> 페이징바 클릭 /board/list?cpage=요청할페이지번호
	 */
	@RequestMapping("/list")
	public String boardList(@RequestParam(value="cpage", defaultValue="1") int currentPage,	
											Model model) {
		
		System.out.println("현재페이지 : " + currentPage);
		
		// 전체 게시글 수 조회
		int listCount = bService.selectListCount();
		PageInfo pi = Pagination.getPageInfo(listCount, currentPage, 5, 5);
		
		// 게시글 목록 조회
		ArrayList<Board> bList = bService.selectList(pi);
		
		model.addAttribute("list", bList);	// 게시글 목록
		model.addAttribute("pi", pi);		// 페이징 관련 데이터
		return "board/boardList";
	}
	
	
}
