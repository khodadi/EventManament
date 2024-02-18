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

@Table(name = "Category_trl",schema = "env_shp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryTrl {
    @Id
    @Column(name = "category_trl_id")
    @GeneratedValue(generator = "provider_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "provider_seq", allocationSize = 1, sequenceName = "provider_seq",schema = "env_shp")
    private Long categoryTrlId;
    @Column(name = "name_trl")
    private String nameTrl;
    @Column(name = "locale_trl")
    private String localeTrl;
    @Column( name = "category_id")
    private Long categoryId;
    @OneToOne
    @JoinColumn(name = "category_id",insertable = false ,updatable=false)
    private Category category;

}
