package com.magic.wow.controller;

import com.magic.wow.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.File;

/**
 * Created by zhaoxf on 2017/3/10.
 */
@Slf4j
@RestController
@RequestMapping("test")
public class TestController {
    @Resource
    private EmailService emailService;

    @RequestMapping(value = "email", method = RequestMethod.GET)
    public String testEmail() {

//        emailService.sendSimple("717815@163.com", "185509965@qq.com", "测试邮件", "我是测试内容!");
        emailService.sendAttachments("717815@163.com", "717815@163.com", "测试邮件", "我是测试内容!",new File("C:\\Users\\zhaoxf\\Desktop\\B.sql"));
        return "SUCCESS";

    }


}
