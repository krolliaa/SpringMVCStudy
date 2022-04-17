package com.zwm.controller;

import com.zwm.pojo.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "myController6")
@RequestMapping(value = "/user", method = RequestMethod.POST)
public class MyController6 {
    @RequestMapping("/some6.do")
    public ModelAndView doSome(Student student) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "形式参数获取前端数据");
        modelAndView.addObject("name", "获取到的 name 参数为：" + student.getName());
        modelAndView.addObject("age", "获取到的 age 参数为：" + student.getAge());
        modelAndView.setViewName("/show");
        return modelAndView;
    }
}
