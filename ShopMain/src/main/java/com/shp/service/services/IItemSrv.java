package com.shp.service.services;

import com.form.OutputAPIForm;
import com.shp.service.dto.ItemAttributeDto;
import com.shp.service.dto.ItemCriteria;
import com.shp.service.dto.ItemDto;
import java.util.ArrayList;

public interface IItemSrv {
    OutputAPIForm<ItemDto> insertItem(ItemDto itemDto);
    OutputAPIForm<ArrayList<ItemAttributeDto>> addItemAttributes(ArrayList<ItemAttributeDto> itemAttributeDtos);

    OutputAPIForm<ArrayList<ItemDto>> listItemProvider(ItemCriteria cri);
}
