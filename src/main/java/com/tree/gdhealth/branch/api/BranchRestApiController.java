package com.tree.gdhealth.branch.api;

import com.tree.gdhealth.dto.Branch;
import com.tree.gdhealth.utils.ResponseEntityGenerator;
import com.tree.gdhealth.utils.exception.TooManyResultsException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**<p>지점도메인의 RestApiController</p>
 * @author 정인호
 */
@Slf4j
@RequiredArgsConstructor
@RestController
public class BranchRestApiController {
    private final BranchApiService service;
    private final ResponseEntityGenerator responseEntityGenerator;

    /**<p> 지점번호로 지점정보를 조회</p>
     * @param branchNo 지점번호
     * @return  1개의 지점정보
     */
    @GetMapping("/api/v1/branch/{branchNo}")
    public ResponseEntity<Object> getBranchOneByBranchNo(@PathVariable int branchNo) {
        try {
            return responseEntityGenerator.respGET(service.getBranchOneByBranchNo(branchNo));
        }catch (TooManyResultsException exception){
            log.error("HttpCode500", exception);
            return responseEntityGenerator.respInternalServerError(exception);
        }
    }

    /**
     * <p> 전제지점을 조회</p>
     * @return 전체지점리스트 정보
     */
    @GetMapping("/api/v1/branch")
    public ResponseEntity<Object> getBranchListAll(){
        return responseEntityGenerator.respGET(service.getBranchListAll());
    }


}
