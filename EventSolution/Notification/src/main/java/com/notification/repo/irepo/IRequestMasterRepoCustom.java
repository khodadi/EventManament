package com.notification.repo.irepo;

import com.infra.dto.Output;
import com.notification.dto.AppNotificationMaster;
import com.notification.dto.DataSetting;
import com.notification.dto.RequestMasterSearch;
import org.springframework.data.repository.NoRepositoryBean;
import java.util.List;

/**
 * @author m.shahrestanaki @createDate 12/10/2022
 */
@NoRepositoryBean
public interface IRequestMasterRepoCustom {
    List searchSpecification(RequestMasterSearch requestMasterSearch);
    Output<AppNotificationMaster> getAndUpdateMaster(DataSetting dataSetting);
    List getAllTrackingCodeByStatus(AppNotificationMaster appNotificationMaster);
}
