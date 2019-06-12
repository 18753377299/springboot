package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//(exclude= {DataSourceAutoConfiguration.class})
@MapperScan("com.example")
@ComponentScan(basePackages="com.example")
@ServletComponentScan(basePackages="com.example.common")
/*缓存配置*/
@EnableCaching
/*开启定时任务*/
@EnableScheduling
public class SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootApplication.class, args);
	}

}
