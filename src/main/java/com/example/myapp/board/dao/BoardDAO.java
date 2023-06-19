package com.example.myapp.board.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.example.myapp.board.vo.BoardVO;
import com.example.myapp.board.vo.Criteria;

//import com.spring.member.vo.MemberVO;


public interface BoardDAO {
	 public List selectAllBoardList() throws DataAccessException;
	 public List selectAllNoticeList() throws DataAccessException;
	 public BoardVO selectArticle(int bno) throws DataAccessException;
	 public void createArticle(BoardVO boardVO) throws DataAccessException;
	 public void bcountUp(int bno) throws DataAccessException;
	 public void updateArticle(BoardVO articleVO) throws DataAccessException;
	 public void deleteArticle(int bno) throws DataAccessException;
	 
	 // ����¡ ������ �Խ��� ���
	 public List<BoardVO> getListPaging(Criteria cri);
	 
	 // �Խ��� ��� �� ����
	 public int getTotal();
//	 public int insertMember(MemberVO memberVO) throws DataAccessException ;
//	 public int deleteMember(String id) throws DataAccessException;
	 

}
