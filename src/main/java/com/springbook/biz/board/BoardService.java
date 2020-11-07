package com.springbook.biz.board;

import java.util.List;

public interface BoardService {
	// CRUD ����� �޼ҵ� ����
	// �� ��� ��ȸ
	List<BoardVO> getBoardList(BoardVO vo);
	
	// �� �� ��ȸ
	BoardVO getBoard(BoardVO vo);
	
	// �� ���
	void insertBoard(BoardVO vo);

	// �� ����
	void updateBoard(BoardVO vo);

	// �� ����
	void deleteBoard(BoardVO vo);
	
	//��� ���
	void insertComment(CommentVO cvo);
	
	//��� �б�
	List<CommentVO> getComment(BoardVO vo);

	//���ƿ� ����
	void updateLike(int bno);
	
	//���ƿ� ���� ��������
	int selectLikeCount(int bno);
}
