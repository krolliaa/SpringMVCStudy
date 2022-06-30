package com.kk.controller;

import com.kk.pojo.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ParameterController {
    @RequestMapping(value = "/servletAPI")
    public String testServletAPI(HttpServletRequest httpServletRequest) {
        String username = httpServletRequest.getParameter("username");
        String password = httpServletRequest.getParameter("password");
        System.out.println(username + ":" + password);
        return "success";
    }

    @RequestMapping(value = "/parameter")
    public String testParam(String username, String password) {
        System.out.println(username + ":" + password);
        return "success";
    }

    @RequestMapping(value = "/testParameter")
    public String testParameter(String username, String password, String hobby) {
        System.out.println(username + ":" + password + ":" + hobby);
        return "success";
    }

    @RequestMapping(value = "/testPojo")
    public String testPojo(User user) {
        System.out.println(user.toString());
        return "success";
    }
}
