package com.tree.gdhealth.domain;

import lombok.Data;

@Data
public class Notice {
   private int noticeNo;
   private int employeeNo;
   private String employeeId;
   private String noticeTitle;
   private String noticeContent;
   private String createdate;
   private String updatedate;
}