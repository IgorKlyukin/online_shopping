package com.example.online_shopping.service;

import com.example.online_shopping.entity.Cart;
import com.example.online_shopping.entity.User;
import com.example.online_shopping.repository.CartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@Service
public class CartService {

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartEntityService cartEntityService;

    public static final String CART = "cart";
    public static final String JSESSIONID = "JSESSIONID";

    public Cart getCart(User user, HttpServletRequest request, HttpServletResponse response) {
        return user != null
                ? getCartByUser(user, request, response)
                : getCartByCookie(request, response);
    }

    public Cart getCartByUser(User user, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookieCart = getCookieCart(request);
        Cart cartCookie = getCartByCookie(cookieCart);
        Cart cartUser = user.getCart();
        if (cartUser == null) {
            if (cartCookie == null) {
                cartUser = new Cart(user);
            } else {
                cartUser = cartCookie;
                cartUser.setUser(user);
                cartUser.setCartCookie(null);
            }
            user.setCart(cartUser);
            cartRepository.save(cartUser);
        } else if (cartCookie != null) {
            cartEntityService.addFromCartToCart(cartCookie, cartUser);
            delete(cartCookie);
        }
        if (cookieCart != null) {
            cookieCart = createCookieCart(null);
            response.addCookie(cookieCart);
        }
        return cartUser;
    }

    public Cart getCartByCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookieCart = getCookieCart(request);
        Cart cartCookie = getCartByCookie(cookieCart);
        if (cartCookie == null) {
            Long valueForCookie = createValueForCookie(request);
            cartCookie = new Cart(valueForCookie);
            cartRepository.save(cartCookie);
            cookieCart = createCookieCart(valueForCookie.toString());
        }
        response.addCookie(cookieCart);
        return cartCookie;
    }

    private Cart getCartByCookie(Cookie cookieCart) {
        Cart cartCookie = null;
        if (cookieCart != null) {
            try {
                cartCookie = cartRepository.findFirstByCartCookie(Long.valueOf(cookieCart.getValue()));
            } catch (NumberFormatException e) {
                logger.error("Поддельный куки: " + cookieCart.getValue());
            }
        }
        return cartCookie;
    }

    public boolean isCartCookie(HttpServletRequest request) {
        return getCookieCart(request) != null;
    }

    public Cookie getCookieCart(HttpServletRequest request) {
        return getCookie(request, CART);
    }

    private Cookie getCookie(HttpServletRequest request, String nameCookie) {
        Cookie[] cookies = request.getCookies();
        return cookies == null ? null : Arrays.stream(cookies)
                .filter(cookie -> nameCookie.equals(cookie.getName()))
                .findFirst()
                .orElse(null);
    }

    private Long createValueForCookie(HttpServletRequest request) {
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

    private Cookie createCookieCart(String value) {
        Cookie cookie = new Cookie(CART, value);
        cookie.setPath("/");
        cookie.setMaxAge(value != null ? 172800 : 0);
        return cookie;
    }

    public void delete(Cart cart) {
        cartRepository.deleteById(cart.getId());
    }
}