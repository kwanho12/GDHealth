package com.tree.gdhealth.headoffice.customer;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tree.gdhealth.dto.PaginationDto;
import com.tree.gdhealth.utils.pagination.HeadofficePagination;

import lombok.RequiredArgsConstructor;

/**
 * @author 진관호
 */
@RequiredArgsConstructor
@Transactional
@Service
public class CustomerService {

	private final CustomerMapper customerMapper;

	/**
	 * 전체 회원 목록을 리턴합니다.
	 * 
	 * @param beginRow   해당 페이지 내에서의 첫번째 회원
	 * @param rowPerPage 한 페이지에 나타낼 회원의 수
	 * @return 회원 목록
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getCustomerList(int beginRow, int rowPerPage) {
		PaginationDto paginationDto = new PaginationDto();
		paginationDto.setBeginRow(beginRow);
		paginationDto.setRowPerPage(rowPerPage);
		return customerMapper.selectCustomerList(paginationDto);
	}

	/**
	 * 전체 회원 수를 리턴합니다.
	 * 
	 * @return 회원 수
	 */
	@Transactional(readOnly = true)
	public int getCustomerCnt() {
		return customerMapper.selectCustomerCnt();
	}

	/**
	 * 검색 조건을 만족하는 회원 목록을 리턴합니다.
	 * 
	 * @param beginRow   해당 페이지 내에서의 첫번째 회원
	 * @param rowPerPage 한 페이지에 나타낼 회원의 수
	 * @param type       검색할 keyword의 속성(id,active...)
	 * @param keyword    검색 내용
	 * @return 검색 후의 회원 목록
	 */
	@Transactional(readOnly = true)
	public List<Map<String, Object>> getCustomerList(int beginRow, int rowPerPage, String type, String keyword) {
		PaginationDto paginationDto = new PaginationDto();
		paginationDto.setBeginRow(beginRow);
		paginationDto.setRowPerPage(rowPerPage);
		paginationDto.setType(type);
		paginationDto.setKeyword(keyword);
		return customerMapper.selectCustomerList(paginationDto);
	}

	/**
	 * 검색 조건을 만족하는 회원 수를 리턴합니다.
	 * 
	 * @param type       검색할 keyword의 속성(id,active...)
	 * @param keyword    검색 내용
	 * @return 검색 조건을 만족하는 회원 수
	 */
	@Transactional(readOnly = true)
	public int getCustomerCnt(String type, String keyword) {
		return customerMapper.selectSearchCnt(type, keyword);
	}
	
	/**
	 * 페이지네이션 정보를 생성하여 페이지네이션 객체를 리턴합니다.
	 *
	 * @param pageNum     현재 페이지 번호
	 * @param customerCnt 고객 수
	 * @return 페이지네이션 정보
	 */
	public HeadofficePagination getPagination(int pageNum, int customerCnt) {
		
		HeadofficePagination pagination = HeadofficePagination.builder()
				.numberOfPaginationToShow(10)
				.rowPerPage(8)
				.currentPageNum(pageNum)
				.rowCnt(customerCnt)
				.build();
		pagination.calculateProperties();
		
		return pagination;
	}

}
