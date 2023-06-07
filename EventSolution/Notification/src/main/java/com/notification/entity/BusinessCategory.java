package com.notification.entity;

import com.infra.entity.ABaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "BUSINESS_CATEGORY" , schema = "NOTIF_USER")
public class BusinessCategory extends ABaseEntity {

    @Id
    @Column(name = "BUSINESS_CATEGORY_ID", nullable = true, insertable = true, updatable = true, length = 10)
    @GeneratedValue(generator = "BUSINESS_CATEGORY_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "BUSINESS_CATEGORY_SEQ", allocationSize = 1, sequenceName = "BUSINESS_CATEGORY_SEQ",schema = "NOTIF_USER")
    private Long businessCategoryId;

    @Basic
    @Column(name = "CATEGORY_CODE", nullable = true, insertable = true,updatable = true,length = 100)
    private Long categoryCode;

    @Basic
    @Column(name = "NAME", nullable = true, insertable = true,updatable = true,length = 255)
    private String name;

    @Basic
    @Column(name = "FARSI_NAME", nullable = true, insertable = true,updatable = true,length = 255)
    private String farsiName;

    @Basic
    @Column(name = "SUB_CATEGORY_CODE", nullable = true, insertable = true,updatable = true,length = 255)
    private Long subCategoryCode;

    public BusinessCategory(Long categoryCode,Long businessCategoryId, String name) {
        this.categoryCode = categoryCode;
        this.businessCategoryId = businessCategoryId;
        this.name = name;
    }
}
