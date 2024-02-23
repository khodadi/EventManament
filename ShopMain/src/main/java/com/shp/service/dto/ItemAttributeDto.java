package com.shp.service.dto;

import com.shp.dao.entity.ItemAttribute;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemAttributeDto {

    private Long attributeId;
    private Long itemId;
    private String attributeValue;
    private String attributeName;

    public ItemAttributeDto(ItemAttribute ent) {
        this(ent.getAttributeId(),ent.getItemId(),ent.getValueAttribute(),ent.getAttribute().getAttributeName());
    }
}
