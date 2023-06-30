package com.example.myapp.board.vo;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Component
@Getter
@Setter
@ToString
public class BoardVO {
	private int rownum;
	private int level;
	private int LVL;
	private int bno;
	private int bParentNO;
	private String bname;
	private int bcount;
	private String bwriter;
	private String bdetail;
	private Date bwritedate;
	private String btype;
}
