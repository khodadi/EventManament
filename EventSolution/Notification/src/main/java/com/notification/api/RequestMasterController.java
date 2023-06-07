package com.notification.api;

import com.infra.dto.Output;
import com.infra.exception.CodeException;
import com.infra.utils.HttpUtils;
import com.infra.utils.MessageUtils;
import com.notification.dto.*;
import com.notification.service.Iservice.IRequestMasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Locale;

/**
 * @Creator 1/11/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/request")
public class RequestMasterController {

    private final IRequestMasterService requestMasterService;

    @PostMapping(value = "/load")
    public Output<ArrayList<TotalRequestMaster>> loadData(@Valid @RequestBody RequestMasterSearch search,
                                                          HttpServletResponse response) {

        Output<ArrayList<TotalRequestMaster>> result = new Output<>();
        try {
            result = requestMasterService.loadData(search);
        } catch (Exception e) {
            result.setSuccess(false);
            result.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        result.setMessage(MessageUtils.getMessage(result, new Locale("fa")));
        response.setStatus(HttpUtils.getHttpCode(result.getErrors()));
        return result;
    }

    @PostMapping(value = "/save")
    public Output<DataRequestMaster> save(@Valid @RequestBody DataRequestMaster dataRequestMaster, HttpServletResponse response) {
        Output<DataRequestMaster> result = new Output<>();
        try {
            result = requestMasterService.save(dataRequestMaster);
        } catch (Exception e) {
            result.setSuccess(false);
            result.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        result.setMessage(MessageUtils.getMessage(result, new Locale("fa")));
        response.setStatus(HttpUtils.getHttpCode(result.getErrors()));
        return result;
    }

    @PutMapping(value = "/update")
    public Output<DataRequestMaster> update(@Valid @RequestBody DataRequestMaster dataRequestMaster, HttpServletResponse response) {
        Output<DataRequestMaster> result = new Output<>();
        try {
            result = requestMasterService.update(dataRequestMaster);
        } catch (Exception e) {
            result.setSuccess(false);
            result.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        result.setMessage(MessageUtils.getMessage(result, new Locale("fa")));
        response.setStatus(HttpUtils.getHttpCode(result.getErrors()));
        return result;
    }

    @DeleteMapping(value = "/delete")
    public Output delete(@RequestParam Long requestMasterId, HttpServletResponse response) {

        Output result = new Output();
        try {
            result = requestMasterService.delete(requestMasterId);
        } catch (Exception e) {
            result.setSuccess(false);
            result.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        result.setMessage(MessageUtils.getMessage(result, new Locale("fa")));
        response.setStatus(HttpUtils.getHttpCode(result.getErrors()));
        return result;

    }

    @PostMapping(value = "/cancelNotification")
    public Output<AppNotificationMaster> cancelNotification(@RequestParam Long requestMasterId, HttpServletResponse response) {
        Output<AppNotificationMaster> result = new Output<>();
        try {
            result = requestMasterService.cancelNotification(requestMasterId);
        } catch (Exception e) {

            result.setSuccess(false);
            result.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        result.setMessage(MessageUtils.getMessage(result, new Locale("fa")));
        response.setStatus(HttpUtils.getHttpCode(result.getErrors()));
        return result;
    }

    @PostMapping(value = "/pauseAndResume")
    public Output<AppNotificationMaster> pauseAndResumeSendingMsg(@RequestBody InputPauseAndResume inputPauseAndResume, HttpServletResponse response) {
        Output<AppNotificationMaster> result = new Output<>();
        try {
            result = requestMasterService.pauseAndResumeSendingMsg(inputPauseAndResume);
        } catch (Exception e) {

            result.setSuccess(false);
            result.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        result.setMessage(MessageUtils.getMessage(result, new Locale("fa")));
        response.setStatus(HttpUtils.getHttpCode(result.getErrors()));
        return result;
    }


}
