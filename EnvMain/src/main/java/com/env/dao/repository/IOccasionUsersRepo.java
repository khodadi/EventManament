package com.env.dao.repository;

import com.env.dao.entity.OccasionUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOccasionUsersRepo extends JpaRepository<OccasionUsers,Long> {
}
