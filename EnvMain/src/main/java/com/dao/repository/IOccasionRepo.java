package com.dao.repository;

import com.dao.entity.Occasion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;

public interface IOccasionRepo extends JpaRepository<Occasion,Long> {
    @Query(" select ent from Occasion ent inner join OccasionUsers usr on (ent.occasionId = usr.occasionId) " +
           "   where  (:occasionId is null or ent.occasionId = :occasionId) and " +
           "          ((usr.userId = :userId) or (usr.userId<> :userId and ent.sharable=true)) ")
    List<Occasion> getOccasionByUserId(@Param("userId") Long userId,@Param("occasionId") Long occasionId, Pageable pageable);
}
