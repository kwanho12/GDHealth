package com.tree.gdhealth.branch;

import com.tree.gdhealth.employee.dto.EmployeeInformation;
import com.tree.gdhealth.program.dto.BranchProgramCalendar;
import com.tree.gdhealth.program.ProgramApiService;
import com.tree.gdhealth.program.dto.BranchProgramDate;
import com.tree.gdhealth.sportsequipment.SportsEquipmentApiService;
import com.tree.gdhealth.sportsequipment.dto.*;
import com.tree.gdhealth.dto.Employee;
import com.tree.gdhealth.employee.api.EmployeeApiService;
import com.tree.gdhealth.employee.login.EmpLoginService;
import com.tree.gdhealth.employee.login.LoginEmployee;
import com.tree.gdhealth.utils.holidayapi.HolidayApi;
import com.tree.gdhealth.utils.holidayapi.HolidayApiVo;
import com.tree.gdhealth.utils.pagination.PageUri;
import com.tree.gdhealth.utils.pagination.PaginationUriGenerator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>branch의 메인 서비스파사드</p>
 * @author 정인호
 */
@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BranchServiceFacade {
    private final EmpLoginService empLoginService;
    private final EmployeeApiService employeeApiService;
    private final SportsEquipmentApiService sportsEquipmentApiService;
    private final ProgramApiService programApiService;
    private final PaginationUriGenerator paginationUriGenerator;
    private final HolidayApi holidayApi;

    /* 직원 도메인영역 시작 */
    public LoginEmployee testEmployeeLogin(Employee employee) {

        return empLoginService.login(employee);
    }

    public List<EmployeeInformation> getBranchEmployeeList(Integer branchNo) {
        return employeeApiService.getEmployeeListByBranchNo(branchNo);
    }

    /* 물품 도메인영역 시작 */
    public Object getSportsEquipmentOrderOne(Integer orderNo) {

        return sportsEquipmentApiService.getSportsEquipmentOrderOne(orderNo);
    }

    /**<p>발주리스트를 가져오는 메소드</p>
     * @param criteria 조회 조건이 내장
     */
    public List<SportsEquipmentOrderInformation> getSportsEquipmentOrderList(
            SportsEquipmentOrderRetrieveCriteria criteria) {

        return sportsEquipmentApiService.getSportsEquipmentOrderList(criteria);
    }

    /**<p> Url 과 파라미터를 조립해 페이지네이션용 URI를 만들어내는 메소드</p>
     * @apiNote {@link BranchServiceFacade} ::getSportsEquipmentOrderList 와 같은 Criteria 으로 수행해야합니다.
     */
    public List<PageUri> getSportsEquipmentOrderListPagination(
            SportsEquipmentOrderRetrieveCriteria criteria,
            String urlPath ) {

        MultiValueMap<String, String> paramMap = new LinkedMultiValueMap<>();
            paramMap.add("requestPage", String.valueOf(criteria.getRequestPage()));
            paramMap.add("isOnlyWaitingList", String.valueOf(criteria.getIsOnlyWaitingList()));
            paramMap.add("branchNo", String.valueOf(criteria.getBranchNo()));
            paramMap.entrySet().removeIf(entry -> entry.getValue().contains("null"));

        int lastPage = sportsEquipmentApiService.getSportsEquipmentOrderListLastPage(criteria);

        return paginationUriGenerator.getPageUriList(criteria.getRequestPage(),lastPage,urlPath,paramMap);
    }

    /**<p>발주를 추가하는 메소드</p>
     * @param reqDto 발주정보가 담긴 요청 객체
     * @return success? true : false
     */
    public boolean addSportsEquipmentOrder(SportsEquipmentOrderAddRequest reqDto) {
        return sportsEquipmentApiService.addSportsEquipmentOrder(reqDto);
    }

    /**<p>발주건의 처리상태를 변경하는 메소드</p>
     * @param orderNo 발주번호
     * @param changeOrderStatus 변경을 요하는 상태문자열 {@link com.tree.gdhealth.utils.enumtype.OrderStatus}
     * @return success? true : false
     */
    public boolean changeSportsEquipmentOrderStatus(Integer orderNo, String changeOrderStatus) {

        return sportsEquipmentApiService.changeSportsEquipmentOrderStatus(orderNo, changeOrderStatus);
    }

    /* 프로그램 도메인영역 시작 */
    /** <p>지점의 프로그램 스케줄이 담긴 캘린더를 생성하는 메소드</p>
     *  date 리스트에 휴일 API로 가져온 공휴일정보를 매핑하여 반환
     * @param requestDate 기준일
     * @return 기준일을 전후로 한 날짜의 프로그램 정보가 포함된 주간캘린더 객체
     */
    public BranchProgramCalendar getBranchProgramCalendar(Integer branchNo, LocalDate requestDate) {

        List<BranchProgramDate> dateList = programApiService.getBranchProgramDateList(branchNo, requestDate, 1, 6);
        /* 오늘날짜 지정*/
        dateList.stream()
                .filter(date -> LocalDate.now().equals(date.getDate()))
                .findFirst()
                .ifPresent(date -> date.setIsToday(true));

        List<HolidayApiVo> holidayList = new ArrayList<>();
        /*휴일 찾기*/
        dateList.stream()
                .map(date -> YearMonth.from(date.getDate()))
                .distinct()
                .forEach(yearMonth -> {
                    try {
                        holidayList.addAll(holidayApi.getHolidayList(yearMonth));
                    } catch (Exception e) {
                        log.warn("휴일정보를 가져오는 중 문제가 발생하였습니다. 제외하고 계속합니다.", e);
                    }
                });

        /*휴일정보를 매핑*/
        holidayList.forEach(holiday -> {
            dateList.stream()
                    .filter(date -> date.getDate().equals(holiday.getLocdate()))
                    .findFirst()
                    .ifPresent(date -> {
                        date.setDateName(holiday.getDateName());
                        date.setIsHoliday(holiday.isHoliday());
                    });
        });

        /* 캘린더 객체 정리*/
        BranchProgramCalendar calendar = new BranchProgramCalendar();
        calendar.setRequestDate(requestDate);
        calendar.setPreviousWeekDate(requestDate.minusWeeks(1));
        calendar.setNextWeekDate(requestDate.plusWeeks(1));
        calendar.setBranchNo(branchNo);
        calendar.setProgramDateList(dateList);

        return calendar;
    }
}
