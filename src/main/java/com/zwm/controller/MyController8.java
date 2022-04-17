package com.zwm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zwm.pojo.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller(value = "myController8")
@RequestMapping(value = "/user", method = RequestMethod.POST)
public class MyController8 {
    @RequestMapping("/some8.do")
    public void doSome(HttpServletResponse httpServletResponse, String name, Integer age) throws IOException {
        System.out.println("Return type is void: name:" + name + " age:" + age);
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        String json = "";
        if (student != null) {
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(student);
            System.out.println("student对象的json格式：" + json);
        }
        httpServletResponse.setContentType("application/text;charset=utf-8");
        PrintWriter printWriter = httpServletResponse.getWriter();
        printWriter.println("发送数据成功");//这个就是返回的 resp
        printWriter.flush();
        printWriter.close();
    }
}
