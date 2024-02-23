package com.shp.dao.entity;

import com.shp.service.dto.CartItemDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attribute",schema = "env_shp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItem extends ABaseEntity {
    @Id
    @Column(name = "card_item_id")
    @GeneratedValue(generator = "card_item_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "card_item_seq", allocationSize = 1, sequenceName = "card_item_seq",schema = "env_shp")
    private Long cardItemId;
    @Column(name = "itemid")
    private Long itemId;
    @OneToOne
    @JoinColumn(name = "item_id",insertable = false ,updatable=false)
    private Item item;
    @Column(name ="price")
    private Long price;
    @Column(name = "count_order")
    private Long countOrder;

    public CartItem(CartItemDto dto) {
        this(null,dto.getItemId(),null,dto.getPrice(),dto.getCountOrder());
    }
}
