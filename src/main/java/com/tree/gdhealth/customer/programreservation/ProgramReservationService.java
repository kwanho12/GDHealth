package com.tree.gdhealth.customer.programreservation;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tree.gdhealth.dto.Branch;

@Service
@Transactional
public class ProgramReservationService {

	@Autowired
	private ProgramReservationMapper programReservationMapper;
	
	public Map<String, Object> getCalendar(Integer targetYear, Integer targetMonth, Integer targetDay){
		
		// 타겟 월의 1일
		Calendar firstDay = Calendar.getInstance();
		firstDay.set(Calendar.DATE, 1);
		
		Calendar today = Calendar.getInstance();
		int	thisYear = today.get(Calendar.YEAR);
		int thisMonth = (today.get(Calendar.MONTH)+1);
		int thisDay = today.get(Calendar.DAY_OF_MONTH);
		System.out.println(thisYear + "year");
		System.out.println(thisMonth + "month");
		System.out.println(thisDay + "day");
		System.out.println(today + "totay");



		
		// 넘어온 year,month가 null이 아니면 새로 들어온 값을 년 월로 바꿈
		if(targetYear != null && targetMonth != null) {
			firstDay.set(Calendar.YEAR, targetYear);
			firstDay.set(Calendar.MONTH, targetMonth);
		}
		
		// 0월에서 -1, 12월에서 +1이 되면 CalendarAPI에서 자동으로 년과 월이 변경된다.
		targetYear = firstDay.get(Calendar.YEAR);
		targetMonth = firstDay.get(Calendar.MONTH);
		
		// firstDate는 1일, lastDate는 API를 통하여 구한다.
		int lastDate = firstDay.getActualMaximum(Calendar.DATE);;
		
		// 1일과 lastDate를 이용하여 앞뒤공백(blank) 수를 구한다
		int beginBlank = firstDay.get(Calendar.DAY_OF_MONTH) - 1;
		int endBlank = 0;
		if((beginBlank + lastDate) % 7 !=0 ) {
			endBlank = 7 - ((beginBlank + lastDate) % 7) ;
		}
		
		// 전체 TD의 수
		int totalTd = beginBlank + lastDate + endBlank;
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("targetYear", targetYear);
		resultMap.put("targetMonth", targetMonth);
		resultMap.put("lastDate", lastDate);
		resultMap.put("beginBlank", beginBlank);
		resultMap.put("endBlank", endBlank);
		resultMap.put("totalTd", totalTd);
		resultMap.put("thisYear", thisYear);
		resultMap.put("thisMonth", thisMonth);
		resultMap.put("thisDay", thisDay);
		
		System.out.println(resultMap);
		
		return resultMap;
		
	}
	
	public List<Map<String, Object>> myCalendarLust (int year, int month, int customerNo){
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("year", year);
		paramMap.put("month", month+1);
		paramMap.put("customerNo", customerNo);
			
		System.out.println(paramMap + "paramMap");
		
		List<Map<String, Object>> resultList = programReservationMapper.myCalendarList(paramMap);
		
		return resultList;

	}
	
	public List<Map<String, Object>> selectProgramByMonth (int year, int month){
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("year", year);
		paramMap.put("month", month+1);
				
		List<Map<String, Object>> resultList = programReservationMapper.selectProgramByMonth(paramMap);
		
		return resultList;
	}
	
//	public List<Map<String, Object>> allCalendarList(){
//
//		
//		List<Map<String, Object>> allList = programReservationMapper.allCalendarList();
//		
//		return allList;
//	}
	
	public List<Map<String, Object>> listPage(int currentPage, int year, int month, int thisDay, int thisYear, int thisMonth){
		
		int rowPerPage = 4;
		int beginRow = (currentPage-1) * rowPerPage;
		
		System.out.println(thisYear + "thisYear");
		System.out.println(year + "year");
		System.out.println(thisMonth + "thisMonth");
		System.out.println(month + "month");

		month = month + 1;
		
		if(year == thisYear) {
			if(month != thisMonth) {
				thisDay = 0;
			}
		}
		
		if(year != thisYear) {
			thisDay = 0;
		}
		
		
		
		Map<String, Object> paramap = new HashMap<>();
		paramap.put("rowPerPage",rowPerPage);
		paramap.put("beginRow",beginRow);
		paramap.put("year",year);
		paramap.put("month",month);
		paramap.put("thisDay",thisDay);
		
		System.out.println("paramap-->>" + paramap);

		
		List<Map<String, Object>> listPage = programReservationMapper.listPage(paramap);
		
		return listPage;
	}
	
	public Map<String, Object> prorsone(int year, int month, int day, String programName){
		
		
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("year", year);
		paramMap.put("month", String.format("%02d",month));
		paramMap.put("day", String.format("%02d",day));
		paramMap.put("programName", programName);
		
		System.out.println(paramMap + "<--paramap출력 ");;
		
		Map<String, Object> resultList = programReservationMapper.proRsOne(paramMap);
		System.out.println(resultList + "<--resultList 출력 결과");
		
		return resultList;
	}
	
	public Integer  reservationDate(int customerNo, int programRservationNo) {
		
		Map<String, Object> reservationDate = new HashMap<>();
		reservationDate.put("programRservationNo", programRservationNo);
		reservationDate.put("customerNo", customerNo);
		
		Integer  resultOverlap = programReservationMapper.reservationDate(reservationDate);
		
		return resultOverlap;
	}
	
	public String customerId(int customerNo) {
		
		String customerId = programReservationMapper.customerId(customerNo);
		
		return customerId;
	}
	
	public Map<String, Object> customerPayment(int customerNo) {
		
		Map<String, Object> paramap = programReservationMapper.customerPayment(customerNo);
		 
		return paramap;
	}
	
	public int programReservationAdd(int programDateNo, int branchNo, Integer paymentNo) {
		
		Map<String, Object> paramap = new HashMap<>();
		paramap.put("programDateNo", programDateNo);
		paramap.put("branchNo", branchNo);
		paramap.put("paymentNo", paymentNo);
		
		System.out.println(paramap + "<--paramap");
		
		programReservationMapper.programReservationAdd(paramap);
		
		return 0;

		
	}
	
	public List<Branch> branchList(){
		
		List<Branch> branchList = programReservationMapper.branchList();
		
		return branchList;
		
	}
	
	public List<Map<String, Object>> myreservation(int cutomerNo){
		
		List<Map<String, Object>> resultList = programReservationMapper.myreservation(cutomerNo);
		
		return resultList;
		
	}
	
	public int reservationdelete(int programReservationNo, int customerNo) {
		
		Map<String, Object> paramap = new HashMap<>();
		paramap.put("customerNo", customerNo);
		paramap.put("programReservationNo", programReservationNo);
		
		System.out.println("reservation 삭제 2 --" + paramap);
		
		int result = programReservationMapper.reservationdelete(paramap);
		
		return result;
		
	}
	
	public int reviewdelete(int programReservationNo, int customerNo) {
				
		Map<String, Object> paramap = new HashMap<>();
		paramap.put("customerNo", customerNo);
		paramap.put("programReservationNo", programReservationNo);
		
		int result1 = programReservationMapper.customerAttendanceDelete(paramap);
		
		System.out.println("test debug");
		
		List<Integer> selectedReviewNo = programReservationMapper.reviewSelect(paramap);
		
	    if (selectedReviewNo.size() > 0) {
	        for(int i=0; i<selectedReviewNo.size();i++) {
	        	Integer reviewNo = selectedReviewNo.get(i);
	    		programReservationMapper.reviewimgdelete(reviewNo);
	        }
	    }
		
		int result = programReservationMapper.reviewdelete(paramap);
		
		return result;
	}
	
	public Integer lastPage(int currentPage, int year, int month, int thisDay) {
		
		int rowPerPage = 4;
		
		Map<String, Object> paramap = new HashMap<>();
		paramap.put("year",year);
		paramap.put("month", String.format("%02d",month)+1);
		paramap.put("thisDay",thisDay);

		
		Integer total = programReservationMapper.lastPage(paramap);
		System.out.println(total + "<--total");
		
		if(total != null) {
			int lastPage = total/rowPerPage;
			if(total % rowPerPage != 0) {
				lastPage = lastPage + 1;
			}
			return lastPage;
		}
		
	    return 0;
	}
}
