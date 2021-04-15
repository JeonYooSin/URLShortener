package com.urlshortener.shortener.service;

import com.google.gson.JsonObject;
import com.urlshortener.shortener.util.ShortenerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
@Slf4j
public class RestShortenerService {

    /**
     *  ##### 단축 URL
     *
     *  resultCode
     *   0 : 성공
     *   -10 : URL 형식 오류
     *   -11 : 비정상 URL
     *   -12 : 접속 불가능 URL
     *
     * */
    public String shortening(HttpServletRequest request, String inputURL){
        int resultCode = 0;
        String outputURL;

        /** 1. inputURL 유효성 검증 */
        try {
            URL url = new URL(inputURL);
            HttpURLConnection con;
            con = (HttpURLConnection)url.openConnection();
            if(con == null || con.getResponseCode() != HttpURLConnection.HTTP_OK){
                log.error("[ERROR] URL connection Error!! | responseCode : {}", con.getResponseCode());
                resultCode = -12;    // 접속 불가능한 URL
            }
        } catch (MalformedURLException e) {
            log.error("[ERROR] URL format Error!!");
            e.printStackTrace();
            resultCode = -10; // URL 형식 오류
        } catch (IOException e) {
            log.error("[ERROR] Abnormal URL Error!!");
            e.printStackTrace();
            resultCode = -11; // 비정상 URL
        }

        /** 2. inputURL 단축 로직 적용
         *   2-1. 이전에 이미 요청한 URL 인지 검증
         *   2-2. 없을 경우 등록
         * */
        outputURL = request.getRequestURL().toString().replace(request.getRequestURI(), "") + "/" + ShortenerUtil.generateShortUrl(8);


        /** 3. 결과 값에 따른 JSON Obj 생성 및 결과 전달 */
        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("result", resultCode);
        jsonObj.addProperty("inputURL", inputURL);
        jsonObj.addProperty("outputURL", outputURL);

        return jsonObj.toString();
    }

}
