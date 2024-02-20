package com.shp.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Creator 2/18/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Entity
@Table(name = "Category",schema = "env_shp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @Column(name = "category_id")
    @GeneratedValue(generator = "provider_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "provider_seq", allocationSize = 1, sequenceName = "provider_seq",schema = "env_shp")
    private Long categoryId;
    @Column(name = "name")
    private String name;
    @Column(name = "parent_category_id")
    private Long parentCategoryId;
}
