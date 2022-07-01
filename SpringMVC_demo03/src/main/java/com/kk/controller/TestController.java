package com.kk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class TestController {

    @RequestMapping(value = "/")
    public String testIndex() {
        return "index";
    }

    @RequestMapping(value = "/testServletAPI")
    public String testServletAPI(HttpServletRequest httpServletRequest) {
        httpServletRequest.setAttribute("testScope", "hello, servletAPI");
        return "success";
    }

    @RequestMapping(value = "/testModelAndView")
    public ModelAndView testModelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("testScope", "Hello Model And View!!!");
        modelAndView.setViewName("success");
        return modelAndView;
    }

    @RequestMapping(value = "/testModel")
    public String testModel(Model model) {
        model.addAttribute("testScope", "Hello Model!!!");
        return "success";
    }

    @RequestMapping(value = "/testModelMap")
    public String testModelMap(ModelMap modelMap) {
        modelMap.addAttribute("testScope", "Hello ModelMap!!!");
        return "success";
    }

    @RequestMapping(value = "/testMap")
    public String testMap(Map map) {
        map.put("testScope", "Hello Map!!!");
        return "success";
    }

    @RequestMapping(value = "/testSession")
    public String testSession(HttpSession httpSession) {
        httpSession.setAttribute("testScope", "Hello Session!!!");
        return "success";
    }

    @RequestMapping(value = "/testApplication")
    public String testApplication(HttpSession httpSession) {
        ServletContext servletContext = httpSession.getServletContext();
        servletContext.setAttribute("testScope", "Hello Application!!!");
        return "success";
    }
}