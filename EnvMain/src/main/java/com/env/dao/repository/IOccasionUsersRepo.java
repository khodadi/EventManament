package com.env.dao.repository;

import com.env.dao.entity.OccasionUsers;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOccasionUsersRepo extends JpaRepository<OccasionUsers,Long> {
    List<OccasionUsers> getByOccasionId(Long occasionId);
    List<OccasionUsers> getByUserId(Long userId,Pageable pageable);

}
