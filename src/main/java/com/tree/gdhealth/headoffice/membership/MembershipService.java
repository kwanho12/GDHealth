package com.tree.gdhealth.headoffice.membership;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tree.gdhealth.domain.Membership;

@Service
@Transactional
public class MembershipService {

	@Autowired
	private MembershipMapper membershipMapper;

	public List<Membership> membershipList() {

		List<Membership> membershipList = membershipMapper.membershipList();

		return membershipList;
	}

	public int addMembership(Membership membership) {

		int result = membershipMapper.addMembership(membership);

		return result;

	}

	public int activeY(Membership membership) {

		membershipMapper.activeY(membership);

		return 1;
	}

	public int activeN(Membership membership) {

		membershipMapper.activeN(membership);

		return 1;
	}
}
