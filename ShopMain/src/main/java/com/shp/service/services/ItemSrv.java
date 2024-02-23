package com.shp.service.services;

import com.basedata.generalcode.CodeException;
import com.form.OutputAPIForm;
import com.shp.dao.entity.Item;
import com.shp.dao.entity.ItemAttribute;
import com.shp.dao.repository.IItemAttributeRepo;
import com.shp.dao.repository.IItemRepo;
import com.shp.service.dto.ItemAttributeDto;
import com.shp.service.dto.ItemCriteria;
import com.shp.service.dto.ItemDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemSrv implements IItemSrv{

    public final IItemRepo itemRepo;
    public final IItemAttributeRepo itemAttributeRepo;

    public ItemSrv(IItemRepo itemRepo, IItemAttributeRepo itemAttributeRepo) {
        this.itemRepo = itemRepo;
        this.itemAttributeRepo = itemAttributeRepo;
    }

    public OutputAPIForm<ItemDto> insertItem(ItemDto itemDto){
        OutputAPIForm<ItemDto> retVal = new OutputAPIForm<>();
        try{
            Item item = new Item(itemDto);
            itemRepo.save(item);
            itemDto.setItemId(item.getItemId());
            retVal.setData(itemDto);
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }

    public OutputAPIForm<ArrayList<ItemAttributeDto>> addItemAttributes(ArrayList<ItemAttributeDto> itemAttributeDtos){
        OutputAPIForm<ArrayList<ItemAttributeDto>> retVal = new OutputAPIForm<>();
        try{
            ItemAttribute itemAttribute;
            retVal.setData(new ArrayList<ItemAttributeDto>());
            for(ItemAttributeDto dto:itemAttributeDtos){
                //validate Dto
                itemAttribute = new ItemAttribute(dto);
                itemAttributeRepo.save(itemAttribute);
                dto.setAttributeId(itemAttribute.getAttributeId());
                retVal.getData().add(dto);
            }
        }catch (Exception e){
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
            retVal.setSuccess(false);
            e.printStackTrace();
        }
        return retVal;
    }

    public OutputAPIForm<ArrayList<ItemDto>> listItemProvider(ItemCriteria cri){
        OutputAPIForm<ArrayList<ItemDto>> retVal = new OutputAPIForm<>();
        ArrayList<ItemDto> itemDtos = new ArrayList<>();
        ItemDto itemDto;
        try{
            List<Item>  items = itemRepo.findAll();
            for(Item ent:items){
                itemDto = new ItemDto(ent);
                for(ItemAttribute itemAttribute:ent.getItemAttribute()){
                    itemDto.getAttributes().add(new ItemAttributeDto(itemAttribute));
                }
                itemDtos.add(itemDto);
            }
            retVal.setData(itemDtos);
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
            e.printStackTrace();

        }
        return retVal;
    }

}
