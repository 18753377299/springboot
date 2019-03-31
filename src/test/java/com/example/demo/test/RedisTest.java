package com.example.demo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.SpringbootApplication;
import com.example.po.Users;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=SpringbootApplication.class)
public class RedisTest {
	
	@Autowired
	private RedisTemplate<String, Object> redisTemplate; 
	
	/**
	 * 添加一个字符串
	 * */
	@Test
	public void testSet() {
		this.redisTemplate.opsForValue().set("key", "你好呀");
	}
	/**
	 * 获取一个字符串
	 * */
	@Test
	public void testGet() {
		String  key = (String)this.redisTemplate.opsForValue().get("key");
		System.out.println("============================="+key);
	}
	
	/**
	 * 添加users对象
	 * **/
	@Test
	public void testSetUsers() {
		Users users =new Users(12, "xiaowanga");
		// 重新设置序列化器
		this.redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		// 设置对象到redis中
		this.redisTemplate.opsForValue().set("users", users);
	}
	
	/**
	 * 获取users对象
	 * **/
	@Test
	public void testGetUsers() {
		// 重新设置序列化器
		this.redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		// 设置对象到redis中
		Users users = (Users)this.redisTemplate.opsForValue().get("users");
		System.out.println(users);
	}
	/**
	* 基于设置 JSON 格式存 Users 对象
	*/
	@Test
	public void testSetUserJson() {
		Users users = new Users(13,"lqk");
		/**
		 * 设置序列化
		 * */
		this.redisTemplate.setValueSerializer(new 
				Jackson2JsonRedisSerializer<>(Users.class));
		this.redisTemplate.opsForValue().set("user_json",users);
		
	}
	
	/**
	* 基于获取 JSON 格式存 Users 对象
	*/
	@Test
	public void testGetUserJson() {
		/**
		 * 设置序列化
		 * */
		this.redisTemplate.setValueSerializer(new 
				Jackson2JsonRedisSerializer<>(Users.class));
		Users users = (Users)this.redisTemplate.opsForValue().get("user_json");
		System.out.println(users);
	}
	
	
	
	
	

}
