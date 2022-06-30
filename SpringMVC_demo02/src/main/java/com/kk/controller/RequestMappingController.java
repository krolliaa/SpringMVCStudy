package com.kk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    //http://localhost:8080/test1/ad/testAnt1
    @GetMapping(value = "/a?a/testAnt1")
    public String testAnt1() {
        return "success";
    }

    //http://localhost:8080/test1/aaabbbccc/testAnt2
    @GetMapping(value = "/a*/testAnt2")
    public String testAnt2() {
        return "success";
    }

    //http://localhost:8080/test1/a/b/c/d/testAnt3
    @GetMapping(value = "/**/testAnt3")
    public String testAnt3() {
        return "success";
    }

    //http://localhost:8080/test1/testRestful/1/admin
    @RequestMapping(value = "/testRestful/{id}/{username}")
    public String testRestful(@PathVariable(value = "id") String id, @PathVariable(value = "username") String username) {
        System.out.println("id: " + id + " ---> username: " + username);
        return "success";
    }
}
