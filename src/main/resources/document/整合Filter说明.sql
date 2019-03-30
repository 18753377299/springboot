第一种整合Filter方式： 
---在filter中添加注解@WebFilter(filterName="FirstFilter",urlPatterns="/*")
---并且在启动类中添加 @ServletComponentScan 即可整合成功

第二种整合filter方式：
在启动类中添加一下方法：
	/**
	 * 注册Filter,注册servlet的方法和这个一样
	 * */
	@Bean
	public FilterRegistrationBean getFilterRegistrationBean() {
		FilterRegistrationBean bean = new FilterRegistrationBean(
				new FirstFilter());
		bean.addUrlPatterns("/*");
		return bean;
	}

第一种整合Listener方式： 

--@WebListener
--public class FirstListener implements ServletContextListener 
---并且在启动类中添加 @ServletComponentScan 即可整合成功

第二种整合Listener方式：
---在启动类中添加一下方法：
/**
* 注册 listener
*/
@Bean
public ServletListenerRegistrationBean<SecondListener>
getServletListenerRegistrationBean(){
ServletListenerRegistrationBean<SecondListener> bean= new
ServletListenerRegistrationBean<SecondListener>(new SecondListener());
return bean;
}


CREATE TABLE
    users
    (
        id VARCHAR(32) NOT NULL,
        age VARCHAR(20) NOT NULL,
        name VARCHAR(20) NOT NULL,
       
        PRIMARY KEY (id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;
