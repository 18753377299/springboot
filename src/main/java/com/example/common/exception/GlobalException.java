//package com.example.common.exception;
//
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
///**
// * 全局异常处理类
// * 需要创建一个能够处理异常的全局异常类。 。 在该类上需
//* 要添加@ControllerAdvice  注解
// * */
//
//@ControllerAdvice
//public class GlobalException {
//	
//	@ExceptionHandler(value=ArithmeticException.class)
//	public String arithmeticExceptionHandler(Exception e) {
//		String except = e.toString();
//		return except;
//	}
//	
//	
//	@ExceptionHandler(value=NullPointerException.class)
//	public String nullPointerExceptionHandler(Exception e) {
//		String except = e.toString();
//		return except;
//	}
//}
