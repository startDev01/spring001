package com.example.myapp.board.vo;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// ����Ʈ���� ������ ������ ���� VO
@Component
@Setter
@Getter
@ToString
public class PageMakeVO {
	// ���� ������
	private int startPage;
	
	// �� ������
	private int endPage;
	
	// ���� ������, ���� ������ ��������
	private boolean prev, next;
	
	// ��ü �Խù� ��
	private int total;
	
	// ���� ������, �������� �Խù� ǥ�ü� ����
	// // ��ü�� ���� ������ ������ �������� ���� ����
	private Criteria cri;
	
	public PageMakeVO() {};
	
	public PageMakeVO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		// ������ ������
		this.endPage = (int)(Math.ceil(cri.getPageNum() / 10.0)) * 10;
		
		// ���� ������
		this.startPage = this.endPage - 9;
		
		// ��ü ������ ������
		int realEnd = (int)(Math.ceil(total * 1.0 / cri.getAmount()));
		
		// ��ü ������ ������(realEnd)�� ȭ�鿡 ���̴� ������ ������(endPage)���� ���� ���,
		// ���̴� ������(endPage)�� ����
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		// ���� ������(startPage)���� 1���� ū ��� true
		this.prev = this.startPage > 1;
		
		// ������ ������(endPage)���� ��ü ������ ������(realEnd) ������ ���� ��� true
		this.next = this.endPage < realEnd;
		
		
		
	}
}
