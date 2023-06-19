package com.example.myapp.member.dao;

import org.springframework.dao.DataAccessException;

import com.example.myapp.member.vo.MemberVO;

public interface MemberDAO {
	public MemberVO findById(MemberVO memberVO) throws DataAccessException;
}
