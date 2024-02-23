package com.tree.gdhealth.branch.api;

import com.tree.gdhealth.branch.dto.BranchRetrieveCriteria;
import com.tree.gdhealth.dto.Branch;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 정인호
 */
@Mapper
public interface BranchApiMapper {
    List<Branch> selectBranchListByCriteria(BranchRetrieveCriteria criteria);
}
