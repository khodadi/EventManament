package com.notification.repo.implrepo;

import com.infra.dto.Output;
import com.notification.basedata.MsgStatusEnum;
import com.notification.basedata.ServiceTypeEnum;
import com.notification.dto.AppNotificationMaster;
import com.notification.dto.DataSetting;
import com.notification.dto.RequestMasterSearch;
import com.notification.repo.irepo.IRequestMasterRepoCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author m.shahrestanaki @createDate 12/10/2022
 */
@Repository
@Slf4j
public class IRequestMasterRepoImpl implements IRequestMasterRepoCustom {
    @PersistenceContext
    private EntityManager hqlManager;

    public List searchSpecification(RequestMasterSearch filter) {
        List output;
        try {
            StringBuilder where = createFilter(filter);
            String hqlStr = " select new com.notification.dto.TotalRequestMaster(master) from RequestMaster master " + where +
                            " order by master." +   filter.getSort();
            output =  hqlManager.createQuery(hqlStr).setFirstResult(filter.getSize() * filter.getPage()).setMaxResults(filter.getSize()).getResultList();
        } catch (Exception e) {
            log.error(e.getMessage());
            output = new ArrayList<>();
        }
        return output;
    }

    @Transactional
    public Output<AppNotificationMaster> getAndUpdateMaster(DataSetting dataSetting) {
        Output<AppNotificationMaster> output = new Output<>();
        try {
            StringBuilder where = createFilterData(dataSetting);
            //----------- hql language ---------
            String hqlStr = " select new com.notification.dto.AppNotificationMaster(master) from RequestMaster master " + where ;
            List<AppNotificationMaster> lst = hqlManager.createQuery(hqlStr).setFirstResult(0).setMaxResults(2).getResultList();
            if(lst.size() > 1){
                output.setNextPage(true);
                lst.remove(1);
            }

            if(lst.size() != 0) {
                StringBuilder whereUpdate = extract(lst);
                String update = " update RequestMaster master set master.status = 1 " + whereUpdate;
                hqlManager.createQuery(update).executeUpdate();
                output.setData(lst.get(0));
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            output.setSuccess(false);
        }
        return output;
    }

    @Override
    public List getAllTrackingCodeByStatus(AppNotificationMaster appNotificationMaster) {
        List output;
        try {
            String hqlStr = " select new com.notification.dto.AppNotificationMaster(master.trackingCode) from RequestMaster master where master.status = 1 ";
            output = hqlManager.createQuery(hqlStr).setFirstResult(appNotificationMaster.getSize() * appNotificationMaster.getPage()).setMaxResults(appNotificationMaster.getSize()).getResultList();

        }catch (Exception e){
            log.error(e.getMessage());
            output = new ArrayList<>();
        }

        return output;
    }

    private StringBuilder extract(List<AppNotificationMaster> output){
        String result = output.stream().map(AppNotificationMaster::getRequestMasterId).map(String::valueOf).collect(Collectors.joining(","));
        return new StringBuilder().append(" where ").append(" master.requestMasterId in(").append(result).append(")");
    }
    private StringBuilder createFilter(RequestMasterSearch filter) {
        StringBuilder where = new StringBuilder();
        if ( filter.getRequestMasterId() !=null || filter.getServiceType() != null || filter.getUserId() != null ||
             filter.getStartDateSend() != null || filter.getEndDateSend() != null ) {
            where.append(" where ");
            boolean addWhereClause = false;
            if(filter.getRequestMasterId() !=null){
                where.append(" master.requestMasterId = ").append(filter.getRequestMasterId());
                addWhereClause=true;
            }
            if (filter.getServiceType() != null) {
                if(addWhereClause){
                    where.append(" and ");
                }
                where.append(" master.serviceType = ").append(filter.getServiceType().getServiceType());
                addWhereClause = true;
            }
            if (filter.getUserId() != null ) {
                if(addWhereClause){
                    where.append(" and ");
                }
                where.append(" master.creatorUserId = ").append(filter.getUserId());
                addWhereClause = true;
            }
            if (filter.getStartDateSend() != null && filter.getEndDateSend() != null) {
                if (addWhereClause) {
                    where.append(" and ");
                }
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                String fromDate = sf.format(filter.getStartDateSend());
                String toDate = sf.format(filter.getEndDateSend());

                where.append("(master.endDateSend >= TO_DATE('").append(fromDate).append(" 00:00:01','YYYY-MM-DD hh24:mi:ss') and " +
                        " master.endDateSend <= TO_DATE('").append(toDate).append(" 23:59:59','YYYY-MM-DD hh24:mi:ss') or")
                        .append(" master.startDateSend >= TO_DATE('").append(fromDate).append(" 00:00:01','YYYY-MM-DD hh24:mi:ss') and " +
                                " master.startDateSend <= TO_DATE('").append(toDate).append(" 23:59:59','YYYY-MM-DD hh24:mi:ss'))");
                addWhereClause = true;
            }
            if (filter.getStartDateSend() != null && filter.getEndDateSend() == null) {
                if (addWhereClause) {
                    where.append(" and ");
                }
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                String fromDate = sf.format(filter.getStartDateSend());
                where.append(" master.startDateSend >= TO_DATE('").append(fromDate).append(" 00:00:01','YYYY-MM-DD hh24:mi:ss')");
                addWhereClause = true;
            }
            if (filter.getStartDateSend() == null && filter.getEndDateSend() != null) {
                if (addWhereClause) {
                    where.append(" and ");
                }
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
                String fromDate = sf.format(filter.getStartDateSend());
                where.append("  master.endDateSend <= TO_DATE(' ").append(fromDate).append(" 23:59:59','YYYY-MM-DD hh24:mi:ss')");
                addWhereClause = true;
            }
            if(!addWhereClause){
                where.setLength(0);
            }
        }
        return where;
    }

    private StringBuilder createFilterData(DataSetting dataSetting) {
        StringBuilder where = new StringBuilder();
        StringBuilder where2 = new StringBuilder();
        boolean addWhereClause = false;

        if (dataSetting.isActiveSms()) {
            where2.append(" master.serviceType = ").append(ServiceTypeEnum.SMS.getServiceType());
            addWhereClause = true;
        }
        if (dataSetting.isActiveEmail()) {
            if(addWhereClause){
                where2.append(" or ");
            }
            where2.append(" master.serviceType = ").append(ServiceTypeEnum.EMAIL.getServiceType());
            addWhereClause = true;
        }
        if (dataSetting.isActiveNotification()) {
            if(addWhereClause){
                where2.append(" or ");
            }
            where2.append(" master.serviceType = ").append(ServiceTypeEnum.NOTIFICATION.getServiceType());
            addWhereClause = true;
        }
        if (dataSetting.isActiveAlarm()) {
            if(addWhereClause){
                where2.append(" or ");
            }
            where2.append(" master.serviceType = ").append(ServiceTypeEnum.ALARM.getServiceType());
            addWhereClause = true;
        }
        if (!addWhereClause) {
            where2.append( "1=1");
        }
/*        int last = where2.lastIndexOf(" or ");
        if(last == where2.length()-4) {
            where2.replace(last, where2.length(), "");
        }*/
        where.append(" where master.status = ").append(MsgStatusEnum.SUBMIT.getMessageStatus()).append("  and (").append(where2).append(") ");
        return where;
    }

}
