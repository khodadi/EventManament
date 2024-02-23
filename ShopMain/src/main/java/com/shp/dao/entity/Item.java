package com.shp.dao.entity;

import com.shp.service.dto.ItemDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "item",schema = "env_shp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item extends ABaseEntity {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(generator = "item_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "item_seq", allocationSize = 1, sequenceName = "item_seq",schema = "env_shp")
    private Long itemId;
    @Column(name ="item_name")
    private String itemName;
    @Column(name ="count_item")
    private Integer countItem;
    @Column(name ="provider_id")
    private Integer providerId;
    @OneToOne
    @JoinColumn(name = "provider_id",insertable = false ,updatable=false)
    private Provider provider;
    @Column(name = "category_id")
    private Long categoryId;
    @OneToOne
    @JoinColumn(name = "category_id",insertable = false ,updatable=false)
    private Category category;
    @OneToMany
    @JoinColumn(name = "item_id")
    private List<ItemAttribute> itemAttribute;
    public Item(ItemDto dto) {
        this(null,dto.getItemName(), dto.getCountItem(), dto.getProviderId(), null,dto.getCategoryId(),null,null);
    }
}
