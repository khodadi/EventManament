package com.shp.dao.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Creator 2/18/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Entity
@Table(name = "provider",schema = "env_shp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Provider extends ABaseEntity{

    @Id
    @Column(name = "provider_id")
    @GeneratedValue(generator = "provider_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "provider_seq", allocationSize = 1, sequenceName = "provider_seq",schema = "env_shp")
    private Long providerId;
    @Column(name = "shop_name")
    private String shopName;
    @Column(name = "NAME_FA")
    private String nameFa;
    @Column(name = "pic_id")
    private Long picId;
    @Column(name = "category_id")
    private Long categoryId;
    @OneToOne
    @JoinColumn(name = "category_id",insertable = false ,updatable=false)
    private Category category;
    @OneToOne
    @JoinColumn(name = "pic_Id",insertable = false ,updatable=false)
    private Pic pic;
    @OneToMany
    @JoinColumn(name = "provider_id")
    private List<ProviderTrl> providerTrls;
}
