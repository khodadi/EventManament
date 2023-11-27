package com.env.dao.repository;

import com.env.dao.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventRepo extends JpaRepository<Event,Long> {
}
