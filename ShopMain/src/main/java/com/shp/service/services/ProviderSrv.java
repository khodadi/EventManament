package com.shp.service.services;

import com.form.OutputAPIForm;
import com.shp.dao.entity.Pic;
import com.shp.dao.entity.Provider;
import com.shp.dao.repository.IPicRepo;
import com.shp.dao.repository.IProviderRepo;
import com.shp.service.dto.ProviderDto;
import org.springframework.stereotype.Service;

/**
 * @Creator 2/18/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Service
public class ProviderSrv implements IProviderSrv{

    private final IProviderRepo providerRepo;
    private final IPicRepo picRepo;

    public ProviderSrv(IProviderRepo providerRepo, IPicRepo picRepo) {
        this.providerRepo = providerRepo;
        this.picRepo = picRepo;
    }

    public OutputAPIForm<ProviderDto> insertProvider(ProviderDto providerDto){

        OutputAPIForm outputAPIForm = new OutputAPIForm();
        //validate Provider
        savePic(providerDto);
        saveProvider(providerDto);

        return outputAPIForm;

    }

    public void savePic(ProviderDto providerDto){
        Pic pic = new Pic(providerDto.getPic(),providerDto.getShopName());
        picRepo.save(pic);
        providerDto.setPicId(pic.getPicId());
    }
    public void saveProvider(ProviderDto providerDto){
        Provider provider = new Provider(providerDto);
        providerRepo.save(provider);
        providerDto.setProviderId(provider.getProviderId());
    }

}
