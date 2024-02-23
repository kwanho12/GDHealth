package com.tree.gdhealth.branch;

import com.tree.gdhealth.program.dto.BranchProgramCalendar;
import com.tree.gdhealth.sportsequipment.dto.SportsEquipmentOrderAddRequest;
import com.tree.gdhealth.sportsequipment.dto.SportsEquipmentOrderInformation;
import com.tree.gdhealth.dto.Employee;
import com.tree.gdhealth.employee.login.LoginEmployee;
import com.tree.gdhealth.sportsequipment.dto.SportsEquipmentOrderRetrieveCriteria;
import com.tree.gdhealth.utils.auth.Auth;
import com.tree.gdhealth.utils.auth.Authority;
import com.tree.gdhealth.utils.pagination.PageUri;
import com.tree.gdhealth.utils.pagination.PaginationUriGenerator;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>지점(branch)의 메인 컨트롤러</p>
 * @author 정인호
 */
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/branch")
@Controller
public class BranchController {
    private final BranchServiceFacade serviceFacade;
    @Auth(AUTHORITY = Authority.BRANCH_EMP_ONLY)
    @GetMapping("/home")
    public String getBranchHome(){

        return "/branch/home";
    }

    /**<p>지점 직원리스트 페이지 호출</p>
     * @param loginEmployee 로그인된 직원
     * @return 지점의 직원리스트 페이지
     */
    @Auth(AUTHORITY = Authority.BRANCH_EMP_ONLY)
    @GetMapping("/employee/list")
    public String getBranchEmployeeList(@SessionAttribute LoginEmployee loginEmployee, Model model){

        model.addAttribute("branchEmployeeList", serviceFacade.getBranchEmployeeList(loginEmployee.getBranchNo()));

        return "/branch/employee/list";
    }

    /**<p>지점 발주리스트 페이지 호출</p>
     * @return 지점의 물품발주조회 페이지
     * @param requestPage (쿼리스트링)요청 페이지번호
     * @param isOnlyWaitingList (쿼리스트링)대기건만 조회할 것인지 여부
     * @apiNote  모델에 출력정보와 페이지네이션 정보를 포함합니다.
     */
    @Auth(AUTHORITY = Authority.BRANCH_EMP_ONLY)
    @GetMapping("/sportsEquipment/order/list")
    public String getBranchSportsEquipmentOrderList(
            @RequestParam(name = "requestPage", defaultValue = "1") Integer requestPage,
            @RequestParam(name = "isOnlyWaitingList", defaultValue = "false") boolean isOnlyWaitingList,
            @SessionAttribute("loginEmployee") LoginEmployee loginEmployee,
            Model model) {

        SportsEquipmentOrderRetrieveCriteria criteria = SportsEquipmentOrderRetrieveCriteria.builder()
                .requestPage(requestPage)
                .isOnlyWaitingList(isOnlyWaitingList)
                .branchNo(loginEmployee.getBranchNo())
                .rowPerPage(10).build();

        List<SportsEquipmentOrderInformation> orderInformationList = serviceFacade.getSportsEquipmentOrderList(criteria);
        List<PageUri> pageUriList = serviceFacade.getSportsEquipmentOrderListPagination(criteria, "/branch/sportsEquipment/order/list");

        model.addAttribute("orderInformationList", orderInformationList);
        model.addAttribute("pageUriList", pageUriList);
        model.addAttribute("requestPage", requestPage);
        return "/branch/sportsEquipment/order/list";
    }

    /**<p>지점 물품발주 폼 페이지 호출</p>
     * @return 지점물품발주 폼 페이지
     */
    @Auth(AUTHORITY = Authority.BRANCH_EMP_ONLY)
    @GetMapping("/sportsEquipment/order/addForm")
    public String getSportsEquipmentOrderAddForm() {
        return "branch/sportsEquipment/order/addForm";
    }

    /**<p>지점물품발주요청 post</p>
     * @param reqDto {@link SportsEquipmentOrderAddRequest} 필드 유효성검사
     * @return 지점 물품 주문 폼 페이지에 메세지를 쿼리스트링 추가하여 리다이렉트
     * @param errors 유호성검사의 오류시 에러메시지를 담아 폼 페이지로 보냅니다.
     */
    @Auth(AUTHORITY = Authority.BRANCH_EMP_ONLY)
    @PostMapping("/sportsEquipment/order")
    public String addSportsEquipmentOrder(@Validated SportsEquipmentOrderAddRequest reqDto, Errors errors, Model model) {
        log.debug(reqDto.toString());
        if (errors.hasErrors()){
            List<String> fieldErrorMessageList = errors.getFieldErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();

            model.addAttribute("fieldErrorMessageList", fieldErrorMessageList);
            return "/branch/sportsEquipment/order/addForm";
        }

        boolean isSuccess = serviceFacade.addSportsEquipmentOrder(reqDto);
        String serverMessage = (isSuccess)? "success": "fail";

        return "redirect:/branch/sportsEquipment/order/addForm?serverMessage="+serverMessage;
    }

    /** <p>지점 프로그램 달력 페이지 호출</p>
     * @param requestDate 기준일자
     * @param model {@link BranchProgramCalendar}
     */
    @Auth(AUTHORITY = Authority.BRANCH_EMP_ONLY)
    @GetMapping("/programCalendar/{requestDate}")
    public String getBranchProgramCalendar(
            @PathVariable(name = "requestDate") LocalDate requestDate,
            @SessionAttribute("loginEmployee") LoginEmployee loginEmployee,
            Model model){

        if(requestDate == null){
            return "redirect:/branch/programCalendar/"+ LocalDate.now();
        }

        BranchProgramCalendar calendar = serviceFacade.getBranchProgramCalendar(loginEmployee.getBranchNo(), requestDate);
        model.addAttribute("calendar", calendar);

        return "/branch/programCalendar";
    }
}
