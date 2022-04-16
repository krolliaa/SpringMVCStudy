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

