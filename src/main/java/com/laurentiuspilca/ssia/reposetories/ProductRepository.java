package com.laurentiuspilca.ssia.reposetories;

import com.laurentiuspilca.ssia.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
