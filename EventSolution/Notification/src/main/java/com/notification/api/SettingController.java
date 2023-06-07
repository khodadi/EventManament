package com.notification.api;

import com.infra.dto.Output;
import com.infra.exception.CodeException;
import com.infra.utils.HttpUtils;
import com.infra.utils.MessageUtils;
import com.notification.dto.DataSetting;
import com.notification.service.Iservice.ISettingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Locale;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/setting")
public class SettingController {

    private final ISettingService settingService;

    @GetMapping(value = "/load")
    public Output<DataSetting> getActiveStatus(HttpServletResponse response) {

        Output<DataSetting> result = new Output<>();
        try {
            result = settingService.getActiveStatus();
        } catch (Exception e) {
            result.setSuccess(false);
            result.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        result.setMessage(MessageUtils.getMessage(result, new Locale("fa")));
        response.setStatus(HttpUtils.getHttpCode(result.getErrors()));
        return result;

    }

    @PostMapping(value = "/update")
    public Output changeData(@Valid @RequestBody DataSetting dataSetting, HttpServletResponse response) {
        Output result = new Output<>();
        try {
            result = settingService.update(dataSetting);
        } catch (Exception e) {
            result.setSuccess(false);
            result.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        result.setMessage(MessageUtils.getMessage(result, new Locale("fa")));
        response.setStatus(HttpUtils.getHttpCode(result.getErrors()));
        return result;
    }
}