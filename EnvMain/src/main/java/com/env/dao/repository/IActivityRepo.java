package com.env.dao.repository;

import com.env.dao.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IActivityRepo extends JpaRepository<Activity,Long> {
}
