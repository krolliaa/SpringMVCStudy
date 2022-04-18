package com.zwm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "myController11")
@RequestMapping(value = "/user")
public class MyController11 {
    @RequestMapping(value = "/some15.do", method = RequestMethod.POST)
    public ModelAndView doSome1() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "SpringMVC请求转发");
        modelAndView.setViewName("forward:/WEB-INF/view/hello.jsp");
        return modelAndView;
    }

    @RequestMapping(value = "/some16.do", method = RequestMethod.POST)
    public ModelAndView doSome2() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "SpringMVC请求转发");
        modelAndView.setViewName("redirect:https://www.baidu.com");
        return modelAndView;
    }
}
