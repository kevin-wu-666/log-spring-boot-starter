package com.wjl.autoConfigure;

import com.wjl.interceptor.LogInterceptor;
import com.wjl.interceptor.TimeInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * author: kevin.wu
 * date: 2019/6/24 16:09
 */
@Configuration
@ConditionalOnProperty(prefix = "log",name = "type",havingValue = "interceptor")
public class MyWebConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new TimeInterceptor());
		registry.addInterceptor(new LogInterceptor());
	}
}
