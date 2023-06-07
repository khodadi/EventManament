package com.notification.repo.irepo;

import com.notification.entity.BaseDataCache;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Creator :  3/18/2023, Saturday
 * @Project : IntelliJ IDEA
 * @Author : Z.Sahraee
 **/
@Transactional
public interface IBaseDataCacheRepository extends CrudRepository<BaseDataCache, String> {
}
