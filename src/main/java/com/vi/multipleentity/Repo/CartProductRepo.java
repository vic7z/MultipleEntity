package com.vi.multipleentity.Repo;

import com.vi.multipleentity.Models.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartProductRepo extends JpaRepository<CartProduct,Integer> {
}
