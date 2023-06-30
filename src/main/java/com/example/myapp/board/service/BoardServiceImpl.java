package com.example.myapp.board.service;

import java.util.List;

import com.example.myapp.board.vo.PagingVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.myapp.board.dao.BoardDAO;
import com.example.myapp.board.vo.BoardVO;
//import com.spring.member.dao.MemberDAO;
//import com.spring.member.vo.MemberVO;

@Service("boardService")
@Transactional(propagation = Propagation.REQUIRED)
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDAO boardDAO;

	@Override
	public List listBoard() throws DataAccessException {
		List boardList = null;
		boardList = boardDAO.selectAllBoardList();
		System.out.println("BoardServiceImpl 실행 : " + boardList);
		return boardList;
	}

	@Override
	public List noticeListBoard() throws DataAccessException {
		List noticeList = null;
		noticeList = boardDAO.selectAllNoticeList();
		return noticeList;
	}

	@Override
	public BoardVO selectArticle(int bno) throws DataAccessException {
		BoardVO articleVO = null;
		articleVO = boardDAO.selectArticle(bno);
		System.out.println("SelectArticleImpl 실행 : " + articleVO);
		return articleVO;
	}

	@Override
	public void createArticle(BoardVO boardVO) throws DataAccessException {
		boardDAO.createArticle(boardVO);
		System.out.println("createArticle 메서드 실행");
	}

	@Override
	public void replyArticle(BoardVO boardVO) throws DataAccessException {
		boardDAO.replyArticle(boardVO);
		System.out.println("replyArticle 메서드 실행");
	}

	@Override
	public int selectNewArticleBno() throws DataAccessException {
		System.out.println("게시물 끝 추가 번호 메소드 실행");
		return boardDAO.selectNewArticleBno();
	}

	@Override
	public void bcountUp(int bno) throws DataAccessException {
		boardDAO.bcountUp(bno);
		System.out.println("조회수 메소드 실행");

	}

	@Override
	public void updateArticle(BoardVO articleVO) throws DataAccessException {
		boardDAO.updateArticle(articleVO);
		System.out.println("게시판 수정 메소드");
	}

	@Override
	public void deleteArticle(int bno) throws DataAccessException {
		boardDAO.deleteArticle(bno);
		System.out.println("게시판 삭제 메소드");
	}

	@Override
	public int getTotal() {
		int total = boardDAO.getTotal();
		return total;
	}

	@Override
	public List selectAllBoardListWithPaging(PagingVO pagingVO) throws DataAccessException {
		return boardDAO.selectAllBoardListWithPaging(pagingVO);
	}

	//	@Override
//	public int addMember(MemberVO member) throws DataAccessException {
//		return memberDAO.insertMember(member);
//	}
//
//	@Override
//	public int removeMember(String id) throws DataAccessException {
//		return memberDAO.deleteMember(id);
//	}
}
