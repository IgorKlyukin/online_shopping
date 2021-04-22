package com.example.online_shopping.controller;

import com.example.online_shopping.entity.Cart;
import com.example.online_shopping.entity.User;
import com.example.online_shopping.service.CartEntityService;
import com.example.online_shopping.service.CartService;
import com.example.online_shopping.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.online_shopping.utils.Cookies.*;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {

    private final CartService cartService;

    private final CartEntityService cartEntityService;

    private final ProductService productService;

    @PostMapping("/addToCart")
    @ResponseBody
    public ObjectNode addToCart(@RequestParam Long id, @AuthenticationPrincipal User user, HttpServletRequest request,
                            HttpServletResponse response) {
        Cart cart = getCart(user, request, response);

        cartEntityService.addProductToCart(cart, productService.getProductById(id));

        return getTotal(cart);
    }

    @GetMapping("/totalCart")
    @ResponseBody
    public ObjectNode totalCart(@AuthenticationPrincipal User user, HttpServletRequest request,
                            HttpServletResponse response) {
        Cart cart = getCart(user, request, response);

        return getTotal(cart);
    }

    private ObjectNode getTotal(Cart cart) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put("countProduct", cartEntityService.getAllCount(cart));
        objectNode.put("sumProduct", cartEntityService.getAllSum(cart));

        return objectNode;
    }

    @GetMapping
    public String getCart(@AuthenticationPrincipal User user, Model model, HttpServletRequest request,
                          HttpServletResponse response) {
        Cart cart = getCart(user, request, response);

        model.addAttribute("cartEntities", cartEntityService.findByCart(cart));
        return "cart";
    }

    @PostMapping("/deleteFromCart")
    @ResponseBody
    public ObjectNode deleteFromCart(@RequestParam Long id, @AuthenticationPrincipal User user, HttpServletRequest request,
                                 HttpServletResponse response) {
        return getValue("deleteCartEntity", cartEntityService.deleteCartEntity(id).toString(),
                isEmpty(user, request, response).toString());
    }

    @PostMapping("/enlargeToCart")
    @ResponseBody
    public String enlargeToCart(@RequestParam Long id) {
        return cartEntityService.enlargeToCart(id);
    }

    @PostMapping("/reduceFromCart")
    @ResponseBody
    public ObjectNode reduceFromCart(@RequestParam Long id,
                                 @AuthenticationPrincipal User user, HttpServletRequest request, HttpServletResponse response) {
        return getValue("reduceFromCart", cartEntityService.reduceFromCart(id),
                isEmpty(user, request, response).toString());
    }

    private Boolean isEmpty(User user, HttpServletRequest request, HttpServletResponse response) {
        Cart cart = getCart(user, request, response);
        return cartEntityService.findByCart(cart).isEmpty();
    }

    private ObjectNode getValue(String nameFirst, String valueFirst, String valueSecond) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.put(nameFirst, valueFirst);
        objectNode.put("isEmpty", valueSecond);

        return objectNode;
    }

    private Cart getCart(User user, HttpServletRequest request, HttpServletResponse response) {
        return user != null
                ? getCartByUser(user, request, response)
                : getCartByCookie(request, response);
    }

    protected Cart getCartByUser(User user, HttpServletRequest request, HttpServletResponse response) {
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
            cartService.save(cartUser);
        } else if (cartCookie != null) {
            cartEntityService.addFromCartToCart(cartCookie, cartUser);
            cartService.delete(cartCookie);
        }
        if (cookieCart != null) {
            cookieCart = createCookieCart(null);
            response.addCookie(cookieCart);
        }
        return cartUser;
    }

    private Cart getCartByCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookieCart = getCookieCart(request);
        Cart cartCookie = getCartByCookie(cookieCart);
        if (cartCookie == null) {
            Long valueForCookie = createValueForCookie(request);
            cartCookie = new Cart(valueForCookie);
            cartService.save(cartCookie);
            cookieCart = createCookieCart(valueForCookie.toString());
        }
        response.addCookie(cookieCart);
        return cartCookie;
    }

    private Cart getCartByCookie(Cookie cookieCart) {
        Cart cartCookie = null;
        if (cookieCart != null) {
            try {
                Long value = Long.valueOf(cookieCart.getValue());
                cartCookie = cartService.getCartByCookie(value);
            } catch (NumberFormatException e) {
                log.error("Поддельный куки: " + cookieCart.getValue());
            }
        }
        return cartCookie;
    }
}
