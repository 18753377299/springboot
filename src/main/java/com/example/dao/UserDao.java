package com.example.dao;

import java.util.List;

import com.example.po.Users;


public interface UserDao {
	
	public List<Users> selectByAll();
	
	public void addUser(Users users);
	
	public Users selectById();
	
	
}
