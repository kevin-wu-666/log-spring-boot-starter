package com.wjl.interceptor;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: kevin
 * @date: 2019/4/11 11:18
 * Description: 计算所有请求的请求时间的拦截器
 */
@Slf4j
public class TimeInterceptor extends HandlerInterceptorAdapter {
	private ThreadLocal<Temp> threadLocal = new ThreadLocal<>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		long start = System.currentTimeMillis();
		Temp temp = new Temp();
		temp.setStart(start);
		temp.setUrl(uri);
		threadLocal.set(temp);
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		Temp temp = threadLocal.get();
		log.info("{}请求耗时：{}s", temp.getUrl(),(System.currentTimeMillis() - temp.getStart()) / 1000);
		threadLocal.remove();
	}

	@Data
	static class Temp {
		String url;
		long start;
	}
}
