package com.kh.study.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.study.board.model.vo.Board;
import com.kh.study.board.model.vo.BoardImg;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

	public int insertBoard(Board b, List<BoardImg> imgList) {
		return 0;
	}

}
