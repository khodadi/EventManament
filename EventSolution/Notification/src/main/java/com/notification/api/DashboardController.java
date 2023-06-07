package com.notification.api;

import com.infra.dto.Output;
import com.infra.exception.CodeException;
import com.infra.utils.HttpUtils;
import com.infra.utils.MessageUtils;
import com.notification.dto.BoundDate;
import com.notification.dto.ResultSearch;
import com.notification.service.Iservice.IRequestMasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Locale;

/**
 * @Creator 1/14/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/dashboard")
public class DashboardController {

    private final IRequestMasterService requestMasterService;

    @PostMapping(value = "/load")
    public @ResponseBody
    Output<List<ResultSearch>> searchBaseOnDate(@RequestBody BoundDate boundDate, HttpServletResponse response) {
        Output<List<ResultSearch>> result = new Output<>();
        try {
            result = requestMasterService.searchBaseOnDate(boundDate);
        } catch (Exception e) {
            result.setSuccess(false);
            result.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        result.setMessage(MessageUtils.getMessage(result, new Locale("fa")));
        response.setStatus(HttpUtils.getHttpCode(result.getErrors()));
        return result;
    }
}
