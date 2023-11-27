package com.env.dao.repository;

import com.env.dao.entity.Itinerary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItineraryRepo extends JpaRepository<Itinerary,Long> {
}
