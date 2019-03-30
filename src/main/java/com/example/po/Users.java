package com.example.po;

import lombok.Data;

@Data
public class Users {
	
	private Integer id;
	private Integer age;
	private String name;
	
	
	public Users(Integer id, Integer age, String name) {
		super();
		this.id = id;
		this.age = age;
		this.name = name;
	}


	public Users(Integer age, String name) {
		super();
		this.age = age;
		this.name = name;
	}


	public Users() {
		super();
	}
	
	
}
