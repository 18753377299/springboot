package com.example.po;

import java.io.Serializable;

import lombok.Data;

@Data
public class Users implements Serializable{
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;
	
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
