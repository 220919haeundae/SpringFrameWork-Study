package com.kh.spring.board.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
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
			
			b.setOriginName(upfile.getOriginalFilename());
			b.setChangeName("resources/uploadFiles/" + saveFile(upfile, session));
			
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
	
	@RequestMapping("/detail")
	public String detailBoard(int boardNo, Model model) {
		
		int result = bService.increaseCount(boardNo);
		
		if(result > 0) {
			// * 조회수 증가 성공 시
			//		1) 해당 게시글 정보를 조회
			Board b = bService.selectBoard(boardNo);
			ArrayList<Reply> rArr = bService.selectReplyList(boardNo);
			//		2) 조회된 정보를 request 영역에 저장
			model.addAttribute("b", b);
			model.addAttribute("rArr", rArr);
			//		3) 상세페이지로 응답
			return "board/boardDetail";
		} else {
			// * 조회수 증가 실패 시
			//		1) 에러메시지를 request 영역에 저장
			model.addAttribute("errorMsg", "게시물 조회에 실패했습니다.");
			//		2) 에러페이지 응답
			return "common/errorPage";
		}
		
		
	}
	
	@RequestMapping("/updateForm")
	public String updateForm(int boardNo, Model model) {
		// 게시글 번호(boardNo)에 해당하는 데이터 조회
		Board b = bService.selectBoard(boardNo);
		
		model.addAttribute("board", b);
		
		if(b != null) {
			return "board/boardUpdate";
		} else {
			model.addAttribute("errorMsg", "게시글을 수정할 수 없습니다.");
			return "common/errorPage";
		}
	}
	
	@RequestMapping("update")
	public String updateBoard(Board board, MultipartFile upfile, HttpSession session, Model model) {
		System.out.println(board);
		System.out.println(upfile);
		
		/*
		 * 새로 추가된 첨부파일이 있을 경우
		 * 		=> 기존에 첨부파일이 있다면, 이전 파일은 제거(삭제)
		 * 			새로 추가된 첨부파일은 서버에 저장
		 * 			첨부파일의 원본명, 저장경로(변경된 파일명)를 DB에 저장
		 */
		if(!upfile.getOriginalFilename().isEmpty()) {
			// 기존에 첨부파일이 있다면 기존 파일 제거
			if(board.getOriginName() != null) {
				new File(session.getServletContext().getRealPath(board.getChangeName())).delete();
			}
			
			String chgName = saveFile(upfile, session);
			
			//DB에 저장하기 위해 Board객체에 새로 추가된 파일 정보 저장
			board.setOriginName(upfile.getOriginalFilename());
			board.setChangeName("resources/uploadFiles/" + chgName);
		}
		
		//Board 객체에는 제목(boardTitle), 내용(boardContent) 필수적으로 값이 있을 것임
		/*
		 * 1) 기존 첨부파일 x, 새로운 첨부파일 x
		 * 		=> originName : null, chageName : null
		 * 
		 * 2) 기존 첨부파일 o, 새로운 첨부파일 x
		 * 		=> originName : 기존 첨부파일의 원본명, chageName : 기존 첨부파일의 변경된 파일명
		 * 
		 * 3) 기존 첨부파일 o, 새로운 첨부파일 o
		 * 		=> originName : 새로운 첨부파일의 원본명, changeName : 새로운 첨부파일의 변경된 파일명
		 * 
		 * 4) 기존 첨부파일 x, 새로운 첨부파일 o
		 * 		=> originName : 새로운 첨부파일의 원본명, changeName : 새로운 첨부파일의 변경된 파일명
		 */
		
		int result = bService.updateBoard(board);
		
		
		if(result > 0) {
			// 수정 성공 시 해당 게시글의 상세페이지로 응답
			model.addAttribute("board", board);
			session.setAttribute("alertMsg", "게시물 수정 성공");
			return "redirect:/board/detail?boardNo="+board.getBoardNo();
		} else {
			// 수정 실패 시 오류메시지와 함께 오류페이지로 응답
			model.addAttribute("errorMsg", "게시물 수정 실패");
			return "common/errorPage";
		}
		
	}
	
	@RequestMapping("/delete")
	public String deleteBoard(int boardNo, Model model, HttpSession session) {
		System.out.println(boardNo);
		
		int result = bService.deleteBoard(boardNo);
		
		if(result > 0) {
			// 게시글 삭제 성공 = > 게시글 목록 페이지 응답(/board/list url 재요청)			
			session.setAttribute("alertMsg", "게시물 삭제 성공했습니다.");
			return "redirect:/board/list"; // => redirect 시 리퀘스트가 전달되지 않음!
		} else {
			// 게시글 삭제 실패 = > 오류 페이지 응답
			model.addAttribute("errorMsg", "게시글 삭제 실패");
			return "common/errorpage";
		}
		
		
	}
	
	private String saveFile(MultipartFile upfile, HttpSession session) {
		String currTime = new SimpleDateFormat("yyyyMMddHHss").format(new Date());
		
		int random = (int)(Math.random() * 1000000 + 100000);
		
		String orgName = upfile.getOriginalFilename();
		
		String ext = orgName.substring(orgName.lastIndexOf("."));
		
		String chgName = currTime + random + ext;
		
		String path = session.getServletContext().getRealPath("resorces/uploadFiles/");
		
		try {
			upfile.transferTo(new File(path + chgName));
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return chgName;
	}
	
	@ResponseBody
	@RequestMapping(value="/rlist", produces="application/json; charset=utf-8")
	public String selectReplyList(int boardNo, HttpServletResponse response) {
		//어떤 게시글의 댓글 목록 조회 => ArrayList<Reply>
		ArrayList<Reply> list = bService.selectReplyList(boardNo);
		return new Gson().toJson(list);
		
	}
	
	@ResponseBody
	@RequestMapping("/rinsert")
	public String insertReply(Reply r) {
		//System.out.println(r);
		int result = bService.insertReply(r);
		return result > 0 ? "success" : "failed";
	}
	
	@ResponseBody
	@RequestMapping(value = "/top5", produces = "application/json; charset=utf-8")
	public String selectBoardTop5() {
		
		return new Gson().toJson(bService.selectBoardTop5());
	}
	
}
