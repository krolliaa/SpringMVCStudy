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

