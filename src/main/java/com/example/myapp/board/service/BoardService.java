package com.example.myapp.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
//import com.spring.member.vo.MemberVO;

import com.example.myapp.board.vo.BoardVO;
import com.example.myapp.board.vo.Criteria;

public interface BoardService {
	 public List listBoard() throws DataAccessException;
	 public List noticeListBoard() throws DataAccessException;
	 public BoardVO selectArticle(int bno) throws DataAccessException;
	 public void createArticle(BoardVO boardVO) throws DataAccessException;
	 public int selectNewArticleBno() throws  DataAccessException;
	 public void bcountUp(int bno) throws DataAccessException;
	 public void updateArticle(BoardVO articleVO) throws DataAccessException;
	 public void deleteArticle(int bno) throws DataAccessException;
	 
	 // ����¡ ������ �Խ��� ���
	 public List<BoardVO> getListPaging(Criteria cri);
	 
	 // �Խ��� ��� �� ����
	 public int getTotal();
//	 public int addMember(MemberVO memberVO) throws DataAccessException;
//	 public int removeMember(String id) throws DataAccessException;

}
