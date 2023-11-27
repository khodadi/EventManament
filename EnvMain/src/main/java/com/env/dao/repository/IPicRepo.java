package com.env.dao.repository;

import com.env.dao.entity.Pic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPicRepo extends JpaRepository<Pic,Long> {
}
