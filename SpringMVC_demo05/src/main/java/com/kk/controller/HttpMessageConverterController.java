package com.kk.controller;

import com.kk.pojo.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

@Controller
public class HttpMessageConverterController {
    @RequestMapping(value = "/testRequestBody")
    public String testRequestBody(@RequestBody String requestBody) {
        Object object = null;
        object.toString();
        System.out.println("RequestBody: " + requestBody);
        return "success";
    }

    @RequestMapping(value = "/testRequestEntity")
    public String testRequestEntity(RequestEntity<String> requestEntity) {
        System.out.println("requestEntity.getHeaders: " + requestEntity.getHeaders());
        System.out.println("requestEntity.getBody: " + requestEntity.getBody());
        System.out.println("requestEntity.getMethod: " + requestEntity.getMethod());
        System.out.println("requestEntity.getType: " + requestEntity.getType());
        System.out.println("requestEntity.getUrl: " + requestEntity.getUrl());
        return "success";
    }

    @RequestMapping(value = "/testResponseBody")
    @ResponseBody
    public String testResponseBody() {
        return "Hello ResponseBody";
    }

    @RequestMapping(value = "/testResponseBodyAndJackson")
    @ResponseBody
    public User testResponseBodyAndJackson() {
        return new User("张三", 188);
    }

    @RequestMapping(value = "/testAjax")
    @ResponseBody
    public String testAjax(String username, String password) {
        System.out.println("username: " + username + "password: " + password);
        return "Hello Axios Ajax";
    }

    @RequestMapping(value = "/testResponseEntity")
    @ResponseBody
    public ResponseEntity<byte[]> testResponseEntity(HttpSession httpSession) {
        ServletContext servletContext = httpSession.getServletContext();
        String realPath = servletContext.getRealPath("/static/img/1.jpg");
        //从磁盘中读到内存 ---> 字节输入流
        FileInputStream fileInputStream = null;
        ResponseEntity<byte[]> responseEntity = null;
        try {
            fileInputStream = new FileInputStream(realPath);
            //获取文件有多少个字节可以读取，缓存读入，一口气直接读
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            //打开路径设置文件下载 ---> 设置响应头信息
            MultiValueMap<String, String> headers = new HttpHeaders();
            //设置下载方式以及下载后的文件名
            headers.add("Content-Disposition", "attachment;filename=picture1.jpg");
            //设置响应状态码
            HttpStatus httpStatus = HttpStatus.OK;
            //返回下载 ---> 字节下载
            responseEntity = new ResponseEntity<byte[]>(bytes, headers, httpStatus);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseEntity;
    }

    @RequestMapping(value = "/testCommonsFileUpload")
    public String testMultipartFile(MultipartFile multipartFile, HttpSession httpSession) throws IOException {
        //非常简单：获取名字然后改名然后判断文件夹，通过 multipartFile 放进去就可以了
        //获取上传的文件信息
        String fileName = multipartFile.getOriginalFilename();
        //自定义文件名
        String copyName = fileName.substring(fileName.lastIndexOf('.'));
        fileName = UUID.randomUUID().toString() + copyName;
        //存储到当前路径中
        ServletContext servletContext = httpSession.getServletContext();
        String realPath = servletContext.getRealPath("/static/img");
        File file = new File(realPath);
        //如果不存在则新建文件夹
        if(!file.exists()) file.mkdir();
        String finalPath = realPath + File.separator + fileName;
        //上传到服务器
        multipartFile.transferTo(new File(finalPath));
        return "success";
    }
}
