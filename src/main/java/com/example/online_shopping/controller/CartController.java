package com.example.online_shopping.controller;

import com.example.online_shopping.entity.Cart;
import com.example.online_shopping.entity.User;
import com.example.online_shopping.service.CartEntityService;
import com.example.online_shopping.service.CartService;
import com.example.online_shopping.service.ProductService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartEntityService cartEntityService;

    @Autowired
    private ProductService productService;

    @PostMapping(value = "/addToCart", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String addToCart(@RequestParam Long id, @AuthenticationPrincipal User user, HttpServletRequest request,
                            HttpServletResponse response) {
        Cart cart = cartService.getCart(user, request, response);

        cartEntityService.addProductToCart(cart, productService.getProductById(id));

        return getTotal(cart);
    }

    @GetMapping(value = "/totalCart", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String totalCart(@AuthenticationPrincipal User user, HttpServletRequest request,
                            HttpServletResponse response) {
        Cart cart = cartService.getCart(user, request, response);

        return getTotal(cart);
    }

    private String getTotal(Cart cart) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("countProduct", cartEntityService.getAllCount(cart));
        jsonObject.addProperty("sumProduct", cartEntityService.getAllSum(cart));

        return jsonObject.toString();
    }

    @GetMapping
    public String getCart(@AuthenticationPrincipal User user, Model model, HttpServletRequest request,
                          HttpServletResponse response) {
        Cart cart = cartService.getCart(user, request, response);

        model.addAttribute("cartEntities", cartEntityService.findByCart(cart));
        return "cart";
    }

    @PostMapping(value = "/deleteFromCart", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String deleteFromCart(@RequestParam Long id, @AuthenticationPrincipal User user, HttpServletRequest request,
                                 HttpServletResponse response) {
        return getValue("deleteCartEntity", cartEntityService.deleteCartEntity(id).toString(),
                isEmpty(user, request, response).toString());
    }

    @PostMapping("/enlargeToCart")
    @ResponseBody
    public String enlargeToCart(@RequestParam Long id) {
        return cartEntityService.enlargeToCart(id);
    }

    @PostMapping(value = "/reduceFromCart", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String reduceFromCart(@RequestParam Long id,
                                 @AuthenticationPrincipal User user, HttpServletRequest request, HttpServletResponse response) {
        return getValue("reduceFromCart", cartEntityService.reduceFromCart(id),
                isEmpty(user, request, response).toString());
    }

    private Boolean isEmpty(User user, HttpServletRequest request, HttpServletResponse response) {
        Cart cart = cartService.getCart(user, request, response);
        return cartEntityService.findByCart(cart).isEmpty();
    }

    private String getValue(String nameFirst, String valueFirst, String valueSecond) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(nameFirst, valueFirst);
        jsonObject.addProperty("isEmpty", valueSecond);

        return jsonObject.toString();
    }

}
