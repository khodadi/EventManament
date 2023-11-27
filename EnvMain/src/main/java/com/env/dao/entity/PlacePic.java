package com.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "PLACE_PIC",schema = "ENV_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlacePic extends ABaseEntity{
    @Id
    @Column(name = "PLACE_PIC_ID")
    @GeneratedValue(generator = "SEQ_PLACE_PIC", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_PLACE_PIC", allocationSize = 1, sequenceName = "SEQ_PLACE_PIC",schema = "ENV_DATA")
    private Long placePicId;
    @Column(name = "PLACE_ID")
    private Long placeId;
    @Column(name = "PIC_ID")
    private Long picId;
}
