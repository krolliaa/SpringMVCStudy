package com.zwm.controller;

import com.zwm.exception.NameException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "myController12")
@RequestMapping(value = "/user")
public class MyController12 {
    @RequestMapping(value = "/some17.do", method = RequestMethod.POST)
    public ModelAndView doSome(String name, Integer age) throws NameException {
        ModelAndView modelAndView = new ModelAndView();
        if (name == null || !"smith".equals(name)) {
            throw new NameException("姓名错误！");
        }
        if (age == null || 80 < age) {
            throw new NameException("年龄错误！");
        }
        modelAndView.addObject("name", name);
        modelAndView.addObject("age", age);
        modelAndView.setViewName("show");
        return modelAndView;
    }
}
