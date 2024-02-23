package com.shp.service.dto;

import com.shp.dao.entity.Provider;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @Creator 2/20/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProviderDto {

    private Long providerId;
    private String shopName;
    private Long categoryId;
    private String categoryName;
    private String shopNameTrl;
    private String localeTrl;
    private byte[] pic;
    private Long picId;

    public ProviderDto(Provider ent) {
        this.setProviderId(ent.getProviderId());
        this.setShopName(ent.getShopName());
        if(Objects.nonNull(ent.getCategory())){
            this.setCategoryId(ent.getCategory().getCategoryId());
            this.setCategoryName(ent.getCategory().getName());
        }
        this.setShopNameTrl(ent.getShopName());
        this.setLocaleTrl("en");
        if(Objects.nonNull(ent.getPic())){
            this.setPic(ent.getPic().getPic());
            this.setPicId(ent.getPic().getPicId());
        }
    }
}
