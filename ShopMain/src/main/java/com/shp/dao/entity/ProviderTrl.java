package com.shp.dao.entity;

import com.shp.service.dto.ProviderDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Creator 2/18/2024
 * @Project IntelliJ IDEA
 * @Author k.khodadi
 **/

@Entity
@Table(name = "provider_trl",schema = "env_shp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderTrl extends ABaseEntity {
    @Id
    @Column(name = "provider_Trl_Id")
    @GeneratedValue(generator = "provider_Trl_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "provider_Trl_seq", allocationSize = 1, sequenceName = "provider_Trl_seq",schema = "env_shp")
    private Long providerTrlId;
    @Column(name = "locale_trl")
    private String localeTrl;
    @Column(name = "provider_trl")
    private String providerTrl;
    @Column(name = "provider_id")
    private Long providerId;
    @OneToOne
    @JoinColumn(name = "provider_id",insertable = false ,updatable=false)
    private Provider provider;

    public ProviderTrl(ProviderDto dto) {
        this.setLocaleTrl(dto.getLocaleTrl());
        this.setProviderId(dto.getProviderId());
        this.setProviderTrl(dto.getShopNameTrl());
    }
}
