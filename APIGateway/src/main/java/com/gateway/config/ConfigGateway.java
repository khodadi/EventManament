package com.gateway.config;

/**
 * @Creator 8/6/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "gateway-config")
public class ConfigGateway {

    private boolean logRequest;
    private boolean checkRequestBody;
    private boolean checkRequestHeader;
    private boolean logResponse;
    private boolean removeDeniedHeaders;
    private List<String> allowHeaders;


    public boolean isLogRequest() {
        return logRequest;
    }

    public void setLogRequest(boolean logRequest) {
        this.logRequest = logRequest;
    }

    public boolean isCheckRequestBody() {
        return checkRequestBody;
    }

    public void setCheckRequestBody(boolean checkRequestBody) {
        this.checkRequestBody = checkRequestBody;
    }

    public boolean isCheckRequestHeader() {
        return checkRequestHeader;
    }

    public void setCheckRequestHeader(boolean checkRequestHeader) {
        this.checkRequestHeader = checkRequestHeader;
    }

    public boolean isLogResponse() {
        return logResponse;
    }

    public void setLogResponse(boolean logResponse) {
        this.logResponse = logResponse;
    }

    public boolean isRemoveDeniedHeaders() {
        return removeDeniedHeaders;
    }

    public void setRemoveDeniedHeaders(boolean removeDeniedHeaders) {
        this.removeDeniedHeaders = removeDeniedHeaders;
    }

    public List<String> getAllowHeaders() {
        return allowHeaders;
    }

    public void setAllowHeaders(List<String> allowHeaders) {
        this.allowHeaders = allowHeaders;
    }
}
