package com.tree.gdhealth.headoffice.customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class CustomerService {

	private final CustomerMapper customerMapper;

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getCustomerList(int beginRow, int rowPerPage) {

		Map<String, Object> map = new HashMap<>();
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);

		List<Map<String, Object>> customerList = customerMapper.customerList(map);

		return customerList;

	}

	@Transactional(readOnly = true)
	public int getCustomerCnt() {

		int customerCnt = customerMapper.customerCnt();
		// 디버깅
		log.debug("고객 수 : " + customerCnt);

		return customerCnt;
	}

	@Transactional(readOnly = true)
	public List<Map<String, Object>> getSearchList(int beginRow, int rowPerPage, String type, String keyword) {

		Map<String, Object> map = new HashMap<>();
		map.put("beginRow", beginRow);
		map.put("rowPerPage", rowPerPage);
		map.put("type", type);
		map.put("keyword", keyword);

		List<Map<String, Object>> searchList = customerMapper.customerList(map);

		return searchList;

	}

	@Transactional(readOnly = true)
	public int getSearchCnt(String type, String keyword) {

		Map<String, Object> map = new HashMap<>();
		map.put("keyword", keyword);
		map.put("type", type);

		int searchCnt = customerMapper.searchCnt(map);
		// 디버깅
		log.debug("검색 결과 개수 : " + searchCnt);

		return searchCnt;
	}

}
