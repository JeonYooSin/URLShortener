package com.urlshortener.shortener.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;

@Slf4j
public class ShortenerUtil {
    private static Random random = new Random();

    public static String generateShortUrl(int urlLength){
        String charArr = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

        String result = "";
        boolean isOk = false;

        while (!isOk){
            result += charArr.charAt(random.nextInt(62));

            if(result.length() == urlLength){
                isOk = true;
                log.info("[UTIL] generate ShortURL : {}", result);
            }
        }

        return result;
    }

}
