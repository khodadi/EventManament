package com.dao.repository;

import com.dao.entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItineraryRepo extends JpaRepository<Itinerary,Long> {
}
