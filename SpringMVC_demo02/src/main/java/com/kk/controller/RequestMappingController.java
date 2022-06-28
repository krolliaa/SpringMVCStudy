package com.kk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = {"/test1", "/test2"}, method = {RequestMethod.GET, RequestMethod.PUT})
public class RequestMappingController {
    @RequestMapping(value = "/testRequestMapping1")
    public String testRequestMapping() {
        return "success";
    }

    @GetMapping(value = "/testRequestMapping2")
    public String testPostMapping() {
        return "success";
    }

    @RequestMapping(value = "/testRequestMapping3", method = RequestMethod.GET, params = {"username=admin", "password!=123456"})
    public String testRequestMappingParams() {
        return "success";
    }

    @RequestMapping(value = "/testRequestMapping4", method = RequestMethod.GET, params = {"username=admin", "password!=123456"}, headers = {"Host=localhost:8081"})
    public String testRequestMappingHeaders() {
        return "success";
    }
}
