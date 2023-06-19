package com.example.myapp.member.vo;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Component
@Getter
@Setter
@ToString
public class MemberVO {
	private String userId;
	private String userPwd;
	private String userGrade;

}
