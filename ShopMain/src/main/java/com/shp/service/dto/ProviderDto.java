package com.shp.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

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
    private byte[] pic;
    private Long picId;

}
