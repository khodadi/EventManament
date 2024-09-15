package com.env.dao.repository;

import com.env.dao.entity.OccasionCost;
import com.env.dao.entity.OccasionPic;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Creator 9/2/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public interface IOccasionPicRepo extends JpaRepository<OccasionPic,Long> {
    List<OccasionPic> getAllByOccasionId(Long occasionId, Pageable pageable);
}
