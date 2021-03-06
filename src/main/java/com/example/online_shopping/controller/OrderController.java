package com.example.online_shopping.controller;

import com.example.online_shopping.entity.*;
import com.example.online_shopping.service.CartEntityService;
import com.example.online_shopping.service.CartService;
import com.example.online_shopping.service.OrderEntityService;
import com.example.online_shopping.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

import static com.example.online_shopping.utils.Cookies.isCartCookie;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    private final OrderEntityService orderEntityService;

    private final CartService cartService;

    private final CartEntityService cartEntityService;

    private final CartController cartController;

    @PostMapping
    public String createOrder(@AuthenticationPrincipal User user, HttpServletRequest request,
                              HttpServletResponse response) {
        boolean isCartCookie = isCartCookie(request);
        Cart cart = cartController.getCartByUser(user, request, response);
        if (isCartCookie) {
            return "redirect:/cart";
        } else {
            Set<CartEntity> cartEntities = cartEntityService.findByCart(cart);
            Order order = saveOrder(user, cartEntities, cart);
            user.setCart(null);
            return "redirect:/order/" + order.getId();
        }
    }

    @Transactional
    Order saveOrder(User user, Set<CartEntity> cartEntities, Cart cart) {
        Order order = orderService.createOrder(user);
        orderEntityService.addOrderEntities(order, cartEntities);
        orderService.updateOrder(order);
        cartService.delete(cart);
        return order;
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable("id") Long id, @AuthenticationPrincipal User user, Model model) {
        Order order = orderService.getOrderByIdAndUser(id, user);
        if (order == null) {
            return "redirect:/order";
        }
        Set<OrderEntity> orderEntities = orderEntityService.findByOrder(order);
        model.addAttribute("orderEntities", orderEntities);
        return "order";
    }

    @GetMapping
    public String getOrder(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("orders", orderService.getOrderAll(user));
        return "orderList";
    }
}
