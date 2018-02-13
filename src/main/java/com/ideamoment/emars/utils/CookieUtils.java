package com.ideamoment.emars.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    private static Logger LOGGER = LoggerFactory.getLogger(CookieUtils.class);

    public CookieUtils() {
    }

    public static String getCookieValue(HttpServletRequest request, String key) {
        try {
            Cookie[] cs = request.getCookies();
            if (cs != null) {
                Cookie[] var3 = cs;
                int var4 = cs.length;

                for(int var5 = 0; var5 < var4; ++var5) {
                    Cookie cookie = var3[var5];
                    if (cookie.getName().equals(key)) {
                        return cookie.getValue();
                    }
                }
            }

            return null;
        } catch (Exception var7) {
            LOGGER.error(var7.getMessage(), var7);
            return null;
        }
    }

    public static void addCookie(HttpServletResponse response, String key, String value, String domain, int expire, boolean httpOnly) {
        addCookie(response, key, value, domain, "/", expire, httpOnly);
    }

    public static void addCookie(HttpServletResponse response, String key, String value, String domain, String path, int expire, boolean httpOnly) {
        try {
            StringBuilder builder = new StringBuilder();
            builder.append(key + "=" + value + ";path=" + path + ";");
            if (httpOnly) {
                builder.append("HttpOnly; ");
            }

            builder.append("domain=" + domain + ";");
            builder.append("max-age=" + expire);
            response.addHeader("Set-Cookie", builder.toString());
        } catch (Exception var8) {
            LOGGER.error(var8.getMessage(), var8);
        }

    }

    public static void delCookie(HttpServletResponse response, String domain, String path, String key) {
        try {
            Cookie cookie = new Cookie(key, (String)null);
            cookie.setMaxAge(0);
            cookie.setPath(path);
            if (domain != null) {
                cookie.setDomain(domain);
            }

            response.addCookie(cookie);
        } catch (Exception var5) {
            LOGGER.error(var5.getMessage(), var5);
        }

    }

    public static void delAllCookie(HttpServletRequest request, HttpServletResponse response, String domainName, String path) {
        try {
            Cookie[] cs = request.getCookies();
            if (cs != null && cs.length > 0) {
                Cookie[] var5 = cs;
                int var6 = cs.length;

                for(int var7 = 0; var7 < var6; ++var7) {
                    Cookie cookie = var5[var7];
                    cookie.setMaxAge(0);
                    cookie.setPath(path);
                    cookie.setDomain(domainName);
                    response.addCookie(cookie);
                }
            }
        } catch (Exception var9) {
            LOGGER.error(var9.getMessage(), var9);
        }

    }
}
