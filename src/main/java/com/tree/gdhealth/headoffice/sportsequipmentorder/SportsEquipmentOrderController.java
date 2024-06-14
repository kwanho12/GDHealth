package com.tree.gdhealth.headoffice.sportsequipmentorder;

import com.tree.gdhealth.branch.BranchServiceFacade;
import com.tree.gdhealth.sportsequipment.dto.SportsEquipmentOrderInformation;
import com.tree.gdhealth.sportsequipment.dto.SportsEquipmentOrderRetrieveCriteria;
import com.tree.gdhealth.utils.pagination.PageUri;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**<p>본사의 물품 발주업무 컨트롤러</p>
 * @author 정인호
 */
@RequiredArgsConstructor
@Controller
public class SportsEquipmentOrderController {
    private final BranchServiceFacade branchServiceFacade;

    /**
     * @param requestPage 쿼리스트링의 요청페이지번호
     * @param isOnlyWaitingList 발주상태가 대기인 것만 추출할 것인지 여부
     * @apiNote 출력정보와 페이지네이션 정보
     */
    @GetMapping("/headoffice/sportsEquipmentOrder/list")
    public String getOrderList(
            @RequestParam(name = "requestPage", defaultValue = "1") int requestPage,
            @RequestParam(name = "isOnlyWaitingList", defaultValue = "false") boolean isOnlyWaitingList,
            @RequestParam (name = "branchNo", required = false) @Nullable Integer branchNo,
            Model model) {

        SportsEquipmentOrderRetrieveCriteria criteria = SportsEquipmentOrderRetrieveCriteria.builder()
                .requestPage(requestPage)
                .isOnlyWaitingList(isOnlyWaitingList)
                .branchNo(branchNo)
                .rowPerPage(10).build();

        List<SportsEquipmentOrderInformation> orderInformationList = branchServiceFacade.getSportsEquipmentOrderList(criteria);
        List<PageUri> pageUriList = branchServiceFacade.getSportsEquipmentOrderListPagination(
                criteria, "/headoffice/sportsEquipmentOrder/list");

        model.addAttribute("orderInformationList", orderInformationList);
        model.addAttribute("pageUriList", pageUriList);
        model.addAttribute("requestPage", requestPage);
        return "/headoffice/sportsEquipmentOrderList";
    }

    /**<p>발주건 하나의 상세정보 페이지 Get</p>
     */
    @GetMapping("/headoffice/sportsEquipmentOrderOne")
    public String getOrderOne(@RequestParam(name = "orderNo", required = false) Integer orderNo,
                              Model model){
        model.addAttribute("orderOne", branchServiceFacade.getSportsEquipmentOrderOne(orderNo));
        return "/headoffice/sportsEquipmentOrderOne";
    }

    /**<p>발주건의 처리상태를 변경하는 post</p>
     */
    @PostMapping("/headoffice/sportsEquipmentOrderOne")
    public String changeOrderStatus(@RequestParam(name = "orderNo") Integer orderNo,
                                    @RequestParam(name = "changeOrderStatus") String changeOrderStatus){
        return "redirect:/headoffice/sportsEquipmentOrderOne?orderNo="+orderNo;
    }
}
