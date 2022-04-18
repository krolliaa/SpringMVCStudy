package com.zwm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller(value = "myController10")
@RequestMapping(value = "/user")
public class MyController10 {
    @RequestMapping(value = "/some13.do")
    public void doSome1(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("../WEB-INF/view/show.jsp").forward(httpServletRequest, httpServletResponse);
    }

    @RequestMapping(value = "/some14.do")
    public void doSome2(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletResponse.sendRedirect("/user/some13.do");
    }
}
