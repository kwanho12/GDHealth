package com.tree.gdhealth.customer.mypage;

import java.io.File;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tree.gdhealth.dto.Customer;
import com.tree.gdhealth.dto.CustomerDetail;
import com.tree.gdhealth.dto.CustomerImg;
import com.tree.gdhealth.dto.CustomerMyPage;

@Service
@Transactional
public class MyPageService {
	@Autowired MyPageMapper myPageMapper;
	
	public CustomerMyPage MyPage(int customerNo) {
		System.out.println(customerNo);
		CustomerMyPage info = myPageMapper.info(customerNo);
		
		
		CustomerImg imgInfo = myPageMapper.customerImgCk(customerNo);
		
		if(imgInfo != null) {
			info.setImgFileName(imgInfo.getCustomerImgFileName());
		}
	
		// bmi 계산식
		double weight = info.getCustomerWeight();
		double height = info.getCustomerHeight();
		height /= 100;
		double bmi = Math.round((weight / (height * height))*10);
		bmi /=10 ;
		info.setCustomerBmi(bmi);
		System.out.println(info.toString());
		System.out.println(bmi);
		return info;
	}
	
	public CustomerImg selectCustomerImg(int customerNo) {
        return myPageMapper.selectCustomerImg(customerNo);
    }
	
	public int attendance(int customerNo) {
		int attendanceCount = myPageMapper.attendanceCount(customerNo);  
		return attendanceCount;
	}
	public int review(int customerNo) {
		int reviewCount = myPageMapper.reviewCount(customerNo);  
		return reviewCount;
	}
	public int question(int customerNo) {
		int questionCount = myPageMapper.questionCount(customerNo);  
		return questionCount;
	}
	public String membership(int customerNo) {
		String membership = myPageMapper.membership(customerNo);  
		return membership;
	}
	//휴대폰번호 하이픈 셋팅
	public String selectPhone(int customerNo) {
		String selectPhone = myPageMapper.selectPhone(customerNo);
		return selectPhone;
	}
	
	// 고객 계정 삭제전 체크
	public int deleteCustomerCk(Customer customer) {
		int result = 0;
		if(myPageMapper.customerDeleteCk(customer) == null || myPageMapper.customerDeleteCk(customer) == 0) {
			return result;
		}
		result = myPageMapper.customerDeleteCk(customer);
		return result;
	}
	
	// 고객 계정 삭제
	public int deleteCustomer(Customer customer, String path) {
		int n1, n2, n3, result;
		result = 0;
		
		try {
			n1 = myPageMapper.customerDelete(customer);
			n2 = myPageMapper.customerDetailDelete(customer);
			CustomerImg customerImg = myPageMapper.customerImgCk(customer);
			n3 = myPageMapper.customerImgDelete(customer);
			if(n1+n2+n3 != 3) {
				throw new RuntimeErrorException(null);
			}
			// 이미지 파일 삭제
            String imagePath = path + "/" + customerImg.getCustomerImgFileName();
            File file = new File(imagePath);
            boolean isFileDeleted = file.delete();
            if(!isFileDeleted) {
            	throw new RuntimeErrorException(null);
            }
			result = 1;
			return result;
			
		} catch (Exception e) {
			throw new RuntimeErrorException(null);
			
		}
		
		
	}
	//고객정보 수정
	public int updateMyPage(CustomerMyPage customerMyPage) {
		
		System.out.println("ff");
		int row = myPageMapper.updateMyPage(customerMyPage);
		System.out.println(row);
		return row;
	}
	//비밀번호 수정
	public int updatePw(Customer customer) {
		int row = myPageMapper.updatePw(customer);
		return row;
	} 
	//이메일 소유 확인
	public int updateEmailCk(CustomerMyPage customerMyPage) {
		int customerNo;
		Integer EmailCheckResult = myPageMapper.EmailCheck(customerMyPage.getCustomerEmail());
		Integer updateEmailCkResult = myPageMapper.updateEmailCk(customerMyPage);
		System.out.println(updateEmailCkResult);
		if(updateEmailCkResult == null || updateEmailCkResult == 0) {
		if(EmailCheckResult == null || EmailCheckResult == 0) {
		   customerNo = 0;
		   return customerNo;
		   }
		   customerNo = myPageMapper.EmailCheck(customerMyPage.getCustomerEmail());
		   return customerNo;
		}
		customerNo = 0;
		
		return customerNo;
	}
	
}
