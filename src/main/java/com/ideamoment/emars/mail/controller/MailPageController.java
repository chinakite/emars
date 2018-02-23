package com.ideamoment.emars.mail.controller;

import com.ideamoment.emars.base.BasePageController;
import com.ideamoment.emars.mail.service.MailSettingService;
import com.ideamoment.emars.model.MailSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MailPageController extends BasePageController {

    @Autowired
    private MailSettingService mailSettingService;

    @RequestMapping(value="/mailSettingPage", method = RequestMethod.GET)
    public String userIndexPage(Model model) {
        MailSetting mailSetting = mailSettingService.loadMailSetting(1L);
        super.setUserInfo(model);

        if(mailSetting != null) {
            model.addAttribute("hostName", mailSetting.getHostName());
            model.addAttribute("port", mailSetting.getPort());
            model.addAttribute("fromEmail", mailSetting.getFromEmail());
            model.addAttribute("fromName", mailSetting.getFromName());
            model.addAttribute("userName", mailSetting.getUserName());
            model.addAttribute("password", mailSetting.getPassword());
            model.addAttribute("ssl", mailSetting.getSsl());
        }

        return "mail/mailSetting";
    }
}
