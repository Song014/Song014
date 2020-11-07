package com.springbook.biz.board.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springbook.biz.board.BoardService;
import com.springbook.biz.board.BoardVO;
import com.springbook.biz.board.CommentVO;

@Service("boardService")
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDAOMybatis boardDAO;

	public List<BoardVO> getBoardList(BoardVO vo) {
		return boardDAO.getBoardList(vo);
	}
	
	public BoardVO getBoard(BoardVO vo) {
		return boardDAO.getBoard(vo);
		
	}
	
	public void insertBoard(BoardVO vo) {
		boardDAO.insertBoard(vo); // 100번 글 등록 성공
		
	}

	public void updateBoard(BoardVO vo) {
		boardDAO.updateBoard(vo);
	}

	public void deleteBoard(BoardVO vo) {
		boardDAO.deleteBoard(vo);
	}

	//댓글 등록
	public void insertComment(CommentVO cvo) {
		boardDAO.insertComment(cvo); 
		
	}

	//댓글 리스트
	public List<CommentVO> getComment(BoardVO vo) {
		return boardDAO.getComment(vo);
	}

	//좋아요 증가
	public void updateLike(int bno) {
		boardDAO.updateLike(bno);
		
	}

	//좋아요 갯수 가져오기
	public int selectLikeCount(int bno) {
		return boardDAO.selectLikeCount(bno);
	}

}