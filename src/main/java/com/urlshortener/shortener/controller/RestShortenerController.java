package com.urlshortener.shortener.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.urlshortener.shortener.service.RestShortenerService;
import io.seruco.encoding.base62.Base62;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

@RestController
public class RestShortenerController {

    @Autowired
    RestShortenerService restShortenerService;


    /**
     *  ##### 단축 URL API
     *
     *  resultCode
     *   0 : 성공
     *   -10 : URL 형식 오류
     *   -11 : 비정상 URL
     *   -12 : 접속 불가능 URL
     *
     * */
    @PostMapping(value = "/shortening")
    public String restShorter(HttpServletRequest request
            , @RequestParam(value = "inputURL") String inputURL){
        System.out.println(inputURL);

        return restShortenerService.shortening(request, inputURL);
    }
}
