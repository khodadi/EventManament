package com.env.dao.repository;

import com.env.dao.entity.Place;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

public interface IPlaceRepo extends JpaRepository<Place,Long> {

    @Query(" select ent from Place ent where                    " +
           "  (:eventId is null or ent.eventId = :eventId) and  " +
           "  (:nameFa is null or ent.nameFa = :nameFa)         ")
    ArrayList<Place> getPlaceByCri(@Param("eventId") Long eventId, @Param("nameFa") String nameFa, Pageable page);

}
