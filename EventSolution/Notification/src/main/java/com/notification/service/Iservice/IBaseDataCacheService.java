package com.notification.service.Iservice;

import com.notification.entity.BaseDataCache;

/**
 * @Creator :  3/18/2023, Saturday
 * @Project : IntelliJ IDEA
 * @Author : Z.Sahraee
 **/
public interface IBaseDataCacheService {
    void save(BaseDataCache baseDataCache);
    BaseDataCache getByBaseName(String baseName);
}
