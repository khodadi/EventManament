package com.shp.service.dto;

import com.shp.dao.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

    private Long itemId;
    private String itemName;
    private Integer countItem;
    private Integer providerId;
    private Long categoryId;
    private String categoryName;
    private ArrayList<ItemAttributeDto> attributes;

    public ArrayList<ItemAttributeDto> getAttributes() {
        if(Objects.isNull(attributes)){
            attributes = new ArrayList<>();
        }
        return attributes;
    }

    public ItemDto(Item dto) {
        this(dto.getItemId(),dto.getItemName(), dto.getCountItem(), dto.getProviderId(),dto.getCategoryId(),dto.getCategory().getName(),null);
    }
}
