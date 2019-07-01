package com.wjl.interceptor;

import com.wjl.annotation.LogAspect;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author: kevin
 * @date: 2019/2/14 18:41
 * Description:
 */
@Aspect
@Slf4j
public class MyLogAspect {

	@Pointcut("@annotation(com.wjl.annotation.LogAspect)")
	public void aop() {
	}

	@Before("aop()")
	public void detail(JoinPoint joinPoint) {
		//获取到请求的属性
		ServletRequestAttributes attributes =
				(ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		//获取到请求对象
		HttpServletRequest request = attributes.getRequest();
		log.info("url=" + request.getRequestURL());//url 包括host 相当于全路径
		log.info("uri=" + request.getRequestURI());//uri 只包除去host都有
		log.info("method=" + request.getMethod());//获取请求的方法，是Get还是Post请求
		//获取被拦截的类名和方法名
		log.info("method=" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
		log.info("args=" + LogInterceptor.paramWrapper(request));
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		LogAspect annotation = method.getAnnotation(LogAspect.class);
		if (annotation.ip()) log.info("ip={}",request.getRemoteAddr());
	}



}
