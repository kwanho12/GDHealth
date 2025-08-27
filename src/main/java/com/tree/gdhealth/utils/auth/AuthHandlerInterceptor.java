package com.tree.gdhealth.utils.auth;

import com.tree.gdhealth.employee.login.LoginEmployee;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/** 접근권한을 확인하고 제어하는 인터셉터 핸들러
 * @author 정인호
 */
@Slf4j
@Component
public class AuthHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);
        if(auth == null) {
            return true;
        }
        Authority authority = auth.AUTHORITY();
        switch (authority){
            case CUSTOMER_ONLY -> {
                Integer customerNo = (Integer) request.getSession().getAttribute("customerNo");
                if(customerNo == null){
                    response.sendRedirect("/error/noAuth");
                    return false;
                }else {
                    return true;
                }
            }

            case BRANCH_EMP_ONLY -> {
                LoginEmployee loginEmployee = (LoginEmployee) request.getSession().getAttribute("loginEmployee");
                if(loginEmployee == null || loginEmployee.getBranchLevel() != 0){
                    response.sendRedirect("/error/noAuth");
                    return false;
                }else {
                    return true;
                }
            }
            case HEAD_EMP_ONLY -> {
                LoginEmployee loginEmployee = (LoginEmployee) request.getSession().getAttribute("loginEmployee");
                if(loginEmployee == null || loginEmployee.getBranchLevel() != 1){
                    response.sendRedirect("/employee/login");
                    return false;
                } else {
                    return true;
                }
            }
        }
        return true;
    }
}
