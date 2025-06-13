package com.limingzheng.productsearch.repository;

import com.limingzheng.productsearch.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
