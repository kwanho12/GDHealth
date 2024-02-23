package com.tree.gdhealth.headoffice.membership;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.tree.gdhealth.dto.Membership;

@Mapper
public interface MembershipMapper {

	List<Membership> membershipList();

	int addMembership(Membership membership);

	int activeY(Membership membership);

	int activeN(Membership membership);

}
