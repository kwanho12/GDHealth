package com.tree.gdhealth.utils.pagination;

import org.springframework.ui.Model;

import lombok.Builder;
import lombok.Getter;

/**
 * 본사 페이지에서 페이지네이션 기능을 제공하는 유틸리티 클래스
 * 
 * @author 진관호
 */
@Builder
@Getter
public class HeadofficePagination {

	/**
	 * 한번에 보여 줄 페이지 번호의 개수
	 */
	private int numberOfPaginationToShow;

	/**
	 * 한 페이지에 출력할 row 개수
	 */
	private int rowPerPage;

	/**
	 * row 개수
	 */
	private int rowCnt;

	/**
	 * 현재 페이지 번호
	 */
	private int currentPageNum;

	/**
	 * 마지막 페이지 번호
	 */
	private int lastPageNum;

	/**
	 * 한 페이지에 출력할 row 중에서 첫 번째 row
	 */
	private int beginRow;

	/**
	 * 표시되는 페이지 번호 중 마지막 번호
	 */
	private int endPageNum;

	/**
	 * 표시되는 페이지 번호 중 첫번째 번호
	 */
	private int startPageNum;

	/**
	 * 이전 버튼 표시 여부
	 * <p>
	 * true일 때 이전 버튼을 표시하고 false일 때 이전 버튼을 표시하지 않는다.
	 * </p>
	 */
	private boolean prev;

	/**
	 * 다음 버튼 표시 여부
	 * <p>
	 * true일 때 다음 버튼을 표시하고 false일 때 다음 버튼을 표시하지 않는다.
	 * </p>
	 */
	private boolean next;

	public void calculateProperties() {

		lastPageNum = (int) Math.ceil((double) rowCnt / rowPerPage);
		if (lastPageNum == 0) {
			lastPageNum = 1;
		}

		endPageNum = (int) (Math.ceil((double) currentPageNum / (double) numberOfPaginationToShow)
				* numberOfPaginationToShow);

		startPageNum = endPageNum - numberOfPaginationToShow + 1;

		int endPageNum_tmp = (int) (Math.ceil((double) rowCnt / (double) rowPerPage));

		if (endPageNum > endPageNum_tmp) {
			endPageNum = endPageNum_tmp;
		}

		prev = startPageNum == 1 ? false : true;
		next = endPageNum * rowPerPage >= rowCnt ? false : true;

		beginRow = (currentPageNum - 1) * rowPerPage;
	}

	/**
	 * 페이지네이션의 공통 속성을 Model 객체에 추가합니다.
	 *
	 * @param pagination  페이지네이션 정보를 담고 있는 HeadofficePagination 객체
	 */
	public void addModelAttributes(Model model, HeadofficePagination pagination) {

		model.addAttribute("lastPage", pagination.getLastPageNum());
		model.addAttribute("currentPage", pagination.getCurrentPageNum());

		model.addAttribute("startPageNum", pagination.getStartPageNum());
		model.addAttribute("endPageNum", pagination.getEndPageNum());

		model.addAttribute("prev", pagination.isPrev());
		model.addAttribute("next", pagination.isNext());

	}

}
