package com.env.dao.repository;

import com.env.dao.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEquipmentRepo extends JpaRepository<Equipment,Long> {
}
