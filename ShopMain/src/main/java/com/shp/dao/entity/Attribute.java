package com.shp.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attribute",schema = "env_shp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attribute {
    @Id
    @Column(name = "attribute_id")
    @GeneratedValue(generator = "item_attribute_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "item_attribute_seq", allocationSize = 1, sequenceName = "item_attribute_seq",schema = "env_shp")
    private Long attributeId;

    @Column(name = "attribute_name")
    private String attributeName;
}
