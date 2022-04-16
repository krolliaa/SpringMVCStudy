package com.zwm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller(value = "myController2")
@RequestMapping(value = "/user", method = RequestMethod.GET)
public class MyController2 {
    @RequestMapping("/some2.do")
    public ModelAndView doSome(HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "HttpServletRequest 获取参数");
        String name = httpServletRequest.getParameter("name");
        modelAndView.addObject("fun", "获取到的参数为：" + name);
        modelAndView.setViewName("/show");
        return modelAndView;
    }
}
