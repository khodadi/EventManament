package com.dao.repository;

import com.dao.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IComponent extends JpaRepository<Component,Long> {
}
