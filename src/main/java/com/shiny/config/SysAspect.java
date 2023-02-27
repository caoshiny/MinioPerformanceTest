package com.shiny.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Slf4j
@Component
public class SysAspect {
    /**
     * 切入点
     */
    @Pointcut("execution(public * com.shiny.service.*.*(..))")
    public void log(){
    }
    private static final Logger logger = LoggerFactory.getLogger(SysAspect.class);

    // 统计请求的处理时间
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        startTime.set(System.currentTimeMillis());
        //接收到请求，记录请求内容
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
        //记录请求的内容
//        System.out.println("Aspect_URL:"+request.getRequestURL().toString());
//        System.out.println("Aspect_Method:"+request.getMethod());
    }

    @AfterReturning(returning = "ret", pointcut = "log()")
    public void doAfterReturning(Object ret) {
        //处理完请求后，返回内容
        System.out.println("方法执行时间:"+ (System.currentTimeMillis() - startTime.get()) + "ms");
    }
}
