package com.tree.gdhealth.customer.signup;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tree.gdhealth.dto.Customer;
import com.tree.gdhealth.dto.CustomerSignUp;

@Service
@Transactional
public class SignUpService {
	@Autowired
	private SignUpMapper signUpMapper;
	

	public int SignUp(CustomerSignUp customerSignUp, MultipartFile imgFile,String path) {
		// 주소 + 상세주소 조합
		String a1 = customerSignUp.getCustomerAddress();
		String a2 = customerSignUp.getCustomerAddressDetail();
		customerSignUp.setCustomerAddress(a1+a2);
		
		try {
			int n1 = signUpMapper.customerIn(customerSignUp); // customer INSERT 쿼리
			int customerNo = signUpMapper.customerNoCk(customerSignUp.getCustomerId()); // customerNo 추출
			customerSignUp.setCustomerNo(customerNo); // customerNo 세팅
			int n2 = signUpMapper.customerDetailIn(customerSignUp); // detail INSERT 쿼리
			int n3 = 0;
			// 파일 추가
			if (imgFile.getSize() != 0) { // 파일이 하나이상 있다면
				String uName = UUID.randomUUID().toString(); // 파일이름
				// 확장자
				String oName = imgFile.getOriginalFilename();
				String extName = oName.substring(oName.lastIndexOf("."));
				// xx.xxx.pdf -> .pdf
				// 파일 이름 세팅
				customerSignUp.setCustomerImgOriginName(imgFile.getOriginalFilename());
				customerSignUp.setCustomerImgSize((int) imgFile.getSize());
				customerSignUp.setCustomerImgType(imgFile.getContentType());
				customerSignUp.setCustomerImgFileName(uName + extName);
				n3 = signUpMapper.customerImgIn(customerSignUp); // img INSERT 쿼리
				
				// 파일이 저장될 디렉토리 경로 설정
		        String directoryPath = "upload/";
		        // 디렉토리 경로 설정
		        File directory = new File(directoryPath);
		        
		        
				System.out.println("파일저장직전");
				// 물리적 file을 원하는 경로(path)에 저장
				File file = new File(path+"/"+ uName + extName); // 빈 파일
				try {
					imgFile.transferTo(file);
				} catch (IllegalStateException | IOException e) {
					// e.printStackTrace();
					// log.error('');
					throw new RuntimeException();
				}
				System.out.println("파일저장완료");
			}
			// 각 작업의 결과를 더하여 반환
			int result = n1 + n2 + n3;

			return result; // 파일 업로드 코드 이후에도 정상적으로 결과 반환
		} catch (Exception e) {
			// 기타 예외가 발생한 경우 롤백 처리 또는 다른 예외 처리 로직 작성
			throw e;
		}
	}
	
	
	public Customer idCk(Customer customer) {
		
		System.out.println(customer.getCustomerId());
		String customerId = customer.getCustomerId();	
		Integer customerNoCkResult = signUpMapper.customerNoCk(customerId);
		if (customerNoCkResult == null || customerNoCkResult == 0) {	// customerNo가 널값이면 0으로 대체
		    customer.setCustomerNo(0);
		    return customer;
		}
		customer.setCustomerNo(signUpMapper.customerNoCk(customerId));
		return customer;
	}
	
	public int emailCk(String customerEmail) {
		int customerNo;
		Integer customerNoCkResult = signUpMapper.customerEmailCk(customerEmail);
		if (customerNoCkResult == null || customerNoCkResult == 0) {	// customerNo가 널값이면 0으로 대체
			customerNo = 0;
		    return customerNo;
		}
		customerNo = signUpMapper.customerEmailCk(customerEmail);
		
		return customerNo;
	}
}
