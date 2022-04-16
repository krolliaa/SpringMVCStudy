package com.zwm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller(value = "myController3")
@RequestMapping(value = "/user", method = RequestMethod.GET)
public class MyController3 {
    @RequestMapping("/some3.do")
    public ModelAndView doSome(String name, Integer age) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "形式参数获取前端数据");
        modelAndView.addObject("name", "获取到的 name 参数为：" + name);
        modelAndView.addObject("age", "获取到的 age 参数为：" + age);
        modelAndView.setViewName("/show");
        return modelAndView;
    }
}
