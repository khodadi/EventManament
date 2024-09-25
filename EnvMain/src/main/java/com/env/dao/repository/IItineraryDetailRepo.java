package com.env.dao.repository;

import com.env.basedata.StateRequest;
import com.env.dao.entity.ItineraryDetail;
import com.env.dao.entity.Occasion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IItineraryDetailRepo extends JpaRepository<ItineraryDetail,Long> {

    @Query(" select ent from ItineraryDetail ent inner join Itinerary iti     " +
           "   on (ent.itineraryId = iti.itineraryId)                         " +
           "  where  iti.occasionId = :occasionId and                         " +
           "   ent.itineraryDetailId =: itineraryDetailId                     ")
    Optional<ItineraryDetail> getItineraryDetailByOccasionAndId(@Param("itineraryDetailId") Long itineraryDetailId, @Param("occasionId") Long occasionId);

    @Query(" select ent from ItineraryDetail ent inner join Itinerary iti     " +
           "   on (ent.itineraryId = iti.itineraryId)                         " +
           "  where  iti.occasionId = :occasionId and                         " +
           "    iti.itineraryId = :itineraryId and                            " +
           "   ent.itineraryDetailId =: itineraryDetailId                     ")
    Optional<ItineraryDetail> getItineraryDetailByOccasionAndId(@Param("itineraryDetailId") Long itineraryDetailId,
                                                                @Param("itineraryId") Long itineraryId,
                                                                @Param("occasionId") Long occasionId);

}
