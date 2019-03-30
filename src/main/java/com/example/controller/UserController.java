package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.po.Users;
import com.example.service.UserService;

@RestController
@RequestMapping(value="/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@PostMapping(value="/adduser")
	public String addUser(@RequestBody Users user) {	
		System.out.println("success");
//		userService.addUser(user);		
		return "showUser";
	} 
	
	@GetMapping(value="/showUser")
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
		String aa= userService.getUserCode();
		System.out.println("++++++++++++++++++++++"+aa);
		return "showUser";
	}
	
	@RequestMapping(value="/showUser2")
	public String  showUser(Model model) {
		List<Users> list =new ArrayList<Users>();
		list.add(new Users(11,"bbbb"));
		list.add(new Users(22,"ddd"));
		
		model.addAttribute("list", list);	
		return "showUser";
	}
}
