package com.magic.wow.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;

import static java.util.Calendar.HOUR_OF_DAY;

/**
 * 基于时间的拦截器实现
 * Created by zhaoxf on 2017/3/10.
 */
@Slf4j
public class TimeBasedAccessInterceptor extends HandlerInterceptorAdapter {

    private int openingTime = 8;
    private int closingTime = 16;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(">>>" + this.getClass().getName() + ">>>在请求处理之前进行调用（Controller方法调用之前）");
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(HOUR_OF_DAY);
        if (openingTime <= hour && hour < closingTime) {
            response.sendRedirect("http://www.baidu.com");
            return true;
        }
        response.sendRedirect("http://www.sina.com");
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        log.info(">>>" + this.getClass().getName() + ">>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info(">>>" + this.getClass().getName() + ">>>在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
        super.afterCompletion(request, response, handler, ex);
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(">>>" + this.getClass().getName() + ">>>afterConcurrentHandlingStarted");
        super.afterConcurrentHandlingStarted(request, response, handler);
    }
}
