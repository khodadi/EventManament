package com.env.dao.repository;

import com.env.dao.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlaceRepo extends JpaRepository<Place,Long> {
}
