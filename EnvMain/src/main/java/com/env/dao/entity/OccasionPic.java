package com.dao.entity;

import com.service.dto.OccasionPicDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * @Creator 9/2/2023
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Entity
@Table(name = "OCCASION_PIC",schema = "ENV_DATA")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OccasionPic extends ABaseEntity{
    @Id
    @Column(name = "OCCASION_PIC_ID")
    @GeneratedValue(generator = "SEQ_OCCASION_PIC", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_OCCASION_PIC", allocationSize = 1, sequenceName = "SEQ_OCCASION_PIC",schema = "ENV_DATA")
    private Long occasionPicId;
    @Column(name = "OCCASION_ID")
    private Long occasionId;
    @Column(name = "SHARABLE")
    private boolean sharable;
    @Column(name = "name")
    private String name;
    @Column(name = "PIC_ID")
    private Long picId;
    @JdbcTypeCode(SqlTypes.JSON)
    @OneToOne
    @JoinColumn(name = "pic_Id",insertable = false ,updatable=false)
    private Pic pic;
    @OneToOne
    @JoinColumn(name = "occasion_id",insertable = false ,updatable=false)
    private Occasion occasion;

    public OccasionPic(OccasionPicDto dto,Pic pic){
        this(null,dto.getOccasionId(), dto.isSharable(), dto.getName(), pic.getPicId(),pic,null);
    }

}
