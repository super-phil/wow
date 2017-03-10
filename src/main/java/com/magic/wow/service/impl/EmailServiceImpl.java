package com.magic.wow.service.impl;

import com.magic.wow.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.io.File;

/**
 * Created by zhaoxf on 2017/3/10.
 */
@Slf4j
@Service
public class EmailServiceImpl implements EmailService {
    @Resource
    private JavaMailSender sender;

    //
    //    public void sendSimpleEmail() {
    //        SimpleMailMessage message=new SimpleMailMessage();
    //        message.setFrom("717815@163.com");//发送者.
    //        message.setTo("717815@163.com");//接收者.
    //        message.setSubject("测试邮件（邮件主题）");//邮件主题.
    //        message.setText("这是邮件内容");//邮件内容.
    //        sender.send(message);//发送邮件
    //    }
    //

    @Override
    public void sendSimple(String from, String to, String subject, String text) {
        sendSimple(from, new String[]{to}, subject, text);
    }


    @Override
    public void sendSimple(String from, String[] to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        try {
            sender.send(message);//发送邮件
            log.info("Send email success!");
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }


    @Override
    public void sendAttachments(String from, String to, String subject, String text, File file) {
        sendAttachments(from, new String[]{to}, subject, text, new File[]{file});
    }

    public void sendAttachments(String from, String[] to, String subject, String text, File file) {
        sendAttachments(from, to, subject, text, new File[]{file});
    }


    @Override
    public void sendAttachments(String from, String to, String subject, String text, File[] files) {
        sendAttachments(from, new String[]{to}, subject, text, files);
    }

    @Override
    public void sendAttachments(String from, String[] to, String subject, String text, File[] files) {
        try {
            //这个是javax.mail.internet.MimeMessage下的，不要搞错了。
            MimeMessage mimeMessage = sender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            //基本设置.
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);
            //org.springframework.core.io.FileSystemResource下的:
            for (File file : files) {
                //添加附件，这里第一个参数是在邮件中显示的名称，也可以直接是head.jpg，但是一定要有文件后缀，不然就无法显示图片了。
                helper.addAttachment(file.getName(), new FileSystemResource(file));
            }
            sender.send(mimeMessage);
            log.info("Send email success!");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }
}
