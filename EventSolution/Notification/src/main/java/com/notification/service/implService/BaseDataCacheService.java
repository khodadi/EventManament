package com.notification.service.implService;

import com.notification.entity.BaseDataCache;
import com.notification.repo.irepo.IBaseDataCacheRepository;
import com.notification.service.Iservice.IBaseDataCacheService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Creator :  3/18/2023, Saturday
 * @Project : IntelliJ IDEA
 * @Author : Z.Sahraee
 **/

@Transactional
@Service("BaseDataCacheService")
public class BaseDataCacheService implements IBaseDataCacheService {

    private final IBaseDataCacheRepository dataCacheRepository;

    public BaseDataCacheService(IBaseDataCacheRepository dataCacheRepository) {
        this.dataCacheRepository = dataCacheRepository;
    }

    @Override
    public void save(BaseDataCache baseDataCache) {
            dataCacheRepository.save(baseDataCache);
    }

    @Transactional(readOnly = true)
    public BaseDataCache getByBaseName(String baseName) {
        Optional<BaseDataCache> obj = dataCacheRepository.findById(baseName);
        return obj.orElse(null);
    }

}
