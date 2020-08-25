package com.web.oa.shiro;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @description: 继承表单验证过滤器，给其增加验证验证码的功能。
 * @author 黄培金
 * @date 2020/8/25 19:53
 * @version 1.0
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request,
                                     ServletResponse response, Object mappedValue) throws Exception {
        //返回true拒绝访问
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse resp = (HttpServletResponse)response;
        String randomcode = req.getParameter("randomcode");
        HttpSession session = req.getSession();
        String validateCode = (String) session.getAttribute("validateCode");

        if(randomcode != null &&validateCode != null && !randomcode.equals(validateCode)){
            request.setAttribute(
                    FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME,"validateCodeError");
            return true;
        }
        return super.onAccessDenied(request, response, mappedValue);
    }

    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        //验证成功后，重新请求Controller的/index，不加这个代码
        //验证成功后，加载的是index.jsp
        WebUtils.getAndClearSavedRequest(request);
        WebUtils.redirectToSavedRequest(request, response, "/index");
        return false;
    }
}
