package com.dao.repository;

import com.dao.entity.ItineraryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItineraryDetailRepo extends JpaRepository<ItineraryDetail,Long> {
}
