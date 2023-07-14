package com.vi.multipleentity.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="Category")
public class Category {
    @Id
    private Integer categoryId;
    private String categoryName;
}
