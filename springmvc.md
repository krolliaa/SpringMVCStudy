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

#### 4.2.2 对象接收用户提交的参数

逐个接收用户提交的参数时，参数的数量少还好处理，但是如果参数的数量多了起来就不好解决了，`SpringMVC`可以使用对象接收参数。`SpringMVC`会自动赋值给对象：【`@RequestParam`不能用于对象参数中，因为无效】

```java
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
<%--<form action="/user/some5.do" method="post">--%>
<%--    姓名：<input type="text" name="rName">--%>
<%--    年龄：<input type="text" name="rAge">--%>
<%--    <input type="submit" value="提交数据">--%>
<%--</form>--%>
<form action="/user/some6.do" method="post">
    姓名：<input type="text" name="name">
    年龄：<input type="text" name="age">
    <input type="submit" value="提交数据">
</form>
</body>
</html>
```

### 4.3 处理器方法的返回值

处理器方法返回值有四大类：`void String Object ModelAndView`

#### 4.3.1 `ModelAndView`

若处理器方法处理完毕之后，需要进行资源跳转并且在资源间传递数据，那么使用`ModelAndView`就非常合适，但是如果只是单纯的跳转并没有传递数据，或者有传递数据但是并没有发生资源跳转，比如`Ajax`请求，此时使用`ModelAndView`就不合适，因为这样就有`Model`数据多余或者是`View`视图多余了。

```java
package com.zwm.controller;

import com.zwm.pojo.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "myController6")
@RequestMapping(value = "/user", method = RequestMethod.POST)
public class MyController7 {
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
<%--<form action="/user/some5.do" method="post">--%>
<%--    姓名：<input type="text" name="rName">--%>
<%--    年龄：<input type="text" name="rAge">--%>
<%--    <input type="submit" value="提交数据">--%>
<%--</form>--%>
<%--<form action="/user/some6.do" method="post">--%>
<%--    姓名：<input type="text" name="name">--%>
<%--    年龄：<input type="text" name="age">--%>
<%--    <input type="submit" value="提交数据">--%>
<%--</form>--%>
<form action="/user/some6.do" method="post">
    姓名：<input type="text" name="name">
    年龄：<input type="text" name="age">
    <input type="submit" value="提交数据">
</form>
</body>
</html>
```

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>/WEB-INF/view/show.jsp从request作用域获取数据</h3><br/>
<h3>msg数据：${msg}</h3><br/>
<h3>msg数据：${name}</h3><br/>
<h3>fun数据：${age}</h3>
</body>
</html>
```

#### 4.3.2 `String`

若处理器方法处理完毕之后需要资源跳转但是资源之间不需要传递数据，也就是说只有视图`View`没有数据`Model`。如果你想传递数据可以使用`HttpServletResponse`

```java
package com.zwm.controller;

import com.zwm.pojo.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller(value = "myController7")
@RequestMapping(value = "/user", method = RequestMethod.POST)
public class MyController7 {
    @RequestMapping("/some7.do")
    public String doSome(HttpServletRequest httpServletRequest, Student student) {
        //获取到前端传递的数据并放置到该域中
        httpServletRequest.setAttribute("studentName", student.getName());
        httpServletRequest.setAttribute("studentAge", student.getAge());
        return "/show";
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
<form action="/user/some7.do" method="post">
    姓名：<input type="text" name="name">
    年龄：<input type="text" name="age">
    <input type="submit" value="提交数据">
</form>
</body>
</html>
```

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h3>/WEB-INF/view/show.jsp从request作用域获取数据</h3><br/>
<h3>student.name:${studentName}</h3><br/>
<h3>student.age:${studentAge}</h3><br/>
</body>
</html>
```

如果不想使用`springApplicationContext`配置的前缀后缀信息，可以使用视图的完整路径，但是需要把`springApplicationContext`中的配置给注释掉【也就是用了一个你不能用另外一种方式】：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
    <context:component-scan base-package="com.zwm.controller"/>
    <!--<bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/view"/>
        <property name="suffix" value=".jsp"/>
    </bean>-->
</beans>
```

```java
package com.zwm.controller;

import com.zwm.pojo.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller(value = "myController7")
@RequestMapping(value = "/user", method = RequestMethod.POST)
public class MyController7 {
    @RequestMapping("/some7.do")
    public String doSome(HttpServletRequest httpServletRequest, Student student) {
        //获取到前端传递的数据并放置到该域中
        httpServletRequest.setAttribute("studentName", student.getName());
        httpServletRequest.setAttribute("studentAge", student.getAge());
        return "/WEB-INF/view/show.jsp";
    }
}
```

#### 4.3.3 `void`

`void`既不表示数据`Model`也不表示视图`view`，常用于`Ajax`请求当中。模拟`Ajax`：

1. 引入`jackson`依赖：

   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   
   <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
       <modelVersion>4.0.0</modelVersion>
   
       <groupId>com.zwm</groupId>
       <artifactId>SpringMVCStudy</artifactId>
       <version>1.0-SNAPSHOT</version>
       <packaging>war</packaging>
   
       <properties>
           <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
           <maven.compiler.source>1.8</maven.compiler.source>
           <maven.compiler.target>1.8</maven.compiler.target>
       </properties>
   
       <dependencies>
           <dependency>
               <groupId>javax.servlet</groupId>
               <artifactId>servlet-api</artifactId>
               <version>2.5</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-webmvc</artifactId>
               <version>5.2.12.RELEASE</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-aspects</artifactId>
               <version>5.3.17</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-aop</artifactId>
               <version>5.2.12.RELEASE</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-web</artifactId>
               <version>5.2.12.RELEASE</version>
           </dependency>
           <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
               <version>8.0.27</version>
           </dependency>
           <dependency>
               <groupId>org.mybatis</groupId>
               <artifactId>mybatis-spring</artifactId>
               <version>1.3.1</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-jdbc</artifactId>
               <version>5.2.2.RELEASE</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-tx</artifactId>
               <version>5.2.12.RELEASE</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-beans</artifactId>
               <version>5.2.12.RELEASE</version>
           </dependency>
           <dependency>
               <groupId>org.springframework</groupId>
               <artifactId>spring-context</artifactId>
               <version>5.2.12.RELEASE</version>
           </dependency>
           <dependency>
               <groupId>junit</groupId>
               <artifactId>junit</artifactId>
               <version>4.11</version>
               <scope>test</scope>
           </dependency>
           <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok</artifactId>
               <version>1.16.18</version>
           </dependency>
           <dependency>
               <groupId>com.fasterxml.jackson.core</groupId>
               <artifactId>jackson-core</artifactId>
               <version>2.9.0</version>
           </dependency>
           <dependency>
               <groupId>com.fasterxml.jackson.core</groupId>
               <artifactId>jackson-databind</artifactId>
               <version>2.9.0</version>
           </dependency>
       </dependencies>
   
       <build>
           <resources>
               <resource>
                   <directory>src/main/java</directory>
                   <includes>
                       <include>**/*.properties</include>
                       <include>**/*.xml</include>
                   </includes>
                   <filtering>false</filtering>
               </resource>
           </resources>
       </build>
   </project>
   ```

2. 在用户请求页面定义好，实现`javascript`脚本，这里使用`jQuery`

   ```jsp
   <%@ page contentType="text/html;charset=UTF-8" language="java" %>
   <html>
   <head>
       <title>Title</title>
       <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.js"></script>
       <script type="text/javascript">
           $(function () {
               $("#btn").click(function () {
                   $.ajax({
                       url: "user/some8.do",
                       data: {
                           name: "smith",
                           age: 18
                       },
                       type: "post",
                       // dataType: "text",
                       success: function (resp) {
                           alert(resp)
                       }
                   })
               })
           });
       </script>
   </head>
   <body>
   <button id="btn">点击发起Ajax请求</button>
   </body>
   </html>
   ```

   这里重点说下`dataType`以及`success: function(resp)`，这里的`dataType`是预期服务器会返回的数据类型，也就是说，如果服务器返回的是`json`格式的数据，那这里就要填写`json`才可以获取到，如果你在这里填的是`text`，但是返回的是`json`数据是获取不到的，这里的获取就是`resp`，同样，如果你这里填的是`json`，但是返回的数据是一个字符串`text`内容，同样是获取不到的。这个`dataType`需要跟服务器端的`httpServletResponse.setContentType("'application/json;charset=utf-8'")`保持一致，如果没写就会按照响应的格式自动匹配。仔细研究下应该不难理解。

3. 控制器对象方法

   ```java
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
   ```

#### 4.3.4 `Object`

返回值数据类型为`object`代表的是`Model`，不返回视图。如果要返回的`String`类型的数据但是返回值也是`String`可以加一个`@ResponseBody`注解，可以适用对象表示的数据响应`Ajax`请求。在`void`已经加入过依赖了所以这里不在赘述。

在`4.3.3`这一小节中虽然返回值是`void`也就是说没有返回任何数据也没有资源跳转，但是我们依然可以通过`HttpServletResponse`给前端页面传递数据，传递`json`格式数据比如对象：`student`对象这些。再回顾一下：

```java
package com.zwm.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zwm.pojo.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
}
```

可以看到如果要返回一个对象，需要进行非常繁琐的操作，需要先使用`ObjectMapper`将`student`对象转化为`json`格式的字符串，然后又要将返回值类型写入：`application/json;charset=utf-8`，使用`PrintWriter`返回。非常的繁琐，能不能直接就返回`return student`呢？可以做到吗？当然可以！

`SpringMVC`要想将自定义的对象返回，需要使用到`HttpMessageConverter`即消息转换器来完成。而`HttpMessageConverter`消息转换器的开启需要使用到注解驱动即`<mvc:annotation-driven>`来完成。当`DispatcherServlet`初始化`SpringMVC`容器时，在`<mvc:annot7ation-driven>`创建注解驱动的时候就会默认创建`8`个`HttpMessageConverter`消息转换器接口实现类的对象。这里的`8`个是针对当前版本`5.2.12.Release`

创建的`8`个对象中有两个需要学习：

1. `StringHttpMessageConverter`：负责读取和写入字符串格式的数据
2. `MappingJackson2HttpMessageConverter`：负责读取和写入`json`格式的数据

`HttpMessageConverter<T>`是一个接口，在`Spring3.0`的时候添加，表示：将请求信息转化为`T`类对象，然后将该对象作为相应信息输出。

该接口下定义的四个方法需要学习：一个是能否转，一个是能否写到响应流当中。响应流支持的类型在`MediaType`中定义。

如果没有声明注解驱动则没有`MappingJackson2HttpMessageConverter`对象的铲射高，就无法实现将对象`Object`转换成`json`格式的数据。

`@ResponseBody`相当于`PrintWriter`，`<mvc:annotation-drivern>`相当于`ObjectMapper objectMapper.writeValueAsString(student)`：

```java
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
}
```

还可以返回`List`集合：

```java
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
}
```

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn").click(function () {
                $.ajax({
                    url: "user/some11.do",
                    data: {
                        name: "smith",
                        age: 18
                    },
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        $.each(data, function (i, n) {
                            alert(n.name + "---" + n.age)
                        })
                    }
                })
            })
        })
    </script>
<body>
<button id="btn">点击发起Ajax请求</button>
</body>
</html>
```

还可以直接返回字符串类型的数据：

```java
@RequestMapping(value = "/some12.do", method = RequestMethod.POST)
@ResponseBody
public String doSomeString() {
    return "===欢迎使用SpringMVC注解开发===";
}
```

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#btn").click(function () {
                $.ajax({
                    url: "user/some12.do",
                    data: {
                        name: "smith",
                        age: 18
                    },
                    type: "post",
                    dataType: "text",
                    success: function (data) {
                        alert(data)
                    }
                })
            })
        })
    </script>
<body>
<button id="btn">点击发起Ajax请求</button>
</body>
</html>
```

可以看到前端页面返回的中文是乱码的，需要在控制器类方法设置下`MIME: Content-Type: text/plain;charset=utf-8;`

### 4.4 解读`url-pattern`

在`Apache Tomcat`的`conf`目录下的`web.xml`中可以发现有一个名为`DefaultServlet`，并且可以看到`<load-on-startup>1</load-on-startup>`表示在`Tomcat`服务器启动时就创建，`DefaultServlet`时用来请求静态资源和没有映射给`DispatcherServlet`的其它`Servlet`请求。

当我们在`web.xml`下设置的`url-pattern`为`/`时，此时所有的请求包括静态资源的请求都会交给`DispatcherServlet`来管控，这样一来静态资源和没有映射的请求就无法自动访问了，这也是为什么之前在使用`jQuery`的时候，我引入本地的资源无法导入，后面是使用在线的`jQuery`资源。所以为了避免这些动态资源和没有添加映射的路径无法自动访问，我们可以做一下设置：【在`SpringMVCApplicationContext.xml`配置文件配置】

1. 我们可以将没有设置映射的请求以及静态资源都交给`Tomcat`的默认`Servlet - DefaultServlet`

   ```xml
   <mvc:default-servlet-handler>
   ```

2. 声明资源所在地址，现在不是无法处理静态资源的`URL`路径吗？那我就亲自告诉你这个资源在哪个位置，你要获取静态资源你就从这里去获取，`location`代表静态资源所在的目录，注意不要将静态资源放在`/WEB-INF`目录下，因为该目录时安全路径外界是无法直接访问的，`mapping`可以简单理解为访问的请求`url`路径

   ```xml
   <mvc:resources location = "/images/", mapping = "/images/**"/>
   ```

除此之外顺带提一下，就如`4.3`中返回数据类型看到的一样，如果需要返回的是`json`格式的数据，需要添加：`<mvc:annotation-driven/>`

只要添加上了`DefaultServlet`，测试后发现可以直接访问本地的`jQuery`资源，怪不得之前一直无法访问...
