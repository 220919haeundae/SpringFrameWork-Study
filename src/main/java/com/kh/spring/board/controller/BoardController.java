package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
	
	@RequestMapping("/enrollForm")
	public String boardEnrollForm() {
		return "board/boardEnrollForm";
	}
	
	
	@RequestMapping("/insert")
	public String insertBoard(Board b, MultipartFile upfile, HttpSession session, Model model) {
		
		//System.out.println(b);
		//System.out.println(upfile);
		
		// 첨부파일이 있는 경우 -> 전달된 파일을 서버에 저장 + Board 객체에 파일 정보를 저장
		if(!upfile.getOriginalFilename().isEmpty()) {	// upfile.getOriginalname.equals("");
			// 파일명을 변경하여 저장
			// 변경 파일명 => yyyyMMddHHmmss + xxxxx(랜덤값) + .확장자
			String currTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			// * 5자리 랜덤값 (10000 ~ 99999)
			int random = (int)(Math.random() * (99999 - 10000+1)) + 10000;
			// * 확장자 (.txt, .java, .png, ...)
			String orgName = upfile.getOriginalFilename();	// "test.png", "test.2024.png"
			String ext = orgName.substring(orgName.lastIndexOf("."));
			
			String chgName = currTime + random + ext;
			
			
			// 업로드할 파일을 저장할 위치의 물리적인 경로 조회
			String path = session.getServletContext().getRealPath("/resources/uploadFiles/");
			
			try {
				upfile.transferTo(new File(path + chgName));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			b.setOriginName(orgName);
			b.setChangeName("resources/uploadFiles/" + chgName);
			
		}
		
		//DB에 게시글 정보 저장(첨부파일 유/무 상관없이 처리)
		int result = bService.insertBoard(b);
		
		if(result > 0) {	// 게시글 등록 성공
			session.setAttribute("alertMsg", "게시물 등록 성공");
			
			return "redirect: list";
		} else {  // 게시글 등록 실패
			model.addAttribute("errorMsg", "게시글 등록 실패");
			return "common/errorPage";
		}
		
		
		
	}
	
	@RequestMapping("detail")
	public String detailPage(int boardNo, Model model) {
		Board b = bService.selectBoard(boardNo);
		
		model.addAttribute(b);
		
		return "board/boardDetail";
	}
	
	
}
