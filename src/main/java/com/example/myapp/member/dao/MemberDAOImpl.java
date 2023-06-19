package com.example.myapp.member.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.example.myapp.member.vo.MemberVO;

@Repository("memberDAO")
public class MemberDAOImpl implements MemberDAO {
	@Autowired
	SqlSession sqlsession;

	@Override
	public MemberVO findById(MemberVO memberVO) throws DataAccessException {
		MemberVO userVO = sqlsession.selectOne("mapper.member.findById",memberVO);
		
		return userVO;
	}
	

}
