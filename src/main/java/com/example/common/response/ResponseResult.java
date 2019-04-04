package com.example.common.response;

import java.util.Map;

import lombok.Data;

@Data
public class ResponseResult {
	
	// 返回结果信息
	private Object result;
	// 状态标志位
	private String  statusCode;
	// 返回的信息
	private String message;
	// 存储键值对信息
	private Map<String,Object> parameterMap;
	// 返回分页总条数
	private long totalCount;
 }
