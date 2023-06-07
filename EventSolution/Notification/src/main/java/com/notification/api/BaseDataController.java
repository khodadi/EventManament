package com.notification.api;

import com.infra.dto.BaseData;
import com.infra.dto.Output;
import com.infra.exception.CodeException;
import com.infra.utils.HttpUtils;
import com.infra.utils.MessageUtils;
import com.notification.dto.InputBaseData;
import com.notification.service.Iservice.IGeneralBaseDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Locale;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/baseData")
public class BaseDataController {
    private final IGeneralBaseDataService generalBaseDataService;

    @PostMapping(value = "/load")
    public @ResponseBody Output<ArrayList<BaseData>> baseDataLoad(@RequestBody InputBaseData inputBaseData, HttpServletResponse response) {
        Output<ArrayList<BaseData>>  result = new Output<>();
        try {
            result = generalBaseDataService.getLovByType(inputBaseData);
        } catch (Exception e) {
            result.setSuccess(false);
            result.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        result.setMessage(MessageUtils.getMessage(result, new Locale("fa")));
        response.setStatus(HttpUtils.getHttpCode(result.getErrors()));
        return result;
    }
}
