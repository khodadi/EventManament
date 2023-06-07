package com.notification.service.implService;

import com.infra.dto.BaseData;
import com.infra.dto.KeyValue;
import com.infra.dto.Output;
import com.notification.basedata.*;
import com.notification.dto.InputBaseData;
import com.notification.entity.*;
import com.notification.repo.irepo.IBusinessCategoryRepository;
import com.notification.repo.irepo.IGeneralBaseDataRepository;
import com.notification.repo.irepo.IPSPRepository;
import com.notification.repo.irepo.IProvinceRepository;
import com.notification.service.Iservice.IBaseDataCacheService;
import com.notification.service.Iservice.ICallOtherServices;
import com.notification.service.Iservice.IGeneralBaseDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GeneralBaseDataService implements IGeneralBaseDataService {
    @Value("${name_string.app_name}")
    private String app_name;
    private final IGeneralBaseDataRepository generalBaseDataRepo;
    private final IBusinessCategoryRepository businessCategoryRepo;
    private final IProvinceRepository provinceRepository;
    private final IPSPRepository pspRepository;
    private final ICallOtherServices callOtherServices;
    private final IBaseDataCacheService baseDataCacheService;

    public GeneralBaseDataService(IGeneralBaseDataRepository generalBaseDataRepo,
                                  IBusinessCategoryRepository businessCategoryRepo,
                                  IProvinceRepository provinceRepository, IPSPRepository pspRepository,
                                  ICallOtherServices callOtherServices,IBaseDataCacheService baseDataCacheService) {
        this.generalBaseDataRepo = generalBaseDataRepo;
        this.businessCategoryRepo = businessCategoryRepo;
        this.provinceRepository = provinceRepository;
        this.pspRepository = pspRepository;
        this.callOtherServices = callOtherServices;
        this.baseDataCacheService = baseDataCacheService;
    }

    @Override
    public Output<ArrayList<BaseData>> getLovByType(InputBaseData inputBaseData) {
        Output<ArrayList<BaseData>> result = new Output<>();
        ArrayList<BaseData> baseDataList = new ArrayList<>();
        if(inputBaseData.getType() != null){
            BaseData data = getDataFromCacheOrDB(inputBaseData.getType());
            baseDataList.add(data);
            if(data.getLov().size() == 0){
                result.setSuccess(false);
            }else {
                result.setData(baseDataList);
            }
        }else {
            baseDataList.add(ServiceTypeEnum.getServiceTypeEnum());
            baseDataList.add(AppTypeEnum.getAppTypeTypeEnum());
            baseDataList.add(getDataFromCacheOrDB(BaseDataEnum.BusinessCategory));
            baseDataList.add(getDataFromCacheOrDB(BaseDataEnum.PSP));
            baseDataList.add(getDataFromCacheOrDB(BaseDataEnum.Province));
            baseDataList.add(getDataFromCacheOrDB(BaseDataEnum.MerchantType));
            baseDataList.add(getDataFromCacheOrDB(BaseDataEnum.ResidencyType));
            baseDataList.add(getDataFromCacheOrDB(BaseDataEnum.TerminalType));
            baseDataList.add(MsgStatusEnum.getMsgStatusEnum());
            baseDataList.add(ScheduleChoiceEnum.getScheduleChoiceEnum());
            baseDataList.add(callOtherServices.getUsers().getData());
            result.setData(baseDataList);
        }
        return result;
    }

    /** for insert data from db to redis: call by job **/
    @Override
    public ArrayList<Output<BaseData>> getLovFromDB() {
        ArrayList<Output<BaseData>> result = new ArrayList<>();
        result.add(getBusinessCategoriesFromDb());
        result.add(getPspsFromDb());
        result.add(getProvinceFromDb());
        result.add(getDataFromDb(BaseDataEnum.MerchantType));
        result.add(getDataFromDb(BaseDataEnum.ResidencyType));
        result.add(getDataFromDb(BaseDataEnum.TerminalType));
        result.add(callOtherServices.getUsers());
        return result;
    }

    private BaseData getDataFromCacheOrDB(BaseDataEnum baseName){
        Output<BaseData> result = new Output<>();
        try {
            result = getBaseDataCache(app_name + baseName.toString());
            if (!result.isSuccess()) {
                switch (baseName){
                    case BusinessCategory:
                        result = getBusinessCategoriesFromDb();
                        break;
                    case PSP:
                        result = getPspsFromDb();
                        break;
                    case Province:
                        result = getProvinceFromDb();
                        break;
                    case MerchantType:
                        result = getDataFromDb(BaseDataEnum.MerchantType);
                        break;
                    case ResidencyType:
                        result = getDataFromDb(BaseDataEnum.ResidencyType);
                        break;
                    case TerminalType:
                        result = getDataFromDb(BaseDataEnum.TerminalType);
                        break;
                }
            }
        }catch (Exception e){
            log.error("Error in read " + baseName, e);
            result.getData().getLov().add(new KeyValue(baseName.toString(),null));
            result.setSuccess(false);
        }
        return result.getData();
    }

    /** data caching in redis **/
    private Output<BaseData> getBaseDataCache(String baseName){
        Output<BaseData> result = new Output<>();
        try {
            BaseDataCache baseDataCache = baseDataCacheService.getByBaseName(baseName);
            if(baseDataCache != null && baseDataCache.getData() != null) {
                result.setData(baseDataCache.getData());
            }else{
                result.setSuccess(false);
            }
        }catch (Exception e){
            log.error("Error in read " + baseName, e);
        }
        return result;
    }

    /** for get three field: terminalType, merchantType, residencyType from one base-table **/
    private Output<BaseData> getDataFromDb(BaseDataEnum baseName) {
        Output<BaseData> result = new Output<>();
        BaseData baseData = new BaseData();
        baseData.setBaseType(baseName.toString());
        try {
            ArrayList<GeneralBaseData> data = generalBaseDataRepo.getAllByBstblDesc(baseName.toString());
            data.forEach(generalBaseData -> baseData.getLov().add(new KeyValue(generalBaseData.getCodeMms().toString(), generalBaseData.getFdesc())));
            BaseDataCache baseDataCache =new BaseDataCache();
            baseDataCache.setData(baseData);
            baseDataCacheService.save(baseDataCache);
            result.setData(baseData);
            if(baseData.getLov().size() == 0){
                result.setSuccess(false);
            }
        } catch (Exception e) {
            log.error("Error in read " + baseName, e);
            result.setSuccess(false);
        }
        return result;
    }

    private Output<BaseData> getProvinceFromDb() {
        Output<BaseData> result = new Output<>();
        BaseData baseData = new BaseData();
        baseData.setBaseType(BaseDataEnum.Province.toString());
        try {
            List<Province> provinces = provinceRepository.findAll();
            provinces.forEach(province -> baseData.getLov().add(new KeyValue(province.getProvinceId().toString(), province.getFarsiName())));
            BaseDataCache baseDataCache =new BaseDataCache();
            baseDataCache.setData(baseData);
            baseDataCacheService.save(baseDataCache);
            result.setData(baseData);
            if(baseData.getLov().size() == 0){
                result.setSuccess(false);
            }
        } catch (Exception e) {
            log.error("Error in read province", e);
            result.setSuccess(false);
        }

        return result;
    }

    private Output<BaseData> getBusinessCategoriesFromDb() {
        Output<BaseData> result = new Output<>();
        BaseData baseData = new BaseData();
        baseData.setBaseType(BaseDataEnum.BusinessCategory.toString());
        try {
            ArrayList<BusinessCategory> businessCategories = businessCategoryRepo.findAllByCategoryCode();
            businessCategories.forEach(businessCategory -> baseData.getLov().add(new KeyValue(String.valueOf(businessCategory.getBusinessCategoryId()), businessCategory.getName())));
            BaseDataCache baseDataCache =new BaseDataCache();
            baseDataCache.setData(baseData);
            baseDataCacheService.save(baseDataCache);
            result.setData(baseData);
            if(baseData.getLov().size() == 0){
                result.setSuccess(false);
            }
        } catch (Exception e) {
            log.error("Error in read business category", e);
            result.setSuccess(false);
        }
        return result;
    }

    private Output<BaseData> getPspsFromDb() {
        Output<BaseData> result = new Output<>();
        BaseData baseData = new BaseData();
        baseData.setBaseType(BaseDataEnum.PSP.toString());
        try {
            List<PSP> psps = pspRepository.findAll();
            psps.forEach(psp -> baseData.getLov().add(new KeyValue(String.valueOf(psp.getPspId()), psp.getFarsiName())));
            BaseDataCache baseDataCache =new BaseDataCache();
            baseDataCache.setData(baseData);
            baseDataCacheService.save(baseDataCache);
            result.setData(baseData);
            if(baseData.getLov().size() == 0){
                result.setSuccess(false);
            }
        } catch (Exception e) {
            log.error("Error in read psp", e);
            result.setSuccess(false);
        }
        return result;
    }


}
