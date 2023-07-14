package com.vi.multipleentity.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="Cart")
public class Cart {
    @Id
    private Integer cartId;
    private Double totalAmount;
    @OneToOne
    private User user;
    @OneToMany(fetch = FetchType.EAGER)
    private List<CartProduct> cartProduct;
}
