package com.example.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

/**
* 通过实现 HandlerExceptionResolver 接口做全局异常处理
*
*
*/
@Configuration
public class GlobalExceptionCustom implements HandlerExceptionResolver{

	public ModelAndView resolveException(HttpServletRequest request,
				HttpServletResponse response, Object handler,Exception ex) {
	ModelAndView mv = new ModelAndView();
	//判断不同异常类型，做不同视图跳转
	if(ex instanceof ArithmeticException){
		System.out.println("=======error1================");
		mv.setViewName("error1");
	}
	if(ex instanceof NullPointerException){
		System.out.println("=======error2================");
		mv.setViewName("error2");
	}
	mv.addObject("error", ex.toString());
	return mv;
	}
}
