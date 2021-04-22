package com.example.online_shopping.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

public class Cookies {

    public static final String CART = "cart";
    public static final String JSESSIONID = "JSESSIONID";

    public static Cookie createCookieCart(String value) {
        Cookie cookie = new Cookie(CART, value);
        cookie.setPath("/");
        cookie.setMaxAge(value != null ? 172800 : 0);
        return cookie;
    }

    public static boolean isCartCookie(HttpServletRequest request) {
        return getCookieCart(request) != null;
    }

    public static Cookie getCookieCart(HttpServletRequest request) {
        return getCookie(request, CART);
    }

    private static Cookie getCookie(HttpServletRequest request, String nameCookie) {
        Cookie[] cookies = request.getCookies();
        return cookies == null ? null : Arrays.stream(cookies)
                .filter(cookie -> nameCookie.equals(cookie.getName()))
                .findFirst()
                .orElse(null);
    }

    public static Long createValueForCookie(HttpServletRequest request) {
        Cookie cookie = getCookie(request, JSESSIONID);
        if (cookie != null) {
            try {
                return Long.valueOf(cookie.getValue());
            }
            catch (NumberFormatException e) {
                return Long.MAX_VALUE;
            }
        }
        return Long.MAX_VALUE;
    }
}
