package com.tree.gdhealth.domain;

import lombok.Data;

@Data
public class CustomerAttendance {
	private int customerAttendanceNo;
	private int programReservationNo;
	private int customerNo;
	private int branchNo;
	private int customerAttendanceDate;
	private int customerAttendanceEnterTime;
	private int customerAttendanceExitTime;
	private int count;
}
