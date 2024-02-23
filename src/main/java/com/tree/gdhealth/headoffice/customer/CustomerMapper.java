package com.tree.gdhealth.headoffice.customer;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {

	List<Map<String, Object>> selectCustomerList(Map<String, Object> map);

	int selectCustomerCnt();

	int selectSearchCnt(Map<String, Object> map);

}
