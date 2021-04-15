package com.urlshortener.shortener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ShortenerController {

    @RequestMapping("/")
    public String init(HttpServletRequest request, HttpServletResponse response){
        return "index";
    }


}
