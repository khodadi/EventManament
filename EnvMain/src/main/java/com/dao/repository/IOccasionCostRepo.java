package com.dao.repository;

import com.dao.entity.OccasionCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOccasionCostRepo extends JpaRepository<OccasionCost,Long> {
}
