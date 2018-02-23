package com.ideamoment.emars.mail.service;

import com.ideamoment.emars.model.MailSetting;

public interface MailSettingService {

    MailSetting loadMailSetting(Long id);

    boolean saveMailSetting(MailSetting mailSetting);

    public String testSendMail(MailSetting email, String testEmail);

}
