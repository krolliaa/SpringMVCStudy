### 4.2 接收请求参数

处理器方法可以包含以下参数：`HttpServletRequest`以及`HttpServletResponse`还有`HttpSession`直接获取想要的参数即可：

```java
package com.zwm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller(value = "myController2")
@RequestMapping(value = "/user", method = RequestMethod.GET)
public class MyController2 {
    @RequestMapping("/some2.do")
    public ModelAndView doSome(HttpServletRequest httpServletRequest) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "HttpServletRequest 获取参数");
        String name = httpServletRequest.getParameter("name");
        modelAndView.addObject("fun", "获取到的参数为：" + name);
        modelAndView.setViewName("/show");
        return modelAndView;
    }
}
```

#### 4.2.1 逐个接收用户提交的参数

直接在控制器方法的形参中写入数据类型和参数，则`SpringMVC`会自动通过`HttpServletRequest`来获取参数，并且最开始获取的都是字符串值，然后会将字符串转化为参数里的数据类型，这就有可能引发一种错误：用户输入的数据类型不是控制器方法参数指定的数据类型，就会显示`400`错误提示，表示服务器接收请求错误即请求无效。因为存在数据类型转换错误：`valueOf(str)`若`str`不正确比如说是一个`null`或者违法的`str`数据转换成`int`类型，所以可以将形参中的`int`类型转换成包装类即`Integer`即可解决问题：发生错误并不会在控制台显示错误信息，而是将错误写进`SpringMVC`里头，所以如果发生错误只会显示错误，对程序无影响

```java
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
```

##### 4.2.1.1 解决`POST`请求中文乱码的问题

可以注册声明一个过滤器，凡是请求都要经过这个过滤器，所以设置编码解决乱码问题就可以放在字符编码过滤器里头去完成操作。在`web.xml`注册声明字符编码过滤器并且配置好参数数据：`encoding forceRequestEncoding forceResponseEncoding`，关于`forceRequestEncoding`和`ForceResponseEncoding`参数数据在`CharacterEncodingFilter`类源码可以看到。

这里得说明一下：`/a/aa/bb[全路径匹配] /a/*[部分路径匹配] *.aa[后缀名匹配]`这三个的区别，第一个代表的是全路径匹配，第二个代表的是部分路径匹配，第三个是后缀名匹配，具体见这篇博客：https://blog.csdn.net/qq_36654606/article/details/85080359

在没有配置`<filter> CharacterEncodingFilter`之前发送`post`请求打印出来是乱码：

![](https://img-blog.csdnimg.cn/d6e463f7f1d341a38c06400c7750a94c.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBAQ3JBY0tlUi0x,size_20,color_FFFFFF,t_70,g_se,x_16)

配置之后正常显示：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
         version="4.0">
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springMVCApplicationContext.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
    <filter>
        <filter-name>characterEncodingFilter</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
        <init-param>
            <param-name>forceRequestEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
        <init-param>
            <param-name>forceResponseEncoding</param-name>
            <param-value>true</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>characterEncodingFilter</filter-name>
        <url-pattern>/**</url-pattern>
    </filter-mapping>
</web-app>
```

```java
package com.zwm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "myController4")
@RequestMapping(value = "/user", method = RequestMethod.POST)
public class MyController4 {
    @RequestMapping("/some4.do")
    public ModelAndView doSome(String name, Integer age) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "形式参数获取前端数据");
        modelAndView.addObject("name", "获取到的 name 参数为：" + name);
        modelAndView.addObject("age", "获取到的 age 参数为：" + age);
        modelAndView.setViewName("/show");
        return modelAndView;
    }
}
```

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--<h3><a href="http://localhost:8080/user/some1.do"> 点击访问 some1.do </a></h3>--%>
<%--<h3><a href="http://localhost:8080/user/some2.do"> 点击访问 some1.do </a></h3>--%>
<h3><a href="http://localhost:8080/user/some3.do?name=zhangsan&age=12"> 点击访问 some3.do 并附带参数</a></h3>
<form action="/user/some4.do" method="post">
    姓名：<input type="text" name="rName">
    年龄：<input type="text" name="rAge">
    <input type="submit" value="提交数据">
</form></br>
</body>
</html>
```

##### 4.2.1.2 解决请求参数和控制器方法形参参数名字不一致问题

形参是`name`但是传递进来的参数名是`rName`，`age`同理，使用`@RequestParam`即可解决，该注解有两个属性：1.`value`：表示需要与请求过来的参数保持一致的名称 2.`required`：表示该请求参数是否必须有值，若必须有但请求中没有就会报错，报`400`错误，默认`@RequestParam`的`required`属性为`true`，直接访问时不带参数将报错

```java
package com.zwm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "myController5")
@RequestMapping(value = "/user", method = RequestMethod.POST)
public class MyController5 {
    @RequestMapping("/some5.do")
    public ModelAndView doSome(@RequestParam(value = "rName") String name, @RequestParam(value = "rAge") Integer age) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "形式参数获取前端数据");
        modelAndView.addObject("name", "获取到的 name 参数为：" + name);
        modelAndView.addObject("age", "获取到的 age 参数为：" + age);
        modelAndView.setViewName("/show");
        return modelAndView;
    }
}
```

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%--<h3><a href="http://localhost:8080/user/some1.do"> 点击访问 some1.do </a></h3>--%>
<%--<h3><a href="http://localhost:8080/user/some2.do"> 点击访问 some1.do </a></h3>--%>
<%--<h3><a href="http://localhost:8080/user/some3.do?name=zhangsan&age=12"> 点击访问 some3.do 并附带参数</a></h3>--%>
<%--<form action="/user/some4.do" method="post">--%>
<%--    姓名：<input type="text" name="rName">--%>
<%--    年龄：<input type="text" name="rAge">--%>
<%--    <input type="submit" value="提交数据">--%>
<%--</form></br>--%>
<form action="/user/some5.do" method="post">
    姓名：<input type="text" name="rName">
    年龄：<input type="text" name="rAge">
    <input type="submit" value="提交数据">
</form>
</body>
</html>
```



