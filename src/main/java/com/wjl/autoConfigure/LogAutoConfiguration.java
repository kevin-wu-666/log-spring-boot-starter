package com.wjl.autoConfigure;

import com.wjl.interceptor.MyLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author: kevin.wu
 * date: 2019/6/24 16:09
 */
@Slf4j
@Configuration
public class LogAutoConfiguration {

	@Bean
	@ConditionalOnProperty(prefix = "log",name = "type",havingValue = "aspect")
	public MyLogAspect logAspect(){
		return new MyLogAspect();
	}
}
