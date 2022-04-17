package com.zwm.controller;

import com.zwm.pojo.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller(value = "myController7")
@RequestMapping(value = "/user", method = RequestMethod.POST)
public class MyController7 {
    @RequestMapping("/some7.do")
    public String doSome(HttpServletRequest httpServletRequest, Student student) {
        //获取到前端传递的数据并放置到该域中
        httpServletRequest.setAttribute("studentName", student.getName());
        httpServletRequest.setAttribute("studentAge", student.getAge());
        return "/WEB-INF/view/show.jsp";
    }
}
