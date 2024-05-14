package com.tree.gdhealth.customer.programreservation;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tree.gdhealth.domain.Branch;

import jakarta.servlet.http.HttpSession;

@Controller
public class ProgramReservationController {
	
	@Autowired
	private ProgramReservationService programReservationService;
	
	@GetMapping("/customer/programrs")
	public String programrs(HttpSession session,Model model
			, @RequestParam(required = false) Integer targetYear
			, @RequestParam(required = false) Integer targetMonth
			, @RequestParam(required = false) Integer targetDay
			, @RequestParam(defaultValue = "1") int currentPageMonth1) {
		
		if(session.getAttribute("customerNo") == null) {
			return "redirect:/customer/login";
		}
		
		
		int customerNo = (int)(session.getAttribute("customerNo"));	
		
		Map<String, Object> calendarMap = programReservationService.getCalendar(targetYear, targetMonth, targetDay);
		model.addAttribute("calendarMap", calendarMap);
		
		
		// ----------------List 페이징-----------------
		
		List<Map<String, Object>> listMonth1 = programReservationService.listPage(currentPageMonth1,(int)(calendarMap.get("targetYear")), (int)(calendarMap.get("targetMonth")), (int)(calendarMap.get("thisDay")), (int)(calendarMap.get("thisYear")), (int)(calendarMap.get("thisMonth")));
		model.addAttribute("listMonth1", listMonth1);
		System.out.println(listMonth1 + "<---listMonth1");
		
		Integer lastPage = programReservationService.lastPage(currentPageMonth1, (int)(calendarMap.get("targetYear")), (int)(calendarMap.get("targetMonth")), (int)(calendarMap.get("thisDay")));
		model.addAttribute("lastPage", lastPage);
		System.out.println(lastPage + "<---lastPage");
		
		
		model.addAttribute("currentPageMonth1", currentPageMonth1);
		System.out.println(currentPageMonth1 + "<---currentPageMonth1");

		// -------------------------------------------
		
		
		List<Map<String, Object>> resultList = programReservationService.selectProgramByMonth((int)(calendarMap.get("targetYear")), (int)(calendarMap.get("targetMonth")));
		model.addAttribute("resultList", resultList);
		System.out.println(resultList + "<---resultList 출력");
		
		List<Map<String, Object>> myCalendarList = programReservationService.myCalendarLust((int)(calendarMap.get("targetYear")), (int)(calendarMap.get("targetMonth")), customerNo);
		model.addAttribute("myCalendarList", myCalendarList);
		System.out.println(myCalendarList + "<---myCalendarList 출력");
		
		return "customer/programreservation";
	}
	
	@GetMapping("/customer/prorsone")
	public String prorsone(HttpSession session,int year, int month, int day, String programName, Model model, RedirectAttributes red) {
		
		if(session.getAttribute("customerNo") == null) {
			return "redirect:/customer/login";
		}
		
		
		int customerNo = (int)(session.getAttribute("customerNo"));
		System.out.println(customerNo + "<--customerNo");
		
		
		
		// -------------이용권 확인 -----------------------------
		
		String msg = "";
		
		Map<String, Object> paramap = programReservationService.customerPayment(customerNo);
		System.out.println(paramap + "<--null확인"); // null값이 들어왔는지 확인 
		
		if(paramap == null) { // null값이면 돌려보냄 
			msg = "이용권이 존재하지 않습니다. 이용권을 구매해주세요";			
			System.out.println("이용권이 없음");
			red.addFlashAttribute("msg", msg);
			return "redirect:/customer/programrs";
		}
		
		int paymentMonth = 0;

		// Object 타입들을 형변환 
		Integer yearObj = Integer.parseInt(String.valueOf(paramap.get("year")));
		Integer monthObj = Integer.parseInt(String.valueOf(paramap.get("month")));
		Integer dayObj = Integer.parseInt(String.valueOf(paramap.get("day")));

		String month1 = String.format("%02d",monthObj);
		String day1 = String.format("%02d",dayObj);

		
		System.out.println(month1 + "<--month1");
		
		String paymentDate = yearObj + month1 + day1;
		System.out.println(paymentDate + "<---paymentDate");

		
		String month2 = String.format("%02d",month);
		String programDate = year + month2 + day;
		System.out.println(programDate + "<---programDate");
		
		if(Integer.parseInt(paymentDate) < Integer.parseInt(programDate)) {
			msg = "이용권 기간 확인 부탁드립니다.";			
			System.out.println("이용권 기간 확인 부탁드립니다. ");
			red.addFlashAttribute("msg", msg);
			return "redirect:/customer/programrs";
		}

		// --------------------------------------------------
		
		//             -----branchList---------
		
		List<Branch> branchList = programReservationService.branchList();
		System.out.println(branchList + "/branchLust");
		model.addAttribute("branchList", branchList);
	
		// --------------------------------------------------
		
		String customerId = programReservationService.customerId(customerNo);
		model.addAttribute("customerId", customerId);
		
		Map<String,Object> resultList = programReservationService.prorsone(year, month, day, programName);
		model.addAttribute("resultList", resultList);
		System.out.println(resultList + "resultList");
		
		return "customer/proRsOne";
		
	}
	
	@PostMapping("/customer/prorsone")
	public String prorsone(HttpSession session, 
			int programDateNo, Integer branchNo, RedirectAttributes red) {
		
		if(session.getAttribute("customerNo") == null) {
			return "redirect:/customer/login";
		}
		
		System.out.println(branchNo + "<------branchNo");
		
		int customerNo = (int)(session.getAttribute("customerNo"));
		
		// -------------중복 신청 유효성 검사------------------
		String msg = "";
		
		Integer resultOverlap = programReservationService.reservationDate(customerNo, programDateNo);
		System.out.println(resultOverlap + "<--resultOverlap");
		if (resultOverlap != null) {
		    msg = "중복 신청은 불가능 합니다.";            
		    System.out.println("중복 신청");
		    red.addFlashAttribute("msg", msg);
		    return "redirect:/customer/programrs";
		}

		
		// ------------------------------------------------

		Map<String, Object> paramap = programReservationService.customerPayment(customerNo);
		
		Integer paymentNo = Integer.parseInt(String.valueOf(paramap.get("paymentNo")));
		
		programReservationService.programReservationAdd(programDateNo, branchNo, paymentNo);
		
		return "redirect:/customer/programrs";
	}
	
	@GetMapping("/customer/myreservation")
	public String myreservation(HttpSession session, Model model) {
		
		if(session.getAttribute("customerNo") == null) {
			return "redirect:/customer/login";
		}
		
		int customerNo = (int)(session.getAttribute("customerNo"));
		List<Map<String, Object>> resultList = programReservationService.myreservation(customerNo);
		model.addAttribute("resultList", resultList);
		System.out.println(resultList + "<---resultList");
		
		return "customer/myreservation";
		
	}
	
    @GetMapping("/customer/reservationCalendar")
    public String showReservationCalendar(HttpSession session,Model model
			, @RequestParam(required = false) Integer targetYear
			, @RequestParam(required = false) Integer targetMonth
			, @RequestParam(required = false) Integer targetDay) {
    	
    	
    	if(session.getAttribute("customerNo") == null) {
			return "redirect:/customer/login";
		}
		
    	
    	System.out.println("ajax calendar -->" + targetYear);
    	System.out.println("ajax calendar -->" + targetMonth);
    	System.out.println("ajax calendar -->" + targetDay);
		
		int customerNo = (int)(session.getAttribute("customerNo"));	
    	
		Map<String, Object> calendarMap = programReservationService.getCalendar(targetYear, targetMonth, targetDay);
		model.addAttribute("calendarMap", calendarMap);
    	
		List<Map<String, Object>> resultList = programReservationService.selectProgramByMonth((int)(calendarMap.get("targetYear")), (int)(calendarMap.get("targetMonth")));
		model.addAttribute("resultList", resultList);
		System.out.println(resultList + "<---resultList 출력");
		
		List<Map<String, Object>> myCalendarList = programReservationService.myCalendarLust((int)(calendarMap.get("targetYear")), (int)(calendarMap.get("targetMonth")), customerNo);
		model.addAttribute("myCalendarList", myCalendarList);
		System.out.println(myCalendarList + "<---myCalendarList 출력");
    	
        return "customer/reservationCalendar";
    }

}
