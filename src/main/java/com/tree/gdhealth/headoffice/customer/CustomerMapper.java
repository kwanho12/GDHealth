package com.tree.gdhealth.headoffice.customer;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tree.gdhealth.dto.PaginationDto;

/**
 * @author 진관호
 */
@Mapper
public interface CustomerMapper {

	List<Map<String, Object>> selectCustomerList(PaginationDto paginationDto);

	int selectCustomerCnt();

	int selectSearchCnt(@Param("type") String type, @Param("keyword") String keyword);

}
