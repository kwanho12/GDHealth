package com.tree.gdhealth.customer.franchisebranch;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.domain.Branch;

@Mapper
public interface FranchiseBranchMapper {
	
	List<Branch> branchInfo();
	
	Branch branchMemberCnt();
	
	int reviewCount();
	
	Branch branchInfoOne(Branch branch);
	
	int branchTrainerCount(Branch branch);
	
	int branchMemberCounterOne(Branch branch);
	
	int branchTrainerCountOne(Branch branch);
	
	int branchReviewCountOne(Branch branch);
	
	
}
