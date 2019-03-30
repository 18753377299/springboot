package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.po.Users;
import com.example.service.UserService;
import com.netflix.infix.lang.infix.antlr.EventFilterParser.time_string_function_return;

@RestController
@RequestMapping(value="/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping(value="/adduser")
	public String addUser(@RequestBody Users user) {	
		System.out.println("success");
		userService.addUser(user);		
		return "showUser";
	} 
	
	@GetMapping(value="/showUser")
	@ResponseBody
	public ModelAndView addUser() {
		ModelAndView mav =new ModelAndView();
		System.out.println("success");
		mav.setViewName("showUser");
		return mav;
	}
	@RequestMapping(value="/showUser1")
	public String showUser1() {
		System.out.println("success");
//		Users user =new Users(12,"zhangsan");
//		userService.addUser(user);	
//		userService.selectByAll();
		
		System.out.println(userService.selectById());
		
		Users user =new Users(12,"zhangsan");
		userService.addUser(user);
		System.out.println("====================after");
		System.out.println(userService.selectById());
		
		return "showUser";
	}
	
	@RequestMapping(value="/showUser2")
	public String  showUser(Model model) {
		List<Users> list =new ArrayList<Users>();
		list.add(new Users(11,"bbbb"));
		list.add(new Users(22,"ddd"));
		int a= 10/0;
		model.addAttribute("list", list);	
		return "showUser";
	}
	
	/**
	* java.lang.ArithmeticException 
	* 该方法需要返回一个 ModelAndView：目的是可以让我们封装异常信息以及视图的指定
	* 参数 Exception e:会将产生异常对象注入到方法中
	*/
//	@ExceptionHandler(value= {ArithmeticException.class})
//	public String arithmeticException(Exception e) {
//		ModelAndView modelAndView = new ModelAndView();
//		modelAndView.addObject("error",e.toString());
//		modelAndView.setViewName("error1");
//		return e.toString();
//	}
	
	/**
	* java.lang.NullPointerException
	* 该方法需要返回一个 ModelAndView：目的是可以让我们封装异常信息以及视
	图的指定
	* 参数 Exception e:会将产生异常对象注入到方法中
	*/
//	@ExceptionHandler(value={java.lang.NullPointerException.class})
//	public ModelAndView nullPointerExceptionHandler(Exception e){
//	ModelAndView mv = new ModelAndView();
//	mv.addObject("error", e.toString());
//	mv.setViewName("error2");
//	return mv;
//	}
	
}
