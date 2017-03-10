package com.magic.wow.controller;

import com.alibaba.fastjson.JSONObject;
import com.magic.wow.service.EmailService;
import com.magic.wow.util.FileUtils;
import com.magic.wow.util.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.CacheControl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaoxf on 2017/3/10.
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestController {
    @Resource
    private EmailService emailService;

    @GetMapping("email")
    public String testEmail() {

//        emailService.sendSimple("717815@163.com", "185509965@qq.com", "测试邮件", "我是测试内容!");
        emailService.sendAttachments("717815@163.com", "717815@163.com", "测试邮件", "我是测试内容!", new File("C:\\Users\\zhaoxf\\Desktop\\B.sql"));
        return "SUCCESS";

    }

    /**
     * 支持异步
     *
     * @return
     */
    @GetMapping("callable")
    public Callable<String> asy() {
        // ... ...
        return () -> {//异步
            // ...
            try {
                Thread.sleep(6000);
                log.info("todo something over");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "someView";
        };
    }


    /**
     * 文件下载
     *
     * @param request
     * @param response
     * @return
     */
    @GetMapping("download")
    public StreamingResponseBody download(HttpServletRequest request, HttpServletResponse response) {
        return outputStream -> {
            //... ...
            FileUtils.download("C:\\Users\\zhaoxf\\Desktop\\DKP.xlsx", request, response);
        };
    }

    @GetMapping("interceptor")
    public String time() {
        return "null";
    }

    @GetMapping("cache")
    public ResponseEntity<Object> ttp() {
        JSONObject jo = new JSONObject();
        jo.put("a", "66");
        jo.put("b", "99");
        return ResponseEntity.ok()
                .contentLength(99999)
                .contentType(MediaType.APPLICATION_PDF)
                .header("aaa", "aadhha")
                .cacheControl(CacheControl.maxAge(30, TimeUnit.DAYS))
                .eTag(2 + "") // lastModified is also available
                .body(ResultUtils.success(999, "66", jo));
    }
}
