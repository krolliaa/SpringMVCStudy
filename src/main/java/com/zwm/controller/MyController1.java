package com.zwm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "myController1")
@RequestMapping(value = "/user", method = RequestMethod.GET)
public class MyController1 {
    @RequestMapping(value = "/some1.do")
    public ModelAndView doSome() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "使用注解开发的SpringMVC应用之 @RequestMapping");
        modelAndView.addObject("fun", "执行doSome方法");
        modelAndView.setViewName("/show");
        return modelAndView;
    }
}
