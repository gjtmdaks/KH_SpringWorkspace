package com.kh.study.board.model.vo;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Board {
	private int no;
	private String title;
	private String subTitle;
	private String content;
	private Date createDate;
}
