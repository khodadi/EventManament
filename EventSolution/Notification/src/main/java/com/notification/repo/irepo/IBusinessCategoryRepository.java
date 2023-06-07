package com.notification.repo.irepo;


import com.notification.entity.BusinessCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface IBusinessCategoryRepository extends JpaRepository<BusinessCategory,Long> {

    @Query(value = "select new com.notification.entity.BusinessCategory(bc.categoryCode , bc.businessCategoryId , bc.name )" +
                   " from BusinessCategory bc " +
                   " group by  bc.categoryCode,bc.businessCategoryId, bc.name " +
                   " order by bc.name " )
    ArrayList<BusinessCategory> findAllByCategoryCode();

}
