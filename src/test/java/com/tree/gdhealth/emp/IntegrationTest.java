package com.tree.gdhealth.emp;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.tree.gdhealth.headoffice.emp.EmpMapper;
import com.tree.gdhealth.dto.Employee;
import com.tree.gdhealth.dto.EmployeeDetail;
import com.tree.gdhealth.dto.EmployeeImg;

/* 
 * Spring Test에서의 @Transactional은 테스트 시작 전에 트랜잭션을 시작하고, 
 * 테스트 완료 후에 항상 롤백하여 DB에 데이터를 남기지 않는다. 
 * 따라서 다음 테스트에 영향을 주지 않으므로 테스트를 반복 실행할 수 있다.
 * 
 * @SpringBootTest는 모든 빈을 등록하여 테스트를 진행한다. 
 * 그렇기 때문에 애플리케이션 규모가 크면 테스트가 많이 느려진다.
 * */
@Transactional
@SpringBootTest
public class IntegrationTest { // DB 테스트
	
	/*
	@Autowired EmpMapper empMapper;

	@Test
	@DisplayName("직원들의 목록") // 현재 연결된 실제 DB로 테스트
	void empListTest() {

		Map<String, Object> map = new HashMap<>();
		// given(준비)
		map.put("displayPost", 0);
		map.put("postNum", 8);

		// when(실행)
		// List<Map<String, Object>> list = empMapper.employeeList(map);

		// then(검증)
		assertThat(list.get(0).get("empName")).isEqualTo("확인용1");
	}

	@Test
	@DisplayName("현재 등록된 직원 수") // 현재 연결된 실제 DB로 테스트
	void empCntTest() {

		// given(준비)

		// when(실행)
	 	int empCnt = empMapper.employeeCnt();

		// then(검증)
		assertThat(empCnt).isEqualTo(8);
	}

	@Test
	@DisplayName("특정한 직원의 상세 정보") // 현재 연결된 실제 DB로 테스트
	void empOneTest() {

		// given(준비)
		String employeeId = "sksmss16";

		// when(실행)
		Map<String, Object> one = empMapper.employeeOne(employeeId); 

		// then(검증)
		assertThat((String) one.get("empName")).isEqualTo("확인용6");
	}

	@Test
	@DisplayName("직원DB에 데이터가 추가되는지 확인") // 테스트 후 자동으로 롤백
	void insertEmpTest() {

		// given(준비)
		Employee e = new Employee();
		e.setBranchNo(1);
		e.setEmployeeId("goodee");
		e.setEmployeePw("1234");

		// when(실행)
		int result = empMapper.insertEmployee(e);

		// then(검증)
		assertThat(result).isEqualTo(1);

	}

	@Test
	@DisplayName("직원Detail DB에 데이터가 추가되는지 확인한다.") // 테스트 후 자동으로 롤백
	void insertEmpDetailTest() {

		// given(준비)
		EmployeeDetail ed = new EmployeeDetail();
		ed.setEmployeeNo(1);
		ed.setEmployeeName("구디");
		ed.setEmployeePhone("01011111111");
		ed.setEmployeeEmail("goodee@naver.com");
		ed.setEmployeeGender("m");

		// when(실행)
		int result = empMapper.insertEmployeeDetail(ed);

		// then(검증)
		assertThat(result).isEqualTo(1);
	}

	@Test
	@DisplayName("직원Img DB에 데이터가 추가되는지 확인") // 테스트 후 자동으로 롤백
	void insertEmpImgTest() {

		// given(준비)
		EmployeeImg ei = new EmployeeImg();
		ei.setEmployeeNo(1);
		ei.setEmployeeImgOriginName("origin.png");
		ei.setEmployeeImgFilename("filename.png");
		ei.setEmployeeImgSize(250);
		ei.setEmployeeImgType("png");

		// when(실행)
		int result = empMapper.insertEmployeeImg(ei);

		// then(검증)
		assertThat(result).isEqualTo(1);

	}
	*/
}
