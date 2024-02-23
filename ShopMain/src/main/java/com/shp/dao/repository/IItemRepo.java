package com.shp.dao.repository;

import com.shp.dao.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemRepo extends JpaRepository<Item,Long> {
}
