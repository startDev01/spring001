package com.example.myapp.board.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.myapp.board.vo.BoardVO;
//import com.spring.member.vo.MemberVO;
import com.example.myapp.board.vo.Criteria;

@Repository("boardDAO")
public class BoardDAOImpl implements BoardDAO {
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List selectAllBoardList() throws DataAccessException {
		List<BoardVO> boardList = null;
		boardList = sqlSession.selectList("mapper.board.selectAllBoardList");
		System.out.println("BoardDAOImpl DAO : " + boardList);
		return boardList;
	}

	@Override
	public List selectAllNoticeList() throws DataAccessException {
		List<BoardVO> noticeList = null;
		noticeList = sqlSession.selectList("mapper.board.selectAllNoticeList");
		System.out.println("NoticeList DAO");
		return noticeList;
	}

	@Override
	public BoardVO selectArticle(int bno) throws DataAccessException {
		BoardVO articleVO = null;
		// �Ű������� �ִٸ� �ĸ�(,) �� ���� �Ѱ������!!
		articleVO = sqlSession.selectOne("mapper.board.selectArticle", bno);
		System.out.println("selectArticle DAO");
		return articleVO;
	}

	@Override
	public void createArticle(BoardVO boardVO) {
		int bno = selectNewArticleNO();
		boardVO.setBno(bno);
		sqlSession.selectOne("mapper.board.createArticle",boardVO);
	}

	@Override
	public int selectNewArticleBno() throws DataAccessException {
		return sqlSession.selectOne("mapper.board.selectNewArticleNO");
	}

	@Override
	public void bcountUp(int bno) {
		sqlSession.selectOne("mapper.board.bcountUp",bno);
	}

	@Override
	public void updateArticle(BoardVO articleVO) throws DataAccessException {
		sqlSession.update("mapper.board.updateArticle", articleVO);
	}

	@Override
	public void deleteArticle(int bno) throws DataAccessException {
		sqlSession.delete("mapper.board.deleteArticle",bno);
	}

	@Override
	public List<BoardVO> getListPaging(Criteria cri) {
		List<BoardVO> boardVO = sqlSession.selectList("mapper.board.getListPaging", cri); 
		return boardVO;
	}
	
	@Override
	public int getTotal() {
		int total = sqlSession.selectOne("mapper.board.getTotal");
		return total;
	}

	private int selectNewArticleNO() throws DataAccessException {
		System.out.println("게시판 글 마지막 번호 출력");
		return sqlSession.selectOne("mapper.board.selectNewArticleNO");
	}
	
	
	
	
	

//	@Override
//	public int insertMember(MemberVO memberVO) throws DataAccessException {
//		int result = sqlSession.insert("mapper.member.insertMember", memberVO);
//		return result;
//	}
//
//	@Override
//	public int deleteMember(String id) throws DataAccessException {
//		int result = sqlSession.delete("mapper.member.deleteMember", id);
//		return result;
//	}
}
