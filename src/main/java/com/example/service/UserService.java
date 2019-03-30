package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.dao.UserDao;
import com.example.po.Users;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;
	
	public String getUserName() {
		System.out.println("================successs");
		List<Users> list = userDao.selectByAll();
		return "you are success";
	}
	public void addUser(Users user) {	
		userDao.addUser(user);
	}
	
	public List<Users> selectByAll(){
		System.out.println("====service=========");
		return userDao.selectByAll();
	}
	
	public String getUserCode() {
		System.out.println("====1111111111111=========");
		return "you are right!";
	}
}
