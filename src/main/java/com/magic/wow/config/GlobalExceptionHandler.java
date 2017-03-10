package com.magic.wow.config;

import com.alibaba.fastjson.JSONException;
import com.magic.wow.util.ResultUtils;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常
 * Created by zhaoxf on 2017/3/6.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private Logger logger = Logger.getLogger(GlobalExceptionHandler.class);
     /*
        * 返回json数据或者String数据：
        * 那么需要在方法上加上注解：
        * 添加return即可。
        */

    /*
     * 返回视图：
     * 定义一个ModelAndView即可，
     * 然后return;
     * 定义视图文件(比如：error.html,error.ftl,error.jsp);
     *
     */
    @ExceptionHandler(value = Exception.class)
    public Object defaultErrorHandler(HttpServletRequest req, Exception e) {
//      // If the exception is annotated with @ResponseStatus rethrow it and let
//      // the framework handle it - like the OrderNotFoundException example
//      // at the start of this post.
//      // AnnotationUtils is a Spring Framework utility class.
//      if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null)
//          throw e;
//
//      // Otherwise setup and send the user to a default error-view.
        ModelAndView mav = new ModelAndView();
        mav.addObject("exception", e.getMessage());
        mav.addObject("url", req.getRequestURL());
        mav.setViewName("error");
        //打印异常信息：
        e.printStackTrace();
        logger.error("GlobalDefaultExceptionHandler.defaultErrorHandler()");
        return mav;
    }

    @ResponseBody
    @ExceptionHandler(value = JSONException.class)
    public Object jsonErrorHandler(HttpServletRequest req, Exception e) {
        //打印异常信息：
        e.printStackTrace();
        logger.error("GlobalDefaultExceptionHandler.jsonErrorHandler()");
        return ResultUtils.error("JSON 解析异常",req.getRequestURL());


    }
}
