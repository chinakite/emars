package com.ideamoment.emars.mail.service.impl;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.mail.dao.MailSettingMapper;
import com.ideamoment.emars.mail.service.MailSettingService;
import com.ideamoment.emars.model.MailSetting;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailSettingServiceImpl implements MailSettingService {

    @Autowired
    private MailSettingMapper mailSettingMapper;

    @Override
    public MailSetting loadMailSetting(Long id) {
        MailSetting mailSetting = mailSettingMapper.findMailSetting(id);
        return mailSetting;
    }

    @Override
    public boolean saveMailSetting(MailSetting mailSetting) {
        Long id = mailSetting.getId();
        MailSetting dbMailSetting = mailSettingMapper.findMailSetting(id);

        if(dbMailSetting != null) {

        }else{
            
        }

        return false;
    }

    @Override
    public String testSendMail(MailSetting email, String testEmail) {
        HtmlEmail testemail = new HtmlEmail();

        // 这里是SMTP发送服务器的名字：163的如下："smtp.163.com"
        testemail.setHostName(email.getHostName());
        testemail.setSmtpPort(Integer.valueOf(email.getPort()));

        // 字符编码集的设置
        testemail.setCharset("UTF-8");

        // 要发送的邮件主题
        testemail.setSubject("悦库时光测试邮件");

        try{
            // 收件人的邮箱
            testemail.addTo(testEmail);
            // 发送人的邮箱
            testemail.setFrom(email.getFromEmail(), email.getFromName());
            // 如果需要认证信息的话，设置认证：用户名-密码。分别为发件人在邮件服务器上的注册名称和密码
            testemail.setAuthentication(email.getUserName(), email.getPassword());
            // 要发送的信息，由于使用了HtmlEmail，可以在邮件内容中使用HTML标签
            testemail.setMsg("<p>收到此邮件表示邮件设置成功，可以保存了，请勿回复。</p>");
            // 发送
            testemail.send();

            return SuccessCode.SUCCESS;
        }catch(Exception e) {
            e.printStackTrace();
            return ErrorCode.EMAIL_SEND_ERROR + "#_#_#" + e.getMessage();
        }
    }
}
