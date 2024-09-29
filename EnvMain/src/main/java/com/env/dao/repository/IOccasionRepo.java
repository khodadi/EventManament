package com.env.dao.repository;

import com.env.basedata.StateRequest;
import com.env.dao.entity.Occasion;
import jakarta.persistence.FetchType;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IOccasionRepo extends JpaRepository<Occasion,Long> {


    @Query(" select ent from Occasion ent inner join OccasionUsers usr on (ent.occasionId = usr.occasionId)  " +
            "   where  (" +
            "    (:occasionId is null or ent.occasionId = :occasionId) and (ent.creatorUserId = :userId)  or   " +   //pay attention to Occasion.creatorUserId
            "          (usr.stateRequest = :state and  usr.userId = :userId)) or                              " +   //pay attention to Occasion User
            "          (  ent.sharable = true )                                                               "     //pay attention to Occasion Sharable
    )
    List<Occasion> getOccasionByI(@Param("userId") Long userId, @Param("occasionId") Long occasionId, @Param("state") StateRequest state, Pageable pageable);

@Query( " select ent from Occasion ent                                                                                 " +
        "    left join fetch OccasionUsers usr on (ent.occasionId = usr.occasionId)                                    " +
        "    left join fetch OccasionCost cost on (ent.occasionId = cost.occasionId)                                   " +
        "    left join fetch Itinerary itin    on (ent.occasionId = itin.occasionId)                                   " +
        "    left join fetch ItineraryDetail itind    on (itin.itineraryId = itind.itineraryId)                        " +
        "    left join fetch ItineraryDetailEquipment itinde on (itinde.itineraryDetailId = itind.itineraryDetailId)   " +
        "    left join fetch Place sourcePlace on  (sourcePlace.placeId = itind.sourcePlaceId)                         " +
        "    left join fetch Place destinationPlace on  (destinationPlace.placeId = itind.destinationPlaceId)          " +
        "   where                                                                                                      " +
        "     (:occasionId is null or ent.occasionId = :occasionId) and (                                              " +
        "         (ent.creatorUserId = :userId)  or                                                                    " +   //pay attention to Occasion.creatorUserId
        "         (usr.stateRequest = :state and  usr.userId = :userId) or                                             " +   //pay attention to Occasion User
        "         (ent.sharable = true )                                                                               " +   //pay attention to Occasion Sharable
        "                                                             )                                                "
    )
    @EntityGraph(attributePaths = { "occasionUsers", "itineraries","occasionCosts"},type = EntityGraph.EntityGraphType.FETCH)
    List<Occasion> getOccasionByUserId(@Param("userId") Long userId, @Param("occasionId") Long occasionId, @Param("state") StateRequest state, Pageable pageable);



    @Query( " select ent from Occasion ent                                                                     " +
            "   where  ent.sharable = true and                                                                 " +
            "          (:occasionId is null or ent.occasionId = :occasionId)                                   " )
    List<Occasion> getOccasionPublic( @Param("occasionId") Long occasionId, Pageable pageable);




    @Query(" select ent from Occasion ent inner join OccasionUsers usr on (ent.occasionId = usr.occasionId)  " +
            "   where  (:occasionId is null or ent.occasionId = :occasionId) and                             " +
            "          (usr.stateRequest = :state and  usr.userId = :userId)                                 ")
    Optional<Occasion> getOccasionByUserIdForEdit(@Param("userId") Long userId, @Param("occasionId") Long occasionId, @Param("state") StateRequest state);

}
