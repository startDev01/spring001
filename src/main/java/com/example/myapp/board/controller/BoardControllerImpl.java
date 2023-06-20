package com.example.myapp.board.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.myapp.board.service.BoardService;
import com.example.myapp.board.vo.BoardVO;
import com.example.myapp.board.vo.Criteria;
import com.example.myapp.board.vo.PageMakeVO;

//import com.spring.member.service.MemberService;
//import com.spring.member.vo.MemberVO;


@Controller("boardController")
public class BoardControllerImpl implements BoardController {
	private static final String ARTICLE_IMAGE_REPO = "C:\\board\\article_image";
	@Autowired
	private BoardService boardService;
	@Autowired
	BoardVO boardVO;
//	@Autowired
//	PageMakeVO pageMake;


	// 게시판 목록(FIX)
	@Override
	@RequestMapping(value="/board/listBoard.do" ,method = RequestMethod.GET)
	public ModelAndView listBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		List boardList = boardService.listBoard();
		// List noticeList = boardService.noticeListBoard();
		ModelAndView mav = new ModelAndView(viewName);
		// mav.addObject("noticeList", noticeList);
		mav.addObject("boardList", boardList);
		return mav;
	}

	// 게시판 목록(페이징)
//	@Override
//	@RequestMapping(value="/board/listBoard.do", method=RequestMethod.GET)
//	public ModelAndView listBoardPaging(Criteria cri, HttpServletRequest request, HttpServletResponse response) throws Exception {
//		String viewName = getViewName(request);
//		// 게시글 목록 출력
//		// List boardList = boardService.listBoard();
//		List boardList = boardService.getListPaging(cri);
//		List noticeList = boardService.noticeListBoard();
//
//		int total = boardService.getTotal();
//
//		System.out.println(viewName);
//
//		PageMakeVO pageMake = new PageMakeVO(cri, total);
//
//		ModelAndView mav = new ModelAndView(viewName);
//		mav.addObject("noticeList", noticeList);
//		mav.addObject("boardList", boardList);
//		mav.addObject("pageMaker", pageMake);
//
//		return mav;
//	}
	
	@Override
	@RequestMapping(value="/board/viewArticle.do", method=RequestMethod.GET)
	public ModelAndView viewArticle(@RequestParam("bno") int bno,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("viewArticle!!!");
		String viewName = getViewName(request);
		
		// 세션 값 확인
		HttpSession session = request.getSession();
		System.out.println("Session 값 : " + session.getAttribute("member")
											+ " /// " + session.getAttribute("isLogOn"));
		boolean isLogOn = false;


		// 조회수 증가
		if(isLogOn) {
			boardService.bcountUp(bno);
		}
		
		// 게시글 조회
		BoardVO articleVO = boardService.selectArticle(bno);
		
		System.out.println(articleVO);
		
		ModelAndView mav = new ModelAndView(viewName);
		mav.setViewName(viewName);
		mav.addObject("articleVO", articleVO);
		
		return mav;
	}
	
	@Override
	@RequestMapping(value="/board/addArticle.done", method=RequestMethod.POST)
	public ModelAndView createArticle(@RequestParam("bname") String bname,
			@RequestParam("bwriter") String bwriter,
			@RequestParam("bdetail") String bdetail,
			@RequestParam("btype") String btype,
			MultipartHttpServletRequest multipartRequest,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		
		boardVO.setBname(bname);
		boardVO.setBwriter(bwriter);
		boardVO.setBdetail(bdetail);
		boardVO.setBtype(btype);
		
		boardService.createArticle(boardVO);
		
		// 업로드한 이미지 파일 이름 가져오기
		String imageFileName = upload(multipartRequest);
		
		// 게시판 추가
		
		ModelAndView mav = new ModelAndView(viewName);
		mav.setViewName(viewName);
		mav.addObject("boardVO", boardVO);
		
		return mav;
	}
	
	@Override
	@RequestMapping(value="/board/updateArticleForm", method=RequestMethod.GET)
	public ModelAndView updateArticleForm(@RequestParam("bno") int bno,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		
		System.out.println("게시판 수정 페이지");
		
		BoardVO articleVO = boardService.selectArticle(bno);
		
		ModelAndView mav = new ModelAndView(viewName);
		mav.setViewName(viewName);
		mav.addObject("articleVO", articleVO);

		
		return mav;
	}
	
	@Override
	@RequestMapping(value="/board/updateArticle", method=RequestMethod.POST)
	public ModelAndView updateArticle(@RequestParam("bno") int bno,
			@RequestParam("bname") String bname,
			@RequestParam("bdetail") String bdetail,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		
		boardVO.setBno(bno);
		boardVO.setBname(bname);
		boardVO.setBdetail(bdetail);
		
		System.out.println("boardVO 객체 확인용 : " + boardVO);
		
		boardService.updateArticle(boardVO);
		
		
		ModelAndView mav = new ModelAndView(viewName);
		mav.setViewName(viewName);
		
		return mav;
	}
	
	@Override
	@RequestMapping(value="/board/deleteArticle.do", method=RequestMethod.GET)
	public ModelAndView deleteArticle(@RequestParam("bno") int bno,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		
		boardService.deleteArticle(bno);
		System.out.println("게시판 삭제");
		
		ModelAndView mav = new ModelAndView(viewName);
		mav.setViewName(viewName);
		
		return mav;
	}
	
	public ModelAndView loginArticle(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		
		
		
		ModelAndView mav = new ModelAndView(viewName);
		mav.setViewName(viewName);
		
		return mav;
	}
	
	
	
	
	
	
	

//	@Override
//	@RequestMapping(value="/member/addMember.do" ,method = RequestMethod.POST)
//	public ModelAndView addMember(@ModelAttribute("member") MemberVO member,
//			                  HttpServletRequest request, HttpServletResponse response) throws Exception {
//		request.setCharacterEncoding("utf-8");
//		int result = 0;
//		result = memberService.addMember(member);
//		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
//		return mav;
//	}
//	
//	@Override
//	@RequestMapping(value="/member/removeMember.do" ,method = RequestMethod.GET)
//	public ModelAndView removeMember(@RequestParam("id") String id, 
//			           HttpServletRequest request, HttpServletResponse response) throws Exception{
//		request.setCharacterEncoding("utf-8");
//		memberService.removeMember(id);
//		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
//		return mav;
//	}
	
	/*@RequestMapping(value = { "/member/loginForm.do", "/member/memberForm.do" }, method =  RequestMethod.GET)*/
	@RequestMapping(value = "/board/*Form.do", method =  RequestMethod.GET)
	public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}

	private String upload(MultipartHttpServletRequest multipartRequest) throws Exception {
		String imageFileName = null;
		Iterator<String> fileNames = multipartRequest.getFileNames();
		while (fileNames.hasNext()) {
			String fileName = fileNames.next();
			MultipartFile mFile = multipartRequest.getFile(fileName);
			imageFileName = mFile.getOriginalFilename();
			File file = new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + fileNames);
			if(mFile.getSize() != 0) {
				if(!file.exists()) {
					file.getParentFile().mkdirs();
					mFile.transferTo(new File(ARTICLE_IMAGE_REPO + "\\" + "temp" + "\\" + imageFileName));
				}
			}
		}
		
		// 리턴값 확인 요망
		return imageFileName;
	}

	private String getViewName(HttpServletRequest request) throws Exception {
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}

		int begin = 0;
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		if (uri.indexOf(";") != -1) {
			end = uri.indexOf(";");
		} else if (uri.indexOf("?") != -1) {
			end = uri.indexOf("?");
		} else {
			end = uri.length();
		}

		String viewName = uri.substring(begin, end);
		if (viewName.indexOf(".") != -1) {
			viewName = viewName.substring(0, viewName.lastIndexOf("."));
		}
		if (viewName.lastIndexOf("/") != -1) {
			viewName = viewName.substring(viewName.lastIndexOf("/"), viewName.length());
		}
		return viewName;
	}

}
