package com.zwm.handler.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MyHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("执行 MyInterceptor ------ preHandle()方法------");
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("执行 MyInterceptor ------ postHandle()方法------ ");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("执行 MyInterceptor ------ afterCompletion()方法--- -- - ");
        HttpSession httpSession = request.getSession();
        Object attribute = httpSession.getAttribute("HttpSession");
        System.out.println("attribute 删除之前：" + attribute);
        httpSession.removeAttribute("HttpSession");
        System.out.println("attribute 删除之后：" + attribute);
    }
}
