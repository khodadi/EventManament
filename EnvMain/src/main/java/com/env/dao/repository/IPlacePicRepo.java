package com.env.dao.repository;

import com.env.dao.entity.PlacePic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlacePicRepo extends JpaRepository<PlacePic,Long> {
    Long countByPlaceId(Long placeId);
    void deleteByPlaceIdAndPlacePicId(Long placeId,Long placePicId);
}
