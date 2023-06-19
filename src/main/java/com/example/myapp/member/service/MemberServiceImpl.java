package com.example.myapp.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.myapp.member.dao.MemberDAO;
import com.example.myapp.member.vo.MemberVO;

@Service("memberService")
public class MemberServiceImpl implements MemberService {
	@Autowired
	MemberDAO memberDAO;
	
	@Override
	public MemberVO findById(MemberVO memberVO) {
		MemberVO userVO = memberDAO.findById(memberVO);
		
		return userVO;
	}
	

}
