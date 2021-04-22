package com.example.online_shopping.controller;

import com.example.online_shopping.entity.User;
import com.example.online_shopping.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/privateOffice")
@RequiredArgsConstructor
public class PrivateOfficeController {

    private final UserService userService;

    @GetMapping
    public String openOffice(Model model, @AuthenticationPrincipal User user) {
        User newUser = new User();
        User authUser = userService.findUserById(user.getId());
        newUser.setUsername(authUser.getUsername());
        newUser.setEmail(authUser.getEmail());
        newUser.setPhone(authUser.getPhone());
        model.addAttribute("userForm", newUser);
        return "privateOffice";
    }

    @PostMapping
    public String updateUser(@ModelAttribute("userForm") @Valid User newUser, BindingResult bindingResult,
                          Model model, @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            return "privateOffice";
        }

        if (!userService.updateUserForId(newUser, user.getId())) {
            model.addAttribute("emailError", "Пользователь с такой почтой уже существует!");
            return "privateOffice";
        }
        model.addAttribute("success", "Сохранено");
        return "privateOffice";
    }
}
