package com.springbook.biz.board.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springbook.biz.board.BoardVO;
import com.springbook.biz.board.CommentVO;

//DAO(Data Access Object)
@Repository
public class BoardDAOMybatis {
	@Autowired
	private SqlSessionTemplate mybatis;

	// CRUD ����� �޼ҵ� ����
	// �� ��� ��ȸ
	public List<BoardVO> getBoardList(BoardVO vo) {
		System.out.println("===>  Mybatis��  getBoardList() ��� ó��");
		return mybatis.selectList("BoardDAO.getBoardList", vo);
	}

	// �� �� ��ȸ
	public BoardVO getBoard(BoardVO vo) {
		System.out.println("===>  Mybatis��  getBoard() ��� ó��");
		
		mybatis.update("BoardDAO.updateReadCount", vo); //��ȸ�� ������Ʈ
		return (BoardVO) mybatis.selectOne("BoardDAO.getBoard", vo);
	}

	// �� ���
	public void insertBoard(BoardVO vo) {
		System.out.println("===> Mybatis��  insertBoard() ��� ó��");
		mybatis.insert("BoardDAO.insertBoard", vo);
	}

	// �� ����
	public void updateBoard(BoardVO vo) {
		System.out.println("===>  Mybatis�� updateBoard() ��� ó��");
		mybatis.update("BoardDAO.updateBoard", vo);
	}

	// �� ����
	public void deleteBoard(BoardVO vo) {
		System.out.println("===>  Mybatis�� deleteBoard() ��� ó��");
		mybatis.delete("BoardDAO.deleteBoard", vo);
	}

	//��� ���
	public void insertComment(CommentVO cvo) {
		System.out.println("===> Mybatis��  insertComment() ��� ó��");
		
		mybatis.insert("BoardDAO.insertComment", cvo); //��� ���
		mybatis.update("BoardDAO.updateCommentCount", cvo);  //��� ���� ������Ʈ
	}

	//��� ����Ʈ
	public List<CommentVO> getComment(BoardVO vo) {
		System.out.println("===>  Mybatis��  getComment() ��� ó��");
		return mybatis.selectList("BoardDAO.getComment", vo);
	}
	
	//���ƿ� ���� +1
	public void updateLike(int bno) {
		System.out.println("===>  Mybatis�� updateLike() ��� ó��");
		mybatis.update("BoardDAO.updateLike", bno);
	}

	//���ƿ� ���� count
	public int selectLikeCount(int bno) {
		System.out.println("===>  Mybatis�� selectLikeCount() ��� ó��");
		
		return mybatis.selectOne("BoardDAO.selectLikeCount", bno);
	}
	
	
	
}
