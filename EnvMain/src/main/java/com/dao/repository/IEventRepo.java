package com.dao.repository;

import com.dao.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventRepo extends JpaRepository<Event,Long> {
}
