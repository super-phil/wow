package com.magic.wow.config;

import com.alibaba.fastjson.JSONException;
import com.magic.wow.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常
 * BindException  400 (Bad Request)
 * <p>
 * ConversionNotSupportedException 500 (Internal Server Error)
 * <p>
 * HttpMediaTypeNotAcceptableException 406 (Not Acceptable)
 * <p>
 * HttpMediaTypeNotSupportedException 415 (Unsupported Media Type)
 * <p>
 * HttpMessageNotReadableException 400 (Bad Request)
 * <p>
 * HttpMessageNotWritableException 500 (Internal Server Error)
 * <p>
 * HttpRequestMethodNotSupportedException 05 (Method Not Allowed)
 * <p>
 * MethodArgumentNotValidException 400 (Bad Request)
 * <p>
 * MissingPathVariableException 500 (Internal Server Error)
 * <p>
 * MissingServletRequestParameterException 400 (Bad Request)
 * <p>
 * MissingServletRequestPartException 400 (Bad Request)
 * <p>
 * NoHandlerFoundException 404 (Not Found)
 * <p>
 * NoSuchRequestHandlingMethodException 404 (Not Found)
 * <p>
 * TypeMismatchException 400 (Bad Request)
 * Created by zhaoxf on 2017/3/6.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
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
        log.error("GlobalDefaultExceptionHandler.defaultErrorHandler()");
        return mav;
    }

    @ResponseBody
    @ExceptionHandler(value = JSONException.class)
    public Object jsonErrorHandler(HttpServletRequest req, Exception e) {
        //打印异常信息：
        e.printStackTrace();
        log.error("GlobalDefaultExceptionHandler.jsonErrorHandler()");
        return ResultUtils.error("JSON 解析异常", req.getRequestURL());


    }
}
