package com.service.dto;

import com.dao.entity.Equipment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.Objects;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentDto {
    private Long equipmentId;
    private String equipmentName;
    private String equipmentNameFa;
    private ArrayList<EquipmentDto> children;

    public ArrayList<EquipmentDto> getChildren() {
        if(Objects.isNull(children)){
            children = new ArrayList<>();
        }
        return children;
    }

    public EquipmentDto(Equipment ent){
        this(ent.getEquipmentId(),ent.getEquipmentName(),ent.getEquipmentNameFa(),new ArrayList<>());
    }
}
