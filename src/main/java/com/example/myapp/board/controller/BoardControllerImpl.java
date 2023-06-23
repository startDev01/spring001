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

import com.example.myapp.member.vo.MemberVO;
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
	@Autowired
	MemberVO memberVO;

	ArrayList<Integer> bnoArr;
//	@Autowired
//	PageMakeVO pageMake;

	// 게시판 목록(FIX)
	@Override
	@RequestMapping(value="/board/listBoard.do" ,method = RequestMethod.GET)
	public ModelAndView listBoard(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		
		// 일반 게시글 목록 서비스 호출
		List boardList = boardService.listBoard();
		
		// 공지 게시글 목록 서비스 호출
		List noticeList = boardService.noticeListBoard();

		// 비로그인시 조회수용 세션 초기화
		HttpSession session = request.getSession();
		System.out.println(session.getAttribute("isLogOn"));
		if(session.getAttribute("isLogOn") == null) {
			System.out.println("비로그인 상태, 중복확인 배열 초기화");
			bnoArr = null;
		}

		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("noticeList", noticeList);
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

		// bnoArr 정수 배열이 없다면 인스턴스 생성
		if(bnoArr == null) {
			bnoArr = new ArrayList<Integer>();
		}

		// 로그인 세션 확인
		if((session.getAttribute("isLogOn")) != null) {
			System.out.println("로그인 상태입니다.");
			session.setAttribute("bnoArr", bnoArr);
			boolean bnoCheck = false;

			// 배열에 저장된 글번호 중복 확인
			for (Integer bnoInArr : bnoArr) {
				if (bnoInArr == bno) {
					System.out.println("중복된 세션 감지, 조회수 증가 X");
					bnoCheck = true;
				}
			}

			// 조회수 증가(Session 값에 따라 조회수 증가 제한)
			if (!bnoCheck) {
				System.out.println("조회수 증가함");
				boardService.bcountUp(bno);

				// 객체 배열에 해당 글번호 추가
				bnoArr.add(bno);
				session.setAttribute("bnoArr", bnoArr);
			}
		} else {
			// 비 로그인 상태시 bnoArr 배열 전부 삭제
			System.out.println("비 로그인 상태입니다.");
			bnoArr = null;
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
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		System.out.println("글쓰기");

		boardVO.setBname(bname);
		boardVO.setBwriter(bwriter);
		boardVO.setBdetail(bdetail);
		boardVO.setBtype(btype);

		int bno = boardService.selectNewArticleBno();

		boardVO.setBno(bno);
		
		boardService.createArticle(boardVO);
		
		// 게시판 추가
		
		ModelAndView mav = new ModelAndView(viewName);
		mav.setViewName(viewName);
		mav.addObject("boardVO", boardVO);
		
		return mav;
	}


	@Override
	@RequestMapping(value = "/board/articleForm.do", method =  RequestMethod.GET)
	public ModelAndView articleForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);

		// 게시글 끝번호 + 1 서비스 조회
		int bno = boardService.selectNewArticleBno();
		
		// 로그인 확인
		HttpSession session = request.getSession();
		if(session.getAttribute("member") == null) {
			System.out.println("비로그인, 로그인폼 진입 불가");
			request.setAttribute("msg", "로그인이 필요합니다.");
			request.setAttribute("url", "/myapp/board/listBoard.do");
			viewName = "alert";
		}
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		if(session.getAttribute("member") != null) {
			mav.addObject("bno", bno);
		}
		return mav;
	}
	
	@Override
	@RequestMapping(value="/board/updateArticleForm", method=RequestMethod.GET)
	public ModelAndView updateArticleForm(@RequestParam("bno") int bno,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		
		System.out.println("게시판 수정 페이지");
		
		// 해당 bno의 게시글 조회
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
		
		// boardVO에 데이터 저장 후 서비스 호출
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
		
		// delete 서비스 호출
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
