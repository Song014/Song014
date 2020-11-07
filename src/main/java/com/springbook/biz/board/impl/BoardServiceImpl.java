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
		boardDAO.insertBoard(vo); // 100�� �� ��� ����
		
	}

	public void updateBoard(BoardVO vo) {
		boardDAO.updateBoard(vo);
	}

	public void deleteBoard(BoardVO vo) {
		boardDAO.deleteBoard(vo);
	}

	//��� ���
	public void insertComment(CommentVO cvo) {
		boardDAO.insertComment(cvo); 
		
	}

	//��� ����Ʈ
	public List<CommentVO> getComment(BoardVO vo) {
		return boardDAO.getComment(vo);
	}

	//���ƿ� ����
	public void updateLike(int bno) {
		boardDAO.updateLike(bno);
		
	}

	//���ƿ� ���� ��������
	public int selectLikeCount(int bno) {
		return boardDAO.selectLikeCount(bno);
	}

}