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

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getCustomerList(int beginRow, int rowPerPage) {
		PaginationDto paginationDto = new PaginationDto();
		paginationDto.setBeginRow(beginRow);
		paginationDto.setRowPerPage(rowPerPage);
		return customerMapper.selectCustomerList(paginationDto);
	}

	@Transactional(readOnly = true)
	public int getCustomerCnt() {
		return customerMapper.selectCustomerCnt();
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getCustomerList(int beginRow, int rowPerPage, String type, String keyword) {
		PaginationDto paginationDto = new PaginationDto();
		paginationDto.setBeginRow(beginRow);
		paginationDto.setRowPerPage(rowPerPage);
		paginationDto.setType(type);
		paginationDto.setKeyword(keyword);
		return customerMapper.selectCustomerList(paginationDto);
	}

	@Transactional(readOnly = true)
	public int getCustomerCnt(String type, String keyword) {
		return customerMapper.selectSearchCnt(type, keyword);
	}

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
