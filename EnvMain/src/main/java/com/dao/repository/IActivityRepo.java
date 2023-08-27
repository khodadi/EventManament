package com.dao.repository;

import com.dao.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IActivityRepo extends JpaRepository<Activity,Long> {
}
