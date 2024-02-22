package com.tree.gdhealth.headoffice.customer;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CustomerMapper {

	List<Map<String, Object>> customerList(Map<String, Object> map);

	int customerCnt();

	int searchCnt(Map<String, Object> map);

}
