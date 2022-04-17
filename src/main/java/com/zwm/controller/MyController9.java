package com.zwm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zwm.pojo.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@Controller(value = "myController9")
@RequestMapping(value = "/user")
public class MyController9 {
    @RequestMapping(value = "/some9.do", method = RequestMethod.POST)
    public void doSome(HttpServletResponse httpServletResponse, String name, Integer age) throws IOException {
        //传统的封装返回就是 void
        System.out.println("获取到的名称和年龄：" + name + " " + age);
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        ObjectMapper objectMapper = new ObjectMapper();
        String studentJson = objectMapper.writeValueAsString(student);
        System.out.println(studentJson);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter printWriter = httpServletResponse.getWriter();
        printWriter.write(studentJson);
        printWriter.flush();
        printWriter.close();
    }

    @RequestMapping(value = "/some10.do", method = RequestMethod.POST)
    @ResponseBody
    public Student doSome(String name, Integer age) {
        Student student = new Student();
        student.setName(name);
        student.setAge(age);
        return student;
    }

    @RequestMapping(value = "/some11.do", method = RequestMethod.POST)
    @ResponseBody
    public List<Student> doSome() {
        List<Student> studentList = new ArrayList<Student>();
        Student student1 = new Student();
        student1.setName("张三");
        student1.setAge(1);
        Student student2 = new Student();
        student2.setName("李四");
        student2.setAge(2);
        Student student3 = new Student();
        student3.setName("王五");
        student3.setAge(3);
        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);
        return studentList;
    }

    @RequestMapping(value = "/some12.do", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
    @ResponseBody
    public String doSomeString() {
        return "===欢迎使用SpringMVC注解开发===";
    }

}
