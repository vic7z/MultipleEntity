package com.vi.multipleentity.Repo;

import com.vi.multipleentity.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ProductRepo extends JpaRepository<Product,Integer> {
}
