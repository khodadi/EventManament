package com.shp.service.services;

import com.form.OutputAPIForm;
import com.shp.service.dto.ProviderDto;

import java.util.ArrayList;

/**
 * @Creator 2/18/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/


public interface IProviderSrv {
    OutputAPIForm insertProvider(ProviderDto providerDto);
    OutputAPIForm<ArrayList<ProviderDto>> getProvider();
}
