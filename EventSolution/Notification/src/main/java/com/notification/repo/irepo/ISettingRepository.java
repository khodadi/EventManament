package com.notification.repo.irepo;


import com.notification.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISettingRepository extends JpaRepository<Setting,Long> {
    List<Setting> getAllByActiveSettingOrderByCreationDateDesc(boolean activeSetting);
}
