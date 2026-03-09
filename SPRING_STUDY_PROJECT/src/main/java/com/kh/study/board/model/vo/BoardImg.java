package com.kh.study.board.model.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class BoardImg {
	private int boardImgNo;
	private String uploadPath;
	private String originName;
	private String changeName;
	private int refNo;
}
