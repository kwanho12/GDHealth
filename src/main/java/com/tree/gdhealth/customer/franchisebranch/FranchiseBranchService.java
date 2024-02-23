package com.tree.gdhealth.customer.franchisebranch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tree.gdhealth.dto.Branch;

@Service
@Transactional
public class FranchiseBranchService {
	@Autowired
	FranchiseBranchMapper franchiseBranchMapper;
	
	// 지점 리스트
	public List<Branch> branchInfo() {
		List<Branch> branch = franchiseBranchMapper.branchInfo();
		return branch;
	}
	public Branch branchMemberCnt() {
		Branch branch = franchiseBranchMapper.branchMemberCnt();
		int branchReviewCount = franchiseBranchMapper.reviewCount();
		int branchTrainerCount = franchiseBranchMapper.branchTrainerCount(branch);
		branch.setReviewAllCount(branchReviewCount);
		branch.setTrainerCount(branchTrainerCount);
		
		return branch;
	}
	
	// 지점 정보, 혼잡도
	public Branch branchInfoOne(Branch branch) {
		Branch data = franchiseBranchMapper.branchInfoOne(branch);
		
		// 선택된 지점의 맴버, 트레이너 수 저장 
		int branchMemberCount = franchiseBranchMapper.branchMemberCounterOne(branch);
		int branchTrainerCount = franchiseBranchMapper.branchTrainerCountOne(branch);
		int branchReviewCount = franchiseBranchMapper.branchReviewCountOne(branch);
		
		data.setCount(branchMemberCount);
		data.setTrainerCount(branchTrainerCount);
		data.setReviewCount(branchReviewCount);
		return data;
	}
}
