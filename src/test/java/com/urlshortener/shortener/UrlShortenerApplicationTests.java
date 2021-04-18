package com.urlshortener.shortener;

import com.urlshortener.shortener.model.InputURLEntity;
import com.urlshortener.shortener.service.RestShortenerService;
import com.urlshortener.shortener.util.ShortenerUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;

@SpringBootTest
class UrlShortenerApplicationTests {

    @Autowired
    RestShortenerService restShortenerService;

    @Test
    void 단축URL생성() {
        String inputURL = "https://www.naver.com/";
        String jsonString = restShortenerService.shortening(new MockHttpServletRequest(), inputURL);

        System.out.println(jsonString);
    }

    @Test
    void insertInputURL(){
        InputURLEntity inputURL = new InputURLEntity();
        inputURL.setInputURL("test");
        inputURL.setShortKey(ShortenerUtil.generateShortUrl(8));

//        inputURLRepository.save(inputURL);
    }

}
