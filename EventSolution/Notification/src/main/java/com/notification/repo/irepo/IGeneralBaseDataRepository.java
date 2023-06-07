package com.notification.repo.irepo;


import com.notification.entity.GeneralBaseData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface IGeneralBaseDataRepository extends JpaRepository<GeneralBaseData, String> {
    ArrayList<GeneralBaseData> getAllByBstblDesc( String name);
}