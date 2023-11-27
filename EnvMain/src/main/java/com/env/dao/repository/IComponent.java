package com.env.dao.repository;

import com.env.dao.entity.Component;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IComponent extends JpaRepository<Component,Long> {
}
