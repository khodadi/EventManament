package com.notification.service.Iservice;

import com.notification.basedata.MsgStatusEnum;
import com.notification.dto.*;
import com.infra.dto.Output;

import java.util.ArrayList;
import java.util.List;

public interface IRequestMasterService {

    Output<List<ResultSearch>> searchBaseOnDate(BoundDate boundDate);

    Output<ArrayList<TotalRequestMaster>> loadData(RequestMasterSearch requestMasterSearch);

    Output<DataRequestMaster> save(DataRequestMaster dataRequestMaster);

    Output<DataRequestMaster> update(DataRequestMaster dataRequestMaster);

    Output delete(Long requestMasterId);

    Output<AppNotificationMaster> getAndUpdateMaster(DataSetting dataSetting);
    void updateStatus (MsgStatusEnum status , Long requestMasterId, Long updater);
    void updateTrackingCode ( Long requestMasterId,Long trackingCode);

    void updateSuccessAndFailed (AppNotificationMaster appNotificationMaster, MsgStatusEnum status);
    List getAllTrackingCodeByStatus(AppNotificationMaster appNotificationMaster);

    Output<AppNotificationMaster> cancelNotification(Long requestMasterId);
    Output<AppNotificationMaster> pauseAndResumeSendingMsg(InputPauseAndResume inputPauseAndResume);
}
