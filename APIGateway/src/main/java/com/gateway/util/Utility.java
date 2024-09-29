package com.gateway.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @Creator 8/6/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Slf4j
public class Utility {




    public static boolean checkInputData(String inputVal) {
        boolean retVal = false;
        Pattern[] paterns = new Pattern[]{
                Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
                Pattern.compile("src[\r\n]*=[\r\n]*\\'(.*?)\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
                Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
                Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
                Pattern.compile("insert(.*?)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
                Pattern.compile("(.*?) or (.*?)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
                Pattern.compile("(.*?) and (.*?)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
                Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
                Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL),
                Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
                Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
                Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL)
        };
        if (inputVal == null) {
            retVal = false;
        } else {
            for (Pattern pattern : paterns) {
                retVal = pattern.matcher(inputVal).matches();
                if (retVal == true) {
                    break;
                }
            }
        }
        return retVal;
    }

    public static StringBuffer gatheringLogRequest(ServerHttpRequest request) {
        StringBuffer requestBuffer = new StringBuffer();
        HttpHeaders headers = request.getHeaders();
        requestBuffer.append("\nRequest Scheme: ").append(request.getURI().getScheme())
                .append(" - Path: ").append(request.getURI().getPath());
        requestBuffer.append("\nRequest Method: ").append(request.getMethod())
                .append(" - Host: ").append(request.getURI().getHost());
        requestBuffer.append("\nRequest Headers: ");
        headers.forEach((key, value) -> requestBuffer.append(key).append(":").append(value).append(","));
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        if (!queryParams.isEmpty()) {
            requestBuffer.append("\nRequest Query Param: ");
            headers.forEach((key, value) -> requestBuffer.append(key).append(":").append(value).append("-"));
        }
        requestBuffer.append("\nRequest ContentType: ").append(headers.getContentType()).append("Content Length: ").append(headers.getContentLength());
        return requestBuffer;
    }



    public static StringBuffer gatheringLogResponse(ServerHttpResponse response, String bodyContent, ServerHttpRequest request, Long startTime) {
        StringBuffer responseBuffer = new StringBuffer();
        responseBuffer.append("\nResponse Original Path: ").append(request.getURI().getPath());
        responseBuffer.append("\nResponse HttpStatus: ").append(response.getStatusCode());
        Long executeTime = (System.currentTimeMillis() - startTime);
        responseBuffer.append("\nResponse time: ").append(executeTime).append(" (miliSecond)");
        HttpHeaders headers = response.getHeaders();
        responseBuffer.append("\nResponse Headers: ").append(response.getStatusCode());
        headers.forEach((key, value) -> responseBuffer.append(key).append(":").append(value).append(", "));
        responseBuffer.append("\nResponse ContentType: ").append(headers.getContentType()).append(" Content Length:").append(headers.getContentLength());
        responseBuffer.append("\nResponse body: ").append(bodyContent);
        return responseBuffer;
    }

    public static void removeDeniedHeaders(ServerWebExchange exchange, List<String> allowHeaders) {
        List<String> allHeaders = new ArrayList<>();
        for (Map.Entry<String, List<String>> header : exchange.getRequest().getHeaders().entrySet()) {
            allHeaders.add(header.getKey());
        }
        allHeaders.removeAll(allowHeaders);
        allHeaders.forEach(item -> {
            exchange.getRequest()
                    .mutate()
                    .headers(httpHeaders -> httpHeaders.remove(item));
        });
    }

}
