package com.magic.wow.service;

import java.io.File;

/**
 * Created by zhaoxf on 2017/3/10.
 */
public interface EmailService {
    /**
     * 发送简单的邮件
     *
     * @param from    发送者
     * @param to      接收者
     * @param subject 邮件主题
     * @param text    邮件内容
     */
    void sendSimple(String from, String to, String subject, String text);

    /**
     * 发送简单的邮件
     *
     * @param from    发送者
     * @param to      多个接收者
     * @param subject 邮件主题
     * @param text    邮件内容
     */
    void sendSimple(String from, String[] to, String subject, String text);
    /**
     * 发送带附件的邮件
     *
     * @param from    发送者
     * @param to      接收者
     * @param subject 邮件主题
     * @param text    邮件内容
     * @param file    附件
     */
    void sendAttachments(String from, String to, String subject, String text, File file);
    /**
     * 发送带附件的邮件
     *
     * @param from    发送者
     * @param to      接收者
     * @param subject 邮件主题
     * @param text    邮件内容
     * @param file    附件
     */
    void sendAttachments(String from, String[] to, String subject, String text, File file);
    /**
     * 发送带附件的邮件
     *
     * @param from    发送者
     * @param to      接收者
     * @param subject 邮件主题
     * @param text    邮件内容
     * @param files   附件
     */
    void sendAttachments(String from, String to, String subject, String text, File[] files);
    /**
     * 发送带附件的邮件
     *
     * @param from    发送者
     * @param to      接收者
     * @param subject 邮件主题
     * @param text    邮件内容
     * @param files   附件
     */
    void sendAttachments(String from, String[] to, String subject, String text, File[] files);
}
