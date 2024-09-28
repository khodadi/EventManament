package com.env.service.services;

import com.form.OutputAPIForm;
import com.env.service.dto.*;
import java.util.ArrayList;

public interface IOccasionSrv {
    OutputAPIForm<OccasionDto> saveOccasion(BaseOccasionDto dto);
    OutputAPIForm<OccasionDto> editOccasion(OccasionDto dto);
    OutputAPIForm<ArrayList<OccasionDto>> listOccasion(CriOccasionDto criOccasion);
    OutputAPIForm<ArrayList<OccasionPicDto>> listOccasionPic(CriOccasionDto criOccasion);
    OutputAPIForm<OccasionPicDto> saveOccasionPic(OccasionPicDto dto);
    OutputAPIForm deleteOccasionPic(OccasionPicDto dto);
    byte[] getImage(OccasionPicDto picDto);

}
