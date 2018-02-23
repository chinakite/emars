package com.ideamoment.emars.mail.controller;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.constants.SuccessCode;
import com.ideamoment.emars.mail.service.MailSettingService;
import com.ideamoment.emars.model.MailSetting;
import com.ideamoment.emars.utils.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    @Autowired
    private MailSettingService mailSettingService;

    @RequestMapping(value="/mail/{id}", method = RequestMethod.POST)
    public JsonData saveMailSetting(
            String hostName,
            String port,
            String fromEmail,
            String fromName,
            String userName,
            String password,
            String ssl) {

        MailSetting email = new MailSetting();
        email.setId(1L);
        email.setHostName(hostName);
        email.setPort(port);
        email.setFromEmail(fromEmail);
        email.setFromName(fromName);
        email.setUserName(userName);
        email.setPassword(password);
        email.setSsl(ssl);
        boolean result = mailSettingService.saveMailSetting(email);

        if(result) {
            return JsonData.SUCCESS;
        }else{
            return JsonData.error(ErrorCode.EMAIL_SAVE_ERROR, ErrorCode.ERROR_MSG.get(ErrorCode.EMAIL_SAVE_ERROR));
        }
    }

    @RequestMapping(value="/testmail/{id}", method = RequestMethod.POST)
    public JsonData testSendMail(
            String hostName,
            String port,
            String fromEmail,
            String fromName,
            String userName,
            String password,
            String ssl,
            String testEmail) {

        MailSetting email = new MailSetting();
        email.setHostName(hostName);
        email.setPort(port);
        email.setFromEmail(fromEmail);
        email.setFromName(fromName);
        email.setUserName(userName);
        email.setPassword(password);
        email.setSsl(ssl);
        String result = mailSettingService.testSendMail(email, testEmail);

        if(result.equals(SuccessCode.SUCCESS)) {
            return JsonData.SUCCESS;
        }else{
            String[] arr = result.split("#_#_#");
            return JsonData.error(arr[0], ErrorCode.ERROR_MSG.get(result) + " : " + arr[1]);
        }
    }
}
