package com.kh.spring.common.scheduling;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.kh.spring.board.model.service.BoardService;
import com.kh.spring.board.model.vo.BoardImg;
import com.kh.spring.member.model.vo.BoardType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class FileDeleteTask {
	/*
	 * 파일삭제 스케슐러
	 *  - 목표 : DB에는 존재하지 않으나, WEB-Server상에만 존재하는 쓸모
	 * 			없는 파일을 삭제
	 * 
	 *  1. 데이터베이스에서 BOARD_IMG에 등록된 모든 첨부파일 경로를 조회
	 *  2. 모든 게시판 유형(BOARD_TYPE)을 조회하여, 각각의 게시판 디렉토리
	 *     경로를 탐색
	 *  3. 해당 디렉토리에서 실제 서버에 존재하는 이미지 파일 목록을 수집한다.
	 *  4. 각 파일이 DB에 등록되어 있는지 여부를 확인
	 *  5. DB에는 존재하지 않는 파일이라면 "삭제"처리 한다.
	 *  6. 이 작업은 유저활동량이 적은 매달 1일 4시에 실행되도록 스케쥴링한다.
	 */
	private final BoardService service;
	private final ServletContext application;
	
//	@Scheduled(cron = "0 0 4 1 * *")
//	@Scheduled(cron = "1/1 * * * * *")
	public void deleteFile() {
		// DB -> BOARD_IMG 테이블 -> CHANGE_NAME 리스트
		List<String> list = service.selectFileList();
		// ex) /resources/imges/board/N/2026030614342769915.png
		
		// DB -> BOARD 테이블 -> BOARD_CD 리스트 / 이걸로 디렉토리 경로 탐색
		List<BoardType> typeList = service.selectBoardTypeMap();
		// ex) [N, 일반] / [P, 사진]
		
		// 반복문 돌리면서 디렉토리(/resources/imges/board/..)에 존재하는 모든 이미지 파일 수집
		for (BoardType type : typeList) {
			String webPath = application.getRealPath("/resources/imges/board/"+type.getBoardCd());

			File path = new File(webPath);
			if(!path.exists()) {
				continue;
			}
			File[] files = path.listFiles();
			List<File> fileList = Arrays.asList(files);
			
			// 만약 imgList에 존재하지 않는 파일이 realImgList에 존재한다면 해당 realImgList파일 삭제(반복문)
			if(!list.isEmpty() && !fileList.isEmpty()) {
				for(File f : fileList) {
					String fileName = f.getName();
					fileName = "/resources/imges/board/"+type.getBoardCd()+"/"+fileName;
					
					if(!list.contains(fileName)) {
						log.debug(fileName+" 삭제");
						f.delete();
					}
				}
			}
		}
	}
}
