package com.kh.spring.common.template;

import com.kh.spring.common.model.vo.PageInfo;

public class Pagination {
	
	public static PageInfo getPageInto(
				int listCount, int currentPage,
				int pageLimit, int boardLimit) {
		
		PageInfo pi = new PageInfo();
		pi.setBoardLimit(boardLimit);
		pi.setPageLimit(pageLimit);
		pi.setListCount(listCount);
		pi.setCurrentPage(currentPage);
		
		// 1. maxPage(최대 페이지 개수)
		int maxPage = (int)Math.ceil(listCount / (double)boardLimit);
		
		// 2. startPage(페이징바의 시작 페이지)
		int startPage = (currentPage - 1) / pageLimit * pageLimit + 1;
		// 만약 17페이지에 있다면 -> (17-1)/10 = 1 -> 1*10 = 10 -> 10+1 = 11
		
		// 3. endPage(페이징바의 종료 페이지)
		int endPage = startPage + pageLimit - 1;
		
		if(endPage > maxPage) {
			endPage = maxPage;
		}
		
		pi.setMaxPage(maxPage);
		pi.setStartPage(startPage);
		pi.setEndPage(endPage);
		
		return pi;
	}
}
