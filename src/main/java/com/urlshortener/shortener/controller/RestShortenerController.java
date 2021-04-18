package com.urlshortener.shortener.controller;

import com.urlshortener.shortener.service.RestShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class RestShortenerController {

    @Autowired
    RestShortenerService restShortenerService;

    /** 단축 URL 호출 */
    @GetMapping(value = "/{shortURL}")
    public void redirectURL(HttpServletRequest request, HttpServletResponse response, @PathVariable("shortURL") String shortURL) throws IOException {
        String url = restShortenerService.getOriginURL(shortURL);

        response.sendRedirect(url);
    }

    /** 단축 URL 조회 */
    @GetMapping(value = "/{shortURL}+")
    public String chartURL(HttpServletRequest request, HttpServletResponse response, @PathVariable("shortURL") String shortURL) throws IOException {
        return restShortenerService.getURLInfo(request, shortURL);
    }

    /**
     *  ##### 단축 URL API
     *
     *  resultCode
     *   0 : 성공
     *   -10 : URL 형식 오류
     *   -11 : 비정상 URL
     *   -12 : 접속 불가능 URL
     *   -20 : DB Insert 오류
     *
     * */
    @PostMapping(value = "/shortening")
    public String restShorter(HttpServletRequest request , @RequestParam(value = "inputURL") String inputURL){
        return restShortenerService.shortening(request, inputURL);
    }
}
