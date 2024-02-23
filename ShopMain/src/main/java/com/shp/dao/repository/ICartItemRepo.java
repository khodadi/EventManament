package com.shp.dao.repository;

import com.shp.dao.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartItemRepo extends JpaRepository<CartItem,Long> {
}
