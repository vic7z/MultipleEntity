package com.vi.multipleentity.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="cartProduct")
public class CartProduct {
    @Id
    private Integer cpId;
    @ManyToOne(fetch = FetchType.EAGER)
    private Cart cart;
    @ManyToOne(fetch = FetchType.EAGER)
    private Product product;
    private Integer quantity=1;
}
