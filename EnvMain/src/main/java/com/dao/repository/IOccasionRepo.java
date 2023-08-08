package com.dao.repository;

import com.dao.entity.Occasion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOccasionRepo extends JpaRepository<Occasion,Long> {
}
