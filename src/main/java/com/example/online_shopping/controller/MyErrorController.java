package com.example.online_shopping.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;

@Controller
public class MyErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(MyErrorController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, HttpServletResponse response) {
        logger.error("error: " + request.getRequestURL() + "?" + request.getQueryString());
        Collections.list(request.getHeaderNames()).forEach(name -> logger.error(name + ": " + request.getHeader(name)));
        logger.error("method: " + request.getMethod());
        logger.error("code: " + response.getStatus());

        return "redirect:/";
    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
