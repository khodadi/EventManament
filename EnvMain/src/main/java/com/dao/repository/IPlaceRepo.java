package com.dao.repository;

import com.dao.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlaceRepo extends JpaRepository<Place,Long> {
}
