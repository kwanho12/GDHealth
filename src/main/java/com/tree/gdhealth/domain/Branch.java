package com.tree.gdhealth.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 정인호
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Branch {
    private Integer branchNo;
    private String branchName;
    private String branchTel;
    private String branchAddress;
    private Boolean isHeadOffice; // -> branch.branch_level
    private Boolean isActive; // -> branch.branch_active
    private Integer count;
    private int trainerCount;
    private int reviewCount;
    private int reviewAllCount;
    private LocalDateTime createdate;
    private LocalDateTime updatedate;

    public void setBranchLevel(char c){
        setIsHeadOffice((c == '1')? Boolean.TRUE : Boolean.FALSE);
    }
    public Integer getBranchLevel(){
        return (isHeadOffice == Boolean.TRUE)? 1 : 0 ;
    }

    public void setBranchActive(char c){
        setIsActive((c == 'Y')? Boolean.TRUE : Boolean.FALSE);
    }
    public char getBranchActive(){
        return (isActive == Boolean.TRUE)? 'Y' : 'N' ;
    }
}
