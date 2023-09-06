package com.service.services;

import com.basedata.generalcode.CodeException;
import com.dao.entity.Itinerary;
import com.dao.entity.ItineraryDetail;
import com.dao.entity.ItineraryDetailEquipment;
import com.dao.entity.Occasion;
import com.dao.repository.IItineraryDetailEquipmentRepo;
import com.dao.repository.IItineraryDetailRepo;
import com.dao.repository.IItineraryRepo;
import com.form.OutputAPIForm;
import com.service.dto.*;
import com.utility.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Optional;

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
                itineraries.add(new ItineraryDto(itinerary.getItineraryId(),itinerary.getOccasionId(),itinerary.getItineraryDate(),null));
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

    public  OutputAPIForm<ArrayList<ItineraryDto>> editDefaultItinerary(Optional<Occasion> occasion,OccasionDto dto){
        OutputAPIForm<ArrayList<ItineraryDto>> retVal = deleteForEditDefaultItinerary(occasion,dto);
        if(retVal.isSuccess()){
            insertForEditDefaultItinerary(occasion,dto,retVal);
        }
        return retVal;
    }

    public OutputAPIForm<ArrayList<ItineraryDto>> deleteForEditDefaultItinerary(Optional<Occasion> occasion,OccasionDto dto){
        OutputAPIForm<ArrayList<ItineraryDto>> retVal = new OutputAPIForm();
        ArrayList<ItineraryDto> itineraryDtos = new ArrayList<>();
        boolean delItinerary = true;
        Timestamp startDate;
        if(occasion.isPresent()){
            ArrayList<Itinerary> delItineraries = new ArrayList<>();
            for(Itinerary itinerary:occasion.get().getItineraries()){
                startDate = DateUtils.getBeginOfDay(new Timestamp(dto.getStartDate().getTime()));
                while (startDate.before(DateUtils.getBeginOfDay(dto.getEndDate()))){
                    delItinerary = true;
                    if(itinerary.getItineraryDate().equals(startDate)){
                        delItinerary = false;
                        break;
                    }
                    startDate = DateUtils.addDays(startDate,1);
                }
                if(delItinerary){
                    delItineraries.add(itinerary);
                }else{
                    itineraryDtos.add(new ItineraryDto(itinerary));
                }
            }
            itineraryRepo.deleteAll(delItineraries);
        }
        retVal.setData(itineraryDtos);
        return retVal;
    }

    public void insertForEditDefaultItinerary(Optional<Occasion> occasion,OccasionDto dto,OutputAPIForm<ArrayList<ItineraryDto>> retVal){
        boolean insetItinerary ;
        Timestamp startDate;
        if(occasion.isPresent()){
            Itinerary addItinerary;
            startDate = DateUtils.getBeginOfDay(new Timestamp(dto.getStartDate().getTime()));
            while (startDate.before(DateUtils.getBeginOfDay(dto.getEndDate()))){
                insetItinerary = true;
                for(Itinerary itinerary:occasion.get().getItineraries()){
                    if(itinerary.getItineraryDate().equals(startDate)){
                        insetItinerary = false;
                        break;
                    }
                }
                if(insetItinerary){
                    addItinerary = new Itinerary(occasion.get().getOccasionId(), startDate);
                    itineraryRepo.save(addItinerary);
                    retVal.getData().add(new ItineraryDto(addItinerary));
                }
                startDate = DateUtils.addDays(startDate,1);
            }
        }
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
