package com.example.myapp.board.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.myapp.board.vo.BoardVO;
import com.example.myapp.board.vo.Criteria;

//import com.spring.member.vo.MemberVO;

public interface BoardController {
	// ���� �Խñ� ����Ʈ ���
	public ModelAndView listBoard(HttpServletRequest request, HttpServletResponse response) throws Exception;

	// ����¡ ���� �Խñ� ����Ʈ ���
//	public ModelAndView listBoardPaging(Criteria cri, HttpServletRequest request, HttpServletResponse response) throws Exception;
//	public ModelAndView addMember(@ModelAttribute("info") MemberVO memberVO,HttpServletRequest request, HttpServletResponse response) throws Exception;
//	public ModelAndView removeMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception;
	public ModelAndView viewArticle(@RequestParam("bno") int bno, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	public ModelAndView createArticle(@RequestParam("bname") String bname, @RequestParam("bwriter") String bwriter,
			@RequestParam("bdetail") String bdetail, @RequestParam("btype") String btype,
									  HttpServletRequest request, HttpServletResponse response)
			throws Exception;

	public ModelAndView articleForm(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ModelAndView updateArticleForm(@RequestParam("bno") int bno,
			HttpServletRequest request, HttpServletResponse response) throws Exception;

	public ModelAndView updateArticle(@RequestParam("bno") int bno,
			@RequestParam("bname") String bname, @RequestParam("bdetail") String bdetail,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public ModelAndView deleteArticle(@RequestParam("bno")int bno,
			HttpServletRequest request, HttpServletResponse response) throws Exception;
}
