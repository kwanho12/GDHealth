package com.tree.gdhealth.utils.pagination;

import org.springframework.ui.Model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class HeadofficePagination {

	// 한번에 표시할 페이징 번호의 갯수
	private int pageNumCnt;

	// 한 페이지에 출력할 게시물 갯수
	private int rowPerPage;

	// 현재 페이지 번호
	private int currentPage;

	// row 개수
	private int cnt;

	// 마지막 페이징번호
	private int lastPage;

	// 한 페이지에 출력할 회원들 중 첫 번째 회원의 row 값
	private int beginRow;

	// 표시되는 페이지 번호 중 마지막 번호
	private int endPageNum;

	// 표시되는 페이지 번호 중 첫번째 번호
	private int startPageNum;

	// 다음/이전 표시 여부
	private boolean prev;
	private boolean next;

	public void calculation() {

		lastPage = (int) Math.ceil((double) cnt / rowPerPage);
		if (lastPage == 0) { // row의 개수가 0일 때(데이터가 없을 때)
			lastPage = 1;
		}

		// 마지막 번호
		endPageNum = (int) (Math.ceil((double) currentPage / (double) pageNumCnt) * pageNumCnt);

		// 시작 번호
		startPageNum = endPageNum - pageNumCnt + 1;

		// 마지막 번호 재계산
		int endPageNum_tmp = (int) (Math.ceil((double) cnt / (double) rowPerPage));

		if (endPageNum > endPageNum_tmp) {
			endPageNum = endPageNum_tmp;
		}

		prev = startPageNum == 1 ? false : true;
		next = endPageNum * rowPerPage >= cnt ? false : true;

		beginRow = (currentPage - 1) * rowPerPage;

	}

	public void paginationAttributes(Model model, HeadofficePagination paging, int page) {

		model.addAttribute("lastPage", paging.getLastPage());
		model.addAttribute("currentPage", page);

		model.addAttribute("startPageNum", paging.getStartPageNum());
		model.addAttribute("endPageNum", paging.getEndPageNum());

		model.addAttribute("prev", paging.isPrev());
		model.addAttribute("next", paging.isNext());

	}

}
