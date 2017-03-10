package com.magic.wow.config;

import com.magic.utils.database.DBUtils;
import com.magic.utils.qiniu.QiNiuUtils;
import com.magic.wow.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.io.File;
import java.time.LocalDate;

/**
 * Created by zhaoxf on 2017/3/10.
 */
@Slf4j
@Configuration
@EnableScheduling
public class ScheduleConfig {
    @Resource
    private EmailService emailService;

    @Value("${database.backup.path}")
    private String backupPath;
    @Value("${database.backup.dbname}")
    private String dbName;
    @Value("${spring.datasource.username}")
    private String dbUser;
    @Value("${spring.datasource.password}")
    private String dbPwd;

    /**
     * 每天23:59:55备份数据库并上传到七牛
     */
    //  @Scheduled(cron = "0/20 * * * * ?") // 每20秒执行一次
    @Scheduled(cron = "55 59 23 * * ?")
    public void backupAndSendEmail() {
        String backupPath = "";
        File file = new File(backupPath + "backup-" + LocalDate.now().toString() + ".sql");
        try {
            DBUtils.backup(file.getPath(), dbName, dbUser, dbPwd);
            //上传到七牛云上 并发送邮件
            emailService.sendAttachments("717815@163.com", "185509965@qq.com", file.getName(), "wow dkp",file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
