package com.shp.dao.entity;

import com.shp.basedata.CartStatus;
import com.shp.service.dto.CartDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "attribute",schema = "env_shp")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends ABaseEntity {
    @Id
    @Column(name = "card_id")
    @GeneratedValue(generator = "card_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "card_seq", allocationSize = 1, sequenceName = "card_seq",schema = "env_shp")
    private Long cardId;
    @Column(name = "state")
    private CartStatus cardStatus;


    public Cart(CartDto dto) {
        this(null,dto.getCartStatus());
    }
}
