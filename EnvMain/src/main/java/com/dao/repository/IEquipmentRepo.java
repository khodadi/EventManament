package com.dao.repository;

import com.dao.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEquipmentRepo extends JpaRepository<Equipment,Long> {
}
