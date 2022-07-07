# 2022年06月28日

## `SpringMVC`简介

### 什么是`MVC`

> `MVC`是一种软件架构的思想，将软件按照模型、视图、控制器来划分。`Model View Controller`。
>
> 用户通过视图层发送请求到服务器，在服务器中请求被`Controller`接收，`Controller`调用相应的`Model`层处理请求，处理完毕之后将结果返回到`Controller`层，`Controller`根据请求处理的结果找到相应的`View`视图，渲染数据之后响应给浏览器。

### 什么是`SpringMVC`

> 是`Spring`的一个后续产品，为其一个子项目。是`Spring`为表述层提供的一套完整的解决方案。三层架构分为：表述层、业务逻辑层、数据访问层。表述层表示前台页面和后台`Servlet`。
>
> `SpringMVC`的特点：
>
> - `Spring`家族原生产品，与`IOC`容器等基础设施无缝对接
> - 基于原生的`Servlet`，拥有强大的前端控制器`DispatcherServlet`，对请求和相应进行统一处理
> - 大幅提高效率，代码清新简洁，性能卓越

### `SpringMVC`实例

- 创建`SpringMVC_demo01`模块

- 添加依赖于`pom.xml`中

  这里特别说明下`javax.servlet-api`这个包，因为`Tomcat`默认已经有了`jsp + servlet-api`，所以在生成包的时候不再需要导入这个包。所以这里的`scope`就写好了：`provided`表示已被提供。

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <project xmlns="http://maven.apache.org/POM/4.0.0"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
      <modelVersion>4.0.0</modelVersion>
  
      <groupId>com.kk</groupId>
      <artifactId>SpringMVC_demo01</artifactId>
      <version>1.0-SNAPSHOT</version>
      <packaging>war</packaging>
  
      <dependencies>
          <dependency>
              <groupId>org.springframework</groupId>
              <artifactId>spring-webmvc</artifactId>
              <version>5.2.12.RELEASE</version>
          </dependency>
          <dependency>
              <groupId>ch.qos.logback</groupId>
              <artifactId>logback-classic</artifactId>
              <version>1.2.3</version>
          </dependency>
          <dependency>
              <groupId>javax.servlet</groupId>
              <artifactId>javax.servlet-api</artifactId>
              <version>3.1.0</version>
              <scope>provided</scope>
          </dependency>
          <dependency>
              <groupId>org.thymeleaf</groupId>
              <artifactId>thymeleaf-spring5</artifactId>
              <version>3.0.12.RELEASE</version>
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

- 添加`webapp`目录 ---> 添加`web.xml`【`ctrl + shift + alt + s`找到`Facets`创建即可】

- 配置`Web.xml` ---> 前端控制器`DispatcherServlet`，在上一步我们已经设置好了`web.xml`在`src/main/resources/webapp/WEB-INF/`目录中，配置如下：

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
           version="4.0">
      <servlet>
          <servlet-name>springMVC</servlet-name>
          <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
      </servlet>
      <servlet-mapping>
          <servlet-name>springMVC</servlet-name>
          <!--设置拦截需要处理的请求路径：/除了不能匹配.jsp路径的请求其余的都可以,因为.jsp的本质就是一个 Servlet 它需要特定的 Servlet 来处理。如果可以处理的话，那就会交给 DispatcherServlet 来处理就找不到 .jsp 页面在哪里了-->
          <url-pattern>/</url-pattern>
      </servlet-mapping>
  </web-app>
  ```

  在这种配置下，`SpringMVC`核心配置文件默认就会放在`WEB-INF`下，文件名为：`SpringMVC-servlet.xml`，但是我们通常是将核心配置文件等统一放到`resources`目录下的，那现在要怎么做呢？此时我们就需要在`web.xml`中配置`<init-param>`标签，设置其核心配置文件的位置和名称。并且**<font color="red">通过`<load-on-startup>`标签设置`SpringMVC`前端控制器的初始化时间。</font>**【`Servlet`在第一次访问时初始化，因为前端控制器`DispatcherServlet`需要处理几乎所有的请求，所以就会有大量的请求，如果都放在第一次访问的时候才初始化，这效率就非常低了，所以最好是在服务器启动时就初始化】

  关于`<load-on-startup>`的值，必须是一个整数，通常不等于`0`，数值越小表示创建的时间越早。如果两个`servlet`中配置`<load-on-starup>`的值一样，则容器会自己选择先创建哪个后创建哪个。如果该值小于`0`或者没有配置该标签的时候，该`Servlet`只有在被真正使用的时候才会创建。

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_4_0.xsd"
           version="4.0">
      <servlet>
          <servlet-name>springMVC</servlet-name>
          <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
          <init-param>
              <param-name>contextConfigLocation</param-name>
              <param-value>classpath:springMVCConfig.xml</param-value>
          </init-param>
          <load-on-startup>1</load-on-startup>
      </servlet>
      <servlet-mapping>
          <servlet-name>springMVC</servlet-name>
          <!--设置拦截需要处理的请求路径：/除了不能匹配.jsp路径的请求其余的都可以-->
          <url-pattern>/</url-pattern>
      </servlet-mapping>
  </web-app>
  ```

- 创建请求控制器：前端控制器只是负责将浏览器发送的请求进行了统一处理，但是具体的请求有不同的处理过程，这就需要请求控制器来完成了。请求控制器中每一个处理请求的方法称为**控制器方法**。这里我们使用注解的方式来生成控制器对象。`@Controller`

  ```java
  package com.kk.controller;
  
  import org.springframework.stereotype.Controller;
  
  @Controller
  public class HelloController {
  
  }
  ```

- 配置`springMVC`核心配置文件 ---> `SpringMVCConfig.xml` ---> 配置扫描组件 + `ThymeleafViewResolver`视图解析器

  ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
      <!--配置组件扫描器-->
      <context:component-scan base-package="com.kk.controller"/>
      <!--配置Thymeleaf视图解析器-->
      <bean id="thymeleafViewResolver" class="org.thymeleaf.spring5.view.ThymeleafViewResolver">
          <!--配置视图解析器的优先级-->
          <property name="order" value="1"/>
          <!--配置解析视图时用的编码-->
          <property name="characterEncoding" value="UTF-8"/>
          <!--配置模板引擎-->
          <property name="templateEngine">
              <bean class="org.thymeleaf.spring5.SpringTemplateEngine">
                  <!--配置模板引擎中的模板解析-->
                  <property name="templateResolver">
                      <bean class="org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver">
                          <!--视图前缀【必须配置】-->
                          <property name="prefix" value="/WEB-INF/templates"/>
                          <!--视图后缀【必须配置】-->
                          <property name="suffix" value=".html"/>
                          <!--配置模板模型 HTML5-->
                          <property name="templateMode" value="HTML5"/>
                          <!--配置页面编码 UTF-8-->
                          <property name="characterEncoding" value="UTF-8"/>
                      </bean>
                  </property>
              </bean>
          </property>
      </bean>
  </beans>
  ```

- 使用`thymeleaf`：要想使用需要在`html`文件中加上`xmlns` ---> 如下：

  ```html
  <!DOCTYPE html>
  <html lang="en" xmlns:th="http://www.thymeleaf.org">
  <head>
      <meta charset="UTF-8">
      <title>Title</title>
  </head>
  <body>
  
  </body>
  </html>
  ```

- 测试`HelloWorld`实现浏览器访问`/`然后返回`index.html`页面，页面如下：【因为我们将`index.html`页面放到了`WEB-INF`目录中，外界时访问不到的，只有通过请求或者请求转发才可以访问到，因此我们需要使用`Controller`中的控制器类控制器对象来访问该页面】

  ```html
  <!DOCTYPE html>
  <html lang="en" xmlns:th="http://www.thymeleaf.org">
  <head>
      <meta charset="UTF-8">
      <title>Title</title>
  </head>
  <body>
      <h1>首页</h1>
  </body>
  </html>
  ```

  `HelloController.java`中配置处理的请求路径：【需要使用`@RequestMapping`注解】

  ```java
  package com.kk.controller;
  
  import org.springframework.stereotype.Controller;
  import org.springframework.web.bind.annotation.RequestMapping;
  
  @Controller
  public class HelloController {
      @RequestMapping(value = "/")
      public String index() {
          return "index";
      }
  }
  ```

  接着部署下`Tomcat`服务器，然后就可以进行测试了，发现是可以成功访问到首页的
  
- `HelloWorld`：访问指定页面 ---> 首页中添加超链接：使用`thymeleaf`获取当前`web`路径`<a th:href="@{/target}"></a>`

  - 修改`index.html`

    ```html
    <!DOCTYPE html>
    <html lang="en" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>Title</title>
    </head>
    <body>
        <h1>首页</h1>
        <a th:href="@{/target}">访问 target 页面，获取 HelloWorld</a>
    </body>
    </html>
    ```

  - 创建`target.html`

    ```html
    <!DOCTYPE html>
    <html lang="en" xmlns:th="http://www.thymeleaf.org">
    <head>
        <meta charset="UTF-8">
        <title>Target</title>
    </head>
    <body>
    HelloWorld
    </body>
    </html>
    ```

## `@RequestMapping`注解

### `@RequestMapping`注解的功能

其作用就是将请求和处理请求的控制器方法关联起来，建立映射关系。`SpringMVC`中的`DispatcherServlet`前端控制器接收到用户的请求，就会在映射关系中找对应的控制器方法来处理这个请求。

### `@RequestMapping`注解的位置

- 放在类上，表示设置映射请求的请求路径统一的初始信息【就是打头路径】

- 放在方法上，表示设置映射请求的请求路径的具体信息【就是最末尾路径】

- 下面是一个例子：访问`/test/testRequestMapping`时才访问得到`success.html`页面

  ```java
  package com.kk.controller;
  
  import org.springframework.stereotype.Controller;
  import org.springframework.web.bind.annotation.RequestMapping;
  
  @Controller
  @RequestMapping(value = "/test")
  public class RequestMappingController {
      @RequestMapping(value = "/testRequestMapping")
      public String testRequestMapping() {
          return "success";
      }
  }
  ```

  ### `@RequestMapping`注解的`value`属性

  `value`属性匹配的是请求路径。

  通过源码可以看到可以给`value`属性设置多个值 ---> `String`类型的数组。

  ```java
  //
  // Source code recreated from a .class file by IntelliJ IDEA
  // (powered by FernFlower decompiler)
  //
  
  package org.springframework.web.bind.annotation;
  
  import java.lang.annotation.Documented;
  import java.lang.annotation.ElementType;
  import java.lang.annotation.Retention;
  import java.lang.annotation.RetentionPolicy;
  import java.lang.annotation.Target;
  import org.springframework.core.annotation.AliasFor;
  
  @Target({ElementType.TYPE, ElementType.METHOD})
  @Retention(RetentionPolicy.RUNTIME)
  @Documented
  @Mapping
  public @interface RequestMapping {
      String name() default "";
  
      @AliasFor("path")
      String[] value() default {};
  
      @AliasFor("value")
      String[] path() default {};
  
      RequestMethod[] method() default {};
  
      String[] params() default {};
  
      String[] headers() default {};
  
      String[] consumes() default {};
  
      String[] produces() default {};
  }
  ```

  ```java
  package com.kk.controller;
  
  import org.springframework.stereotype.Controller;
  import org.springframework.web.bind.annotation.RequestMapping;
  
  @Controller
  @RequestMapping(value = {"/test1", "/test2"})
  public class RequestMappingController {
      @RequestMapping(value = "/testRequestMapping")
      public String testRequestMapping() {
          return "success";
      }
  }
  ```

### `@RequestMapping`注解的`method`属性

`method`属性匹配的是请求方式。是一个`RequestMethod`类型的数组，表示请求映射可以匹配多种请求方式。若当前请求的请求地址满足请求映射的`value`属性【不满足将直接报`404`错误】，但是请求方式不满足`method`则浏览器报`405`错误，表示请求方式错误。通过`Postman`可以验证上述说法。

```html
<!doctype html>
<html lang="zh">

<head>
	<title>HTTP状态 405 - 方法不允许</title>
	<style type="text/css">
		body {
			font-family: Tahoma, Arial, sans-serif;
		}

		h1,
		h2,
		h3,
		b {
			color: white;
			background-color: #525D76;
		}

		h1 {
			font-size: 22px;
		}

		h2 {
			font-size: 16px;
		}

		h3 {
			font-size: 14px;
		}

		p {
			font-size: 12px;
		}

		a {
			color: black;
		}

		.line {
			height: 1px;
			background-color: #525D76;
			border: none;
		}
	</style>
</head>

<body>
	<h1>HTTP状态 405 - 方法不允许</h1>
	<hr class="line" />
	<p><b>类型</b> 状态报告</p>
	<p><b>消息</b> Request method &#39;POST&#39; not supported</p>
	<p><b>描述</b> 请求行中接收的方法由源服务器知道，但目标资源不支持</p>
	<hr class="line" />
	<h3>Apache Tomcat/8.5.78</h3>
</body>

</html>
```

### `@RequestMapping`结合请求方式的派生注解

> - 处理`GET`请求的映射 ---> `@GetMapping` ---> 相当于`@RequestMapping(method = RequestMethod.GET)`
> - 处理`POST`请求的映射 ---> `@PostMapping` ---> 相当于`@RequestMapping(method = RequestMethod.POST)`
> - 处理`PUT`请求的映射 ---> `@PutMapping` ---> 相当于`@RequestMapping(method = RequestMethod.PUT)`
> - 处理`DELETE`请求的映射 ---> `@DeleteMapping` ---> 相当于`@RequestMapping(method = RequestMethod.DELETE)`

```java
@GetMapping(value = "/testRequestMapping2")
public String testPostMapping() {
    return "success";
}
```

目前浏览器只支持`GET POST`请求方式发送请求给服务器，就算你在前端页面比如一个表单设置的`methdo`为其它请求方式的字符串`PUT DELETE`等，还是默认会转化为`GET`的请求方式。

倘若执意需要发送`PUT DELETE`请求，则需要通过`spring`提供的过滤器`HandlerHttpMethodFilter`，该方式在`Restful`中会学习。

### `@RequestMapping`注解的`params`属性

该属性表示的是请求参数`parameter`，是一个字符串类型的数组，可以通过四种表达式设置请求参数和请求映射的匹配关系：

- `param`：要求请求映射所匹配的请求会必须携带`param`请求参数
- `!param`：要求请求映射所匹配的请求必须不能携带`param`请求参数
- `param=value`：要求请求映射所匹配的请求必须携带`param`请求参数且`param=value`
- `param!=value`：要求请求映射所匹配的请求必须携带`param`请求参数但是`param!=value`

下面是一个可以说明`param`属性的例子：【我觉得可以拿来做数据库账号的比对使用，但是感觉不太安全】

```java
@RequestMapping(value = "/testRequestMapping3", method = RequestMethod.GET, params = {"username=admin", "password!=123456"})
public String testRequestMappingParam() {
    return "success";
}
```

此时若访问链接：`http://localhost:8080/test1/testRequestMapping3?username=zhangsan&password=654321`不可以访问，因为`username != admin`，只有当`username`为`admin`并且`password`不为`123456`的时候才可以访问，否则将返回`400`错误【客户端错误】。

如果使用`Thymeleaf`添加参数则应该使用：`<a th:href="@{/test1/testRequestMapping3(username='admin', password='654321')}">测试 @RequestMapping 中的 params 参数</a>`

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>首页demo02</h1>
    <a th:href="@{/test1/testRequestMapping3(username='admin', password='654321')}">测试 @RequestMapping 中的 params 参数</a>
</body>
</html>
```

### `@RequestMapping`注解的`headers`属性

- `@RequestMapping`注解的`headers`属性通过请求的请求头信息匹配请求映射
- `RequestMapping`注解的`headers`属性是一个字符串类型的数组，可以通过四种表达式设置请求头信息和请求映射的匹配关系
  - `header`：要求请求映射所匹配的请求必须携带`header`请求头信息
  - `!header`：要求请求映射所匹配的请求必须不能携带`header`请求头信息
  - `header=value`：要求请求映射所匹配的请求必须携带`header`请求头信息且请求头信息必须等于`value`
  - `header!=value`：要求请求映射所匹配的请求必须携带`header`请求头信息且请求头信息必须不等于`value`
- 若请求路径成功但是不满足`headers`属性则报`404资源未找到`错误，`params`属性则为`400`，而`method`属性则为`405`，`value`属性则为`404`

```java
@RequestMapping(value = "/testRequestMapping4", method = RequestMethod.GET, params = {"username=admin", "password!=123456"}, headers = {"Host=localhost:8081"})
public String testRequestMappingHeaders() {
    return "success";
}
```

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>首页demo02</h1>
    <a th:href="@{/test1/testRequestMapping4(username='admin', password='654321')}">测试 @RequestMapping 中的 headers 参数</a>
</body>
</html>
```

# 2022年06月29日

## `SpringMVC`支持`ant`风格的路径

**`ant`风格可以简单理解为模糊匹配。**

- `?`：表示任意的单个字符

  ```java
  //http://localhost:8080/test1/ad/testAnt1
  @GetMapping(value = "/a?a/testAnt1")
  public String testAnt1() {
      return "success";
  }
  ```

- `*`：表示任意的`0`个或者多个字符，即有或没有都可以

  ```java
  //http://localhost:8080/test1/aaabbbccc/testAnt2
  @GetMapping(value = "/a*/testAnt2")
  public String testAnt2() {
      return "success";
  }
  ```

- `**`：表示任意的一层或多层目录，没有也可以即：`http://localhost:8080/test1/testAnt3`

  ```java
  //http://localhost:8080/test1/a/b/c/d/testAnt3
  @GetMapping(value = "/**/testAnt3")
  public String testAnt3() {
      return "success";
  }
  ```

注意：在使用`**`的时候，只能使用`/**/xxx`的方式，如果你是这样写的：`test1/a**a/testAnt3`则这个`a**a`真的是单独当作一个`*`来写的，你要是想要达到多层目录的效果，那你只能使用：`/**/xxx`这样当你访问：`/a/b/c/d/xxx`的时候依然可以访问到。

## `SpringMVC`支持路径中的占位符`Restful`

- 原始方式：`/deleteUser?id=1`
- `Restful`方式：`/deleteUser/1`

`SpringMVC`路径中的占位符常用于`Restful`风格中，当请求路径中将某些数据通过路径的方式传输到服务器中，就可以在相应的`@RequestMapping`注解中的`value`属性通过占位符表示传输的数据，在通过`@PathVariable`注解，将占位符所表示的数据赋值给控制器方法的形参。

```java
//http://localhost:8080/test1/testRestful/1/admin
@RequestMapping(value = "/testRestful/{id}/{username}")
public String testRestful(@PathVariable(value = "id") String id, @PathVariable(value = "username") String username) {
    System.out.println("id: " + id + " ---> username: " + username);
    return "success";
}
```

## `SpringMVC`获取请求参数

- **<font color="red">使用`ServletAPI`获取请求参数</font>**

  `HttpServletRequest ---> httpServletRequest.getParameter("")`

  ```java
  package com.kk.controller;
  
  import org.springframework.stereotype.Controller;
  import org.springframework.web.bind.annotation.RequestMapping;
  
  import javax.servlet.http.HttpServletRequest;
  
  @Controller
  public class ParameterController {
      @RequestMapping(value = "/servletAPI")
      public String test(HttpServletRequest httpServletRequest) {
          String username = httpServletRequest.getParameter("username");
          String password = httpServletRequest.getParameter("password");
          System.out.println(username + ":" + password);
          return "success";
      }
  }
  ```

  ```html
  <a th:href="@{/servletAPI(username = 'admin', password = '123456')}">ServletAPI 获取请求参数：HttpServletRequest</a>
  ```

- **<font color="red">通过控制器方法的形参获取请求参数</font>**

  前提条件：形参跟传递进来的参数的名称一致

  在控制器方法的形参位置，设置和请求参数同名的形参，当浏览器发送请求，匹配到请求映射时，在`DispatcherServlet`中就会将请求参数赋值给相应的形参。

  ```java
  @RequestMapping(value = "/parameter")
  public String testParam(String username, String password) {
      System.out.println(username + ":" + password);
      return "success";
  }
  ```

  ```html
  <a th:href="@{/parameter(username = 'admin', password = '123456')}">获取请求参数：控制器方法形式参数</a>
  ```

  如果是复选框的话，则可以使用一个单独的字符串【逗号分隔】，也可以使用一个字符串数组

  ```java
  @RequestMapping(value = "/testParameter")
  public String testParameter(String username, String password, String hobby) {
      System.out.println(username + ":" + password + ":" + hobby);
      return "success";
  }
  ```

  ```java
  @RequestMapping(value = "/testParameter")
  public String testParameter(String username, String password, String[] hobby) {
      System.out.println(username + ":" + password + ":" + Arrays.toString(hobby));
      return "success";
  }
  ```

  ```html
  <form th:action="@{/testParameter}" method="post">
      username: <input type="text" name="username"/><br/>
      password: <input type="text" name="password"/><br/>
      hobby:
      <input name="hobby" type="checkbox" value="a"/>a
      <input name="hobby" type="checkbox" value="b"/>b
      <input name="hobby" type="checkbox" value="c"/>c
      <input type="submit" value="SpringMVC获取参数">
  </form>

- **<font color="red">通过`@RequestParam()`获取请求参数【解决名称不一致问题】</font>**

  `@RequestParam()`默认的`required`属性是`true`，表示必须传输名称为`value`的参数，否则报错报`400`错误。`defaultValue`表示默认值。该注解可以解决传递进来的名称跟后台名称不一致的问题。

  `@RequestParam(value = "", required = true/false, defaultValue = "")`

- **<font color="red">`@RequestHeader`</font>**

  - `@RequestHeader`是将请求头信息和控制器方法的形参创建映射关系

  - `@RequestHeader`注解一共有三个属性：`value required defaulValue`，用法同`@RequestParam`

- **<font color="red">`@CookieValue`</font>**

  - `@CookieValue`是将`cookie`数据和控制器方法的形参创建映射关系

  - `@CookieValue`注解一共有三个属性：`value required defaulValue`，用法同`@RequestParam`

- **<font color="red">通过`POJO`获取请求参数</font>**

  - 可以在控制器方法的形参位置设置一个实体类类型的形参，此时若浏览器传输的请求参数的参数名和实体类中的属性名一致时，则请求参数就会为此属性赋值

    ```html
    <form th:action="@{/testPojo}" method="post">
        用户名：<input type="text" name="username"><br>
        密码：<input type="password" name="password"><br>
        性别：<input type="radio" name="sex" value="男">男
            <input type="radio" name="sex" value="女">女<br>
        年龄：<input type="text" name="age"><br>
        邮箱：<input type="text" name="email"><br>
        <input type="submit">
    </form>
    ```

    ```java
    @RequestMapping(value = "/testPojo")
    public String testPojo(User user) {
        System.out.println(user.toString());
        return "success";
    }
    ```

    ```java
    package com.kk.pojo;
    
    public class User {
        private String username;
        private String password;
        private String sex;
        private Integer age;
        private String email;
    
        public User() {
        }
    
        public User(String username, String password, String sex, Integer age, String email) {
            this.username = username;
            this.password = password;
            this.sex = sex;
            this.age = age;
            this.email = email;
        }
    
        public String getUsername() {
            return username;
        }
    
        public void setUsername(String username) {
            this.username = username;
        }
    
        public String getPassword() {
            return password;
        }
    
        public void setPassword(String password) {
            this.password = password;
        }
    
        public String getSex() {
            return sex;
        }
    
        public void setSex(String sex) {
            this.sex = sex;
        }
    
        public Integer getAge() {
            return age;
        }
    
        public void setAge(Integer age) {
            this.age = age;
        }
    
        public String getEmail() {
            return email;
        }
    
        public void setEmail(String email) {
            this.email = email;
        }
    
        @Override
        public String toString() {
            return "User{" +
                    "username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    ", sex='" + sex + '\'' +
                    ", age=" + age +
                    ", email='" + email + '\'' +
                    '}';
        }
    }
    ```

- **<font color="red">解决获取请求参数的乱码问题</font>**

  `User{username='??????', password='123465', sex='??·', age=1000, email='lkoipa01@gmail.com'}`

  解决获取请求参数的乱码问题，可以使用`SpringMVC`提供的编码过滤器`CharacterEncodingFilter`，但是必须在`web.xml`中扫描。并且一定要将处理编码的过滤器配置在其它过滤器之前，否则无效。

  ```xml
  <filter>
      <filter-name>CharacterEncodingFilter</filter-name>
      <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
      <init-param>
          <param-name>encoding</param-name>
          <param-value>UTF-8</param-value>
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
      <filter-name>CharacterEncodingFilter</filter-name>
      <url-pattern>/*</url-pattern>
  </filter-mapping>
  ```

# 2022年06月30日

## 域对象共享数据

域对象就是在一定范围内可以共享共享数据，通常有`3`种。

> - `request`：一次请求，多个资源共享数据
> - `session`：默认一次会话，多个请求，多个资源共享数据
> - `servletContext`：一个应用，多个会话，多个请求，多个资源共享同一份数据

### 使用`ServletAPI`向`request`域对象共享数据

```java
@RequestMapping(value = "/testServletAPI")
public String testServletAPI(HttpServletRequest httpServletRequest) {
    httpServletRequest.setAttribute("testScope", "hello, servletAPI");
    return "success";
}
```

前端获取共享参数：

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>success</h1>
    <h2 th:text="${testScope}"></h2>
</body>
</html>
```

### 使用`ModelAndView`向`request`域对象共享数据

```html
<a th:href="@{/testModelAndView}">测试使用ModelAndView向request域对象共享数据</a><br/>
```

```java
@RequestMapping(value = "/testModelAndView")
public ModelAndView testModelAndView() {
	ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("testScope", "Hello Model And View!!!");
    modelAndView.setViewName("success");
    return modelAndView;
}
```

### 使用`	Model`向`request`域对象共享数据

```java
@RequestMapping(value = "/testModel")
public String testModel(Model model) {
    model.addAttribute("testScope", "Hello Model!!!");
    return "success";
}
```

### 使用`	map`向`request`域对象共享数据

```java
@RequestMapping(value = "/testMap")
public String testMap(Map map) {
    map.put("testScope", "Hello Map!!!");
    return "success";
}
```

### 使用`	ModelMap`向`request`域对象共享数据

```java
@RequestMapping(value = "/testModelMap")
public String testModelMap(ModelMap modelMap) {
    modelMap.addAttribute("testScope", "Hello ModelMap!!!");
    return "success";
}
```

### `Model、ModelMap、Map`三者之间的关系

三者其实本质上都是`BindingAwareModelMap`类型的。`Model Map ModelMap`都是接口，而它们的实现类都是通过`BindingAwareModelMap`实现的。并且加上`servletAPI`最终都会转化为`ModelAndView`。

**<font color="red">通过源码可以看到程序先执行到`DispatcherServlet`调用了`handle()`方法，然后调用了控制器方法。【所有的其实底层都去创建了`ModelAndView`，包括`servletAPI ModelAndView Model Map ModelMap`】</font>**

### 向`session`域共享数据

```java
@RequestMapping(value = "/testSession")
public String testSession(HttpSession httpSession) {
    httpSession.setAttribute("testScope", "Hello Session!!!");
    return "success";
}
```

### 向`application`域共享数据

```java
@RequestMapping(value = "/testApplication")
public String testApplication(HttpSession httpSession) {
    ServletContext servletContext = httpSession.getServletContext();
    servletContext.setAttribute("testScope", "Hello Application!!!");
    return "success";
}
```

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <h1>success</h1>
    <h2 th:text="${testScope}"></h2>
    <h2 th:text="${session.testScope}"></h2>
    <h2 th:text="${application.testScope}"></h2>
</body>
</html>
```

# 2022年07月01日

## `SpringMVC`视图

`SpringMVC`中的视图是`View`接口，视图的作用就是渲染数据，将模型`Model`中的数据展示给用户。`SpringMVC`视图的种类很多，默认有转发视图和重定向视图。当工程引入`JSTL`依赖时，转发视图会自动转换为`JSTLView`。若使用的视图技术为`Thymeleaf`，并且配置其相关视图解析器，则解析之后得到的是`ThymeleafView`。

### `ThymeleafView`

没有任何前后缀【`forward redirect`】，返回的就是一个字符串，因为我们设置了视图解析器，则会默认自动加上前后缀。会通过转发的方式实现视图跳转。称为跳转视图。

```java
@RequestMapping(value = "/")
public String testIndex() {
    return "index";
}
```

### 转发视图

`SpringMVC`默认的转发视图为：`InternalResourceView`，当控制器方法中所设置的视图名称以`forward:`为前缀时，创建`InternalResourceView`内部资源视图，此时视图名称不会被`SpringMVC`配置文件中所配置的视图解析器解析，而是会将前缀`forward:`去掉，剩余部门作为最终路径通过转发的方式实现跳转。

```java
@RequestMapping(value = "/")
public String testIndex() {
    return "forward:index";
}
```

### 重定向视图

`SpringMVC`默认的重定向视图为：`InternalResourceView`，当控制器方法中所设置的视图名称以`redirect:`为前缀时，创建`InternalResourceView`内部资源视图，此时视图名称不会被`SpringMVC`配置文件中所配置的视图解析器解析，而是会将前缀`redirect:`去掉，剩余部门作为最终路径通过重定向的方式实现跳转。

```java
@RequestMapping(value = "/")
public String testIndex() {
    return "redirect:index";
}
```

### 视图控制器`view-controller`

当控制器方法中，仅仅用来实现页面跳转，即只需设置视图名称时，可以将处理器方法使用`view-controller`标签进行表示。【为了避免控制器方法对应的映射失效，还需加上`<mvc:annotation-driven/>`】

```xml
<!--path:设置处理的请求地址 view-name:设置请求地址所对应的视图名称-->
<mvc:view-controller path="/testView" view-name="success"></mvc:view-controller>
```

> 注：当`SpringMVC`中设置任何一个`view-controller`时，其它控制器中的请求映射将全部失效，此时需要在`SpringMVC`中的核心配置文件中设置开启`mvc`注解驱动的标签`<mvc:annotation-driven/>`

```xml
<mvc:view-controller path="/testViewController" view-name="success"/>
<mvc:annotation-driven/>
```

## `Restful`

### `Restful`简介

`Restful`全称：`Representaional State Transfer`表现层资源状态转移。

- 资源：资源是一种看待服务器的方式，即将服务器看作是由很多离散的资源组成。每个资源是服务器上一个可命名的抽象概念。因为资源是一个抽象的概念，所以它不仅仅能代表服务器文件系统中的一个文件、数据库中的一张表等等具体的东西，可以将资源设计的要多抽象有多抽象，只要想象力允许而且客户端应用开发者能够理解都可以当作资源。与面向对象设计类似，资源是以名词为核心来组织的，首先关注的是名词。一个资源可以由一个或者多个`URI`来标识。`URI`即是资源的名称，也是资源在`Web`上的地址。对某个资源感兴趣的客户端应用，可以通过资源的`URI`与其进行交互。
- 资源的表数：资源的表数是一段对于资源在某个特定时刻的状态的描述。可以在客户端-服务器端之间转移（交换）。资源的表数可以有多种格式，例如`HTML/XML/JSON/纯文本/图片/视频/音频`等等。资源的表述格 式可以通过协商机制来确定。请求-响应方向的表述通常使用不同的格式。
- 状态转移：状态转移指的是在客户端和服务端之间转移`transfer`代表资源状态的表述。通过转移和操作资源的表数，来简介实现操作资源的目的。

### `Restful`的实现

具体说，就是`HTTP`协议里面，四个表示操作方式的动词：`GET、POST、PUT、DELETE`。 它们分别对应四种基本操作：`GET`用来获取资源，`POST`用来新建资源，`PUT`用来更新资源，`DELETE`用来删除资源。 `REST`风格提倡`URL`地址使用统一的风格设计，从前到后各个单词使用斜杠分开，不使用问号键值对方式携带请求参数，而是将要发送给服务器的数据作为`URL`地址的一部分，以保证整体风格的一致性。

| 操作     | 传统方式           | REST风格                  |
| -------- | ------------------ | ------------------------- |
| 查询操作 | `getUserById?id=1` | `user/1-->get`请求方式    |
| 保存操作 | `saveUser`         | `user-->post`请求方式     |
| 删除操作 | `deleteUser?id=1`  | `user/1-->delete`请求方式 |
| 更新操作 | `updateUser`       | `user-->put`请求方式      |

### `HiddenHttpMethodFilter`

由于浏览器目前支持`GET/POST`两种请求方式，如果执意要发送`PUT/DELETES`的请求该如何发送呢？

`SpringMVC`提供了`HiddenHttpMethodFilter`帮助我们将`POST`请求转换为`PUT/DELETE`请求，当然是有条件才可以处理的：

> 1. 当前请求的请求方式必须为`post`
> 2. 当前请求必须传输请求参数`_method`【就是你要转成什么？】

满足以上条件，`HiddenHttpMethodFilter`过滤器就会将当前请求的请求方式转换为请求参数`_method`的值，因此请求参数`_method`的值才是最终的请求方式。

我们前面也学习过一个过滤器就是编码过滤器`CharacterEncodingFilter`，我们需要将其放在比`HiddenHttpMethod`前面`CharacterEncodingFilter`才起作用。类比于`CharacterFilterEncoding`在`web.xml`中注册`HiddenMethodFilter`：

```xml
<filter>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <filter-class>org.springframework.web.filter.HiddenHttpMethodFilter</filter-class>
</filter>
<filter-mapping>
    <filter-name>HiddenHttpMethodFilter</filter-name>
    <url-pattern>/*</url-pattern>
</filter-mapping>
```

**为什么`CharacterEncodingFilter`要放在前面呢？**因为我们知道这个过滤器的作用就是用来给请求参数设置编码的，所以需要放置在请求参数前面。正因如此，`HiddenHttpMethodFilter`它内部恰恰有一个获取请求参数的操作：

```java
String paramValue = request.getParameter(this.methodParam);
```

所以`CharacterEncodingFilter`需要放在前面，`HiddenHttpMethodFilter`需要放在后边。

使用时在前端这样使用即可：

```html
<form th:action="@{/user}" method="post">
    <input type="hidden" name="_method" value="PUT">
    用户名：<input type="text" name="username"><br/>
    密码：<input type="password" name="passwprd"><br/>
    <input type="submit" value="修改"/>
</form>
```

### `Restful`案例

**要求实现对员工信息的增删查改。**

# 2022年07月06日

## 处理静态资源

默认`Tomcat`是有一个`servlet`的，而因为我们还配置了一个`DispatcherServlet`所以会使用我们自己配置的，倘若开启这个`default-servlet-handler`则处理不了的资源就交给默认的`Servlet`。找得到使用的是：`RequestMappingHandlerMapping`找不到则使用的是`DefaultServletHttpRequestHandler`。

```xml
<mvc:default-servlet-handler/>
```

## `HttpMessageConverter`

报文信息转换器，将请求报文转换为`Java`对象，或将`Java`对象转换为响应报文。`HttpMessageConverter`提供了两个注解和两个类型：`@RequestBody`还有`@ResponseBody`

### `@RequestBody`

`@RequestBody`可以获取请求体，需要在控制器方法设置一个形参，使用`@RequestBody`进行标识，当前请求的请求体就会为当前注解所标识的形参赋值。

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<h1>首页</h1>
<form th:action="@{/testRequestBody}" method="post">
    用户名：<input type="text" name="username">
    密码：<input type="password" name="password">
    <input type="submit" value="提交">
</form>
</body>
</html>
```

```java
package com.kk;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HttpMessageConverterController {
    @RequestMapping(value = "/testRequestBody")
    public String testRequestBody(@RequestBody String requestBody) {
        System.out.println("RequestBody: " + requestBody);
        return "success";
    }
}
```

获取到结果：`RequestBody: username=Administrator&password=123`

### `RequestEntity`

`RequestEntity`封装请求报文的一种类型，需要在控制器方法的形参中设置该类型的形参，当前请求的请求报文就会赋值给形参，可以通过`getHeaders()`获取请求头信息，通过`getBody()`获取请求体信息。

```java
@RequestMapping(value = "/testRequestEntity")
public String testRequestEntity(RequestEntity<String> requestEntity) {
    System.out.println("requestEntity.getHeaders: " + requestEntity.getHeaders());
    System.out.println("requestEntity.getBody: " + requestEntity.getBody());
    System.out.println("requestEntity.getMethod: " + requestEntity.getMethod());
    System.out.println("requestEntity.getType: " + requestEntity.getType());
    System.out.println("requestEntity.getUrl: " + requestEntity.getUrl());
    return "success";
}
```

### `@ResponseBody`

该注解用于将返回值直接作为响应报文响应给浏览器。

```java
@RequestMapping(value = "/testResponseBody")
@ResponseBody
public String testResponseBody() {
    return "Hello ResponseBody";
}
```

浏览器显示：`Hello ResponseBody`

### `SpringMVC`处理`json`

1. 导入`jackson`依赖

   ```xml
   <dependency>
       <groupId>com.fasterxml.jackson.core</groupId>
       <artifactId>jackson-databind</artifactId>
       <version>2.9.0</version>
   </dependency>
   ```

2. 开启注解启动，此时在`HandlerAdaptor`中会自动装配一个消息转换器：`Mappingjackson2HttpMessageConverter`可以将响应到浏览器的`Java`对象转换为`json`格式的字符串。

   ```xml
   <mvc:annotation-drivern/>
   ```

3. 此时直接使用`@ResponseBody`若返回一个对象直接返回`Json`格式的字符串

   ```java
   @RequestMapping(value = "/testResponseBodyAndJackson")
   @ResponseBody
   public User testResponseBodyAndJackson() {
       return new User("张三", 188);
   }
   ```

   ```java
   {
     "name": "张三",
     "age": 188
   }

### `SpringMVC`处理`Ajax`

记得重新`clean`下：

```html
<div id="app">
    <a th:href="@{/testAjax}" @click="testAjax">testAjax</a><br>
</div>
<script type="text/javascript" th:src="@{/static/js/vue.js}"></script>
<script type="text/javascript" th:src="@{/static/js/axios.min.js}"></script>
<script type="text/javascript">
    var vue = new Vue({
        el:"#app",
        methods:{
            testAjax:function (event) {
                axios({
                    method:"post",
                    url:event.target.href,
                    params:{
                        username:"admin",
                        password:"123456"
                    }
                }).then(function (response) {
                    alert(response.data);
                });
                event.preventDefault();
            }
        }
    });
</script>
```

```java
@RequestMapping(value = "/testAjax")
@ResponseBody
public String testAjax(String username, String password) {
    System.out.println("username: " + username + "password: " + password);
    return "Hello Axios Ajax";
}
```

### `@RestController`

该注解其实就是`@RquestMapping`和`@ResponseBody`两个注解的复合注解。

### `@ResponseEntity`

用于控制器方法的返回值类型，该控制器方法的返回值就是响应到浏览器的响应报文。

## 文件上传和下载

使用`ResponseEntity`访问某`URL`路径，下载某个文件：

```java
@RequestMapping(value = "/testResponseEntity")
@ResponseBody
public ResponseEntity<byte[]> testResponseEntity(HttpSession httpSession) {
    ServletContext servletContext = httpSession.getServletContext();
    String realPath = servletContext.getRealPath("/static/img/1.jpg");
    //从磁盘中读到内存 ---> 字节输入流
    FileInputStream fileInputStream = null;
    ResponseEntity<byte[]> responseEntity  = null;
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
        if(fileInputStream != null) {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    return responseEntity;
}
```

若是文件上传，需要在`form`表单指定为`post`并且添加属性：`enctype="multipart/form-data"`。`SpringMVC`将上传的文件封装到`MultipartFile`对象中，通过此对象可以获取文件相关信息。

1. 添加文件上传的依赖

   ```xml
   <dependency>
       <groupId>commons-fileupload</groupId>
       <artifactId>commons-fileupload</artifactId>
       <version>1.3.1</version>
   </dependency>
   ```

2. 在`applicationContext.xml`配置文件中添加文件解析器配置，因为只有通过文件解析器的解析才能将文件转换为`MultipartFile`对象

   ```xml
   <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver"/>
   ```

3. 控制器方法处理上传过程

   ```java
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

4. 测试用的前端页面

   ```html
   <form th:action="@{/testCommonsFileUpload}" method="post" enctype="multipart/form-data">
       头像：<input type="file" name="multipartFile"><br>
       <input type="submit" value="上传">
   </form>
   ```

**然后你可以在`target`文件夹中找到上传的文件。**

## 拦截器

如何声明拦截器：

```xml
<bean id="myFirstInterceptor" class="com.kk.config.MyFirstInterceptor"/>
<mvc:interceptors>
    <mvc:interceptor>
        <mvc:mapping path="/**"/>
        <mvc:exclude-mapping path="/testRequestEntity"/>
        <ref bean="myFirstInterceptor"/>
    </mvc:interceptor>
</mvc:interceptors>
```

或者可以在拦截器上面加`@Component`注解，然后在配置文件中使用`<ref bean=""/>`也可以。

拦截器中有三个抽象方法：`preHandle postHandle afterCompletion`

- `preHandle`：控制器方法执行之前执行`preHandle()`，其`boolean`类型的返回值表示是否拦截或放行，返回`true`为放行，即调用控制器方法；返回`false`表示拦截，即不调用控制器方法
- `postHanlde`：控制器方法执行之后执行`postHandle()`
- `afterCompletion`：处理完视图和模型数据，渲染视图完毕之后执行`afterComplation()`

### 多个拦截器的执行顺序

如果所有的`preHandle()`返回的都是`true`：

> 此时拦截器会按照在`applicationContext.xml`配置的顺序执行，并且其中的`preHandle()`是顺序执行的而`postHandle()`和`afterCompletion()`是反序执行的，比如有`1 2 3`号拦截器，则拦截器的序号为：`1 2 3 3 2 1 3 2 1`

如果有一个拦截器返回了`false`：

> 此时拦截器只会执行返回`false`之前的`preHandle()`以及`afterCompletion()`，`postHandle()`因为压根不会执行控制器方法，所以是一个都不会执行的。
>
> ```
> firstPreHandle
> secondPreHandle
> secondafterCompletion
> firstafterCompletion
> ```

## 异常处理器

`SpringMVC`提供了一个处理异常控制器方法执行过程中所出现的异常的接口：`HandlerExceptionResolver`【处理异常解析器-异常处理器】，`HandlerExceptionResolver`接口的实现类有：`DefaultHandlerExceptionResolver`和`SimpleMappingExceptionResolver`【自定义异常处理器】。

`SimpleMappingExceptionResolver`自定义异常处理方式：

```xml
<bean class="org.springframework.web.servlet.handler.SimpleMappingExceptionResolver">
    <property name="exceptionMappings">
        <props>
            <!--设置发生异常时跳转的视图-->
            <prop key="java.lang.ArithmeticException">error</prop>
        </props>
    </property>
    <!--设置发生异常时的设置属性名，将出现的异常信息在请求域中进行共享-->
    <property name="exceptionAttribute" value="ex"/>
</bean>
```

基于注解的异常处理：`@ControllerAdvice`以及`@ExceptionHandler`

```java
package com.kk.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class HandlerExceptionController {
    @ExceptionHandler(value = {ArithmeticException.class, NullPointerException.class})
    public String handlerExceptionResolverTest(Exception exception, Model model) {
        model.addAttribute("exception", exception);
        return "error";
    }
}
```

## 注解配置`SpringMVC`

## `SpringMVC`执行流程

1. 用户向服务器发送请求，请求被`SpringMVC`的前端控制器`DispatcherServlet`所捕获

2. `DispatcherServlet`对请求的`URL`进行解析，得到请求资源标识符`URI`，然后判断请求`URI`对应的映射，分映射存在与不存在两种情况

   - 不存在：

     - 判断是否配置了`<mvc:default-servlet-handler/>`
       - 如果没有配置，则前端控制器直接报映射查找不到，客户端展示`404`错误
       - 如果配置了，则访问资源，一般为静态资源，找不到则客户端展示`404`错误，找到直接返回

   - 存在：

     3. 根据该`URI`，调用`HandlerMapping`处理器映射器查找获取该`Handler`配置的所有相关的对象【包括`Handler`对象以及`Handler`对象对应的拦截器】，最后以`HandlerExecutionChain`处理器执行链对象的形式返回给前端控制器

     4. `DispatcherServlet`根据获得的处理器执行链对象`HandlerExecutionChain`，选择一个合适的处理器适配器`HandlerApdater`去处理

     5. 如果成功获取处理器适配器`HandlerAdapter`，此时将开始执行拦截器的`preHandler(..)`方法【正向】

     6. 提取`Request`请求中的模型数据，将其填充进处理器`Handler`当作参数，然后开始执行`Handler`方法，这个`Handler`方法就是我们常说的控制器方法，处理请求。在填充`Handler`的入参过程中根据自身配置`Spring`将帮你做一些额外的工作：

        - `HttpMessageConverter`：将请求消息如`json、xml`等数据转换成一个对象，将对象转换为指定的响应信息`@RequestBody @ResponseBody RequestEntity ResponseEntity`

        - 数据转换：对请求消息进行数据转换。如`String`转换成`Integer Double`等

        - 数据格式化：对请求消息进行数据格式化。如将字符串转换成格式化数字或格式化日期等

        - 数据验证：验证数据的有效性【长度、格式等】，验证结果存储到`BindingResult`或`Error`中

     7. `Handler`执行完成之后，向`DispatcherServlet`返回一个`ModelAndView`对象
     8. 此时将开始执行拦截器的`postHandle(..)`方法【逆向】
     9. 根据返回的`ModelAndView`【此时会判断是否存在异常：如果存在异常则执行`HandlerExceptionResolver`进行异常处理】选择一个合适的`ViewResolver`进行试图解析，根据`Model`和`View`来渲染视图
     10. 渲染视图完毕执行拦截器中的`afterCompletion(..)`方法【逆向】
     11. 将渲染结果返回给客户端

## `SpringMVC`常用组件

- `DispatcherServlet`：前端控制器，不需要工程师开发，由框架提供

  作用：统一处理请求和响应，是整个流程控制的中心，由它调用其它组件处理用户的请求

- `HandlerMapping`：处理器映射器，不需要工程师开发，由框架提供

  作用：根据请求的`URL、Method`等信息查找处理器即控制器`handler`，即控制器方法

- `Handler`：处理器，需要工程师开发

  作用：在`DispatcherServlet`控制下处理器对具体的用户请求进行处理

- `HandlerAdaptor`：处理器适配器，不需要工程师开发，由框架提供

  作用：通过处理器适配器对控制器方法进行执行

- `ViewResolver`：视图解析器，不需要工程师开发，由框架提供

  作用：进行视图解析，得到响应的视图，例如：`ThymeleafView InternalResourceView RedirectView`

- `View`：视图

  作用：将模型数据通过页面展示给用户

**【前端控制器、处理器映射器、处理器适配器、视图解析器、处理器、视图】**

