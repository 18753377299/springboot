package com.example.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value="/file")
public class FileUploadController {
	/**
	 * 处理文件上传
	 * @throws IOException 
	 * @throws IllegalStateException 
	 * */
	@RequestMapping(value="/fileUpload",method= {RequestMethod.POST,RequestMethod.GET})
	public Map<String,Object> fileUpload(@RequestParam(value="file") MultipartFile file) throws IllegalStateException, IOException{
		System.out.println(file.getOriginalFilename());
		String path = "E:/"+file.getOriginalFilename();
		File fileNew =new File(path);
		file.transferTo(fileNew);
		
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("msg", "ok");
		return map;
		
	}
	
	
}
