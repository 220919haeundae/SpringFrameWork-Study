package com.kh.spring.board.service;

import java.util.ArrayList;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.spring.board.model.dao.BoardDao;
import com.kh.spring.board.model.vo.Board;
import com.kh.spring.board.model.vo.Reply;
import com.kh.spring.common.model.vo.PageInfo;

@Service
public class BoardServiceImpl implements BoardService {


	private final SqlSessionTemplate sqlSession;
	private final BoardDao boardDao;


	@Autowired
	public BoardServiceImpl(SqlSessionTemplate sqlSession, BoardDao boardDao) {
		this.sqlSession = sqlSession;
		this.boardDao = boardDao;
	}


	@Override
	public int selectListCount() {
		return boardDao.selectListCount(sqlSession);
	}


	@Override
	public ArrayList<Board> selectList(PageInfo pi) {
		return boardDao.selectList(sqlSession, pi);
	}


	@Override
	public int insertBoard(Board b) {
		

		return boardDao.insertBoard(sqlSession, b);
	}


	@Override
	public int increaseCount(int boardNo) {
		
		return boardDao.increaseCount(sqlSession, boardNo);
	}


	@Override
	public Board selectBoard(int boardNo) {
		
		return boardDao.selectBoard(sqlSession, boardNo);
	}


	@Override
	public int updateBoard(Board b) {
		return boardDao.updateboard(sqlSession, b);
	}


	@Override
	public int deleteBoard(int boardNo) {
		return boardDao.deleteBoard(sqlSession, boardNo);
	}


	@Override
	public ArrayList<Reply> selectReplyList(int boardNo) {
		return boardDao.selectListReply(sqlSession, boardNo);
	}


	@Override
	public int insertReply(Reply r) {
		
		return boardDao.insertReply(sqlSession, r);
	}


	@Override
	public ArrayList<Board> selectBoardTop5() {
		return boardDao.selectBoardTop5(sqlSession);
	}

}
