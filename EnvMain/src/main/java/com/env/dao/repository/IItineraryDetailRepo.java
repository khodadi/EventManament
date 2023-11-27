package com.env.dao.repository;

import com.env.dao.entity.ItineraryDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItineraryDetailRepo extends JpaRepository<ItineraryDetail,Long> {
}
