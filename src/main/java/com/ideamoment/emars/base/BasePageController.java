package com.ideamoment.emars.base;

import com.ideamoment.emars.utils.UserContext;
import com.ideamoment.emars.utils.UserCookie;
import org.springframework.ui.Model;

import java.util.Map;

/**
 * Created by Chinakite on 2018/2/21.
 */
public class BasePageController {
    protected void setUserInfo(Model model) {
        Map<String, Object> userInfo = UserContext.get();
        model.addAttribute("curUserId", userInfo.get(UserCookie.USERID));
        model.addAttribute("curUserName", userInfo.get(UserCookie.USERNAME));
    }
}
