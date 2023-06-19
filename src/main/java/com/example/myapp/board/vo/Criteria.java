package com.example.myapp.board.vo;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// �鿣�忡�� ������ ������ ���� VO
@Component
@Setter
@Getter
@ToString
public class Criteria {
	// ���� ������
	private int pageNum;
	
	// �� ������ �� ������ �Խù� ����
	private int amount;
	
	// �⺻ ������ -> �⺻ ���� : pageNum = 1, amount = 10
	public Criteria() {
		this(1,10);
	}
	
	// ������ -> ���ϴ� pageNum, ���ϴ� amount
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
}
