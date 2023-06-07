package com.notification.service.implService;

import com.infra.basedata.ActivationStatusEnum;
import com.infra.dto.Output;
import com.infra.exception.CodeException;
import com.infra.utils.DateUtils;
import com.infra.utils.InfraUtility;
import com.infra.utils.StringUtils;
import com.notification.basedata.MsgStatusEnum;
import com.notification.basedata.ScheduleChoiceEnum;
import com.notification.basedata.ServiceTypeEnum;
import com.notification.dto.*;
import com.notification.entity.RequestMaster;
import com.notification.exception.CodeExceptionProject;
import com.notification.repo.irepo.IRequestMasterRepo;
import com.notification.service.Iservice.ICallOtherServices;
import com.notification.service.Iservice.IRequestMasterService;
import com.notification.utils.Utility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
@Transactional
public class RequestMasterService implements IRequestMasterService {

    private final IRequestMasterRepo iRequestMasterRepoRequest;
    private final ICallOtherServices callOtherServices;

    public RequestMasterService(IRequestMasterRepo iRequestMasterRepoRequest, ICallOtherServices callOtherServices) {
        this.iRequestMasterRepoRequest = iRequestMasterRepoRequest;
        this.callOtherServices = callOtherServices;
    }

    public Output<ArrayList<TotalRequestMaster>> loadData(RequestMasterSearch requestMasterSearch) {
        Output<ArrayList<TotalRequestMaster>> result = new Output<>();
        try {
            int pageSize = requestMasterSearch.getSize();
            requestMasterSearch.setSize(pageSize+1);
            ArrayList<TotalRequestMaster> responseDtos = (ArrayList<TotalRequestMaster>)iRequestMasterRepoRequest.searchSpecification(requestMasterSearch);
            if(responseDtos.size() > pageSize) {
                result.setNextPage(true);
                responseDtos.remove(responseDtos.get(responseDtos.size()-1));
            }
            result.setData(responseDtos);
        } catch (Exception e) {
            log.error(e.getMessage());
            result.getErrors().add(CodeException.BAD_DATA);
            result.setSuccess(false);
        }
        return result;
    }

    public Output<DataRequestMaster> save(DataRequestMaster dataRequestMaster) {
        Output<DataRequestMaster> result = new Output<>();
        try {
            if (dataRequestMaster.getRequestMasterId() == null) {
            result = checkValidate(dataRequestMaster);
            if(result.isSuccess()) {
                    RequestMaster requestMaster = dataRequestMaster.convertDtoToEntity();
                    iRequestMasterRepoRequest.save(requestMaster);
                    dataRequestMaster.setRequestMasterId(requestMaster.getRequestMasterId());
                    result.setData(dataRequestMaster);
                }
            }else{
                result.setSuccess(false);
                result.getErrors().add(CodeException.BAD_DATA);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.getErrors().add(CodeException.SYSTEM_EXCEPTION);
            log.error("Error in save Request Master",e);
        }
        return result;
    }

    public Output<DataRequestMaster> update(DataRequestMaster dataRequestMaster) {
        Output<DataRequestMaster> result = new Output<>();
        try {
            if(dataRequestMaster.getRequestMasterId() == null){
                result.getErrors().add(CodeException.BAD_DATA);
                result.setSuccess(false);
            }else {
                result = checkValidate(dataRequestMaster);
                Optional<RequestMaster> oldMaster = iRequestMasterRepoRequest.findById(dataRequestMaster.getRequestMasterId());
                if (oldMaster.isPresent()) {
                    if (oldMaster.get().getStatus().equals(MsgStatusEnum.SUBMIT)) {
                        dataRequestMaster.convertDtoToEntity(oldMaster.get());
                        iRequestMasterRepoRequest.save(oldMaster.get());
                        result.setData(dataRequestMaster);
                    } else {
                        result.setSuccess(false);
                        result.getErrors().add(CodeExceptionProject.DISABLE_EDIT);
                    }
                }
            }
        }catch (Exception e) {
            result.setSuccess(false);
            result.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        return result;
    }

    @Override
    public Output delete(Long requestMasterId) {
        Output result = new Output();
        try {
            if(requestMasterId != null) {
                Optional<RequestMaster> requestMaster = iRequestMasterRepoRequest.findById(requestMasterId);
                if (requestMaster.isPresent()) {
                    if(requestMaster.get().getStatus().equals(MsgStatusEnum.SUBMIT) ){
                        AtomicReference<Long> currentUser = new AtomicReference<>(1L);
                        InfraUtility.giveCurrentUser().ifPresent(item -> currentUser.set(item.getUserId()));
                        iRequestMasterRepoRequest.updateStatus(MsgStatusEnum.CANCEL_BY_ADMIN,requestMasterId,currentUser.get());
                    } else{
                        result.setSuccess(false);
                        result.getErrors().add(CodeExceptionProject.DISABLE_DELETE);
                    }
                }
            } else{
                result.setSuccess(false);
                result.getErrors().add(CodeException.BAD_DATA);
            }

        } catch (Exception e) {
            result.setSuccess(false);
            result.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        return result;
    }

    @Override
    public Output<List<ResultSearch>> searchBaseOnDate(BoundDate boundDate) {
        Output<List<ResultSearch>> result = new Output<>();
        try {
            if(boundDate.getScheduleChoiceEnum() == null &&
               boundDate.getStartDateSend() == null &&
               boundDate.getEndDateSend() == null){
                  boundDate = new BoundDate(ScheduleChoiceEnum.BEFORE_WEEK);
            }
            Utility.computingDate(boundDate);
           List<ResultSearch> resultSearch = new ArrayList<>();
           resultSearch = iRequestMasterRepoRequest.totalPercent(boundDate.getStartDateSend(),boundDate.getEndDateSend());
           if (!resultSearch.isEmpty()) {
               if(!resultSearch.contains(ServiceTypeEnum.SMS)){
                   resultSearch.add(new ResultSearch(ServiceTypeEnum.SMS,0L , 0L));
               }else if(!resultSearch.contains(ServiceTypeEnum.EMAIL)){
                   resultSearch.add(new ResultSearch(ServiceTypeEnum.EMAIL,0L , 0L));
               }else if(!resultSearch.contains(ServiceTypeEnum.NOTIFICATION)){
                   resultSearch.add(new ResultSearch(ServiceTypeEnum.NOTIFICATION,0L , 0L));
               }else if(!resultSearch.contains(ServiceTypeEnum.ALARM)){
                   resultSearch.add(new ResultSearch(ServiceTypeEnum.ALARM,0L , 0L));
               }else {
                   resultSearch.forEach(resultSearch1 ->
                   {
                       new ResultSearch(resultSearch1.getServiceType(), resultSearch1.getFailedCount(), resultSearch1.getSuccessCount());
                   });
               }
               result.setData(resultSearch);
           }else {
               log.error("there isn't data in preferred date!");
               result.getErrors().add(CodeExceptionProject.NOT_EXIST_DATA);
               result.setSuccess(false);
           }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            result.getErrors().add(CodeException.SYSTEM_EXCEPTION);
            result.setSuccess(false);
        }
        return result;
    }

    public Output checkValidate(DataRequestMaster dataRequestMaster){

        Output result = new Output<>();
       if(dataRequestMaster.getServiceType().equals(ServiceTypeEnum.NOTIFICATION) || dataRequestMaster.getServiceType().equals(ServiceTypeEnum.EMAIL)) {
            result = checkTitle(dataRequestMaster.getTitle());
        }else{
           dataRequestMaster.setTitle(null);
       }
       if(result.isSuccess() && (dataRequestMaster.getStartDateSend() != null && dataRequestMaster.getEndDateSend() != null)){
          result = validateDateAndTime(dataRequestMaster);
       }
        return result;
    }

    private Output validateDateAndTime(DataRequestMaster data) {
        Output result = new Output<>();
        Calendar calendarStart = Calendar.getInstance();
        calendarStart.setTimeInMillis(data.getStartTimeSend().getTime());
        Calendar calendarEnd = Calendar.getInstance();
        calendarEnd.setTimeInMillis(data.getEndTimeSend().getTime());
        data.setStartDateSend(DateUtils.addMinutes(DateUtils.getBeginOfDay(data.getStartDateSend()),calendarStart.get(Calendar.HOUR_OF_DAY)*60 + calendarStart.get(Calendar.MINUTE)));
        data.setEndDateSend(DateUtils.addMinutes(DateUtils.getBeginOfDay(data.getEndDateSend()),calendarEnd.get(Calendar.HOUR_OF_DAY)*60 + calendarEnd.get(Calendar.MINUTE)));

        if(!DateUtils.towDatesVsCurrentDate(data.getStartDateSend(),data.getEndDateSend(),true)){
            result.getErrors().add(CodeExceptionProject.NOTIFICATION_UNBOUND_DATE);
            result.setSuccess(false);
        }
        return result;
    }


    public Output checkTitle(String title){
        Output result = new Output<>();
        if(!StringUtils.validateEmptyString(title).isSuccess()){
            result.getErrors().add(CodeExceptionProject.NOTIFICATION_TITLE_NOTNULL);
            result.setSuccess(false);
        }
        /*
        if (title.length()<10){
            result.getErrors().add(CodeExceptionProject.NOTIFICATION_TITLE_LENGTH);
            result.setSuccess(false);
        }
        */
        return result;
    }

    @Transactional
    public void updateStatus (MsgStatusEnum status , Long requestMasterId,Long updater){
        iRequestMasterRepoRequest.updateStatus(status,requestMasterId, updater);
    }

    @Transactional
    public void updateTrackingCode(Long requestMasterId, Long trackingCode){
        iRequestMasterRepoRequest.updateTrackingCode(requestMasterId,trackingCode);
    }

    public Output<AppNotificationMaster> getAndUpdateMaster(DataSetting dataSetting){
        return iRequestMasterRepoRequest.getAndUpdateMaster(dataSetting);
    }

    @Transactional
    public void updateSuccessAndFailed(AppNotificationMaster appNotificationMaster, MsgStatusEnum status) {
        iRequestMasterRepoRequest.updateSuccessAndFailed(appNotificationMaster,status);
    }

    public List getAllTrackingCodeByStatus(AppNotificationMaster appNotificationMaster) {
        return iRequestMasterRepoRequest.getAllTrackingCodeByStatus(appNotificationMaster);
    }

    @Override
    public Output<AppNotificationMaster> cancelNotification(Long requestMasterId) {
        Output<AppNotificationMaster> result = new Output<>();
        try {
            boolean flag = false;
            if(requestMasterId == null){
                result.getErrors().add(CodeException.BAD_DATA);
                result.setSuccess(false);
            }else {
                RequestMaster old = iRequestMasterRepoRequest.getOne(requestMasterId);
                if(old.getStatus().equals(MsgStatusEnum.DOING)) {
                    AppNotificationMaster data = new AppNotificationMaster(old.getTrackingCode(),ActivationStatusEnum.Inactive.getActivationStatusCode(),
                                                                            MsgStatusEnum.CANCEL_BY_ADMIN.getMessageStatus());

                    result = callOtherServices.saveAndupdateDataMasterInServiceBi(data, flag);
                    if (result.isSuccess()) {
                        AtomicReference<Long> currentUser = new AtomicReference<>(1L);
                        InfraUtility.giveCurrentUser().ifPresent(item -> currentUser.set(item.getUserId()));
                        iRequestMasterRepoRequest.updateServiceStatus(ActivationStatusEnum.Inactive,  MsgStatusEnum.CANCEL_BY_ADMIN, requestMasterId, currentUser.get());
                    } else {
                        result.getErrors().add(CodeException.BAD_DATA);
                        result.setSuccess(false);
                    }
                }else {
                    result.getErrors().add(CodeExceptionProject.DISABLE_CANSEL);
                    result.setSuccess(false);
                }
            }

        }catch (Exception e) {
            result.setSuccess(false);
            result.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }

        return result;
    }

    @Override
    public Output<AppNotificationMaster> pauseAndResumeSendingMsg(InputPauseAndResume inputPauseAndResume) {
        Output<AppNotificationMaster> result = new Output<>();
        try {
            boolean flag = false;
            RequestMaster old = iRequestMasterRepoRequest.getOne(inputPauseAndResume.getRequestMasterId());
            if(inputPauseAndResume.getRequestMasterId() == null){
                result.getErrors().add(CodeException.BAD_DATA);
                result.setSuccess(false);
            }else {
                    if (inputPauseAndResume.getStatus().equals(MsgStatusEnum.PAUSE)) {
                        if (old.getStatus().equals(MsgStatusEnum.DOING)) {
                            AppNotificationMaster data = new AppNotificationMaster(old.getTrackingCode(), MsgStatusEnum.PAUSE.getMessageStatus());
                            result = callOtherServices.saveAndupdateDataMasterInServiceBi(data, flag);
                            if (result.isSuccess()) {
                                AtomicReference<Long> currentUser = new AtomicReference<>(1L);
                                InfraUtility.giveCurrentUser().ifPresent(item -> currentUser.set(item.getUserId()));
                                iRequestMasterRepoRequest.updateStatus(MsgStatusEnum.PAUSE, inputPauseAndResume.getRequestMasterId(), currentUser.get());
                            } else {
                                result.getErrors().add(CodeException.BAD_DATA);
                                result.setSuccess(false);
                            }
                        } else {
                            result.getErrors().add(CodeExceptionProject.DISABLE_PAUSE_OR_RESUME);
                            result.setSuccess(false);
                        }
                    } else if (inputPauseAndResume.getStatus().equals(MsgStatusEnum.RESUME)) {
                        if (old.getStatus().equals(MsgStatusEnum.PAUSE)) {
                            AppNotificationMaster data = new AppNotificationMaster(old.getTrackingCode(), MsgStatusEnum.RESUME.getMessageStatus());
                            result = callOtherServices.saveAndupdateDataMasterInServiceBi(data, flag);
                            if (result.isSuccess()) {
                                AtomicReference<Long> currentUser = new AtomicReference<>(1L);
                                InfraUtility.giveCurrentUser().ifPresent(item -> currentUser.set(item.getUserId()));
                                iRequestMasterRepoRequest.updateStatus(MsgStatusEnum.DOING, inputPauseAndResume.getRequestMasterId(), currentUser.get());
                            } else {
                                result.getErrors().add(CodeException.BAD_DATA);
                                result.setSuccess(false);
                            }
                        } else {
                            result.getErrors().add(CodeExceptionProject.DISABLE_PAUSE_OR_RESUME);
                            result.setSuccess(false);
                        }
                    }
            }
        }catch (Exception e){
            result.setSuccess(false);
            result.getErrors().add(CodeException.SYSTEM_EXCEPTION);
        }
        return result;
    }

}
