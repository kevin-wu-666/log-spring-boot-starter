package com.wjl.interceptor;

import com.alibaba.fastjson.JSON;
import com.wjl.annotation.LogAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: kevin
 * @date: 2019/4/11 11:14
 * Description: 日志的拦截器（特殊的Controller需要打印日志 此时该拦截器可以配合@LogAspect注解打印日志）
 */
@Slf4j
public class LogInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			HandlerMethod method = (HandlerMethod) handler;
			boolean logger = false;
			boolean controller = false;
			LogAspect logAspect = method.getMethodAnnotation(LogAspect.class);
			if (logAspect != null) {
				logger = true;
			} else {
				logAspect = method.getBeanType().getAnnotation(LogAspect.class);
				if (logAspect != null) {
					logger = true;
				}
			}
			Annotation[] annotations = method.getBeanType().getAnnotations();
			for (Annotation annotation : annotations) {
				if (annotation instanceof RestController || annotation instanceof Controller) {
					controller = true;
				}
			}
			if (logger && controller) {
				//执行打印
				log.info("【url】" + request.getRequestURI());
				//获取请求的方法，是Get还是Post请求
				log.info("【method】" + request.getMethod());
				//获取被拦截的类名和方法名
				log.info("【方法】" + method.getBeanType().getName() + "." + method.getMethod().getName());
				//参数
				log.info("【参数】" + paramWrapper(request));
				//判断是否需要打印ip
				if (logAspect.ip()) {
					log.info("【ip】" + request.getRemoteAddr());
				}
			}
		}
		return true;
	}

	public static String paramWrapper(HttpServletRequest request) {

		HashMap<String, Object> result = new HashMap<>();
		Enumeration<String> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String k = enumeration.nextElement();
			String v = request.getParameter(k);
			result.put(k, v);
		}

		Map map = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		for (Object o : map.keySet()) {
			String key = (String) o;
			result.put(key, map.get(key));
		}
		return JSON.toJSONString(result);
	}
}
