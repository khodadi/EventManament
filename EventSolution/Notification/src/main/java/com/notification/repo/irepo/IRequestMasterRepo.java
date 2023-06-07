package com.notification.repo.irepo;

import com.infra.basedata.ActivationStatusEnum;
import com.notification.basedata.MsgStatusEnum;
import com.notification.dto.AppNotificationMaster;
import com.notification.dto.ResultSearch;
import com.notification.entity.RequestMaster;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface IRequestMasterRepo extends JpaRepository<RequestMaster, Long> , IRequestMasterRepoCustom {

 @Query(value =
         " select new com.notification.dto.ResultSearch(e.serviceType,                                             " +
                 "       sum(e.failedCount),                                       " +
                 "       sum(e.successCount) )                                      " +
                 " from RequestMaster e                                                           " +
                 " where (e.endDateSend>=:startDateSend and e.endDateSend<=:endDateSend) or    " +
                 "(e.startDateSend>=:startDateSend and e.startDateSend<=:endDateSend)  " +
                 " group by e.serviceType                                                          ")
 List<ResultSearch> totalPercent(Timestamp startDateSend, Timestamp endDateSend);

 @Transactional
 @Modifying
 @Query("update RequestMaster set status =:status ,updaterUserId =:updater, lastUpdate= current_timestamp where requestMasterId =:requestMasterId")
 void updateStatus(MsgStatusEnum status, Long requestMasterId, Long updater);

 @Transactional
 @Modifying
 @Query("update RequestMaster set trackingCode =:trackingCode where requestMasterId =:requestMasterId")
 void updateTrackingCode( Long requestMasterId,Long trackingCode);

 @Modifying
 @Query("update RequestMaster set successCount =:#{#data.successCount} , failedCount =:#{#data.failedCount}  " +
         " , status = :status where trackingCode =:#{#data.trackingCode} ")
 void updateSuccessAndFailed(@Param("data") AppNotificationMaster data, MsgStatusEnum status);

 @Modifying
 @Query("update RequestMaster set serviceStatus =:serviceStatus, status=:status ,updaterUserId =:updater, lastUpdate= current_timestamp where requestMasterId =:requestMasterId")
 void updateServiceStatus(ActivationStatusEnum serviceStatus, MsgStatusEnum status, Long requestMasterId,Long updater);

}