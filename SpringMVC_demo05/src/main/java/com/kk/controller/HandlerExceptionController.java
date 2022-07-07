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
