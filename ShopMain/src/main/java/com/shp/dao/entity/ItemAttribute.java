package com.shp.dao.entity;

import com.shp.service.dto.ItemAttributeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "item_attribute",schema = "env_shp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemAttribute {
    @Id
    @Column(name = "item_attribute_id")
    @GeneratedValue(generator = "item_attribute_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "item_attribute_seq", allocationSize = 1, sequenceName = "item_attribute_seq",schema = "env_shp")
    private Long itemAttributeId;
    @Column(name = "attribute_id")
    private Long attributeId;
    @OneToOne
    @JoinColumn(name = "attribute_id",insertable = false ,updatable=false)
    private Attribute attribute;
    @Column(name = "item_id")
    private Long itemId;
    @OneToOne
    @JoinColumn(name = "item_id",insertable = false ,updatable=false)
    private Item item;
    @Column(name = "value_attribute")
    private String valueAttribute;

    public ItemAttribute(ItemAttributeDto dto) {
        this(null,dto.getAttributeId(),null,dto.getItemId(),null,dto.getAttributeValue());
    }
}
