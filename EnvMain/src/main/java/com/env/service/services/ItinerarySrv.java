package com.env.service.services;

import com.basedata.generalcode.CodeException;
import com.env.dao.entity.*;
import com.env.dao.repository.*;
import com.env.service.dto.*;
import com.form.OutputAPIForm;
import com.utility.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ItinerarySrv implements IItinerarySrv {

    private final IItineraryRepo itineraryRepo;
    private final IItineraryDetailRepo itineraryDetailRepo;
    private final IItineraryDetailEquipmentRepo itineraryDetailEquipmentRepo;
    private final IPlaceRepo placeRepo;
    private final IOccasionSrv occasionSrv;
    private final IOccasionRepo occasionRepo;

    public ItinerarySrv(IItineraryRepo itineraryRepo, IItineraryDetailRepo itineraryDetailRepo, IItineraryDetailEquipmentRepo itineraryDetailEquipmentRepo, IPlaceRepo placeRepo, IOccasionSrv occasionSrv, IOccasionRepo occasionRepo) {
        this.itineraryRepo = itineraryRepo;
        this.itineraryDetailRepo = itineraryDetailRepo;
        this.itineraryDetailEquipmentRepo = itineraryDetailEquipmentRepo;
        this.placeRepo = placeRepo;
        this.occasionSrv = occasionSrv;
        this.occasionRepo = occasionRepo;
    }
    @Override
    public OutputAPIForm<ArrayList<ItineraryDto>> saveDefaultItinerary(BaseOccasionDto baseOccasionDto, Long occasionId) {
        OutputAPIForm<ArrayList<ItineraryDto>> retVal = new OutputAPIForm<>();
        ArrayList<ItineraryDto> itineraries = new ArrayList<>();
        try{
            long numberOfDate = DateUtils.diffTwoDateDay(DateUtils.getBeginOfDay(baseOccasionDto.getStartDate()),DateUtils.getBeginOfDay(baseOccasionDto.getEndDate()));
            for(int i = 0;i <= numberOfDate;i++){
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

    public void insertForEditDefaultItinerary(Optional<Occasion> occasion, OccasionDto dto, OutputAPIForm<ArrayList<ItineraryDto>> retVal){
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
            Place srcPlace = (dto!= null && dto.getSourceId() != null) ? placeRepo.getReferenceById(dto.getSourceId()):null;
            Place decPlace = (dto!= null && dto.getDestinationId() != null) ? placeRepo.getReferenceById(dto.getDestinationId()): null;
            if(Objects.nonNull(srcPlace)){
                ItineraryDetail itineraryDetail = new ItineraryDetail(dto,srcPlace,decPlace);
                ItineraryDetailEquipment itineraryDetailEquipment;
                itineraryDetailRepo.save(itineraryDetail);
                ItineraryDetailDto data = new ItineraryDetailDto(itineraryDetail);
                for(Long equipId:dto.getItineraryEquipments()){
                    itineraryDetailEquipment = new ItineraryDetailEquipment(null,equipId,itineraryDetail.getItineraryDetailId());
                    itineraryDetailEquipmentRepo.save(itineraryDetailEquipment);
                    data.getItineraryEquipments().add(new ItineraryDetailEquipmentDto(itineraryDetailEquipment));
                }
                retVal.setData(data);
            }else{
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.NOT_FIND_REFERENCE);
            }

        }catch (Exception e){
            log.error(e.getMessage());
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.DATA_BASE_EXCEPTION);
        }
        return retVal;
    }

    public OutputAPIForm updateItineraryDetail(BaseItineraryDetailDto dto){
        OutputAPIForm retVal = validationItineraryDetail(dto);
        try{
            if(retVal.isSuccess()){
                Optional<ItineraryDetail> itineraryDetail = itineraryDetailRepo.getItineraryDetailByOccasionAndId(dto.getItineraryDetailId(),dto.getItineraryId(),dto.getOccasionId());
                if(itineraryDetail.isPresent()){
                    itineraryDetail.get().update(dto);
                    itineraryDetailRepo.save(itineraryDetail.get());
                    updateItineraryDetailEquipments(dto);
                }else{
                    retVal.setSuccess(false);
                    retVal.getErrors().add(CodeException.NOT_FIND_REFERENCE);
                }
            }
        }catch (Exception e){
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.UNDEFINED);
        }
        return retVal;
    }

    public OutputAPIForm deleteItineraryDetail(OccasionItineraryDetailDto dto){
        OutputAPIForm retVal = validationOccasionItineraryDetail(dto);
        try{
            if(retVal.isSuccess()) {
                Optional<ItineraryDetail> itineraryDetail = itineraryDetailRepo.getItineraryDetailByOccasionAndId(dto.getItineraryDetailDto(),dto.getOccasionId());
                if(itineraryDetail.isPresent()){
                    itineraryDetailRepo.delete(itineraryDetail.get());
                }else{
                    retVal.setSuccess(false);
                    retVal.getErrors().add(CodeException.NOT_FIND_REFERENCE);
                }
            }
        }catch (Exception e){
            log.error("Error In Delete Itinerary Detail",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.UNDEFINED);
        }
        return retVal;
    }

    public OutputAPIForm updateItineraryDetailEquipments(BaseItineraryDetailDto dto){
        OutputAPIForm retVal = new OutputAPIForm();
        if(Objects.nonNull(dto.getItineraryEquipments())){
            ItineraryDetailEquipment itineraryDetailEquipment;
            itineraryDetailEquipmentRepo.deleteByItineraryDetailId(dto.getItineraryDetailId());
            for(Long equipId:dto.getItineraryEquipments()){
                itineraryDetailEquipment = new ItineraryDetailEquipment(null,equipId,dto.getItineraryDetailId());
                itineraryDetailEquipmentRepo.save(itineraryDetailEquipment);
            }
        }
        return retVal;
    }

    public OutputAPIForm validationOccasionItineraryDetail(OccasionItineraryDetailDto dto){

        OutputAPIForm retVal = new OutputAPIForm();
        try{
            if(Objects.nonNull(dto) && Objects.nonNull(dto.getItineraryDetailDto()) && Objects.nonNull(dto.getItineraryDetailDto())) {
                retVal = occasionSrv.hasAccessOccasion(dto.getOccasionId());
            }else{
                retVal.setSuccess(false);
                retVal.getErrors().add(CodeException.MANDATORY_FIELD);
            }
        }catch (Exception e){
            log.error("Error In validation  Itinerary Detail",e);
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.UNDEFINED);
        }

        return retVal;
    }

    public OutputAPIForm validationItineraryDetail(BaseItineraryDetailDto dto){
        OutputAPIForm retVal = new OutputAPIForm();
        if(Objects.nonNull(dto) &&
           Objects.nonNull(dto.getItineraryDetailId()) &&
           Objects.nonNull(dto.getItineraryId()) &&
           Objects.nonNull(dto.getOccasionId())){
            retVal = occasionSrv.hasAccessOccasion(dto.getOccasionId());
        }else{
            retVal.setSuccess(false);
            retVal.getErrors().add(CodeException.MANDATORY_FIELD);
        }
        return retVal;
    }
}
