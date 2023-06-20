package com.example.myapp.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.myapp.member.service.MemberService;
import com.example.myapp.member.vo.MemberVO;

@Controller("memberController")
public class MemberController {
	@Autowired
	MemberService memberService;
	
	@Autowired
	MemberVO memberVO;
	
	@RequestMapping(value="/member/login", method=RequestMethod.GET)
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		
		ModelAndView mav = new ModelAndView(viewName);
		
		return mav;
	}
	
	@RequestMapping(value="/member/login", method=RequestMethod.POST)
	public ModelAndView loginDone(@ModelAttribute("memberVO") MemberVO memberVO,
			RedirectAttributes rAttr,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ModelAndView mav = new ModelAndView();
		System.out.println("세션 로그인 ID : "+ memberVO.getUserId());
		
		MemberVO userVO = memberService.findById(memberVO);
		
		if(memberVO != null) {
			HttpSession session = request.getSession();
			session.setAttribute("member", userVO);
			session.setAttribute("isLogOn", true);
			mav.setViewName("redirect:/board/listBoard.do");
		}else {
			rAttr.addAttribute("result", "loginFailed");
			mav.setViewName("redirect:/member/login");
		}
		
//		memberService.findById(request.getParameter("userId"));
		
		// mav.setViewName("redirect:/board/listBoard.do");
		
		return mav;
	}
	
	@RequestMapping(value="/member/logout.do", method=RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("member");
		session.removeAttribute("isLogOn");
		
		String viewName = getViewName(request);
		
		ModelAndView mav = new ModelAndView(viewName);
		
		mav.setViewName("redirect:/board/listBoard.do");
		
		return mav;
	}
	
	
	
	
	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
  private ModelAndView form(@RequestParam(value= "result", required=false) String result, // 95�࿡�� ���� ���� �Ķ���ͷ� ������
                        HttpServletRequest request, 
                        HttpServletResponse response) throws Exception {
     String viewName = (String)request.getAttribute("viewName");
     ModelAndView mav = new ModelAndView();
     mav.addObject("result",result);
     mav.setViewName(viewName);
     return mav;
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
