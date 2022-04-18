package com.zwm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "myController13")
@RequestMapping(value = "/user")
public class MyController13 {
    @RequestMapping(value = "/some18.do", method = RequestMethod.POST)
    public ModelAndView doSome(String name, Integer age) {
        System.out.println("=====执行MyController中的doSome方法=====");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("myName", name);
        modelAndView.addObject("myAge", age);
        modelAndView.setViewName("/show");
        return modelAndView;
    }
}
