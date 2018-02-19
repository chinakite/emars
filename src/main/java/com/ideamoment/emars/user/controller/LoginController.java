package com.ideamoment.emars.user.controller;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.model.User;
import com.ideamoment.emars.user.service.UserService;
import com.ideamoment.emars.utils.JsonData;
import com.ideamoment.emars.utils.UserContext;
import com.ideamoment.emars.utils.UserCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public String loginPage() {
        return "login";
    }

    @RequestMapping(value="/login/submit", method = RequestMethod.POST)
    @ResponseBody
    public JsonData login(HttpServletRequest request,
                        HttpServletResponse response,
                        String userName,
                        String password) {
        User user = userService.login(userName, password);

        if(user == null) {
            return JsonData.error(ErrorCode.LOGIN_ERROR, "用户名或密码错误");
        }else{
            Map<String, Object> userInfo = new HashMap<String, Object>();
            userInfo.put("userId", "1");
            userInfo.put("userName", userName);
            userInfo.put("version", UserCookie.CURRENT_VERSION);

            String cookieValue = UserCookie.generateCookieValue(userInfo);

            Cookie cookie = new Cookie(UserCookie.EMARS_USER, cookieValue);
            cookie.setMaxAge(UserCookie.EXPIRE_TIME);
            cookie.setPath("/");
            response.addCookie(cookie);

            return JsonData.SUCCESS;
        }
    }

}
