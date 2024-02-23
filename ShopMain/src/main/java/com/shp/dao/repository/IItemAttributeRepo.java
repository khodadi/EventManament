package com.shp.dao.repository;

import com.shp.dao.entity.ItemAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemAttributeRepo extends JpaRepository<ItemAttribute,Long> {
}
