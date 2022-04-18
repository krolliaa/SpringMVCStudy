package com.zwm.handler;

import com.zwm.exception.AgeException;
import com.zwm.exception.NameException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MyExceptionHandler {

    @ExceptionHandler(value = NameException.class)
    public ModelAndView nameError(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "姓名异常！");
        modelAndView.addObject("ex", exception);
        modelAndView.setViewName("nameError");
        return modelAndView;
    }

    @ExceptionHandler(value = AgeException.class)
    public ModelAndView ageError(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "年龄异常！");
        modelAndView.addObject("ex", exception);
        modelAndView.setViewName("ageError");
        return modelAndView;
    }

    @ExceptionHandler
    public ModelAndView otherError(Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("msg", "其它异常！");
        modelAndView.addObject("ex", exception);
        modelAndView.setViewName("otherError");
        return modelAndView;
    }
}
