package com.dao.repository;

import com.dao.entity.OccasionCost;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOccasionCostRepo extends JpaRepository<OccasionCost,Long> {
    List<OccasionCost> getAllByOccasionId(Long occasionId, Pageable pageable);
}
