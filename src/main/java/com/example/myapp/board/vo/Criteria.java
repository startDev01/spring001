package com.example.myapp.board.vo;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 백엔드에서 동작할 페이지 구성 VO
@Component
@Setter
@Getter
@ToString
public class Criteria {
	// 현재 페이지
	private int pageNum;
	
	// 한 페이지 당 보여질 게시물 갯수
	private int amount;
	
	// 기본 생성자 -> 기본 세팅 : pageNum = 1, amount = 10
	public Criteria() {
		this(1,10);
	}
	
	// 생성자 -> 원하는 pageNum, 원하는 amount
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
}
