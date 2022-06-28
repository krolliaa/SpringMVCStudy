2022年06月28日

`SpringMVC`简介

1. 什么是`MVC`

   > `MVC`是一种软件架构的思想，将软件按照模型、视图、控制器来划分。`Model View Controller`。
   >
   > 用户通过视图层发送请求到服务器，在服务器中请求被`Controller`接收，`Controller`调用相应的`Model`层处理请求，处理完毕之后将结果返回到`Controller`层，`Controller`根据请求处理的结果找到相应的`View`视图，渲染数据之后响应给浏览器。

2. 什么是`SpringMVC`

   > 是`Spring`的一个后续产品，为其一个子项目。是`Spring`为表述层提供的一套完整的解决方案。三层架构分为：表述层、业务逻辑层、数据访问层。表述层表示前台页面和后台`Servlet`。
   >
   > `SpringMVC`的特点：
   >
   > - `Spring`家族原生产品，与`IOC`容器等基础设施无缝对接
   > - 基于原生的`Servlet`，拥有强大的前端控制器`DispatcherServlet`，对请求和相应进行统一处理
   > - 大幅提高效率，代码清新简洁，性能卓越

3. `SpringMVC`实例

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
     
   - 