package com.ideamoment.emars.interceptor;

import com.ideamoment.emars.constants.ErrorCode;
import com.ideamoment.emars.utils.CookieUtils;
import com.ideamoment.emars.utils.UserContext;
import com.ideamoment.emars.utils.UserCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {

    private final static Logger LOG = LoggerFactory.getLogger(LoginInterceptor.class);

    public static final String EMARS_USER = "_emars_user";

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //如果是探活接口，直接放行
        if ("/_isok".equals(httpServletRequest.getRequestURI())) {
            return true;
        }

        String retUrl = getURL(httpServletRequest);

        String cookieValue = CookieUtils.getCookieValue(httpServletRequest, EMARS_USER);
        if (cookieValue == null) {
            LOG.debug("[EMARS_USER preHandle] cookie EMARS_USER is null ");
            redirectToLogin(httpServletRequest, httpServletResponse, retUrl);
            return false;
        } else {
            Map<String, Object> userInfo = parseCookieValue(cookieValue);
            if (userInfo != null) {
                LOG.debug("[EMARS_USER preHandle] cookie EMARS_USER is valid, PASS! ");
                UserContext.set(userInfo);
                return true;
            } else {
                LOG.debug("[EMARS_USER preHandle] cookie EMARS_USER is invalid ");
                redirectToLogin(httpServletRequest, httpServletResponse, retUrl);
                return false;
            }
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    private void redirectToLogin(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, String retUrl) throws Exception {
        boolean isAjax = "XMLHttpRequest".equals(httpServletRequest.getHeader("X-Requested-With"));
        isAjax = isAjax || "true".equals(httpServletRequest.getHeader("AJAX_REQ"));
        if(isAjax) {
            String responseContent = "{\"code\":\"" + ErrorCode.UNAUTH_ERROR + "\",\"msg\":\"" + httpServletRequest.getRemoteHost() + ":" + httpServletRequest.getRemotePort() + "\"}";
            httpServletResponse.getWriter().println(responseContent);
        }else{
            String url = "/login?retUrl=" + retUrl;

            LOG.debug("redirectToLogin url:{}", url);
            httpServletResponse.sendRedirect(url);
        }
    }

    private static String getURL(HttpServletRequest req) {

        String scheme = req.getScheme(); // http
        String serverName = req.getServerName(); // hostname.com
        int serverPort = req.getServerPort(); // 80
        String contextPath = req.getContextPath(); // /mywebapp
        String servletPath = req.getServletPath(); // /servlet/MyServlet
        String pathInfo = req.getPathInfo(); // /a/b;c=123
        String queryString = req.getQueryString(); // d=789

        // Reconstruct original requesting URL
        StringBuilder url = new StringBuilder();
        url.append(scheme).append("://").append(serverName);

        if (serverPort != 80 && serverPort != 443) {
            url.append(":").append(serverPort);
        }

        url.append(contextPath).append(servletPath);

        if (pathInfo != null) {
            url.append(pathInfo);
        }
        if (queryString != null) {
            url.append("?").append(queryString);
        }
        return url.toString();
    }

    private Map<String, Object> parseCookieValue(String cookieValue) {
        Map<String, Object> result = UserCookie.parseCookieValue(cookieValue);
        return result;
    }
}
