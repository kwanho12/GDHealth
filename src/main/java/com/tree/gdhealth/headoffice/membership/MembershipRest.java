package com.tree.gdhealth.headoffice.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tree.gdhealth.vo.Membership;

@RestController
public class MembershipRest {

	@Autowired
	private MembershipService membershipService;

	@PostMapping("/headoffice/deleteMembership")
	public int deleteMembership(Membership membership) {

		String active = membership.getActive();
		System.out.println("aactive" + active);

		if ("N".equals(active)) {

			membershipService.activeY(membership);

		}

		if ("Y".equals(active)) {

			membershipService.activeN(membership);

		}

		return 1;
	}
}
