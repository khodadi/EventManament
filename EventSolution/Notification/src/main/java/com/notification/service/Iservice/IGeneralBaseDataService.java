package com.notification.service.Iservice;

import com.infra.dto.BaseData;
import com.infra.dto.Output;
import com.notification.dto.InputBaseData;

import java.util.ArrayList;

public interface IGeneralBaseDataService {
    Output<ArrayList<BaseData>> getLovByType(InputBaseData inputBaseData);
    ArrayList<Output<BaseData>> getLovFromDB();
}

