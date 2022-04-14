package com.zwm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "myController")
public class MyController {
    @RequestMapping(value = "/some.do")
    public ModelAndView doSome() {
        System.out.println("这里是控制器对象，是由中央调度器DispatcherServlet调用的，用于处理some.do请求");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "使用注解开发的SpringMVC应用");
        modelAndView.addObject("fun", "doSome");
        modelAndView.setViewName("/show");//跳转
        return modelAndView;
    }
}
