package com.shp.service.services;

import com.basedata.generalcode.CodeException;
import com.form.OutputAPIForm;
import com.shp.dao.entity.Pic;
import com.shp.dao.entity.Provider;
import com.shp.dao.entity.ProviderTrl;
import com.shp.dao.repository.IPicRepo;
import com.shp.dao.repository.IProviderRepo;
import com.shp.dao.repository.IProviderTrlRepo;
import com.shp.service.dto.ProviderDto;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @Creator 2/18/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Service
public class ProviderSrv implements IProviderSrv{

    private final IProviderRepo providerRepo;
    private final IPicRepo picRepo;

    private final IProviderTrlRepo providerTrlRepo;

    public ProviderSrv(IProviderRepo providerRepo, IPicRepo picRepo, IProviderTrlRepo providerTrlRepo) {
        this.providerRepo = providerRepo;
        this.picRepo = picRepo;
        this.providerTrlRepo = providerTrlRepo;
    }

    public OutputAPIForm<ProviderDto> insertProvider(ProviderDto providerDto){
        OutputAPIForm outputAPIForm = new OutputAPIForm();
        // validate Provider
        try {
            savePic(providerDto);
            saveProvider(providerDto);
            saveProviderTrl(providerDto);
        }catch (Exception e){
            outputAPIForm.setSuccess(false);
            outputAPIForm.getErrors().add(CodeException.SYSTEM_EXCEPTION);
            e.printStackTrace();
        }
        return outputAPIForm;
    }

    public OutputAPIForm<ArrayList<ProviderDto>> getProvider(){
        OutputAPIForm outputAPIForm = new OutputAPIForm();
        // validate Provider
        try {
            ArrayList<ProviderDto> providerDtos = new ArrayList<>();
            List<Provider> providers = providerRepo.findAll();
            providers.forEach(provider -> {
                providerDtos.add(new ProviderDto(provider));
            });
            outputAPIForm.setData(providerDtos);
        }catch (Exception e){
            outputAPIForm.setSuccess(false);
            outputAPIForm.getErrors().add(CodeException.SYSTEM_EXCEPTION);
            e.printStackTrace();
        }
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
    public void saveProviderTrl(ProviderDto providerDto){
        ProviderTrl providerTrl = new ProviderTrl(providerDto);
        providerTrlRepo.save(providerTrl);
    }

}
