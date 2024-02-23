package com.shp.dao.repository;

import com.shp.dao.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartRepo extends JpaRepository<Cart,Long> {
}
