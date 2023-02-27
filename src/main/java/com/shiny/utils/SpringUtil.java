package com.shiny.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException{
        if(SpringUtil.applicationContext == null){
            SpringUtil.applicationContext = applicationContext;
        }
        System.out.println("===================================================");
    }

    // 获取applicationContext
    public static ApplicationContext getApplicationContext(){
        return applicationContext;
    }

    // 通过name获取Bean
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }
}
