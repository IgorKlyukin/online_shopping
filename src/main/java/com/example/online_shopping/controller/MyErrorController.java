package com.example.online_shopping.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Controller
@Slf4j
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, HttpServletResponse response) {
        log.error("error: " + request.getRequestURL() + "?" + request.getQueryString());
        Collections.list(request.getHeaderNames()).forEach(name -> log.error(name + ": " + request.getHeader(name)));
        log.error("method: " + request.getMethod());
        log.error("code: " + response.getStatus());

        return "redirect:/";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
