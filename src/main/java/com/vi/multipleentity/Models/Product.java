package com.vi.multipleentity.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name="Product")
public class Product {
    @Id
    private Integer productId;
    private String productName;
    private Double price;
    @ManyToOne(fetch = FetchType.EAGER)
    private User Seller;
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;
}
