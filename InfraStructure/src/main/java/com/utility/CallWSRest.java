package com.utility;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.from.OutputAPIForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotNull;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.util.Base64;

/**
 * @Creator 8/9/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Slf4j
public class CallWSRest {


    private static RestTemplate createRestTemplate() {
        RestTemplate obj = new RestTemplate();
        obj.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8)); //debug for CRM send utf-8 characters
        return obj;
    }

    public static HttpHeaders createBasicAuthHeaders(String auth) {
        String base64Creds = Base64.getEncoder().encodeToString(auth.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        headers.add("X-FORWARDED-FOR","185.173.168.3");
        headers.add("Content-Type","application/json");
        return headers;
    }

    public static OutputAPIForm<Object> post(String url, String auth, @NotNull Object input, Class clazz) {
        return sendRequest(createRestTemplate(),url, HttpMethod.POST, createRequest(createBasicAuthHeaders(auth), input),clazz);
    }

    private static HttpEntity createRequest(HttpHeaders header, Object input) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return new HttpEntity<>(objectMapper.writeValueAsString(input), header);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static OutputAPIForm<Object> sendRequest(RestTemplate restTemplate, String url, HttpMethod type, HttpEntity request, Class clazz) {
        log.info("WebConnect class start - url: " + url);
        OutputAPIForm<Object> retVal = new OutputAPIForm();
        try {
            Timestamp st = DateUtils.getCurrentDate();
            ResponseEntity<?> response = restTemplate.exchange(url, type, request, clazz);
            Timestamp et = DateUtils.getCurrentDate();
            log.info("Response url - " + url + " : " , DateUtils.diffTwoTimestamp(et, st));
            if (response.getStatusCode().equals(HttpStatus.OK)) {
                retVal.setSuccess(true);
                retVal.setData(response.getBody());
            } else {
                retVal.setSuccess(false);
            }
        } catch (Exception e) {
            log.error("WebConnect catch Error - url: " + url);
            log.error(e.getMessage());
            e.printStackTrace();
            retVal.setSuccess(false);
        }
        return retVal;
    }
}
