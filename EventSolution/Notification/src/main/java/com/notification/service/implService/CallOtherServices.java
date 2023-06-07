package com.notification.service.implService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.infra.dto.BaseData;
import com.infra.dto.Output;
import com.infra.dto.WebServiceRestDto;
import com.infra.service.WebServiceRest;
import com.infra.utils.InfraUtility;
import com.notification.dto.AppNotificationMaster;
import com.notification.service.Iservice.ICallOtherServices;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * @Creator 1/14/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Service
@Slf4j
public class CallOtherServices implements ICallOtherServices {

    @Value("${authentication.baseurl:http://AUTHENTICATION-SERVICE/authentication/api/v1/}")
    private String AUTH_URL;
    @Value("${faragir-bi.baseurl: http://localhost:8080/api/srv/}")
    private String FARAGIR_BI_URL;
    @Value("${faragir-bi.path.saveNotif:notification/insert}")
    private String SAVE_DATA_MASTER;
    @Value("${faragir-bi.path.updateNotif:notification/update}")
    private String UPDATE_DATA_MASTER;
    @Value("${faragir-bi.path.loadNotif:notification/load}")
    private String LOAD_DATA_MASTER;
    @Value("${authentication.path.getUserName:user/getUsers/}")
    private String USERS;
    @Value("${authentication.path.getPermissionByApp:user/getPermissionByApp}")
    private String PERMISSION_BY_APP;

    private final RestTemplate loadBalanced;

    public CallOtherServices(@LoadBalanced RestTemplate loadBalanced) {
        this.loadBalanced = loadBalanced;
    }

    @Override
    public Output<BaseData> getUsers() {
        Output<BaseData> lst = new Output<>();
            WebServiceRestDto response = WebServiceRest.getBalancer(loadBalanced, AUTH_URL + USERS, InfraUtility.createAuthHeaders(), lst);
            if (response.isSuccess()) {
            Gson gson = new Gson();
            lst = gson.fromJson (response.getResult().toString(),Output.class);
            }
        return lst;
    }

    @Override
    public List<AppNotificationMaster> loadDataMasterFromWebServiceBI(List<Long> ids) {
        List<AppNotificationMaster> result = new ArrayList<>();
        try {
            WebServiceRestDto response = WebServiceRest.post(FARAGIR_BI_URL + LOAD_DATA_MASTER, createAuthHeadersBI(), ids);
            if (response.isSuccess()) {
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNodeRoot = objectMapper.readTree(response.getResult().toString());

                if (jsonNodeRoot.get("success") != null && Boolean.parseBoolean(jsonNodeRoot.get("success").toString())) {
                    JsonNode jsonData = jsonNodeRoot.get("data");
                    if (jsonData != null && jsonData.isArray()) {
                        jsonData.iterator().forEachRemaining(jsonNode -> {
                            try {
                                result.add(objectMapper.readValue(jsonNode.toString(), AppNotificationMaster.class));
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                                log.error(e.getMessage());
                            }
                        });
                    }
                }
            }
        }catch (Exception e){
            log.error( e.getMessage());
        }
        return result;
    }

    @Override
    public Output<AppNotificationMaster> saveAndupdateDataMasterInServiceBi(AppNotificationMaster appNotificationMaster, boolean flag) {
        Output<AppNotificationMaster> result= new Output<>();
        try {
            WebServiceRestDto response;
            if (StringUtil.isNullOrEmpty(FARAGIR_BI_URL)) {
                log.error("\n FARAGIR_BI_URL not set correctly");
                result.setSuccess(false);
            } else {
                result.setSuccess(true);
                if (flag) {
                    response = WebServiceRest.post(FARAGIR_BI_URL + SAVE_DATA_MASTER, createAuthHeadersBI(), appNotificationMaster);
                } else {
                    response = WebServiceRest.post(FARAGIR_BI_URL + UPDATE_DATA_MASTER, createAuthHeadersBI(), appNotificationMaster);
                }
                if (response.isSuccess()) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode jsonNodeRoot = null;
                    jsonNodeRoot = objectMapper.readTree(response.getResult().toString());

                    if (jsonNodeRoot.get("success") != null && Boolean.parseBoolean(jsonNodeRoot.get("success").toString())) {
                        JsonNode jsonData = jsonNodeRoot.get("data");
                        if (jsonData != null && jsonData.isArray()) {
                            jsonData.iterator().forEachRemaining(jsonNode -> {
                                try {
                                    result.setData(objectMapper.readValue(jsonNode.toString(), AppNotificationMaster.class));
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                    log.error(e.getMessage());
                                }
                            });
                        }
                    }
                }
            }
        }catch (Exception e){
                log.error( e.getMessage());
            }
        return result;
    }

    public static HttpHeaders createAuthHeadersBI() {
        String authStr = "webservicenotification:!QAZxsw2";
        String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        headers.add("X-FORWARDED-FOR","172.0.0.1");
        headers.add("Content-Type","application/json");
        return headers;
    }

    public static HttpHeaders createAuthHeadersAuth() {
        String authStr = "khodadi:!QAZxsw2";
        String base64Creds = Base64.getEncoder().encodeToString(authStr.getBytes());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Creds);
        headers.add("X-FORWARDED-FOR","127.0.0.1");
        headers.add("Content-Type","application/json");
        return headers;
    }

    public Output<List<String>> getUserPermission(String applicationName) {
        Output<List<String>> lst = new Output<>();
        WebServiceRestDto response = WebServiceRest.getBalancer(loadBalanced, AUTH_URL + PERMISSION_BY_APP + applicationName, InfraUtility.createAuthHeaders(), lst);
        if (response.isSuccess()) {
            Gson gson = new Gson();
            lst = gson.fromJson (response.getResult().toString(),Output.class);
        }else {
            lst.setSuccess(false);
        }
        return lst;
    }
}
