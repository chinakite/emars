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
import org.springframework.mobile.device.Device;
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
    public String loginPage(Device device) {
        if(device.isMobile()) {
            return "m_login";
        }else{
            return "login";
        }
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

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        Cookie cookies[] = request.getCookies();
        if (cookies != null)
        {
            for (int i = 0; i < cookies.length; i++)
            {
                if (cookies[i].getName().equals(UserCookie.EMARS_USER))
                {
                    Cookie cookie = new Cookie(UserCookie.EMARS_USER,"");//这边得用"",不能用null
                    cookie.setPath("/");//设置成跟写入cookies一样的
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        return "redirect:/login";
    }

}
