package com.urlshortener.shortener.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.urlshortener.shortener.model.InputURLEntity;
import com.urlshortener.shortener.repository.InputURLRepository;
import com.urlshortener.shortener.util.ShortenerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class RestShortenerService {
    @Autowired
    InputURLRepository inputURLRepository;

    @Value("${url.short.key.length:7}")
    private int SHORT_KEY_LENGTH;

    private Gson gson = new Gson();

    /** 기존 URL 찾기 */
    public String getOriginURL(String shortKey) {
        InputURLEntity selectURLData = inputURLRepository.findByShortURL(shortKey);

        if(selectURLData == null){
            return "/error/404";
        }

        /** 요청 횟수 증가 */
        selectURLData.setCallCount(selectURLData.getCallCount() + 1);
        inputURLRepository.save(selectURLData);

        return selectURLData.getInputURL();
    }

    /** 단축 URL 조회 */
    public String getURLInfo(HttpServletRequest request, String shortKey) {
        InputURLEntity selectURLData = inputURLRepository.findByShortURL(shortKey);

        if(selectURLData == null){
            //
        }

        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("result", 0);
        jsonObj.addProperty("inputURL", selectURLData.getInputURL());
        jsonObj.addProperty("shortURL", request.getRequestURL().toString().replace(request.getRequestURI(), "") + "/" + selectURLData.getShortKey());
        jsonObj.addProperty("callCount", selectURLData.getCallCount());
        jsonObj.addProperty("createDate", selectURLData.getCreateDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        return jsonObj.toString();
    }

    /**
     *  ##### 단축 URL 생성
     *
     *  resultCode
     *   0 : 성공
     *   -10 : URL 형식 오류
     *   -11 : 비정상 URL
     *   -12 : 접속 불가능 URL
     *   -20 : DB Insert 오류
     *
     * */
    public String shortening(HttpServletRequest request, String inputURL){
        int resultCode = 0;
        InputURLEntity resultURL = new InputURLEntity();

        /** 1. inputURL 유효성 검증 */
        try {
            URL url = new URL(inputURL);
            HttpURLConnection con;
            con = (HttpURLConnection)url.openConnection();
            if(con == null || con.getResponseCode() != HttpURLConnection.HTTP_OK){
                log.error("[ERROR] URL connection Error!! | responseCode : {}", con.getResponseCode());
                resultURL.setResult(-12); // 접속 불가능한 URL
                resultURL.setMsg("요청하신 URL은 접속 불가능한 URL 입니다.");
                return gson.toJson(resultURL);
            }
        } catch (MalformedURLException e) {
            log.error("[ERROR] URL format Error!!");
            e.printStackTrace();
            resultURL.setResult(-10); // URL 형식 오류
            resultURL.setMsg("요청하신 URL의 형식이 잘못되었습니다.");
            return gson.toJson(resultURL);
        } catch (IOException e) {
            log.error("[ERROR] Abnormal URL Error!!");
            e.printStackTrace();
            resultURL.setResult(-11); // 비정상 URL
            resultURL.setMsg("비정상 URL 입니다.");
            return gson.toJson(resultURL);
        }

        /** 2. inputURL 단축 로직 적용
         *   2-1. 이전에 이미 요청한 URL 인지 검증
         *   2-2. 없을 경우 등록
         * */
        List<InputURLEntity> getInputURL = inputURLRepository.findByInputURL(inputURL);

        if(getInputURL.size() < 1){
            try {
                InputURLEntity inputURLEntity;
                String shortKey;
                // ShortKey 중복 검증
                do{
                    shortKey =  ShortenerUtil.generateShortUrl(SHORT_KEY_LENGTH);
                    inputURLEntity = inputURLRepository.findByShortURL(shortKey);
                }
                while (inputURLEntity != null);

                inputURLEntity = new InputURLEntity();
                inputURLEntity.setInputURL(inputURL);
                inputURLEntity.setShortKey(shortKey);

                inputURLRepository.save(inputURLEntity);

                getInputURL.add(inputURLEntity);
            }catch (Exception e){
                log.error("[ERROR] H2 DB INSERT ERROR !!");
                e.printStackTrace();
                resultURL.setResult(-20); // DB INSERT 오류
                resultURL.setMsg("DB 처리 도중 오류가 발생하였습니다.");
                return gson.toJson(resultURL);
            }
        }

        /** 3. 결과 값에 따른 JSON Obj 생성 및 결과 전달 */
        resultURL = getInputURL.get(0);

        JsonObject jsonObj = new JsonObject();
        jsonObj.addProperty("result", resultCode);
        jsonObj.addProperty("inputURL", resultURL.getInputURL());
        jsonObj.addProperty("shortURL", request.getRequestURL().toString().replace(request.getRequestURI(), "") + "/" + resultURL.getShortKey());
        jsonObj.addProperty("callCount", resultURL.getCallCount());

        return jsonObj.toString();
    }


}
