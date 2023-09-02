package com.dao.repository;

import com.dao.entity.OccasionUsers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOccasionUsersRepo extends JpaRepository<OccasionUsers,Long> {
}
