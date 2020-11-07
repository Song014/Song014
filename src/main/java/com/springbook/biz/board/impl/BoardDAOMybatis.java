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

	// CRUD 기능의 메소드 구현
	// 글 목록 조회
	public List<BoardVO> getBoardList(BoardVO vo) {
		System.out.println("===>  Mybatis로  getBoardList() 기능 처리");
		return mybatis.selectList("BoardDAO.getBoardList", vo);
	}

	// 글 상세 조회
	public BoardVO getBoard(BoardVO vo) {
		System.out.println("===>  Mybatis로  getBoard() 기능 처리");
		
		mybatis.update("BoardDAO.updateReadCount", vo); //조회수 업데이트
		return (BoardVO) mybatis.selectOne("BoardDAO.getBoard", vo);
	}

	// 글 등록
	public void insertBoard(BoardVO vo) {
		System.out.println("===> Mybatis로  insertBoard() 기능 처리");
		mybatis.insert("BoardDAO.insertBoard", vo);
	}

	// 글 수정
	public void updateBoard(BoardVO vo) {
		System.out.println("===>  Mybatis로 updateBoard() 기능 처리");
		mybatis.update("BoardDAO.updateBoard", vo);
	}

	// 글 삭제
	public void deleteBoard(BoardVO vo) {
		System.out.println("===>  Mybatis로 deleteBoard() 기능 처리");
		mybatis.delete("BoardDAO.deleteBoard", vo);
	}

	//댓글 등록
	public void insertComment(CommentVO cvo) {
		System.out.println("===> Mybatis로  insertComment() 기능 처리");
		
		mybatis.insert("BoardDAO.insertComment", cvo); //댓글 등록
		mybatis.update("BoardDAO.updateCommentCount", cvo);  //댓글 갯수 업데이트
	}

	//댓글 리스트
	public List<CommentVO> getComment(BoardVO vo) {
		System.out.println("===>  Mybatis로  getComment() 기능 처리");
		return mybatis.selectList("BoardDAO.getComment", vo);
	}
	
	//좋아요 갯수 +1
	public void updateLike(int bno) {
		System.out.println("===>  Mybatis로 updateLike() 기능 처리");
		mybatis.update("BoardDAO.updateLike", bno);
	}

	//좋아요 갯수 count
	public int selectLikeCount(int bno) {
		System.out.println("===>  Mybatis로 selectLikeCount() 기능 처리");
		
		return mybatis.selectOne("BoardDAO.selectLikeCount", bno);
	}
	
	
	
}
