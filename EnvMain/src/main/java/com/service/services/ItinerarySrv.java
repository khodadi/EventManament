package com.service.services;

import com.basedata.CodeException;
import com.dao.entity.Itinerary;
import com.dao.entity.ItineraryDetail;
import com.dao.entity.ItineraryDetailEquipment;
import com.dao.repository.IItineraryDetailEquipmentRepo;
import com.dao.repository.IItineraryDetailRepo;
import com.dao.repository.IItineraryRepo;
import com.form.OutputAPIForm;
import com.service.dto.*;
import com.utility.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
@Transactional
@Slf4j
public class ItinerarySrv implements IItinerarySrv {

    private final IItineraryRepo itineraryRepo;
    private final IItineraryDetailRepo itineraryDetailRepo;

    private final IItineraryDetailEquipmentRepo itineraryDetailEquipmentRepo;

    public ItinerarySrv(IItineraryRepo itineraryRepo, IItineraryDetailRepo itineraryDetailRepo, IItineraryDetailEquipmentRepo itineraryDetailEquipmentRepo) {
        this.itineraryRepo = itineraryRepo;
        this.itineraryDetailRepo = itineraryDetailRepo;
        this.itineraryDetailEquipmentRepo = itineraryDetailEquipmentRepo;
    }
    @Override
    public OutputAPIForm<ArrayList<ItineraryDto>> saveDefaultItinerary(BaseOccasionDto baseOccasionDto,Long occasionId) {
        OutputAPIForm<ArrayList<ItineraryDto>> retVal = new OutputAPIForm<>();
        ArrayList<ItineraryDto> itineraries = new ArrayList<>();
        try{
            long numberOfDate = DateUtils.diffTwoDateDay(DateUtils.getBeginOfDay(baseOccasionDto.getStartDate()),DateUtils.getBeginOfDay(baseOccasionDto.getEndDate()));
            for(int i = 0;i < numberOfDate;i++){
                Itinerary itinerary = new Itinerary(occasionId,DateUtils.addDays(DateUtils.getBeginOfDay(baseOccasionDto.getStartDate()),i));
                itineraryRepo.save(itinerary);
                itineraries.add(new ItineraryDto(itinerary.getItineraryId(),itinerary.getOccasionId(),itinerary.getItineraryDate()));
            }
            retVal.setData(itineraries);
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
            log.error(e.getMessage());
            e.printStackTrace();
        }
        return retVal;
    }

    public OutputAPIForm saveItineraryDetail(BaseItineraryDetailDto dto){

        OutputAPIForm<ItineraryDetailDto> retVal = new OutputAPIForm();
        try{
            ItineraryDetail itineraryDetail = new ItineraryDetail(dto);
            ItineraryDetailEquipment itineraryDetailEquipment;
            itineraryDetailRepo.save(itineraryDetail);
            ItineraryDetailDto data = new ItineraryDetailDto(itineraryDetail);
            for(Long equipId:dto.getItineraryEquipments()){
                itineraryDetailEquipment = new ItineraryDetailEquipment(null,equipId,itineraryDetail.getItineraryDetailId());
                itineraryDetailEquipmentRepo.save(itineraryDetailEquipment);
                data.getItineraryEquipments().add(new ItineraryDetailEquipmentDto(itineraryDetailEquipment));
            }
            retVal.setData(data);
        }catch (Exception e){
            log.error(e.getMessage());
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }

}
