package com.dao.repository;

import com.dao.entity.Pic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPicRepo extends JpaRepository<Pic,Long> {
}
